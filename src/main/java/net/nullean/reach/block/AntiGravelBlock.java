package net.nullean.reach.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class AntiGravelBlock extends RisingBlock {
    public AntiGravelBlock(BlockBehaviour.Properties p_53736_) {
        super(p_53736_);
    }

    public int getDustColor(BlockState p_53738_, BlockGetter p_53739_, BlockPos p_53740_) {
        return -8356741;
    }
}
