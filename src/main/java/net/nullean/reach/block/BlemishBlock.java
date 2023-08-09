package net.nullean.reach.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.nullean.reach.entity.monster.Blemish;
import net.nullean.reach.registry.ReachEntities;
import net.nullean.reach.registry.ReachParticles;

public class BlemishBlock extends Block implements net.minecraftforge.common.IForgeShearable
{
    public BlemishBlock(Properties p_49795_) {
        super(p_49795_);
    }

    public VoxelShape getVisualShape(BlockState p_154276_, BlockGetter p_154277_, BlockPos p_154278_, CollisionContext p_154279_) {
        return Shapes.empty();
    }

    public void animateTick(BlockState p_221969_, Level p_221970_, BlockPos p_221971_, RandomSource p_221972_) {

        if (p_221972_.nextInt(100) == 0) {
            p_221970_.playSound((Player)null, (double)p_221971_.getX() + 0.5D, (double)p_221971_.getY() + 0.5D, (double)p_221971_.getZ() + 0.5D, SoundEvents.RESPAWN_ANCHOR_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        if (p_221972_.nextInt(8) == 1) {
            double d0 = (double)p_221971_.getX() + 0.5D + (0.5D - p_221972_.nextDouble());
            double d1 = (double)p_221971_.getY() + 1.0D;
            double d2 = (double)p_221971_.getZ() + 0.5D + (0.5D - p_221972_.nextDouble());
            double d3 = (double)p_221972_.nextFloat() * 0.04D;
            p_221970_.addParticle(ReachParticles.BLEMISH_BUBBLE.get(), d0, d1, d2, 0.0D, d3, 0.0D);
        }
    }

    public static boolean canEntityWalkOnPowderSnow(Entity p_154256_) {
        if (p_154256_ instanceof Blemish) {
            return true;
        } else if (p_154256_ instanceof LivingEntity) {
            return !((LivingEntity) p_154256_).getItemBySlot(EquipmentSlot.FEET).isEmpty();
        } else {
            return false;
        }
    }

    public void stepOn(Level p_153777_, BlockPos p_153778_, BlockState p_153779_, Entity p_153780_) {
        //p_153780_ = (LivingEntity)p_153780_;

        if (!canEntityWalkOnPowderSnow(p_153780_)) {
            p_153780_.makeStuckInBlock(p_153779_, new Vec3(0.25D, (double)0.05F, 0.25D));
            p_153780_.hurt(DamageSource.WITHER, 2.0F);
        }

        super.stepOn(p_153777_, p_153778_, p_153779_, p_153780_);
    }
}
