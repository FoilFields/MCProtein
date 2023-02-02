package foilfields.mcprotein.registers;

import foilfields.mcprotein.MCProtein;
import foilfields.mcprotein.items.FishOilBottleItem;
import foilfields.mcprotein.items.MilkBottleItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static foilfields.mcprotein.registers.RegisterFluids.STILL_FISH_OIL;
import static foilfields.mcprotein.registers.RegisterFluids.STILL_WHEY;

public class RegisterItems {
    public static final Item WHEY_PROTEIN = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "whey_protein"), new Item(new Item.Settings().group(MCProtein.MOD_ITEM_GROUP)));
    public static final Item CREATINE = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "creatine"), new Item(new Item.Settings().group(MCProtein.MOD_ITEM_GROUP)));
    public static final Item MEGA_CREATINE = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "mega_creatine"), new Item(new Item.Settings().group(MCProtein.MOD_ITEM_GROUP)));
    public static final Item FISH_OIL_BUCKET = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "fish_oil_bucket"), new BucketItem(STILL_FISH_OIL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(MCProtein.MOD_ITEM_GROUP)));;
    public static final Item WHEY_BUCKET = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "whey_bucket"), new BucketItem(STILL_WHEY, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(MCProtein.MOD_ITEM_GROUP)));;
    //public static final Item POWDERED_MILK_BUCKET = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "powdered_milk_bucket"), new Item(new FabricItemSettings().group(MOD_ITEM_GROUP)));
    public static final Item MILK_BOTTLE = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "milk_bottle"), (Item)new MilkBottleItem(new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE).food(FoodComponents.HONEY_BOTTLE).group(MCProtein.MOD_ITEM_GROUP).maxCount(16)));
    public static final Item WHEY_BOTTLE = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "whey_bottle"), (Item)new MilkBottleItem(new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE).food(FoodComponents.HONEY_BOTTLE).group(MCProtein.MOD_ITEM_GROUP).maxCount(16)));
    public static final Item FISH_OIL_BOTTLE = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "fish_oil_bottle"), (Item)new FishOilBottleItem(new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE).food(FoodComponents.HONEY_BOTTLE).group(MCProtein.MOD_ITEM_GROUP).maxCount(16)));
    public static final Item CURD = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "curd"), new Item(new Item.Settings().group(MCProtein.MOD_ITEM_GROUP).food(FoodComponents.CARROT)));
    public static final Item CHEESE = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "cheese"), new Item(new Item.Settings().group(MCProtein.MOD_ITEM_GROUP).food(FoodComponents.BREAD)));
    public static final Item WHEY_PROTEIN_BLOCK = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "whey_protein_block"), new BlockItem(RegisterBlocks.WHEY_PROTEIN_BLOCK, new FabricItemSettings().group(MCProtein.MOD_ITEM_GROUP)));
    public static final Item CREATINE_BLOCK = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "creatine_block"), new BlockItem(RegisterBlocks.CREATINE_BLOCK, new FabricItemSettings().group(MCProtein.MOD_ITEM_GROUP)));


    public static void register() {

    }
}
