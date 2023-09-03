package net.nullean.reach.block;

import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;

public class SoulLavaBlock extends LiquidBlock implements BucketPickup
{
    public SoulLavaBlock(java.util.function.Supplier<? extends FlowingFluid> p_54694_, BlockBehaviour.Properties p_54695_) {
        super(p_54694_, p_54695_);
    }
}