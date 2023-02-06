package foilfields.mcprotein.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

/** Pumpkin block class.
 * <p>Lets the block have rotational properties.</p>
 * @author woukie
 */
public class PumpkinBlock extends HorizontalFacingBlock {

    /** Default pumpkin block constructor.
     * <p>Registers default state as well as calling super.</p>
     * @param settings block settings
     */
    public PumpkinBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

    /** Amends custom properties to block.
     * <p>Amends horizontal facing property.</p>
     * @param builder blocks builder
     */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING);
    }

    /** Gets the outline shape of the pumpkin.
     * @param state BlockState of the block
     * @param world world the block is in
     * @param pos position of the block
     * @param ctx shape context
     * @return full cube
     */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return VoxelShapes.fullCube();
    }

    /** Gets the placement state of the block
     *
     * @param ctx placement context
     * @return placement state with rotation
     */
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing().getOpposite());
    }

}
