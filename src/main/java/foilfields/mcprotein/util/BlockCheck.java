package foilfields.mcprotein.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

/** Class containing methods for useful block checks.
 * @author woukie
 */
public class BlockCheck {

    /** Gets whether a block is a valid heat source.
     * <p>Used by cauldrons to check if they should speed up ageing.</p>
     * @return whether the block is a valid heat source
     * @param block the block to check
     * @author woukie
     */
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
