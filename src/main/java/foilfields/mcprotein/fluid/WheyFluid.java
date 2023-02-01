package foilfields.mcprotein.fluid;

import foilfields.mcprotein.MCProtein;
import foilfields.mcprotein.registers.RegisterFluids;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public abstract class WheyFluid extends AbstractFluid {
    @Override
    public Fluid getStill() {
        return RegisterFluids.STILL_WHEY;
    }

    @Override
    public Fluid getFlowing() {
        return RegisterFluids.FLOWING_WHEY;
    }

    @Override
    public Item getBucketItem() {
        return MCProtein.WHEY_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState fluidState) {
        return RegisterFluids.WHEY.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(fluidState));
    }

    public static class Flowing extends WheyFluid {
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

    public static class Still extends WheyFluid {
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
