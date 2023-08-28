package net.nullean.reach.item.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.nullean.reach.client.tile.SnagSignTile;
import net.nullean.reach.gui.ReachSignScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SnagSignItem extends StandingAndWallBlockItem {
    public SnagSignItem(Item.Properties properties, Block signBlock, Block wallSignBlock) {
        super(signBlock, wallSignBlock, properties, Direction.DOWN);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(@Nonnull BlockPos blockPos, @Nonnull Level world, @Nullable Player player, @Nonnull ItemStack stack, @Nonnull BlockState blockState) {
        boolean flag = super.updateCustomBlockEntityTag(blockPos, world, player, stack, blockState);
        if (!flag && player != null) {
            if (world.isClientSide) {
                openGui((SnagSignTile) world.getBlockEntity(blockPos));
            }else {
                BlockEntity tile = world.getBlockEntity(blockPos);
                if (tile instanceof SnagSignTile) {
                    ((SnagSignTile) tile).setAllowedPlayerEditor(player);
                }
            }
        }
        return flag;
    }

    @OnlyIn(Dist.CLIENT)
    private void openGui(SnagSignTile tile) {
        Minecraft.getInstance().setScreen(new ReachSignScreen(tile, true));
    }
}