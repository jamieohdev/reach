package net.nullean.reach.world.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.nullean.reach.registry.ReachStructures;

import java.util.Optional;

public class BlemishRuinStructure extends Structure {
    public static final Codec<BlemishRuinStructure> CODEC = RecordCodecBuilder.create((p_229075_) -> {
        return p_229075_.group(settingsCodec(p_229075_), Codec.floatRange(0.0F, 1.0F).fieldOf("large_probability").forGetter((p_229077_) -> {
            return p_229077_.largeProbability;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("cluster_probability").forGetter((p_229073_) -> {
            return p_229073_.clusterProbability;
        })).apply(p_229075_, BlemishRuinStructure::new);
    });

    public final float largeProbability;
    public final float clusterProbability;

    public BlemishRuinStructure(Structure.StructureSettings p_229060_, float p_229062_, float p_229063_) {
        super(p_229060_);
        this.largeProbability = p_229062_;
        this.clusterProbability = p_229063_;
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext p_226571_)
    {
        return onTopOfChunkCenter(p_226571_, Heightmap.Types.OCEAN_FLOOR_WG, (p_229068_) -> {
            this.generatePieces(p_229068_, p_226571_);
        });
    }

    private void generatePieces(StructurePiecesBuilder p_229070_, Structure.GenerationContext p_229071_) {
        BlockPos blockpos = new BlockPos(p_229071_.chunkPos().getMinBlockX(), 90, p_229071_.chunkPos().getMinBlockZ());
        Rotation rotation = Rotation.getRandom(p_229071_.random());
        BlemishRuinStructurePieces.addPieces(p_229071_.structureTemplateManager(), blockpos, rotation, p_229070_, p_229071_.random(), this);
    }


    @Override
    public StructureType<?> type() {
        return ReachStructures.BLEMISH_STRUCTURES.get();
    }
}
