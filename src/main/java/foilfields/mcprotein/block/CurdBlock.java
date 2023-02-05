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
    public CurdBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockSoundGroup getSoundGroup(BlockState state) {
        return BlockSoundGroup.SLIME;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, net.minecraft.world.BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0f, 0.0001f, 0f, 1f, 0.0625f, 1f);
    }
}
