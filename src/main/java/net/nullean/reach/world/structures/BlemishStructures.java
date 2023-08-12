package net.nullean.reach.world.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.CheckerboardColumnBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.nullean.reach.Reach;
import net.nullean.reach.registry.ReachBlocks;
import net.nullean.reach.registry.ReachStructures;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BlemishStructures extends Structure {

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

    /*
     * This is where extra checks can be done to determine if the structure can spawn here.
     * This only needs to be overridden if you're adding additional spawn conditions.
     *
     * Fun fact, if you set your structure separation/spacing to be 0/1, you can use
     * extraSpawningChecks to return true only if certain chunk coordinates are passed in
     * which allows you to spawn structures only at certain coordinates in the world.
     *
     * Basically, this method is used for determining if the land is at a suitable height,
     * if certain other structures are too close or not, or some other restrictive condition.
     *
     * For example, Pillager Outposts added a check to make sure it cannot spawn within 10 chunk of a Village.
     * (Bedrock Edition seems to not have the same check)
     *
     * If you are doing Nether structures, you'll probably want to spawn your structure on top of ledges.
     * Best way to do that is to use getBaseColumn to grab a column of blocks at the structure's x/z position.
     * Then loop through it and look for land with air above it and set blockpos's Y value to it.
     * Make sure to set the final boolean in JigsawPlacement.addPieces to false so
     * that the structure spawns at blockpos's y value instead of placing the structure on the Bedrock roof!
     *
     * Also, please for the love of god, do not do dimension checking here.
     * If you do and another mod's dimension is trying to spawn your structure,
     * the locate command will make minecraft hang forever and break the game.
     * Use the biome tags for where to spawn the structure and users can datapack
     * it to spawn in specific biomes that aren't in the dimension they don't like if they wish.
     */
    private static boolean extraSpawningChecks(Structure.GenerationContext context) {
        BlockPos blockPos = context.chunkPos().getWorldPosition();

        int landHeight = context.chunkGenerator().getFirstOccupiedHeight(blockPos.getX(), blockPos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());

        NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(blockPos.getX(), blockPos.getZ(), context.heightAccessor(), context.randomState());

        BlockState topBlock = columnOfBlocks.getBlock(landHeight);

        return topBlock.getFluidState().isEmpty();
    }
//  From the Bumblezone (@TelepathicGrunt)
    public static int getMaxTerrainLimit(ChunkGenerator chunkGenerator) {
        return chunkGenerator.getMinY() + chunkGenerator.getGenDepth();
    }

    public static BlockPos getLowestLand(ChunkGenerator chunkGenerator, RandomState randomState, BlockPos centerPos, LevelHeightAccessor heightLimitView, boolean canBeOnLiquid, boolean canBeInLiquid) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos().set(centerPos.getX(), 1, centerPos.getZ());
        NoiseColumn blockView = chunkGenerator.getBaseColumn(mutable.getX(), mutable.getZ(), heightLimitView, randomState);
        BlockState currentBlockstate = blockView.getBlock(mutable.getY());
        BlockState pastBlockstate = currentBlockstate;
        while (mutable.getY() <= getMaxTerrainLimit(chunkGenerator)) {
            if(canBeInLiquid && !currentBlockstate.getFluidState().isEmpty())
            {
                mutable.move(Direction.UP);
                return mutable;
            }
            else if((canBeOnLiquid || !pastBlockstate.getFluidState().isEmpty()) && currentBlockstate.isAir())
            {
                mutable.move(Direction.UP);
                return mutable;
            }

            mutable.move(Direction.UP);
            pastBlockstate = currentBlockstate;
            currentBlockstate = blockView.getBlock(mutable.getY());
        }

        return mutable;
    }
//  From the Bumblezone (@TelepathicGrunt)

    @Override
    public @NotNull Optional<Structure.GenerationStub> findGenerationPoint(Structure.@NotNull GenerationContext context) {

        if (!extraSpawningChecks(context)) {
           return Optional.empty();
        }
//  From the Bumblezone (@TelepathicGrunt)
        ChunkPos chunkpos = context.chunkPos();
        int y = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
        BlockPos centerPos = new BlockPos(chunkpos.getMinBlockX(), y, chunkpos.getMinBlockZ());

        if (this.biomeRadius.isPresent() && !(context.biomeSource() instanceof CheckerboardColumnBiomeSource)) {
            int validBiomeRange = this.biomeRadius.get();
            int sectionY = centerPos.getY();
            if (projectStartToHeightmap.isPresent()) {
                sectionY += getLowestLand(
                        context.chunkGenerator(),
                        context.randomState(),
                        centerPos,
                        context.heightAccessor(),
                        true,
                        projectStartToHeightmap.get() == Heightmap.Types.OCEAN_FLOOR_WG
                ).getY();
            }
            sectionY = QuartPos.fromBlock(sectionY);

            for (int curChunkX = chunkpos.x - validBiomeRange; curChunkX <= chunkpos.x + validBiomeRange; curChunkX++) {
                for (int curChunkZ = chunkpos.z - validBiomeRange; curChunkZ <= chunkpos.z + validBiomeRange; curChunkZ++) {
                    Holder<Biome> biome = context.biomeSource().getNoiseBiome(QuartPos.fromSection(curChunkX), sectionY, QuartPos.fromSection(curChunkZ), context.randomState().sampler());
                    if (!context.validBiome().test(biome)) {
                        return Optional.empty();
                    }
                }
            }
        }
//  From the Bumblezone (@TelepathicGrunt)

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

    @Override
    public StructureType<?> type() {
        return ReachStructures.BLEMISH_STRUCTURES.get(); // Helps the game know how to turn this structure back to json to save to chunks
    }
}
