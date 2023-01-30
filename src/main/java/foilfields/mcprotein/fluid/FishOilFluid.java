package foilfields.mcprotein.fluid;

import foilfields.mcprotein.MCProtein;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public abstract class FishOilFluid extends AbstractFluid {
    @Override
    public Fluid getStill() {
        return MCProtein.STILL_FISH_OIL;
    }

    @Override
    public Fluid getFlowing() {
        return MCProtein.FLOWING_FISH_OIL;
    }

    @Override
    public Item getBucketItem() {
        return MCProtein.FISH_OIL_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState fluidState) {
        return MCProtein.FISH_OIL.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(fluidState));
    }

    public static class Flowing extends FishOilFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

    public static class Still extends FishOilFluid {
        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}
