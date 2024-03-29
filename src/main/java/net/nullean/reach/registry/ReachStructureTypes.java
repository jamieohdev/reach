package net.nullean.reach.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.nullean.reach.Reach;
import net.nullean.reach.world.structures.BlemishStructures;
import net.nullean.reach.world.structures.CalmStructures;
import net.nullean.reach.world.structures.CloudStructure;

public class ReachStructureTypes {

    /**
     * We are using the Deferred Registry system to register our structure as this is the preferred way on Forge.
     * This will handle registering the base structure for us at the correct time so we don't have to handle it ourselves.
     */
    public static final DeferredRegister<StructureType<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(Registries.STRUCTURE_TYPE, Reach.MOD_ID);

    /**
     * Registers the base structure itself and sets what its path is. In this case,
     * this base structure will have the resourcelocation of structure_tutorial:sky_structures.
     */
    public static final RegistryObject<StructureType<BlemishStructures>> BLEMISH_STRUCTURES = DEFERRED_REGISTRY_STRUCTURE.register("blemish_ruin", () -> explicitStructureTypeTyping(BlemishStructures.CODEC));
    public static final RegistryObject<StructureType<CalmStructures>> CALM_STRUCTURES = DEFERRED_REGISTRY_STRUCTURE.register("calm_ruin", () -> explicitStructureTypeTyping(CalmStructures.CODEC));
    public static final RegistryObject<StructureType<CloudStructure>> LARGE_CLOUD = DEFERRED_REGISTRY_STRUCTURE.register("large_cloud", () -> explicitStructureTypeTyping(CloudStructure.CODEC));

    /**
     * Originally, I had a double lambda ()->()-> for the RegistryObject line above, but it turns out that
     * some IDEs cannot resolve the typing correctly. This method explicitly states what the return type
     * is so that the IDE can put it into the DeferredRegistry properly.
     */
    private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(Codec<T> structureCodec) {
        return () -> structureCodec;
    }
}
