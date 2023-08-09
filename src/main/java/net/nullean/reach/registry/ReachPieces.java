package net.nullean.reach.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.nullean.reach.Reach;
import net.nullean.reach.world.structures.BlemishRuinStructurePieces;

public class ReachPieces
{
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, Reach.MOD_ID);

    public static final RegistryObject<StructurePieceType> BLEMISH_RUIN_PIECE = setTemplatePieceId(BlemishRuinStructurePieces.BlemishRuinPiece::new, "blemish_ruin");

    private static RegistryObject<StructurePieceType> setFullContextPieceId(StructurePieceType pieceType, String id) {
        return STRUCTURE_PIECE_TYPES.register(id, () -> pieceType);
    }

    private static RegistryObject<StructurePieceType> setTemplatePieceId(StructurePieceType.StructureTemplateType structureTemplateType, String id) {
        return setFullContextPieceId(structureTemplateType, id);
    }
}
