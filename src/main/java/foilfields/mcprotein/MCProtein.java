package foilfields.mcprotein;

import foilfields.mcprotein.fluid.FishOilFluid;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MCProtein implements ModInitializer {
    public static FlowableFluid STILL_FISH_OIL;
    public static FlowableFluid FLOWING_FISH_OIL;
    public static Item FISH_OIL_BUCKET;
    public static Block FISH_OIL;

    @Override
    public void onInitialize() {
        STILL_FISH_OIL = Registry.register(Registry.FLUID, new Identifier("mcprotein", "fish_oil"), new FishOilFluid.Still());
        FLOWING_FISH_OIL = Registry.register(Registry.FLUID, new Identifier("mcprotein", "flowing_fish_oil"), new FishOilFluid.Flowing());
        FISH_OIL_BUCKET = Registry.register(Registry.ITEM, new Identifier("mcprotein", "fish_oil_bucket"),
        new BucketItem(STILL_FISH_OIL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
        FISH_OIL = Registry.register(Registry.BLOCK, new Identifier("mcprotein", "fish_oil"), new FluidBlock(STILL_FISH_OIL, FabricBlockSettings.copy(Blocks.WATER)){});
    }

}
