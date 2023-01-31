package foilfields.mcprotein;

import foilfields.mcprotein.fluid.FishOilFluid;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.impl.item.group.ItemGroupExtensions;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MCProtein implements ModInitializer {
    public static FlowableFluid STILL_FISH_OIL;
    public static FlowableFluid FLOWING_FISH_OIL;
    public static Item FISH_OIL_BUCKET, POWDERED_MILK;
    public static Block FISH_OIL;
    public static String MOD_ID = "mcprotein";
    //custom item group
    final ItemGroup MCPROTEIN = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "mcprotein")).icon(() -> new ItemStack(FISH_OIL_BUCKET)).build();

    @Override
    public void onInitialize() {
        STILL_FISH_OIL = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "fish_oil"), new FishOilFluid.Still());
        FLOWING_FISH_OIL = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "flowing_fish_oil"), new FishOilFluid.Flowing());
        FISH_OIL_BUCKET = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fish_oil_bucket"),
        new BucketItem(STILL_FISH_OIL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(MCPROTEIN)));
        FISH_OIL = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "fish_oil"), new FluidBlock(STILL_FISH_OIL, FabricBlockSettings.copy(Blocks.WATER)){});

        POWDERED_MILK = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "powdered_milk"), new Item(new FabricItemSettings().group(MCPROTEIN)));
    }

}
