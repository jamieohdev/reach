package net.nullean.reach.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.nullean.reach.registry.ReachParticles;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.UUID;

public class Spreader extends FlyingMob implements NeutralMob {

    Spreader Spreader;
    
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;
    Vec3 moveTargetPoint = Vec3.ZERO;
    BlockPos anchorPoint = BlockPos.ZERO;

    public Spreader(EntityType<? extends FlyingMob> p_20806_, Level p_20807_) {
        super(p_20806_, p_20807_);
        this.moveControl = new Spreader.SpreaderMoveControl(this);
        this.xpReward = 5;
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.FLYING_SPEED, (double)0.07F).add(Attributes.MOVEMENT_SPEED, (double)0.07F).add(Attributes.ATTACK_DAMAGE, 2.0D).add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(5, new Spreader.RandomFloatAroundGoal(this));
        this.goalSelector.addGoal(7, new Spreader.SpreaderLookGoal(this));
    }
    
    protected BodyRotationControl createBodyControl() {
        return new Spreader.SpreaderBodyRotationControl(this);
    }

    public boolean isPushable() {
        return false;
    }

    protected void doPush(Entity p_27415_) {
    }

    protected void pushEntities() {
    }

    public boolean shouldRenderAtSqrDistance(double p_33107_) {
        return true;
    }

    public void tick() {
        super.tick();
        this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
    }

    public void aiStep() {
        if (this.level.isClientSide) {
            if (this.random.nextInt(24) == 0 && !this.isSilent()) {
                this.level.playLocalSound(this.getX() + 0.5D, this.getY() + 0.5D, this.getZ() + 0.5D, SoundEvents.MAGMA_CUBE_SQUISH_SMALL, this.getSoundSource(), 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F, false);
            }

            for(int i = 0; i < 2; ++i) {
                this.level.addParticle(ReachParticles.BLEMISH_SPORE.get(),
                        this.getRandomX(0.5D),
                        this.getRandomY(),
                        this.getRandomZ(0.5D),
                        0.0D, 0.0D, 0.0D);
            }
        }

        this.jumping = false;
        if (!this.level.isClientSide) {
            this.updatePersistentAnger((ServerLevel)this.level, true);
        }

        super.aiStep();
    }


    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
    }

    protected float getStandingEyeHeight(Pose p_218356_, EntityDimensions p_218357_) {
        return p_218357_.height * 0.6F;
    }

    public boolean causeFallDamage(float p_218321_, float p_218322_, DamageSource p_218323_) {
        return false;
    }

    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    protected void playStepSound(BlockPos p_218364_, BlockState p_218365_) {
    }

    protected void checkFallDamage(double p_218316_, boolean p_218317_, BlockState p_218318_, BlockPos p_218319_) {
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    public boolean isFlapping() {
        return !this.isOnGround();
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    public void setRemainingPersistentAngerTime(int p_29543_) {
        this.remainingPersistentAngerTime = p_29543_;
    }

    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    public void setPersistentAngerTarget(@Nullable UUID p_29539_) {
        this.persistentAngerTarget = p_29539_;
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public static <T extends Mob> boolean checkSpreaderSpawnRules(EntityType<T> tEntityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        return true;
    }

    class SpreaderBodyRotationControl extends BodyRotationControl {
        public SpreaderBodyRotationControl(Mob p_33216_) {
            super(p_33216_);
        }

        public void clientTick() {
            Spreader.this.yHeadRot = Spreader.this.yBodyRot;
            Spreader.this.yBodyRot = Spreader.this.getYRot();
        }
    }

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

    static class SpreaderMoveControl extends MoveControl {
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
