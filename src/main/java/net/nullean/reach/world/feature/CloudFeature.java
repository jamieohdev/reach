package net.nullean.reach.world.feature;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.nullean.reach.registry.ReachBlocks;
import net.nullean.reach.world.feature.configurations.CloudConfiguration;

public class CloudFeature  extends Feature<CloudConfiguration> {
    public CloudFeature(Codec<CloudConfiguration> codec) {
        super(codec);
    }

    /**
     * Randomly places an area blocks in a direction to create a cloud.
     * The code is taken from older versions.
     * @param context The {@link FeaturePlaceContext} with a {@link CloudConfiguration}.
     * @return Whether the placement was successful, as a {@link Boolean}.
     */
    @Override
    public boolean place(FeaturePlaceContext<CloudConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        boolean direction = random.nextBoolean();
        BlockPos blockPos = context.origin().offset(-random.nextInt(8), 0, (direction ? 8 : 0) - random.nextInt(8));
        CloudConfiguration config = context.config();

        if (blockPos.getY() <= 100)
        {
            for (int amount = 0; amount < config.bounds(); ++amount) {
                int xOffset = random.nextInt(2);
                int yOffset = (random.nextBoolean() ? random.nextInt(3) - 1 : 0);
                int zOffset = random.nextInt(2);

                if (direction) {
                    blockPos = blockPos.offset(xOffset, yOffset, -zOffset);
                } else {
                    blockPos = blockPos.offset(xOffset, yOffset, zOffset);
                }

                for (int x = blockPos.getX(); x < blockPos.getX() + random.nextInt(2) + 3; ++x) {
                    for (int y = blockPos.getY(); y < blockPos.getY() + random.nextInt(1) + 2; ++y) {
                        for (int z = blockPos.getZ(); z < blockPos.getZ() + random.nextInt(2) + 3; ++z) {
                            BlockPos newPosition = new BlockPos(x, y, z);

                            if (level.isEmptyBlock(newPosition)) {
                                if (Math.abs(x - blockPos.getX())
                                        + Math.abs(y - blockPos.getY())
                                        + Math.abs(z - blockPos.getZ())
                                        < 4 + random.nextInt(2)) {
                                    this.setBlock(level, new BlockPos(x, y + 6, z), ReachBlocks.CLOUD_WHITE.get().defaultBlockState());
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        else
        {
            return false;
        }
    }
}