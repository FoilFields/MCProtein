package foilfields.mcprotein.registers;

import foilfields.mcprotein.MCProtein;
import foilfields.mcprotein.fluid.FishOilFluid;
import foilfields.mcprotein.fluid.WheyFluid;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Class for registering modded fluids.
 */
public class RegisterFluids {
    public static final FlowableFluid STILL_FISH_OIL = Registry.register(Registry.FLUID, new Identifier(MCProtein.MOD_ID, "fish_oil"), new FishOilFluid.Still());
    public static final FlowableFluid FLOWING_FISH_OIL = Registry.register(Registry.FLUID, new Identifier(MCProtein.MOD_ID, "flowing_fish_oil"), new FishOilFluid.Flowing());
    public static final Block FISH_OIL = Registry.register(Registry.BLOCK, new Identifier(MCProtein.MOD_ID, "fish_oil"), new FluidBlock(STILL_FISH_OIL, FabricBlockSettings.copy(Blocks.WATER)){});

    public static final FlowableFluid STILL_WHEY = Registry.register(Registry.FLUID, new Identifier(MCProtein.MOD_ID, "whey"), new WheyFluid.Still());;
    public static final FlowableFluid FLOWING_WHEY = Registry.register(Registry.FLUID, new Identifier(MCProtein.MOD_ID, "flowing_whey"), new WheyFluid.Flowing());
    public static final Block WHEY = Registry.register(Registry.BLOCK, new Identifier(MCProtein.MOD_ID, "whey"), new FluidBlock(STILL_WHEY, FabricBlockSettings.copy(Blocks.WATER)){});

    /** Registers fluids.
     * <p>Should be called once when the server initialises.</p>
     */
    public static void register() {

    }
}
