package net.nullean.reach.world.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.CheckerboardColumnBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.material.Material;
import net.nullean.reach.Reach;
import net.nullean.reach.registry.ReachBlocks;
import net.nullean.reach.registry.ReachStructureTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BlemishStructures extends Structure {
    Blocks blocks;
    public static final Codec<BlemishStructures> CODEC = RecordCodecBuilder.<BlemishStructures>mapCodec(instance ->
            instance.group(BlemishStructures.settingsCodec(instance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                    Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                    Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)
            ).apply(instance, BlemishStructures::new)).codec();

    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int size;
    private final HeightProvider startHeight;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;
    public Optional<Integer> biomeRadius;

    public BlemishStructures(Structure.StructureSettings config,
                         Holder<StructureTemplatePool> startPool,
                         Optional<ResourceLocation> startJigsawName,
                         int size,
                         HeightProvider startHeight,
                         Optional<Heightmap.Types> projectStartToHeightmap,
                         int maxDistanceFromCenter)
    {
        super(config);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.size = size;
        this.startHeight = startHeight;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
        this.biomeRadius = Optional.of(1);
    }

    WorldGenLevel worldgenlevel;
    /*
    *   fix cour
    */
    @Override
    public @NotNull Optional<Structure.GenerationStub> findGenerationPoint(Structure.@NotNull GenerationContext context) {
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        ChunkPos chunkpos = context.chunkPos();

        int y = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
        BlockPos centerPos = new BlockPos(chunkpos.getMinBlockX(), y, chunkpos.getMinBlockZ());

        //start at bottom of dimension
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos().set(centerPos.getX(), 1, centerPos.getZ());

        //get column of blocks at a specific x/z spot.
        NoiseColumn blockView = chunkGenerator.getBaseColumn(mutable.getX(), mutable.getZ(), context.heightAccessor(), context.randomState());

        // The block we are currently at
        BlockState currentBlockstate = blockView.getBlock(mutable.getY());

        // main loop will keep running until either we hit top of dimension or we found surface and break out
        while (mutable.getY() <= chunkGenerator.getMinY() + chunkGenerator.getGenDepth()) {
            if(currentBlockstate.getFluidState().isEmpty() && !currentBlockstate.isAir()) {
                    // Not liquid or air. Thus, solid block. Break out of loop.
                    // Mutable's y value is set to where we need it.
                    if (currentBlockstate.getFluidState().isEmpty() && !(blockView.getBlock(mutable.getY() - 3).getMaterial() == Material.AIR)
                            && (blockView.getBlock(mutable.getY() + 8).getMaterial() == Material.AIR))
                    {
                        break;
                    }
            }

            // Move up if current block is fluid or air as it would fail above check. Get block at new y value
            mutable.move(Direction.UP);
            currentBlockstate = blockView.getBlock(mutable.getY());
        }

        // If we did not find a suitable spot, return empty optional so we do not spawn structure at all at this spot
        if (mutable.getY() >= chunkGenerator.getMinY() + chunkGenerator.getGenDepth()) {
            return Optional.empty();
        }

        // Add the mutable y value to our startHeight value.
        // This lets us use startHeight to offset our structure from the mutable y value we found that was the bottom most solid block
        centerPos = centerPos.above(mutable.getY());

        Reach.LOGGER.debug("blemish ruin located at {}", centerPos);

        Optional<Structure.GenerationStub> structurePiecesGenerator =
                JigsawPlacement.addPieces(
                        context, // Used for JigsawPlacement to get all the proper behaviors done.
                        this.startPool, // The starting pool to use to create the structure layout from
                        this.startJigsawName, // Can be used to only spawn from one Jigsaw block. But we don't need to worry about this.
                        this.size, // How deep a branch of pieces can go away from center piece. (5 means branches cannot be longer than 5 pieces from center piece)
                        centerPos, // Where to spawn the structure.
                        false, // "useExpansionHack" This is for legacy villages to generate properly. You should keep this false always.
                        this.projectStartToHeightmap, // Adds the terrain height's y value to the passed in blockpos's y value. (This uses WORLD_SURFACE_WG heightmap which stops at top water too)
                        // Here, blockpos's y value is 60 which means the structure spawn 60 blocks above terrain height.
                        // Set this to false for structure to be place only at the passed in blockpos's Y value instead.
                        // Definitely keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
                        this.maxDistanceFromCenter); // Maximum limit for how far pieces can spawn from center. You cannot set this bigger than 128 or else pieces gets cutoff.

        /*
         * Note, you are always free to make your own JigsawPlacement class and implementation of how the structure
         * should generate. It is tricky but extremely powerful if you are doing something that vanilla's jigsaw system cannot do.
         * Such as for example, forcing 3 pieces to always spawn every time, limiting how often a piece spawns, or remove the intersection limitation of pieces.
         */

        // Return the pieces generator that is now set up so that the game runs it when it needs to create the layout of structure pieces.
        return structurePiecesGenerator;
    }

    public void afterPlace(WorldGenLevel p_230227_, StructureManager p_230228_, ChunkGenerator p_230229_, RandomSource p_230230_, BoundingBox p_230231_, ChunkPos p_230232_, PiecesContainer p_230233_) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        int i = p_230227_.getMinBuildHeight();
        BoundingBox boundingbox = p_230233_.calculateBoundingBox();
        int j = boundingbox.minY();

        for(int k = p_230231_.minX(); k <= p_230231_.maxX(); ++k) {
            for(int l = p_230231_.minZ(); l <= p_230231_.maxZ(); ++l) {
                blockpos$mutableblockpos.set(k, j, l);
                if (!p_230227_.isEmptyBlock(blockpos$mutableblockpos) && boundingbox.isInside(blockpos$mutableblockpos) && p_230233_.isInsidePiece(blockpos$mutableblockpos)) {
                    for(int i1 = j - 1; i1 > i; --i1) {
                        blockpos$mutableblockpos.setY(i1);
                        if (!p_230227_.isEmptyBlock(blockpos$mutableblockpos) && !p_230227_.getBlockState(blockpos$mutableblockpos).getMaterial().isLiquid()) {
                            break;
                        }

                        p_230227_.setBlock(blockpos$mutableblockpos, Blocks.DEEPSLATE_TILES.defaultBlockState(), 2);
                    }
                }
            }
        }

    }

    @Override
    public StructureType<?> type() {
        return ReachStructureTypes.BLEMISH_STRUCTURES.get(); // Helps the game know how to turn this structure back to json to save to chunks
    }
}
