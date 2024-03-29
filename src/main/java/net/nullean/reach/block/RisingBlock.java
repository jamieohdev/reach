package net.nullean.reach.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.nullean.reach.entity.block.RisingBlockEntity;

public class RisingBlock extends Block implements Fallable {
    public RisingBlock(BlockBehaviour.Properties p_53205_) {
        super(p_53205_);
    }

    public void onPlace(BlockState p_53233_, Level p_53234_, BlockPos p_53235_, BlockState p_53236_, boolean p_53237_) {
        p_53234_.scheduleTick(p_53235_, this, this.getDelayAfterPlace());
    }

    public BlockState updateShape(BlockState p_53226_, Direction p_53227_, BlockState p_53228_, LevelAccessor p_53229_, BlockPos p_53230_, BlockPos p_53231_) {
        p_53229_.scheduleTick(p_53230_, this, this.getDelayAfterPlace());
        return super.updateShape(p_53226_, p_53227_, p_53228_, p_53229_, p_53230_, p_53231_);
    }

    public void tick(BlockState p_221124_, ServerLevel p_221125_, BlockPos p_221126_, RandomSource p_221127_) {
        if (isFree(p_221125_.getBlockState(p_221126_.below())) && p_221126_.getY() >= p_221125_.getMinBuildHeight()) {
            RisingBlockEntity Risingblockentity = RisingBlockEntity.fall(p_221125_, p_221126_, p_221124_);
            this.rising(Risingblockentity);
        }
    }

    protected void rising(RisingBlockEntity p_53206_) {
    }

    protected int getDelayAfterPlace() {
        return 2;
    }

    public static boolean isFree(BlockState p_53242_) {
        Material material = p_53242_.getMaterial();
        return p_53242_.isAir() || p_53242_.is(BlockTags.FIRE) || material.isLiquid() || p_53242_.canBeReplaced();
    }

    public void animateTick(BlockState p_221129_, Level p_221130_, BlockPos p_221131_, RandomSource p_221132_) {
        if (p_221132_.nextInt(16) == 0) {
            BlockPos blockpos = p_221131_.below();
            if (isFree(p_221130_.getBlockState(blockpos))) {
                double d0 = (double)p_221131_.getX() + p_221132_.nextDouble();
                double d1 = (double)p_221131_.getY() + 0.05D;
                double d2 = (double)p_221131_.getZ() + p_221132_.nextDouble();
                p_221130_.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, p_221129_), d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }

    }

    public int getDustColor(BlockState p_53238_, BlockGetter p_53239_, BlockPos p_53240_) {
        return -16777216;
    }
}