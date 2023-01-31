package foilfields.mcprotein;

import foilfields.mcprotein.events.ClientPlayConnectionJoin;
import foilfields.mcprotein.fluid.FishOilFluid;
import foilfields.mcprotein.items.MilkBottleItem;
import foilfields.mcprotein.networking.SwoleMessages;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Map;

import static net.minecraft.block.Blocks.CAULDRON;

public class MCProtein implements ModInitializer {
    public static final String MOD_ID = "mcprotein";

    public static ItemGroup MOD_ITEM_GROUP;

    public static final FlowableFluid STILL_FISH_OIL = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "fish_oil"), new FishOilFluid.Still());;
    public static final FlowableFluid FLOWING_FISH_OIL = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "flowing_fish_oil"), new FishOilFluid.Flowing());

    public static final Item FISH_OIL_BUCKET = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fish_oil_bucket"), new BucketItem(STILL_FISH_OIL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(MOD_ITEM_GROUP)));;
    public static final Item POWDERED_MILK_BUCKET = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "powdered_milk_bucket"), new Item(new FabricItemSettings().group(MOD_ITEM_GROUP)));
    public static final Item MILK_BOTTLE = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "milk_bottle"), (Item)new MilkBottleItem(new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE).food(FoodComponents.HONEY_BOTTLE).group(ItemGroup.FOOD).maxCount(16)));
    public static final Item FISH_OIL_BOTTLE = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fish_oil_bottle"), (Item)new MilkBottleItem(new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE).food(FoodComponents.HONEY_BOTTLE).group(ItemGroup.FOOD).maxCount(16)));

    public static final Block FISH_OIL = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "fish_oil"), new FluidBlock(STILL_FISH_OIL, FabricBlockSettings.copy(Blocks.WATER)){});
    public static final Block FISH_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "fish_block"), new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(4f)));

    //      CAULDRONS

    // MILK
    public static final Map<Item, CauldronBehavior> MILK_CAULDRON_BEHAVIOUR = CauldronBehavior.createMap();
    public static final Block MILK_CAULDRON = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "milk_cauldron"), new LeveledCauldronBlock(AbstractBlock.Settings.copy(CAULDRON), LeveledCauldronBlock.RAIN_PREDICATE, MILK_CAULDRON_BEHAVIOUR));
    public static final CauldronBehavior FILL_WITH_MILK = (state, world, pos, player, hand, stack) -> net.minecraft.block.cauldron.CauldronBehavior.fillCauldron(world, pos, player, hand, stack, (BlockState) MILK_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY);

    // FISH OIL
    public static final Map<Item, CauldronBehavior> FISH_OIL_CAULDRON_BEHAVIOUR = CauldronBehavior.createMap();
    public static final Block FISH_OIL_CAULDRON = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "fish_oil_cauldron"), new LeveledCauldronBlock(AbstractBlock.Settings.copy(CAULDRON), LeveledCauldronBlock.RAIN_PREDICATE, FISH_OIL_CAULDRON_BEHAVIOUR));
    public static final CauldronBehavior FILL_WITH_FISH_OIL = (state, world, pos, player, hand, stack) -> net.minecraft.block.cauldron.CauldronBehavior.fillCauldron(world, pos, player, hand, stack, (BlockState) FISH_OIL_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY);

    @Override
    public void onInitialize() {
        MOD_ITEM_GROUP = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "mcprotein")).icon(() -> new ItemStack(FISH_OIL_BUCKET)).build();
        FuelRegistry.INSTANCE.add(FISH_OIL_BUCKET, 200*32);

        SwoleMessages.RegisterS2CPackets();
        ClientPlayConnectionEvents.JOIN.register(new ClientPlayConnectionJoin());
    }
}
