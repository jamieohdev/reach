package net.nullean.reach.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;
import net.nullean.reach.registry.ReachBlocks;
import net.nullean.reach.util.ShardUtils;
import net.nullean.reach.world.feature.configurations.ShardConfiguration2;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

public class ShardLargeFeature2 extends Feature<ShardConfiguration2>
{
    public static Block block1;

    public ShardLargeFeature2(Codec<ShardConfiguration2> p_159960_, Block block)
    {
        super(p_159960_);
        block1 = block;
    }

    public boolean place(FeaturePlaceContext<ShardConfiguration2> p_159967_)
    {
        WorldGenLevel worldgenlevel = p_159967_.level();
        BlockPos blockpos = p_159967_.origin();
        ShardConfiguration2 ShardConfiguration2 = p_159967_.config();
        RandomSource random = p_159967_.random();
        if (!ShardUtils.isEmptyOrWater(worldgenlevel, blockpos))
        {
            return false;
        }
        else
        {
            Optional<Column> optional = Column.scan(worldgenlevel, blockpos, ShardConfiguration2.floorToCeilingSearchRange, ShardUtils::isEmptyOrWater, ShardUtils::isShardBaseOrLava);
            if (optional.isPresent() && optional.get() instanceof Column.Range)
            {
                Column.Range column$range = (Column.Range)optional.get();
                if (column$range.height() < 4)
                {
                    return false;
                }
                else
                {
                    int i = (int)((float)column$range.height() * ShardConfiguration2.maxColumnRadiusToCaveHeightRatio);
                    int j = Mth.clamp(i, ShardConfiguration2.columnRadius.getMinValue(), ShardConfiguration2.columnRadius.getMaxValue());
                    int k = Mth.randomBetweenInclusive(random, ShardConfiguration2.columnRadius.getMinValue(), j);
                    ShardLargeFeature2.LargeShard Shardfeature$Shard = makeShard(blockpos.atY(column$range.ceiling() - 1), false, random, k, ShardConfiguration2.stalactiteBluntness, ShardConfiguration2.heightScale);
                    ShardLargeFeature2.LargeShard Shardfeature$Shard1 = makeShard(blockpos.atY(column$range.floor() + 1), true, random, k, ShardConfiguration2.stalagmiteBluntness, ShardConfiguration2.heightScale);
                    ShardLargeFeature2.WindOffsetter Shardfeature$windoffsetter;
                    if (Shardfeature$Shard.isSuitableForWind(ShardConfiguration2) && Shardfeature$Shard1.isSuitableForWind(ShardConfiguration2)) {
                        Shardfeature$windoffsetter = new ShardLargeFeature2.WindOffsetter(blockpos.getY(), random, ShardConfiguration2.windSpeed);
                    }
                    else
                    {
                        Shardfeature$windoffsetter = ShardLargeFeature2.WindOffsetter.noWind();
                    }

                    boolean flag = Shardfeature$Shard.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(worldgenlevel, Shardfeature$windoffsetter);
                    boolean flag1 = Shardfeature$Shard1.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(worldgenlevel, Shardfeature$windoffsetter);
                    if (flag)
                    {
                        Shardfeature$Shard.placeBlocks(worldgenlevel, random, Shardfeature$windoffsetter);
                    }

                    if (flag1)
                    {
                        Shardfeature$Shard1.placeBlocks(worldgenlevel, random, Shardfeature$windoffsetter);
                    }

                    return true;
                }
            }
            else
            {
                return false;
            }
        }
    }

    private static ShardLargeFeature2.LargeShard makeShard(BlockPos p_159969_, boolean p_159970_, RandomSource p_159971_, int p_159972_, FloatProvider p_159973_, FloatProvider p_159974_)
    {
        return new ShardLargeFeature2.LargeShard(p_159969_, p_159970_, p_159972_, (double)p_159973_.sample(p_159971_), (double)p_159974_.sample(p_159971_));
    }

    static final class LargeShard
    {
        private BlockPos root;
        private final boolean pointingUp;
        private int radius;
        private final double bluntness;
        private final double scale;

        LargeShard(BlockPos p_159981_, boolean p_159982_, int p_159983_, double p_159984_, double p_159985_)
        {
            this.root = p_159981_;
            this.pointingUp = p_159982_;
            this.radius = p_159983_;
            this.bluntness = p_159984_;
            this.scale = p_159985_;
        }

        private int getHeight() {
            return this.getHeightAtRadius(0.0F);
        }

