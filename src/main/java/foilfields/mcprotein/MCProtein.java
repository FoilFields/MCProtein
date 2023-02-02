package foilfields.mcprotein;

import foilfields.mcprotein.block.CurdBlock;
import foilfields.mcprotein.block.FishCauldronBlock;
import foilfields.mcprotein.block.MilkCauldronBlock;
import foilfields.mcprotein.events.ClientPlayConnectionJoin;
import foilfields.mcprotein.items.FishOilBottleItem;
import foilfields.mcprotein.items.MilkBottleItem;
import foilfields.mcprotein.networking.SwoleMessages;
import foilfields.mcprotein.registers.RegisterCauldrons;
import foilfields.mcprotein.util.EntityDataSaver;
import foilfields.mcprotein.util.SwoleData;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static foilfields.mcprotein.networking.SwoleMessages.ATTACK_SYNC_ID;
import static foilfields.mcprotein.networking.SwoleMessages.SWIM_SYNC_ID;
import static foilfields.mcprotein.registers.RegisterFluids.STILL_FISH_OIL;
import static foilfields.mcprotein.registers.RegisterFluids.STILL_WHEY;

public class MCProtein implements ModInitializer {
    public static final String MOD_ID = "mcprotein";

    public static ItemGroup MOD_ITEM_GROUP = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "mcprotein")).icon(() -> new ItemStack(Blocks.CAULDRON)).build();

    public static final Item FISH_OIL_BUCKET = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fish_oil_bucket"), new BucketItem(STILL_FISH_OIL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(MOD_ITEM_GROUP)));;
    public static final Item WHEY_BUCKET = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "whey_bucket"), new BucketItem(STILL_WHEY, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(MOD_ITEM_GROUP)));;
    //public static final Item POWDERED_MILK_BUCKET = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "powdered_milk_bucket"), new Item(new FabricItemSettings().group(MOD_ITEM_GROUP)));
    public static final Item MILK_BOTTLE = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "milk_bottle"), (Item)new MilkBottleItem(new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE).food(FoodComponents.HONEY_BOTTLE).group(MOD_ITEM_GROUP).maxCount(16)));
    public static final Item WHEY_BOTTLE = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "whey_bottle"), (Item)new MilkBottleItem(new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE).food(FoodComponents.HONEY_BOTTLE).group(MOD_ITEM_GROUP).maxCount(16)));
    public static final Item FISH_OIL_BOTTLE = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fish_oil_bottle"), (Item)new FishOilBottleItem(new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE).food(FoodComponents.HONEY_BOTTLE).group(MOD_ITEM_GROUP).maxCount(16)));
    public static final Item CURD = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "curd"), new Item(new Item.Settings().group(MOD_ITEM_GROUP).food(FoodComponents.CARROT)));
    public static final Item CHEESE = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "cheese"), new Item(new Item.Settings().group(MOD_ITEM_GROUP).food(FoodComponents.BREAD)));

    //public static final Block FISH_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "fish_block"), new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(4f)));
    public static final Block CURD_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "curd_block"), new CurdBlock(FabricBlockSettings.of(Material.SNOW_LAYER).mapColor(MapColor.YELLOW).noCollision().collidable(false)));
    public static final Item WHEY_PROTEIN_POWDER = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "whey_protein_powder"), new Item(new Item.Settings().group(MOD_ITEM_GROUP)));
    public static final Item CREATINE = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "creatine"), new Item(new Item.Settings().group(MOD_ITEM_GROUP)));
    public static final Item MEGA_CREATINE = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "mega_creatine"), new Item(new Item.Settings().group(MOD_ITEM_GROUP)));

    @Override
    public void onInitialize() {
        FuelRegistry.INSTANCE.add(FISH_OIL_BUCKET, 200*32);

        BlockRenderLayerMap.INSTANCE.putBlock(MCProtein.CURD_BLOCK, RenderLayer.getCutout());

        RegisterCauldrons.register();
        MilkCauldronBlock.register();
        FishCauldronBlock.register();

        SwoleMessages.RegisterS2CPackets();

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            server.getWorlds().forEach(serverWorld -> {
                serverWorld.getPlayers().forEach(player -> {
                    if (Math.random() < 0.03) SwoleData.addStat((EntityDataSaver) player, -1, "attack", ATTACK_SYNC_ID);
                    if (Math.random() < 0.03) SwoleData.addStat((EntityDataSaver) player, -1, "swim", SWIM_SYNC_ID);
                });
            });
        });

        ClientPlayConnectionEvents.JOIN.register(new ClientPlayConnectionJoin());
    }
}
