package net.nullean.reach.world.structures;

import com.google.common.collect.Lists;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.nullean.reach.Reach;
import net.nullean.reach.registry.ReachPieces;

import java.util.List;

public class CalmRuinStructurePieces
{
    private static final ResourceLocation[] RUINS = new ResourceLocation[]{new ResourceLocation(Reach.MOD_ID + "calm_ruin/ruin_calm_small_1"), new ResourceLocation(Reach.MOD_ID + "calm_ruin/ruin_calm_small_2")};
    private static final ResourceLocation[] BIG_RUINS = new ResourceLocation[]{new ResourceLocation(Reach.MOD_ID + "calm_ruin/ruin_calm_large_1"), new ResourceLocation(Reach.MOD_ID + "calm_ruin/ruin_calm_large_2")};

    private static ResourceLocation getSmallRuin(RandomSource p_228983_) {
        return Util.getRandom(RUINS, p_228983_);
    }

    private static ResourceLocation getBigRuin(RandomSource p_229011_) {
        return Util.getRandom(BIG_RUINS, p_229011_);
    }

    public static void addPieces(StructureTemplateManager structureTemplateManager, BlockPos blockpos, Rotation rotation, StructurePiecesBuilder p_229070_, WorldgenRandom random, CalmRuinStructure structure) {
        boolean flag = random.nextFloat() <= structure.largeProbability;
        float f = flag ? 0.9F : 0.8F;
        addPiece(structureTemplateManager, blockpos, rotation, p_229070_, random, flag, f);
        if (flag && random.nextFloat() <= structure.clusterProbability) {
            addClusterRuins(structureTemplateManager, random, rotation, blockpos, p_229070_);
        }
    }

    private static void addClusterRuins(StructureTemplateManager p_228988_, RandomSource p_228989_, Rotation p_228990_, BlockPos p_228991_, StructurePieceAccessor p_228993_) {
        BlockPos blockpos = new BlockPos(p_228991_.getX(), 90, p_228991_.getZ());
        BlockPos blockpos1 = StructureTemplate.transform(new BlockPos(15, 0, 15), Mirror.NONE, p_228990_, BlockPos.ZERO).offset(blockpos);
        BoundingBox boundingbox = BoundingBox.fromCorners(blockpos, blockpos1);
        BlockPos blockpos2 = new BlockPos(Math.min(blockpos.getX(), blockpos1.getX()), blockpos.getY(), Math.min(blockpos.getZ(), blockpos1.getZ()));
        List<BlockPos> list = allPositions(p_228989_, blockpos2);
        int i = Mth.nextInt(p_228989_, 4, 8);

        for(int j = 0; j < i; ++j) {
            if (!list.isEmpty()) {
                int k = p_228989_.nextInt(list.size());
                BlockPos blockpos3 = list.remove(k);
                Rotation rotation = Rotation.getRandom(p_228989_);
                BlockPos blockpos4 = StructureTemplate.transform(new BlockPos(5, 0, 6), Mirror.NONE, rotation, BlockPos.ZERO).offset(blockpos3);
                BoundingBox boundingbox1 = BoundingBox.fromCorners(blockpos3, blockpos4);
                if (!boundingbox1.intersects(boundingbox)) {
                    addPiece(p_228988_, blockpos3, rotation, p_228993_, p_228989_, false, 0.8F);
                }
            }
        }

    }

    private static List<BlockPos> allPositions(RandomSource p_228985_, BlockPos p_228986_) {
        List<BlockPos> list = Lists.newArrayList();
        list.add(p_228986_.offset(-16 + Mth.nextInt(p_228985_, 1, 8), 0, 16 + Mth.nextInt(p_228985_, 1, 7)));
        list.add(p_228986_.offset(-16 + Mth.nextInt(p_228985_, 1, 8), 0, Mth.nextInt(p_228985_, 1, 7)));
        list.add(p_228986_.offset(-16 + Mth.nextInt(p_228985_, 1, 8), 0, -16 + Mth.nextInt(p_228985_, 4, 8)));
        list.add(p_228986_.offset(Mth.nextInt(p_228985_, 1, 7), 0, 16 + Mth.nextInt(p_228985_, 1, 7)));
        list.add(p_228986_.offset(Mth.nextInt(p_228985_, 1, 7), 0, -16 + Mth.nextInt(p_228985_, 4, 6)));
        list.add(p_228986_.offset(16 + Mth.nextInt(p_228985_, 1, 7), 0, 16 + Mth.nextInt(p_228985_, 3, 8)));
        list.add(p_228986_.offset(16 + Mth.nextInt(p_228985_, 1, 7), 0, Mth.nextInt(p_228985_, 1, 7)));
        list.add(p_228986_.offset(16 + Mth.nextInt(p_228985_, 1, 7), 0, -16 + Mth.nextInt(p_228985_, 4, 8)));
        return list;
    }

    private static void addPiece(StructureTemplateManager p_229002_, BlockPos p_229003_, Rotation p_229004_, StructurePieceAccessor p_229005_, RandomSource p_229006_, boolean p_229008_, float p_229009_) {
        ResourceLocation resourcelocation = p_229008_ ? getBigRuin(p_229006_) : getSmallRuin(p_229006_);
        p_229005_.addPiece(new CalmRuinStructurePieces.CalmRuinPiece(p_229002_, resourcelocation, p_229003_, p_229004_, p_229009_, p_229008_));


    }

    public static class CalmRuinPiece extends TemplateStructurePiece {
        private final float integrity;
        private final boolean isLarge;

