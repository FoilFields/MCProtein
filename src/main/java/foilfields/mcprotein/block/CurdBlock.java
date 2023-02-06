package foilfields.mcprotein.block;

import foilfields.mcprotein.networking.SwoleMessages;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

/** Curd block class.
 * <p>Block that appears on top of a milk cauldron after it has aged.</p>
 * @author woukie
 */
public class CurdBlock extends Block {
    /** Constructor.
     * <p>No special behaviour outside super(settings).</p>
     * @param settings block settings
     * @author woukie
     */
    public CurdBlock(Settings settings) {
        super(settings);
    }

    /** Gets the sound group of the block
     *
     * @param state BlockState of the block
     * @return slime sounds
     */
    @Override
    public BlockSoundGroup getSoundGroup(BlockState state) {
        return BlockSoundGroup.SLIME;
    }

    /** Gets the outline shape of the curd block.
     * @param state BlockState of the block
     * @param world world the block is in
     * @param pos position of the block
     * @param context shape context
     * @return a curd-shaped cuboid
     */
    @Override
    public VoxelShape getOutlineShape(BlockState state, net.minecraft.world.BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0f, 0.0001f, 0f, 1f, 0.0625f, 1f);
    }
}
