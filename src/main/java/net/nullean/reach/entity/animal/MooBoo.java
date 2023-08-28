package net.nullean.reach.entity.animal;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.nullean.reach.registry.ReachBlocks;

public class MooBoo extends Cow
{
    private static final EntityDataAccessor<String> DATA_TYPE = SynchedEntityData.defineId(MooBoo.class, EntityDataSerializers.STRING);

    public MooBoo(EntityType<? extends MooBoo> p_28285_, Level p_28286_) {
        super(p_28285_, p_28286_);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    public float getWalkTargetValue(BlockPos p_28933_, LevelReader p_28934_) {
        return p_28934_.getBlockState(p_28933_.below()).is(ReachBlocks.SOUL_GRASS_BLOCK.get()) ? 10.0F : p_28934_.getPathfindingCostFromLightLevels(p_28933_);
    }

    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    protected static boolean isBrightEnoughToSpawn(BlockAndTintGetter p_186210_, BlockPos p_186211_) {
        return p_186210_.getRawBrightness(p_186211_, 0) > 1;
    }

    public static boolean checkAnimalSpawnRules(EntityType<? extends Animal> p_218105_, LevelAccessor p_218106_, MobSpawnType p_218107_, BlockPos p_218108_, RandomSource p_218109_) {
        return isBrightEnoughToSpawn(p_218106_, p_218108_);
    }

    public static boolean checkMooBooSpawnRules(EntityType<MooBoo> p_218201_, LevelAccessor p_218202_, MobSpawnType p_218203_, BlockPos p_218204_, RandomSource p_218205_) {
        return true;
    }

    public boolean fireImmune() {
        return true;
    }

    public boolean isInvulnerableTo(DamageSource p_20122_) {
        if (p_20122_.isMagic())
        {
            return true;
        }
        if (p_20122_.isFire())
        {
            return true;
        }
        return this.isRemoved() || p_20122_ != DamageSource.OUT_OF_WORLD && !p_20122_.isCreativePlayer();
    }

    public boolean causeFallDamage(float p_148859_, float p_148860_, DamageSource p_148861_) {
        return false;
    }

    public boolean hurt(DamageSource p_30386_, float p_30387_) {
        if (this.isInvulnerableTo(p_30386_)) {
            return false;
        } else {
            Entity entity = p_30386_.getEntity();
            if (!this.level.isClientSide) {
                this.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 60 , 3), this);
            }

            if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
                p_30387_ = (p_30387_ + 1.0F) / 2.0F;
            }

            return super.hurt(p_30386_, p_30387_);
        }
    }
}
