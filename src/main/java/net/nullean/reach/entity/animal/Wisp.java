package net.nullean.reach.entity.animal;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.level.Level;

public class Wisp extends Allay
{
    public Wisp(EntityType<? extends Allay> p_218310_, Level p_218311_) {
        super(p_218310_, p_218311_);
    }

    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    public void aiStep() {
        if (this.level.isClientSide) {
            for(int i = 0; i < 0.1; ++i) {
                this.level.addParticle(ParticleTypes.SOUL_FIRE_FLAME,
                        this.getRandomX(0.5D),
                        this.getRandomY(),
                        this.getRandomZ(0.5D),
                        0.0D, 0.0D, 0.0D);
            }
        }

        super.aiStep();
    }


}
