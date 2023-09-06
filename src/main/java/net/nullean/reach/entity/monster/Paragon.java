package net.nullean.reach.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.nullean.reach.entity.ai.ParagonNavigation;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class Paragon extends FlyingMob implements Enemy {
    private static final EntityDataAccessor<Boolean> DATA_IS_CHARGING = SynchedEntityData.defineId(Paragon.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_ID_ATTACK_TARGET = SynchedEntityData.defineId(Paragon.class, EntityDataSerializers.INT);
    @Nullable
    private LivingEntity clientSideCachedAttackTarget;
    private int clientSideAttackTime;
    private int explosionPower = 1;

    public Paragon(EntityType<? extends Paragon> p_32725_, Level p_32726_) {
        super(p_32725_, p_32726_);
        this.xpReward = 5;
        this.moveControl = new Paragon.ParagonMoveControl(this);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(5, new Paragon.RandomFloatAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Paragon.class, 12.0F, 0.01F));
        this.goalSelector.addGoal(4, new Paragon.ParagonAttackGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, new Paragon.ParagonAttackSelector(this)));

    }

    public boolean isCharging() {
        return this.entityData.get(DATA_IS_CHARGING);
    }

    public void setCharging(boolean p_32759_) {
        this.entityData.set(DATA_IS_CHARGING, p_32759_);
    }

    public int getExplosionPower() {
        return this.explosionPower;
    }

    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    private static boolean isReflectedFireball(DamageSource p_238408_) {
        return p_238408_.getDirectEntity() instanceof LargeFireball && p_238408_.getEntity() instanceof Player;
    }

    public boolean isInvulnerableTo(DamageSource p_238289_) {
        return !isReflectedFireball(p_238289_) && super.isInvulnerableTo(p_238289_);
    }

    public boolean hurt(DamageSource p_32730_, float p_32731_) {
        if (isReflectedFireball(p_32730_)) {
            super.hurt(p_32730_, 1000.0F);
            return true;
        } else {
            return !this.isInvulnerableTo(p_32730_) && super.hurt(p_32730_, p_32731_);
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_CHARGING, false);
        this.entityData.define(DATA_ID_ATTACK_TARGET, 0);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 6.0D).add(Attributes.MOVEMENT_SPEED, 0.5D).add(Attributes.FOLLOW_RANGE, 16.0D).add(Attributes.MAX_HEALTH, 30.0D);
    }

    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.GUARDIAN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_32750_) {
        return SoundEvents.GUARDIAN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.GUARDIAN_DEATH;
    }

    void setActiveAttackTarget(int p_32818_) {
        this.entityData.set(DATA_ID_ATTACK_TARGET, p_32818_);
    }

    public boolean hasActiveAttackTarget() {
        return this.entityData.get(DATA_ID_ATTACK_TARGET) != 0;
    }


    @Nullable
    public LivingEntity getActiveAttackTarget() {
        if (!this.hasActiveAttackTarget()) {
            return null;
        } else if (this.level.isClientSide) {
            if (this.clientSideCachedAttackTarget != null) {
                return this.clientSideCachedAttackTarget;
            } else {
                Entity entity = this.level.getEntity(this.entityData.get(DATA_ID_ATTACK_TARGET));
                if (entity instanceof LivingEntity) {
                    this.clientSideCachedAttackTarget = (LivingEntity)entity;
                    return this.clientSideCachedAttackTarget;
                } else {
                    return null;
                }
            }
        } else {
            return this.getTarget();
        }
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> p_32834_) {
        super.onSyncedDataUpdated(p_32834_);
        if (DATA_ID_ATTACK_TARGET.equals(p_32834_)) {
            this.clientSideAttackTime = 0;
            this.clientSideCachedAttackTarget = null;
        }

    }

    public int getAttackDuration() {
        return 80;
    }

    protected float getSoundVolume() {
        return 5.0F;
    }

    public static boolean checkParagonSpawnRules(EntityType<Paragon> p_218985_, LevelAccessor p_218986_, MobSpawnType p_218987_, BlockPos p_218988_, RandomSource p_218989_) {
        return p_218986_.getDifficulty() != Difficulty.PEACEFUL && p_218989_.nextInt(20) == 0 && checkMobSpawnRules(p_218985_, p_218986_, p_218987_, p_218988_, p_218989_);
    }

    public int getMaxSpawnClusterSize() {
        return 1;
    }

    public void addAdditionalSaveData(CompoundTag p_32744_) {
        super.addAdditionalSaveData(p_32744_);
        p_32744_.putByte("ExplosionPower", (byte)this.explosionPower);
    }

    public void readAdditionalSaveData(CompoundTag p_32733_) {
        super.readAdditionalSaveData(p_32733_);
        if (p_32733_.contains("ExplosionPower", 99)) {
            this.explosionPower = p_32733_.getByte("ExplosionPower");
        }

    }

    protected float getStandingEyeHeight(Pose p_32741_, EntityDimensions p_32742_) {
        return 2.6F;
    }


    public float getAttackAnimationScale(float p_32813_) {
        return ((float)this.clientSideAttackTime + p_32813_) / (float)this.getAttackDuration();
    }

    public void aiStep() {
        if (this.isAlive()) {
            if (this.level.isClientSide) {
                this.clientSideSpikesAnimationO = this.clientSideSpikesAnimation;
                this.clientSideSpikesAnimation += (0.0F - this.clientSideSpikesAnimation) * 0.25F;

                if (this.hasActiveAttackTarget()) {
                    if (this.clientSideAttackTime < this.getAttackDuration()) {
                        ++this.clientSideAttackTime;
                    }

                    LivingEntity livingentity = this.getActiveAttackTarget();
                    if (livingentity != null) {
                        this.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
                        this.getLookControl().tick();
                        double d5 = (double)this.getAttackAnimationScale(0.0F);
                        double d0 = livingentity.getX() - this.getX();
                        double d1 = livingentity.getY(0.5D) - this.getEyeY();
                        double d2 = livingentity.getZ() - this.getZ();
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        double d4 = this.random.nextDouble();

                        while(d4 < d3) {
                            d4 += 1.8D - d5 + this.random.nextDouble() * (1.7D - d5);
                            this.level.addParticle(ParticleTypes.BUBBLE, this.getX() + d0 * d4, this.getEyeY() + d1 * d4, this.getZ() + d2 * d4, 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            }
            if (this.hasActiveAttackTarget()) {
                this.setYRot(this.yHeadRot);
            }
        }

        super.aiStep();
    }


    public int getMaxHeadXRot() {
        return 180;
    }

    private float clientSideSpikesAnimation;
    private float clientSideSpikesAnimationO;
    public float getSpikesAnimation(float p_32866_) {
        return Mth.lerp(p_32866_, this.clientSideSpikesAnimationO, this.clientSideSpikesAnimation);
    }
    
    static class ParagonLookGoal extends Goal {
        private final Paragon Paragon;

        public ParagonLookGoal(Paragon p_32762_) {
            this.Paragon = p_32762_;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return true;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            if (this.Paragon.getTarget() == null) {
                Vec3 vec3 = this.Paragon.getDeltaMovement();
                this.Paragon.setYRot(-((float)Mth.atan2(vec3.x, vec3.z)) * (180F / (float)Math.PI));
                this.Paragon.yBodyRot = this.Paragon.getYRot();
            } else {
                LivingEntity livingentity = this.Paragon.getTarget();
                double d0 = 64.0D;
                if (livingentity.distanceToSqr(this.Paragon) < 4096.0D) {
                    double d1 = livingentity.getX() - this.Paragon.getX();
                    double d2 = livingentity.getZ() - this.Paragon.getZ();
                    this.Paragon.setYRot(-((float)Mth.atan2(d1, d2)) * (180F / (float)Math.PI));
                    this.Paragon.yBodyRot = this.Paragon.getYRot();
                }
            }

        }
    }

    static class ParagonMoveControl extends MoveControl {
        private final Paragon Paragon;
        private int floatDuration;

        public ParagonMoveControl(Paragon p_32768_) {
            super(p_32768_);
            this.Paragon = p_32768_;
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                if (this.floatDuration-- <= 0) {
                    this.floatDuration += this.Paragon.getRandom().nextInt(5) + 2;
                    Vec3 vec3 = new Vec3(this.wantedX - this.Paragon.getX(), this.wantedY - this.Paragon.getY(), this.wantedZ - this.Paragon.getZ());
                    double d0 = vec3.length();
                    vec3 = vec3.normalize();
                    if (this.canReach(vec3, Mth.ceil(d0))) {
                        this.Paragon.setDeltaMovement(this.Paragon.getDeltaMovement().add(vec3.scale(0.1D)));
                    } else {
                        this.operation = MoveControl.Operation.WAIT;
                    }
                }

            }
        }

        private boolean canReach(Vec3 p_32771_, int p_32772_) {
            AABB aabb = this.Paragon.getBoundingBox();

            for(int i = 1; i < p_32772_; ++i) {
                aabb = aabb.move(p_32771_);
                if (!this.Paragon.level.noCollision(this.Paragon, aabb)) {
                    return false;
                }
            }

            return true;
        }
    }

    static class ParagonShootFireballGoal extends Goal {
        private final Paragon Paragon;
        public int chargeTime;

        public ParagonShootFireballGoal(Paragon p_32776_) {
            this.Paragon = p_32776_;
        }

        public boolean canUse() {
            return this.Paragon.getTarget() != null;
        }

        public void start() {
            this.chargeTime = 0;
        }

        public void stop() {
            this.Paragon.setCharging(false);
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingentity = this.Paragon.getTarget();
            if (livingentity != null) {
                double d0 = 64.0D;
                if (livingentity.distanceToSqr(this.Paragon) < 4096.0D && this.Paragon.hasLineOfSight(livingentity)) {
                    Level level = this.Paragon.level;
                    ++this.chargeTime;
                    if (this.chargeTime == 10 && !this.Paragon.isSilent()) {
                        level.levelEvent((Player)null, 1015, this.Paragon.blockPosition(), 0);
                    }

                    if (this.chargeTime == 20) {
                        double d1 = 4.0D;
                        Vec3 vec3 = this.Paragon.getViewVector(1.0F);
                        double d2 = livingentity.getX() - (this.Paragon.getX() + vec3.x * 4.0D);
                        double d3 = livingentity.getY(0.5D) - (0.5D + this.Paragon.getY(0.5D));
                        double d4 = livingentity.getZ() - (this.Paragon.getZ() + vec3.z * 4.0D);
                        if (!this.Paragon.isSilent()) {
                            level.levelEvent((Player)null, 1016, this.Paragon.blockPosition(), 0);
                        }

                        LargeFireball largefireball = new LargeFireball(level, this.Paragon, d2, d3, d4, this.Paragon.getExplosionPower());
                        largefireball.setPos(this.Paragon.getX() + vec3.x * 4.0D, this.Paragon.getY(0.5D) + 0.5D, largefireball.getZ() + vec3.z * 4.0D);
                        level.addFreshEntity(largefireball);
                        this.chargeTime = -40;
                    }
                } else if (this.chargeTime > 0) {
                    --this.chargeTime;
                }

                this.Paragon.setCharging(this.chargeTime > 10);
            }
        }
    }

    static class RandomFloatAroundGoal extends Goal {
        private final Paragon Paragon;

        public RandomFloatAroundGoal(Paragon p_32783_) {
            this.Paragon = p_32783_;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            MoveControl movecontrol = this.Paragon.getMoveControl();
            if (!movecontrol.hasWanted()) {
                return true;
            } else {
                double d0 = movecontrol.getWantedX() - this.Paragon.getX();
                double d1 = movecontrol.getWantedY() - this.Paragon.getY();
                double d2 = movecontrol.getWantedZ() - this.Paragon.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void start() {
            RandomSource randomsource = this.Paragon.getRandom();
            double d0 = this.Paragon.getX() + (double)((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.Paragon.getY() + (double)((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d2 = this.Paragon.getZ() + (double)((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.Paragon.getMoveControl().setWantedPosition(d0, d1, d2, 1.0D);
        }
    }

    static class ParagonAttackGoal extends Goal {
        private final Paragon guardian;
        private int attackTime;

        public ParagonAttackGoal(Paragon p_32871_) {
            this.guardian = p_32871_;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            LivingEntity livingentity = this.guardian.getTarget();
            return livingentity != null && livingentity.isAlive();
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse() && (this.guardian.getTarget() != null && this.guardian.distanceToSqr(this.guardian.getTarget()) > 9.0D);
        }

        public void start() {
            this.attackTime = -10;
            this.guardian.getNavigation().stop();
            LivingEntity livingentity = this.guardian.getTarget();
            if (livingentity != null) {
                this.guardian.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
            }

            this.guardian.hasImpulse = true;
        }

        public void stop() {
            this.guardian.setActiveAttackTarget(0);
            this.guardian.setTarget((LivingEntity)null);
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingentity = this.guardian.getTarget();
            if (livingentity != null) {
                this.guardian.getNavigation().stop();
                this.guardian.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
                if (!this.guardian.hasLineOfSight(livingentity)) {
                    this.guardian.setTarget((LivingEntity)null);
                } else {
                    ++this.attackTime;
                    if (this.attackTime == 0) {
                        this.guardian.setActiveAttackTarget(livingentity.getId());
                        if (!this.guardian.isSilent()) {
                            this.guardian.level.broadcastEntityEvent(this.guardian, (byte)61);
                        }
                    } else if (this.attackTime >= this.guardian.getAttackDuration()) {
                        float f = 1.0F;
                        if (this.guardian.level.getDifficulty() == Difficulty.HARD) {
                            f += 2.0F;
                        }

                        f += 2.0F;


                        livingentity.hurt(DamageSource.indirectMagic(this.guardian, this.guardian), f);
                        livingentity.hurt(DamageSource.mobAttack(this.guardian), (float)this.guardian.getAttributeValue(Attributes.ATTACK_DAMAGE));
                        this.guardian.setTarget((LivingEntity)null);
                    }

                    super.tick();
                }
            }
        }
    }

    static class ParagonAttackSelector implements Predicate<LivingEntity> {
        private final Paragon guardian;

        public ParagonAttackSelector(Paragon p_32879_) {
            this.guardian = p_32879_;
        }

        public boolean test(@Nullable LivingEntity p_32881_) {
            return (p_32881_ instanceof Player || p_32881_ instanceof Squid || p_32881_ instanceof Axolotl) && p_32881_.distanceToSqr(this.guardian) > 9.0D;
        }
    }
}