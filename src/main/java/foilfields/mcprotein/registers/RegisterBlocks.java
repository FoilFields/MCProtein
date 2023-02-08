package foilfields.mcprotein.registers;

import foilfields.mcprotein.block.*;
import foilfields.mcprotein.block.PumpkinBlock;
import foilfields.mcprotein.plants.CustomCrop;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static foilfields.mcprotein.MCProtein.MOD_ID;

/**
 * Class for registering modded blocks.
 * <p>Also responsible for registering cauldrons.</p>
 */
public class RegisterBlocks {
    public static final Block REINFORCED_CARVED_PUMPKIN = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "reinforced_carved_pumpkin"), new PumpkinBlock(FabricBlockSettings.of(Material.GOURD).mapColor(MapColor.IRON_GRAY).sounds(BlockSoundGroup.METAL).hardness(1.0f).resistance(1.0f)));
    public static final Block CURD_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "curd_block"), new CurdBlock(FabricBlockSettings.of(Material.SNOW_LAYER).mapColor(MapColor.YELLOW).noCollision().collidable(false)));
    public static final Block CREATINE_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "creatine_block"), new Block(FabricBlockSettings.of(Material.SNOW_BLOCK).mapColor(MapColor.LIGHT_BLUE).hardness(0.2f).resistance(0.2f).sounds(BlockSoundGroup.SNOW)));
    public static final Block WHEY_PROTEIN_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "whey_protein_block"), new Block(FabricBlockSettings.of(Material.SNOW_BLOCK).mapColor(MapColor.PALE_YELLOW).hardness(0.2f).resistance(0.2f).sounds(BlockSoundGroup.SNOW)));
    public static final Block GLUCOSE_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "glucose_block"), new GlucoseBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).mapColor(MapColor.PALE_YELLOW).slipperiness(0.8f).sounds(BlockSoundGroup.SLIME).nonOpaque()));

    public static final Block BCAA_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "bcaa_block"), new Block(FabricBlockSettings.of(Material.SNOW_BLOCK).mapColor(MapColor.PALE_YELLOW).hardness(0.2f).resistance(0.2f).sounds(BlockSoundGroup.SNOW)));
    public static final Block DEXTROSE_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "dextrose_block"), new Block(FabricBlockSettings.of(Material.SNOW_BLOCK).mapColor(MapColor.PALE_YELLOW).hardness(0.2f).resistance(0.2f).sounds(BlockSoundGroup.SNOW)));
    public static final Block GLUTAMINE_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "glutamine_block"), new Block(FabricBlockSettings.of(Material.SNOW_BLOCK).mapColor(MapColor.PALE_YELLOW).hardness(0.2f).resistance(0.2f).sounds(BlockSoundGroup.SNOW)));
    public static final Block NITRIC_OXIDE_BOOSTER_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "nitric_oxide_booster_block"), new Block(FabricBlockSettings.of(Material.SNOW_BLOCK).mapColor(MapColor.PALE_YELLOW).hardness(0.2f).resistance(0.2f).sounds(BlockSoundGroup.SNOW)));
    public static final Block CASEIN_PROTEIN_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "casein_protein_block"), new Block(FabricBlockSettings.of(Material.SNOW_BLOCK).mapColor(MapColor.PALE_YELLOW).hardness(0.2f).resistance(0.2f).sounds(BlockSoundGroup.SNOW)));

    public static final CropBlock CAFFEINE_CROP = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "caffeine_crop_block"), new CustomCrop(AbstractBlock.Settings.of(Material.PLANT).nonOpaque().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.BAMBOO)));
//    public static final Block CHEESE_WHEEL = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "whey_protein_block"), new Block(FabricBlockSettings.of(Material.SNOW_BLOCK).mapColor(MapColor.PALE_YELLOW).hardness(0.2f).resistance(0.2f).sounds(BlockSoundGroup.SNOW)));

    /**
     * Registers blocks and cauldrons.
     * <p>Should be called once when the server initialises.</p>
     *
     * @author woukie
     */
    public static void register() {
        MilkCauldronBlock.register();
        FishCauldronBlock.register();
    }
}
