package foilfields.mcprotein;

import foilfields.mcprotein.networking.SwoleMessages;
import foilfields.mcprotein.plants.CustomCrop;
import foilfields.mcprotein.registers.RegisterCauldrons;
import foilfields.mcprotein.registers.*;
import foilfields.mcprotein.util.EntityDataSaver;
import foilfields.mcprotein.util.SwoleData;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static foilfields.mcprotein.networking.SwoleMessages.*;

/**
 * Entry class for the server.
 * <p>Responsible for initialising server side code.</p>
 */
public class MCProtein implements ModInitializer {

    /**
     * Mod ID.
     */
    public static final String MOD_ID = "mcprotein";

    /**
     * Custom mod item group.
     * <p>Register every item in this mod in this group.</p>
     */
    public static ItemGroup MOD_ITEM_GROUP = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "mcprotein")).icon(() -> new ItemStack(Blocks.CAULDRON)).build();

    /**
     * Server entry point
     * <p>Triggers registers and controls natural decay of players abilities.</p>
     */
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

    public static final Block BLENDER_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "blender_block"), new Block(FabricBlockSettings.of(Material.METAL).strength(4.0f).requiresTool()));
    public static final Item BLENDER_ITEM = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "blender_item"), new BlockItem(BLENDER_BLOCK, new FabricItemSettings().group(MOD_ITEM_GROUP)));

    public static final CropBlock CAFFEINE_CROP = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "caffeine_crop_block"), new CustomCrop(AbstractBlock.Settings.of(Material.PLANT).nonOpaque().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.BAMBOO)));
    public static final Item CAFFEINE_SEED = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "caffeine_seed"), new AliasedBlockItem(MCProtein.CAFFEINE_CROP, new Item.Settings().group(MOD_ITEM_GROUP)));


    @Override
    public void onInitialize() {
        FuelRegistry.INSTANCE.add(RegisterItems.FISH_OIL_BUCKET, 200*32);

        SwoleMessages.RegisterC2SPackets();
        BlockRenderLayerMap.INSTANCE.putBlock(MCProtein.CURD_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MCProtein.CAFFEINE_CROP, RenderLayer.getTranslucent());

        RegisterCauldrons.register();
        RegisterEntities.register();
        RegisterFluids.register();
        RegisterBlocks.register();
        RegisterItems.register();
        RegisterStatusEffects.register();

//        ServerPlayConnectionEvents.JOIN.register(new ServerPlayConnectionJoin());

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            server.getWorlds().forEach(serverWorld -> {
                serverWorld.getPlayers().forEach(player -> {
                    if (Math.random() < 0.03) SwoleData.addStat((EntityDataSaver) player, -1, "attack", ATTACK_SYNC_ID);
                    if (Math.random() < 0.03) SwoleData.addStat((EntityDataSaver) player, -1, "swim", SWIM_SYNC_ID);
                    if (Math.random() < 0.03) SwoleData.addStat((EntityDataSaver) player, -1, "sprint", SPRINT_SYNC_ID);
                    if (Math.random() < 0.03) SwoleData.addStat((EntityDataSaver) player, -1, "jump", JUMP_SYNC_ID);
                    if (Math.random() < 0.03) SwoleData.addStat((EntityDataSaver) player, -1, "mine", MINE_SYNC_ID);
                    if (Math.random() < 0.03) SwoleData.addStat((EntityDataSaver) player, -1, "defence", DEFENCE_SYNC_ID);
                });
            });
        });
    }
}
