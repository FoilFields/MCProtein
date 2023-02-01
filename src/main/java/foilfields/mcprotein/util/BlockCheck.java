package foilfields.mcprotein.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class BlockCheck {
    public static boolean isHeatSource(BlockState block) {
        return block.isOf(Blocks.FIRE)
                || block.isOf(Blocks.SOUL_FIRE)
                || block.isOf(Blocks.CAMPFIRE)
                || block.isOf(Blocks.SOUL_CAMPFIRE)
                || block.isOf(Blocks.LAVA)
                || block.isOf(Blocks.LAVA_CAULDRON)
                || block.isOf(Blocks.MAGMA_BLOCK)
                || block.isOf(Blocks.TORCH)
                || block.isOf(Blocks.REDSTONE_TORCH)
                || block.isOf(Blocks.REDSTONE_WALL_TORCH)
                || block.isOf(Blocks.SOUL_TORCH)
                || block.isOf(Blocks.SOUL_WALL_TORCH)
                || block.isOf(Blocks.WALL_TORCH);
    }
}
