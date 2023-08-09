package net.nullean.reach.entity.proejctile;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.nullean.reach.entity.monster.Spreader;
import net.nullean.reach.registry.ReachBlocks;
import net.nullean.reach.registry.ReachEntities;

public class SpreaderBlast extends AbstractHurtingProjectile implements ItemSupplier {
    private int explosionPower = 1;
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(Fireball.class, EntityDataSerializers.ITEM_STACK);

    public SpreaderBlast(EntityType<SpreaderBlast> entityEntityType, Level level) {
        super(entityEntityType, level);
    }

    public SpreaderBlast(Level p_181151_, LivingEntity p_181152_, double p_181153_, double p_181154_, double p_181155_, int p_181156_) {
        super(EntityType.FIREBALL, p_181152_, p_181153_, p_181154_, p_181155_, p_181151_);
        this.explosionPower = p_181156_;
    }

    public void setItem(ItemStack p_37011_) {
        if (!p_37011_.is(Items.DIORITE) || p_37011_.hasTag()) {
            this.getEntityData().set(DATA_ITEM_STACK, Util.make(p_37011_.copy(), (p_37015_) -> {
                p_37015_.setCount(1);
            }));
        }

    }

    protected ItemStack getItemRaw() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    public ItemStack getItem() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(Items.DIORITE) : itemstack;
    }

    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

    public void addAdditionalSaveData(CompoundTag p_37013_) {
        super.addAdditionalSaveData(p_37013_);
        ItemStack itemstack = this.getItemRaw();
        if (!itemstack.isEmpty()) {
            p_37013_.put("Item", itemstack.save(new CompoundTag()));
        }

    }

    public void readAdditionalSaveData(CompoundTag p_37009_) {
        super.readAdditionalSaveData(p_37009_);
        ItemStack itemstack = ItemStack.of(p_37009_.getCompound("Item"));
        this.setItem(itemstack);
    }

    protected void onHitEntity(EntityHitResult p_37386_) {
        super.onHitEntity(p_37386_);
        if (!this.level.isClientSide) {
            Entity entity = p_37386_.getEntity();
            Entity entity1 = this.getOwner();
            int i = entity.getRemainingFireTicks();
            entity.hurt(DamageSource.indirectMagic(this, entity), 8.0F);
            if (!entity.hurt(DamageSource.indirectMagic(this, entity1), 8.0F)) {
                entity.setRemainingFireTicks(i);
            } else if (entity1 instanceof LivingEntity) {
                this.doEnchantDamageEffects((LivingEntity)entity1, entity);
            }

        }
    }

    protected void onHitBlock(BlockHitResult p_36755_) {
        super.onHitBlock(p_36755_);
        if (!this.level.isClientSide) {
            Entity entity = this.getOwner();
            if (!(entity instanceof Mob) || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, entity)) {

                BlockState blockstate = ReachBlocks.BLEMISH_BLOCK.get().defaultBlockState();
                BlockPos blockpos = p_36755_.getBlockPos().relative(p_36755_.getDirection());

                if (this.level.getBlockState(blockpos).isAir() && blockstate.canSurvive(this.level, blockpos)) {
                    this.level.setBlockAndUpdate(blockpos, blockstate);
                }

                for(int i = 0; i < 4; ++i) {
                    BlockPos blockpos1 = blockpos.offset(this.random.nextInt(3) - 1, this.random.nextInt(3) - 1, this.random.nextInt(3) - 1);
                    blockstate = BaseFireBlock.getState(this.level, blockpos1);
                    if (this.level.getBlockState(blockpos1).isAir() && blockstate.canSurvive(this.level, blockpos1)) {
                        this.level.setBlockAndUpdate(blockpos1, ReachBlocks.BLEMISH_BLOCK.get().defaultBlockState());
                        this.level.setSkyFlashTime(2);
                    }
                }
            }
        }
    }

    protected void onHit(HitResult p_37388_) {
        super.onHit(p_37388_);
        if (!this.level.isClientSide) {
            this.discard();
        }

    }

    public boolean isPickable() {
        return false;
    }

    public boolean hurt(DamageSource p_37381_, float p_37382_) {
        return false;
    }
}