        public CalmRuinPiece(StructureTemplateManager p_229018_, ResourceLocation p_229019_, BlockPos p_229020_, Rotation p_229021_, float p_229022_, boolean p_229024_) {
            super(ReachPieces.BLEMISH_RUIN_PIECE.get(), 0, p_229018_, p_229019_, p_229019_.toString(), makeSettings(p_229021_), p_229020_);
            this.integrity = p_229022_;
            this.isLarge = p_229024_;
        }

        public CalmRuinPiece(StructureTemplateManager p_229026_, CompoundTag p_229027_) {
            super(ReachPieces.BLEMISH_RUIN_PIECE.get(), p_229027_, p_229026_, (p_229053_) -> {
                return makeSettings(Rotation.valueOf(p_229027_.getString("Rot")));
            });
            this.integrity = p_229027_.getFloat("Integrity");
            this.isLarge = p_229027_.getBoolean("IsLarge");
        }



        private static StructurePlaceSettings makeSettings(Rotation p_229037_) {
            return (new StructurePlaceSettings()).setRotation(p_229037_).setMirror(Mirror.NONE).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
        }

        protected void addAdditionalSaveData(StructurePieceSerializationContext p_229039_, CompoundTag p_229040_) {
            super.addAdditionalSaveData(p_229039_, p_229040_);
            p_229040_.putString("Rot", this.placeSettings.getRotation().name());
            p_229040_.putFloat("Integrity", this.integrity);
            p_229040_.putBoolean("IsLarge", this.isLarge);
        }

        protected void handleDataMarker(String p_229046_, BlockPos p_229047_, ServerLevelAccessor p_229048_, RandomSource p_229049_, BoundingBox p_229050_) {
            if ("chest".equals(p_229046_)) {
                p_229048_.setBlock(p_229047_, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.WATERLOGGED, Boolean.valueOf(p_229048_.getFluidState(p_229047_).is(FluidTags.WATER))), 2);
                BlockEntity blockentity = p_229048_.getBlockEntity(p_229047_);
                if (blockentity instanceof ChestBlockEntity) {
                    ((ChestBlockEntity)blockentity).setLootTable(this.isLarge ? BuiltInLootTables.UNDERWATER_RUIN_BIG : BuiltInLootTables.UNDERWATER_RUIN_SMALL, p_229049_.nextLong());
                }
            } else if ("drowned".equals(p_229046_)) {
                Drowned drowned = EntityType.DROWNED.create(p_229048_.getLevel());
                if (drowned != null) {
                    drowned.setPersistenceRequired();
                    drowned.moveTo(p_229047_, 0.0F, 0.0F);
                    drowned.finalizeSpawn(p_229048_, p_229048_.getCurrentDifficultyAt(p_229047_), MobSpawnType.STRUCTURE, (SpawnGroupData)null, (CompoundTag)null);
                    p_229048_.addFreshEntityWithPassengers(drowned);
                    if (p_229047_.getY() > p_229048_.getSeaLevel()) {
                        p_229048_.setBlock(p_229047_, Blocks.AIR.defaultBlockState(), 2);
                    } else {
                        p_229048_.setBlock(p_229047_, Blocks.WATER.defaultBlockState(), 2);
                    }
                }
            }

        }

        public void postProcess(WorldGenLevel p_229029_, StructureManager p_229030_, ChunkGenerator p_229031_, RandomSource p_229032_, BoundingBox p_229033_, ChunkPos p_229034_, BlockPos p_229035_) {
            this.placeSettings.clearProcessors().addProcessor(new BlockRotProcessor(this.integrity)).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
            int i = p_229029_.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, this.templatePosition.getX(), this.templatePosition.getZ());
            this.templatePosition = new BlockPos(this.templatePosition.getX(), i, this.templatePosition.getZ());
            BlockPos blockpos = StructureTemplate.transform(new BlockPos(this.template.getSize().getX() - 1, 0, this.template.getSize().getZ() - 1), Mirror.NONE, this.placeSettings.getRotation(), BlockPos.ZERO).offset(this.templatePosition);
            this.templatePosition = new BlockPos(this.templatePosition.getX(), this.getHeight(this.templatePosition, p_229029_, blockpos), this.templatePosition.getZ());
            super.postProcess(p_229029_, p_229030_, p_229031_, p_229032_, p_229033_, p_229034_, p_229035_);
        }

        private int getHeight(BlockPos p_229042_, BlockGetter p_229043_, BlockPos p_229044_) {
            int i = p_229042_.getY();
            int j = 512;
            int k = i - 1;
            int l = 0;

            for(BlockPos blockpos : BlockPos.betweenClosed(p_229042_, p_229044_)) {
                int i1 = blockpos.getX();
                int j1 = blockpos.getZ();
                int k1 = p_229042_.getY() - 1;
                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(i1, k1, j1);
                BlockState blockstate = p_229043_.getBlockState(blockpos$mutableblockpos);

                for(FluidState fluidstate = p_229043_.getFluidState(blockpos$mutableblockpos); (blockstate.isAir() || fluidstate.is(FluidTags.WATER) || blockstate.is(BlockTags.ICE)) && k1 > p_229043_.getMinBuildHeight() + 1; fluidstate = p_229043_.getFluidState(blockpos$mutableblockpos)) {
                    --k1;
                    blockpos$mutableblockpos.set(i1, k1, j1);
                    blockstate = p_229043_.getBlockState(blockpos$mutableblockpos);
                }

                j = Math.min(j, k1);
                if (k1 < k - 2) {
                    ++l;
                }
            }

            int l1 = Math.abs(p_229042_.getX() - p_229044_.getX());
            if (k - j > 2 && l > l1 - 2) {
                i = j + 1;
            }

            return i;
        }
    }
}

