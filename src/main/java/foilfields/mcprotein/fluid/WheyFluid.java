package foilfields.mcprotein.fluid;

import foilfields.mcprotein.MCProtein;
import foilfields.mcprotein.registers.RegisterFluids;
import foilfields.mcprotein.registers.RegisterItems;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

/**
 * Custom fluid class for whey.
 */
public abstract class WheyFluid extends AbstractFluid {

    /**
     * Gets the still fluid block corresponding to the fluid
     * @return the still fluid block
     */
    @Override
    public Fluid getStill() {
        return RegisterFluids.STILL_WHEY;
    }

    /**
     * Gets the flowing fluid block corresponding to the fluid
     * @return the flowing fluid block
     */
    @Override
    public Fluid getFlowing() {
        return RegisterFluids.FLOWING_WHEY;
    }

    /**
     * Gets the corresponding bucket item
     * @return the bucket item
     */
    @Override
    public Item getBucketItem() {
        return RegisterItems.WHEY_BUCKET;
    }

    /**
     * Gets the block state with the level of the fluid
     * @param fluidState the fluid state
     * @return the block state
     */
    @Override
    protected BlockState toBlockState(FluidState fluidState) {
        return RegisterFluids.WHEY.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(fluidState));
    }

    /**
     * The flowing class of the fluid
     */
    public static class Flowing extends WheyFluid {

        /**
         * Add a property to the flowing fluid
         * @param builder builder to add to
         */
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        /**
         * Get the level of the fluid
         * @param fluidState fluid state
         * @return the level of the fluid
         */
        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        /**
         * Get whether the fluid is still or flowing
         * @param fluidState fluid state
         * @return whether the fluid is still
         */
        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

    /**
     * The still class of the fluid
     */
    public static class Still extends WheyFluid {

        /**
         * Get the level of the fluid
         * @param fluidState fluid state
         * @return the level of the fluid
         */
        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        /**
         * Get whether the fluid is still or flowing
         * @param fluidState fluid state
         * @return whether the fluid is still
         */
        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}
