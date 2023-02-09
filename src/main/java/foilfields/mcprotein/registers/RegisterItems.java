package foilfields.mcprotein.registers;

import foilfields.mcprotein.MCProtein;
import foilfields.mcprotein.items.FishOilBottleItem;
import foilfields.mcprotein.items.MilkBottleItem;
import foilfields.mcprotein.items.SwoleFood;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static foilfields.mcprotein.MCProtein.MOD_ID;
import static foilfields.mcprotein.registers.RegisterFluids.STILL_FISH_OIL;
import static foilfields.mcprotein.registers.RegisterFluids.STILL_WHEY;

/**
 * Class for registering modded items.
 */
public class RegisterItems {
    public static final FoodComponent SNORT = new FoodComponent.Builder().hunger(0).saturationModifier(1f).build();

    public static final Item WHEY_PROTEIN = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "whey_protein"), new SwoleFood(new Item.Settings().group(MCProtein.MOD_ITEM_GROUP).food(SNORT), RegisterStatusEffects.WHEY_PROTEIN, 20 * 60 * 6, 0));
    public static final Item CASEIN_PROTEIN = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "casein_protein"), new SwoleFood(new Item.Settings().group(MCProtein.MOD_ITEM_GROUP).food(SNORT), RegisterStatusEffects.CASEIN_PROTEIN, 20 * 60 * 6, 0));
    public static final Item CREATINE = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "creatine"), new SwoleFood(new Item.Settings().group(MCProtein.MOD_ITEM_GROUP).food(SNORT), RegisterStatusEffects.CREATINE, 20 * 60 * 6, 0));
    public static final Item BCAA = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "bcaa"), new SwoleFood(new Item.Settings().group(MCProtein.MOD_ITEM_GROUP).food(SNORT), RegisterStatusEffects.BCAA, 20 * 60 * 6, 0));
    public static final Item BEETROOT_EXTRACT = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "beetroot_extract"), new Item(new Item.Settings().group(MCProtein.MOD_ITEM_GROUP).food(FoodComponents.BEETROOT)));
    public static final Item NITRIC_OXIDE_BOOSTER = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "nitric_oxide_booster"), new SwoleFood(new Item.Settings().group(MCProtein.MOD_ITEM_GROUP).food(SNORT), RegisterStatusEffects.NITRIC_OXIDE_BOOSTER, 20 * 60 * 6, 0));
    public static final Item GLUCOSE = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "glucose"), new Item(new Item.Settings().group(MCProtein.MOD_ITEM_GROUP).food(FoodComponents.COOKIE)));
    public static final Item BACTERIA = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "bacteria"), new Item(new Item.Settings().group(MCProtein.MOD_ITEM_GROUP).food(FoodComponents.POISONOUS_POTATO)));
    public static final Item GLUTAMINE = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "glutamine"), new SwoleFood(new Item.Settings().group(MCProtein.MOD_ITEM_GROUP).food(SNORT), RegisterStatusEffects.GLUTAMINE, 20 * 60 * 6, 0));
    public static final Item DEXTROSE = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "dextrose"), new SwoleFood(new Item.Settings().group(MCProtein.MOD_ITEM_GROUP).food(SNORT), RegisterStatusEffects.DEXTROSE, 20 * 60 * 6, 0));
    public static final Item CAFFEINE = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "caffeine"), new SwoleFood(new Item.Settings().group(MCProtein.MOD_ITEM_GROUP).food(SNORT), RegisterStatusEffects.CAFFEINE, 20 * 60 * 6, 0));

    public static final Item MILK_BOTTLE = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "milk_bottle"), new MilkBottleItem(new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE).food(FoodComponents.HONEY_BOTTLE).group(MCProtein.MOD_ITEM_GROUP).maxCount(16)));
    public static final Item WHEY_BOTTLE = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "whey_bottle"), new MilkBottleItem(new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE).food(FoodComponents.HONEY_BOTTLE).group(MCProtein.MOD_ITEM_GROUP).maxCount(16)));
    public static final Item FISH_OIL_BOTTLE = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "fish_oil_bottle"), (Item)new FishOilBottleItem(new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE).food(FoodComponents.HONEY_BOTTLE).group(MCProtein.MOD_ITEM_GROUP).maxCount(16)));

    public static final Item FISH_OIL_BUCKET = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "fish_oil_bucket"), new BucketItem(STILL_FISH_OIL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(MCProtein.MOD_ITEM_GROUP)));;
    public static final Item WHEY_BUCKET = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "whey_bucket"), new BucketItem(STILL_WHEY, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(MCProtein.MOD_ITEM_GROUP)));;
    //public static final Item POWDERED_MILK_BUCKET = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "powdered_milk_bucket"), new Item(new FabricItemSettings().group(MOD_ITEM_GROUP)));

    public static final Item CURD = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "curd"), new Item(new Item.Settings().group(MCProtein.MOD_ITEM_GROUP).food(FoodComponents.CARROT)));
    public static final Item CHEESE = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "cheese"), new Item(new Item.Settings().group(MCProtein.MOD_ITEM_GROUP).food(FoodComponents.BREAD)));

    public static final Item REINFORCED_CARVED_PUMPKIN = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "reinforced_carved_pumpkin"), new BlockItem(RegisterBlocks.REINFORCED_CARVED_PUMPKIN, new FabricItemSettings().group(MCProtein.MOD_ITEM_GROUP)));
    public static final Item WHEY_PROTEIN_BLOCK = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "whey_protein_block"), new BlockItem(RegisterBlocks.WHEY_PROTEIN_BLOCK, new FabricItemSettings().group(MCProtein.MOD_ITEM_GROUP)));
    public static final Item CREATINE_BLOCK = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "creatine_block"), new BlockItem(RegisterBlocks.CREATINE_BLOCK, new FabricItemSettings().group(MCProtein.MOD_ITEM_GROUP)));
    public static final Item GLUCOSE_BLOCK = Registry.register(Registry.ITEM, new Identifier(MCProtein.MOD_ID, "glucose_block"), new BlockItem(RegisterBlocks.GLUCOSE_BLOCK, new FabricItemSettings().group(MCProtein.MOD_ITEM_GROUP)));

    public static final Item BCAA_BLOCK = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "bcaa_block"), new BlockItem(RegisterBlocks.BCAA_BLOCK, new FabricItemSettings().group(MCProtein.MOD_ITEM_GROUP)));
    public static final Item CAFFEINE_BLOCK = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "caffeine_block"), new BlockItem(RegisterBlocks.CAFFEINE_BLOCK, new FabricItemSettings().group(MCProtein.MOD_ITEM_GROUP)));
    public static final Item DEXTROSE_BLOCK = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "dextrose_block"), new BlockItem(RegisterBlocks.DEXTROSE_BLOCK, new FabricItemSettings().group(MCProtein.MOD_ITEM_GROUP)));
    public static final Item GLUTAMINE_BLOCK = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "glutamine_block"), new BlockItem(RegisterBlocks.GLUTAMINE_BLOCK, new FabricItemSettings().group(MCProtein.MOD_ITEM_GROUP)));
    public static final Item NITRIC_OXIDE_BOOSTER_BLOCK = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "nitric_oxide_booster_block"), new BlockItem(RegisterBlocks.NITRIC_OXIDE_BOOSTER_BLOCK, new FabricItemSettings().group(MCProtein.MOD_ITEM_GROUP)));
    public static final Item CASEIN_PROTEIN_BLOCK = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "casein_protein_block"), new BlockItem(RegisterBlocks.CASEIN_PROTEIN_BLOCK, new FabricItemSettings().group(MCProtein.MOD_ITEM_GROUP)));

    public static final Item COFFEE_BEAN = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "coffee_bean"), new AliasedBlockItem(RegisterBlocks.COFFEE_BUSH, new Item.Settings().group(MCProtein.MOD_ITEM_GROUP)));

    /** Registers items.
     * <p>Should be called once when the server initialises.</p>
     */
    public static void register() {

    }
}
