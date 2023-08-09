package net.nullean.reach.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.nullean.reach.entity.proejctile.SpreaderBlast;

import java.util.EnumSet;

public class Spreader  extends FlyingMob implements Enemy {
    public float xBodyRot;
    public float xBodyRotO;
    public float zBodyRot;
    public float zBodyRotO;
    public float tentacleMovement;
    public float oldTentacleMovement;
    public float tentacleAngle;
    public float oldTentacleAngle;
    private float speed;
    private float tentacleSpeed;
    private float rotateSpeed;
    private float tx;
    private float ty;
    private float tz;

    public Spreader(EntityType<? extends FlyingMob> p_20806_, Level p_20807_) {
        super(p_20806_, p_20807_);
        this.random.setSeed((long)this.getId());
        this.tentacleSpeed = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
        this.xpReward = 5;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new Spreader.SpreaderMoveControl(this));
        this.goalSelector.addGoal(5, new Spreader.RandomFloatAroundGoal(this));
        this.goalSelector.addGoal(7, new Spreader.SpreaderLookGoal(this));
        this.goalSelector.addGoal(7, new Spreader.SpreaderShootFireballGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (p_253576_) -> {
            return Math.abs(p_253576_.getY() - this.getY()) <= 4.0D;
        }));
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D);
    }

    public void aiStep() {
        super.aiStep();
        this.xBodyRotO = this.xBodyRot;
        this.zBodyRotO = this.zBodyRot;
        this.oldTentacleMovement = this.tentacleMovement;
        this.oldTentacleAngle = this.tentacleAngle;
        this.tentacleMovement += this.tentacleSpeed;
        if ((double)this.tentacleMovement > (Math.PI * 2D)) {
            if (this.level.isClientSide) {
                this.tentacleMovement = ((float)Math.PI * 2F);
            } else {
                this.tentacleMovement -= ((float)Math.PI * 2F);
                if (this.random.nextInt(10) == 0) {
                    this.tentacleSpeed = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
                }

                this.level.broadcastEntityEvent(this, (byte)19);
            }
        }

        if (!this.isInWaterOrBubble()) {
            if (this.tentacleMovement < (float)Math.PI) {
                float f = this.tentacleMovement / (float)Math.PI;
                this.tentacleAngle = Mth.sin(f * f * (float)Math.PI) * (float)Math.PI * 0.25F;
                if ((double)f > 0.75D) {
                    this.speed = 1.0F;
                    this.rotateSpeed = 1.0F;
                } else {
                    this.rotateSpeed *= 0.8F;
                }
            } else {
                this.tentacleAngle = 0.0F;
                this.speed *= 0.9F;
                this.rotateSpeed *= 0.99F;
            }

            if (!this.level.isClientSide) {
                this.setDeltaMovement((double)(this.tx * this.speed), (double)(this.ty * this.speed), (double)(this.tz * this.speed));
            }

            Vec3 vec3 = this.getDeltaMovement();
            double d0 = vec3.horizontalDistance();
            this.yBodyRot += (-((float)Mth.atan2(vec3.x, vec3.z)) * (180F / (float)Math.PI) - this.yBodyRot) * 0.1F;
            this.setYRot(this.yBodyRot);
            this.zBodyRot += (float)Math.PI * this.rotateSpeed * 1.5F;
            this.xBodyRot += (-((float)Mth.atan2(d0, vec3.y)) * (180F / (float)Math.PI) - this.xBodyRot) * 0.1F;
        } else {
            this.tentacleAngle = Mth.abs(Mth.sin(this.tentacleMovement)) * (float)Math.PI * 0.25F;
            if (!this.level.isClientSide) {
                double d1 = this.getDeltaMovement().y;
                if (this.hasEffect(MobEffects.LEVITATION)) {
                    d1 = 0.05D * (double)(this.getEffect(MobEffects.LEVITATION).getAmplifier() + 1);
                } else if (!this.isNoGravity()) {
                    d1 -= 0.08D;
                }

                this.setDeltaMovement(0.0D, d1 * (double)0.98F, 0.0D);
            }

            this.xBodyRot += (-90.0F - this.xBodyRot) * 0.02F;
        }

    }

    protected SoundEvent getSquirtSound() {
        return SoundEvents.SQUID_SQUIRT;
    }

    private void spawnInk() {
        this.playSound(this.getSquirtSound(), this.getSoundVolume(), this.getVoicePitch());
        Vec3 vec3 = this.rotateVector(new Vec3(0.0D, -1.0D, 0.0D)).add(this.getX(), this.getY(), this.getZ());

        for(int i = 0; i < 30; ++i) {
            Vec3 vec31 = this.rotateVector(new Vec3((double)this.random.nextFloat() * 0.6D - 0.3D, -1.0D, (double)this.random.nextFloat() * 0.6D - 0.3D));
            Vec3 vec32 = vec31.scale(0.3D + (double)(this.random.nextFloat() * 2.0F));
            ((ServerLevel)this.level).sendParticles(this.getInkParticle(), vec3.x, vec3.y + 0.5D, vec3.z, 0, vec32.x, vec32.y, vec32.z, (double)0.1F);
        }

    }
    
    private Vec3 rotateVector(Vec3 p_29986_) {
        Vec3 vec3 = p_29986_.xRot(this.xBodyRotO * ((float)Math.PI / 180F));
        return vec3.yRot(-this.yBodyRotO * ((float)Math.PI / 180F));
    }

    protected ParticleOptions getInkParticle() {
        return ParticleTypes.SQUID_INK;
    }

    public void travel(Vec3 p_29984_) {
        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    public void handleEntityEvent(byte p_29957_) {
        if (p_29957_ == 19) {
            this.tentacleMovement = 0.0F;
        } else {
            super.handleEntityEvent(p_29957_);
        }

    }

    public void setMovementVector(float p_29959_, float p_29960_, float p_29961_) {
        this.tx = p_29959_;
        this.ty = p_29960_;
        this.tz = p_29961_;
    }

    public boolean hasMovementVector() {
        return this.tx != 0.0F || this.ty != 0.0F || this.tz != 0.0F;
    }
    
    public boolean isCharging() {
        return false;
    }

    public static boolean checkSpreaderSpawnRules(EntityType<Spreader> p_218985_, LevelAccessor p_218986_, MobSpawnType p_218987_, BlockPos p_218988_, RandomSource p_218989_) {
        return p_218986_.getDifficulty() != Difficulty.PEACEFUL && p_218989_.nextInt(20) == 0 && checkMobSpawnRules(p_218985_, p_218986_, p_218987_, p_218988_, p_218989_);
    }

 /*   static class SpreaderMoveControl extends MoveControl {
        private final Spreader Spreader;
        private int floatDuration;

        public SpreaderMoveControl(Spreader p_32768_) {
            super(p_32768_);
            this.Spreader = p_32768_;
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                if (this.floatDuration-- <= 0) {
                    this.floatDuration += this.Spreader.getRandom().nextInt(5) + 2;
                    Vec3 vec3 = new Vec3(this.wantedX - this.Spreader.getX(), this.wantedY - this.Spreader.getY(), this.wantedZ - this.Spreader.getZ());
                    double d0 = vec3.length();
                    vec3 = vec3.normalize();
                    if (this.canReach(vec3, Mth.ceil(d0))) {
                        this.Spreader.setDeltaMovement(this.Spreader.getDeltaMovement().add(vec3.scale(0.1D)));
                    } else {
                        this.operation = MoveControl.Operation.WAIT;
                    }
                }

            }
        }

        private boolean canReach(Vec3 p_32771_, int p_32772_) {
            AABB aabb = this.Spreader.getBoundingBox();

            for(int i = 1; i < p_32772_; ++i) {
                aabb = aabb.move(p_32771_);
                if (!this.Spreader.level.noCollision(this.Spreader, aabb)) {
                    return false;
                }
            }

            return true;
        }
    }
*/

    static class SpreaderLookGoal extends Goal {
        private final Spreader Spreader;

        public SpreaderLookGoal(Spreader p_32762_) {
            this.Spreader = p_32762_;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return true;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            if (this.Spreader.getTarget() == null) {
                Vec3 vec3 = this.Spreader.getDeltaMovement();
                this.Spreader.setYRot(-((float)Mth.atan2(vec3.x, vec3.z)) * (180F / (float)Math.PI));
                this.Spreader.yBodyRot = this.Spreader.getYRot();
            } else {
                LivingEntity livingentity = this.Spreader.getTarget();
                double d0 = 64.0D;
                if (livingentity.distanceToSqr(this.Spreader) < 4096.0D) {
                    double d1 = livingentity.getX() - this.Spreader.getX();
                    double d2 = livingentity.getZ() - this.Spreader.getZ();
                    this.Spreader.setYRot(-((float)Mth.atan2(d1, d2)) * (180F / (float)Math.PI));
                    this.Spreader.yBodyRot = this.Spreader.getYRot();
                }
            }

        }
    }
    
    class SpreaderMoveControl extends Goal {
        private final Spreader squid;

        public SpreaderMoveControl(Spreader p_30004_) {
            this.squid = p_30004_;
        }

        public boolean canUse() {
            return true;
        }

        public void tick() {
            int i = this.squid.getNoActionTime();
            if (i > 100) {
                this.squid.setMovementVector(0.0F, 0.0F, 0.0F);
            } else if (this.squid.getRandom().nextInt(reducedTickDelay(50)) == 0 || this.squid.wasTouchingWater || !this.squid.hasMovementVector()) {
                float f = this.squid.getRandom().nextFloat() * ((float)Math.PI * 2F);
                float f1 = Mth.cos(f) * 0.2F;
                float f2 = -0.1F + this.squid.getRandom().nextFloat() * 0.2F;
                float f3 = Mth.sin(f) * 0.2F;
                this.squid.setMovementVector(f1, f2, f3);
            }

        }
    }
    
    static class SpreaderShootFireballGoal extends Goal {
        private final Spreader Spreader;
        public int chargeTime;

        public SpreaderShootFireballGoal(Spreader p_32776_) {
            this.Spreader = p_32776_;
        }

        public boolean canUse() {
            return this.Spreader.getTarget() != null;
        }

        public void start() {
            this.chargeTime = 0;
        }

        public void stop() {
            this.Spreader.setCharging(false);
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingentity = this.Spreader.getTarget();
            if (livingentity != null) {
                double d0 = 64.0D;
                if (livingentity.distanceToSqr(this.Spreader) < 4096.0D && this.Spreader.hasLineOfSight(livingentity)) {
                    Level level = this.Spreader.level;
                    ++this.chargeTime;
                    if (this.chargeTime == 10 && !this.Spreader.isSilent()) {
                        level.levelEvent((Player)null, 1015, this.Spreader.blockPosition(), 0);
                    }

                    if (this.chargeTime == 20) {
                        double d1 = 4.0D;
                        Vec3 vec3 = this.Spreader.getViewVector(1.0F);
                        double d2 = livingentity.getX() - (this.Spreader.getX() + vec3.x * 4.0D);
                        double d3 = livingentity.getY(0.5D) - (0.5D + this.Spreader.getY(0.5D));
                        double d4 = livingentity.getZ() - (this.Spreader.getZ() + vec3.z * 4.0D);
                        if (!this.Spreader.isSilent()) {
                            //level.levelEvent((Player)null, 1016, this.Spreader.blockPosition(), 0);
                        }

                        SpreaderBlast largefireball = new SpreaderBlast(level, this.Spreader, d2, d3, d4, this.Spreader.getExplosionPower());
                        largefireball.setPos(this.Spreader.getX() + vec3.x * 4.0D, this.Spreader.getY(0.5D) + 0.5D, largefireball.getZ() + vec3.z * 4.0D);
                        level.addFreshEntity(largefireball);
                        this.chargeTime = -40;
                    }
                } else if (this.chargeTime > 0) {
                    --this.chargeTime;
                }

                this.Spreader.setCharging(this.chargeTime > 10);
            }
        }
    }

    private int getExplosionPower() {
        return 1;
    }

    private void setCharging(boolean b) {
    }

    static class RandomFloatAroundGoal extends Goal {
        private final Spreader Spreader;

        public RandomFloatAroundGoal(Spreader p_32783_) {
            this.Spreader = p_32783_;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            MoveControl movecontrol = this.Spreader.getMoveControl();
            if (!movecontrol.hasWanted()) {
                return true;
            } else {
                double d0 = movecontrol.getWantedX() - this.Spreader.getX();
                double d1 = movecontrol.getWantedY() - this.Spreader.getY();
                double d2 = movecontrol.getWantedZ() - this.Spreader.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void start() {
            RandomSource randomsource = this.Spreader.getRandom();
            double d0 = this.Spreader.getX() + (double)((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.Spreader.getY() + (double)((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d2 = this.Spreader.getZ() + (double)((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.Spreader.getMoveControl().setWantedPosition(d0, d1, d2, 1.0D);
        }
    }
}