        private int getMinY() {
            return this.pointingUp ? this.root.getY() : this.root.getY() - this.getHeight();
        }

        private int getMaxY() {
            return !this.pointingUp ? this.root.getY() : this.root.getY() + this.getHeight();
        }

        boolean moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(WorldGenLevel p_159990_, ShardLargeFeature2.WindOffsetter p_159991_)
        {
            while(this.radius > 1)
            {
                BlockPos.MutableBlockPos blockpos$mutableblockpos = this.root.mutable();
                int i = Math.min(10, this.getHeight());

                for(int j = 0; j < i; ++j)
                {
                    if (p_159990_.getBlockState(blockpos$mutableblockpos).is(Blocks.LAVA))
                    {
                        return false;
                    }

                    if (ShardUtils.isCircleMostlyEmbeddedInStone(p_159990_, p_159991_.offset(blockpos$mutableblockpos), this.radius))
                    {
                        this.root = blockpos$mutableblockpos;
                        return true;
                    }

                    blockpos$mutableblockpos.move(this.pointingUp ? Direction.DOWN : Direction.UP);
                }

                this.radius /= 2;
            }

            return false;
        }

        private int getHeightAtRadius(float p_159988_)
        {
            return (int) ShardUtils.getShardHeight((double)p_159988_, (double)this.radius, this.scale, this.bluntness);
        }

        void placeBlocks(WorldGenLevel p_159993_, RandomSource p_159994_, ShardLargeFeature2.WindOffsetter p_159995_)
        {
            Random rand = new Random();
            for(int i = -this.radius; i <= this.radius; ++i)
            {
                for(int j = -this.radius; j <= this.radius; ++j)
                {
                    float f = Mth.sqrt((float)(i * i + j * j));
                    if (!(f > (float)this.radius))
                    {
                        int k = this.getHeightAtRadius(f);
                        if (k > 0)
                        {
                            if ((double)p_159994_.nextFloat() < 0.2D)

                            {
                                k = (int)((float)k * Mth.randomBetween(p_159994_, 0.8F, 1.0F));
                            }

                            BlockPos.MutableBlockPos blockpos$mutableblockpos = this.root.offset(i, 0, j).mutable();
                            boolean flag = false;

                            for(int l = 0; l < k; ++l)
                            {
                                BlockPos blockpos = p_159995_.offset(blockpos$mutableblockpos);
                                if (ShardUtils.isEmptyOrWaterOrLava(p_159993_, blockpos))
                                {
                                    flag = true;
                                    Block block = ShardLargeFeature2.block1;
                                    p_159993_.setBlock(blockpos, block.defaultBlockState(), 2);
                                }
                                else if (flag && p_159993_.getBlockState(blockpos).is(Blocks.SOUL_SOIL.defaultBlockState().getBlock()) || flag && p_159993_.getBlockState(blockpos).is(ReachBlocks.SOULSTONE.get()) || flag && p_159993_.getBlockState(blockpos).is(ReachBlocks.SOULSLATE.get()))
                                {
                                    break;
                                }

                                blockpos$mutableblockpos.move(this.pointingUp ? Direction.UP : Direction.DOWN);
                            }
                        }
                    }
                }
            }

        }

        boolean isSuitableForWind(ShardConfiguration2 p_159997_) {
            return this.radius >= p_159997_.minRadiusForWind && this.bluntness >= (double)p_159997_.minBluntnessForWind;
        }
    }

    static final class WindOffsetter {
        private final int originY;
        @Nullable
        private final Vec3 windSpeed;

        WindOffsetter(int p_160004_, RandomSource p_160005_, FloatProvider p_160006_) {
            this.originY = p_160004_;
            float f = p_160006_.sample(p_160005_);
            float f1 = Mth.randomBetween(p_160005_, 0.0F, (float)Math.PI);
            this.windSpeed = new Vec3((double)(Mth.cos(f1) * f), 0.0D, (double)(Mth.sin(f1) * f));
        }

        private WindOffsetter() {
            this.originY = 0;
            this.windSpeed = null;
        }

        static ShardLargeFeature2.WindOffsetter noWind() {
            return new ShardLargeFeature2.WindOffsetter();
        }

        BlockPos offset(BlockPos p_160009_) {
            if (this.windSpeed == null) {
                return p_160009_;
            } else {
                int i = this.originY - p_160009_.getY();
                Vec3 vec3 = this.windSpeed.scale((double)i);
                return p_160009_.offset(Mth.floor(vec3.x), 0, Mth.floor(vec3.z));
            }
        }
    }
}