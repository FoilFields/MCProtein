package foilfields.mcprotein;

import foilfields.mcprotein.Blocks.CustomCauldronBlock;
import foilfields.mcprotein.Blocks.CustomLeveledCauldronBlock;
import foilfields.mcprotein.events.ClientPlayConnectionJoin;
import foilfields.mcprotein.fluid.FishOilFluid;
import foilfields.mcprotein.networking.SwoleMessages;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.v1.FabricBlock;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.impl.item.group.ItemGroupExtensions;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Map;

import static net.minecraft.block.Blocks.CAULDRON;

public class MCProtein implements ModInitializer {
    public static FlowableFluid STILL_FISH_OIL;
    public static FlowableFluid FLOWING_FISH_OIL;
    public static Item FISH_OIL_BUCKET, POWDERED_MILK;
    public static Block FISH_OIL, FISH_BLOCK;
    public static CustomCauldronBlock FISH_OIL_CAULDRON;
    public static CustomLeveledCauldronBlock LEVELED_MILK_CAULDRON;
    public static String MOD_ID = "mcprotein";
    //custom item group
    final ItemGroup MCPROTEIN = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "mcprotein")).icon(() -> new ItemStack(FISH_OIL_BUCKET)).build();

    public static final Map<Item, CauldronBehavior> MILK_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();

    @Override
    public void onInitialize() {


        STILL_FISH_OIL = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "fish_oil"), new FishOilFluid.Still());
        FLOWING_FISH_OIL = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "flowing_fish_oil"), new FishOilFluid.Flowing());
        FISH_OIL_BUCKET = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fish_oil_bucket"), new BucketItem(STILL_FISH_OIL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(MCPROTEIN)));

        FISH_OIL = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "fish_oil"), new FluidBlock(STILL_FISH_OIL, FabricBlockSettings.copy(Blocks.WATER)){});
        FuelRegistry.INSTANCE.add(FISH_OIL_BUCKET, 200*32);

        POWDERED_MILK = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "powdered_milk"), new Item(new FabricItemSettings().group(MCPROTEIN)));

        FISH_OIL_CAULDRON = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "fish_oil_cauldron"), new CustomCauldronBlock(LeveledCauldronBlock.Settings.of(Material.METAL, MapColor.STONE_GRAY).requiresTool().strength(2.0F).nonOpaque()){});
        FISH_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "fish_block"), new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(4f)));

        LEVELED_MILK_CAULDRON = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "leveled_milk_cauldron"), new CustomLeveledCauldronBlock(AbstractBlock.Settings.copy(CAULDRON), CustomLeveledCauldronBlock.RAIN_PREDICATE, CustomLeveledCauldronBlock.WATER_CAULDRON_BEHAVIOR));

        SwoleMessages.RegisterS2CPackets();

        ClientPlayConnectionEvents.JOIN.register(new ClientPlayConnectionJoin());
    }

}
