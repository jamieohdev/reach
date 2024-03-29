package net.nullean.reach.world.structures;

import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.nullean.reach.Reach;
import net.nullean.reach.registry.ReachStructurePieceTypes;

import java.util.HashSet;
import java.util.Set;


public class CloudChunk extends StructurePiece {
    private final Set<BlockPos> positions = new HashSet<>();
    private final BlockStateProvider blocks;

    public CloudChunk(Set<BlockPos> positions, BlockStateProvider blocks, BoundingBox bounds, Direction direction) {
        super(ReachStructurePieceTypes.LARGE_CLOUD.get(), 0, bounds);
        this.setOrientation(direction);
        this.positions.addAll(positions);
        this.blocks = blocks;
    }

    public CloudChunk(StructurePieceSerializationContext context, CompoundTag tag) {
        super(ReachStructurePieceTypes.LARGE_CLOUD.get(), tag);
        ListTag positions = tag.getList("Positions", Tag.TAG_COMPOUND);
        for (Tag position : positions) {
            this.positions.add(NbtUtils.readBlockPos((CompoundTag) position));
        }
        this.blocks = BlockStateProvider.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, tag.get("Blocks"))).getOrThrow(true, Reach.LOGGER::error);
    }

    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        ListTag positions = new ListTag();
        for (BlockPos position : this.positions) {
            positions.add(NbtUtils.writeBlockPos(position));
        }
        tag.put("Positions", positions);
        BlockStateProvider.CODEC.encodeStart(NbtOps.INSTANCE, this.blocks).resultOrPartial(Reach.LOGGER::error).ifPresent(value -> tag.put("Blocks", value));
    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager manager, ChunkGenerator generator, RandomSource random, BoundingBox bounds, ChunkPos chunkPos, BlockPos blockPos) {
        if (!this.positions.isEmpty()) {
            this.positions.removeIf(pos -> this.placeBlock(level, this.blocks.getState(random, pos), pos, bounds));
        }
    }

    protected boolean placeBlock(WorldGenLevel level, BlockState state, BlockPos pos, BoundingBox bounds) {
        if (bounds.isInside(pos)) {
            if (this.canBeReplaced(level, pos)) {
                level.setBlock(pos, state, 2);
            }
            return true;
        }
        return false;
    }

    protected boolean canBeReplaced(LevelReader level, BlockPos pos) {
        return level.isEmptyBlock(pos);
    }
}