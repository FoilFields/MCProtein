package foilfields.mcprotein.registers;

import foilfields.mcprotein.block.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static foilfields.mcprotein.MCProtein.MOD_ID;

/**
 * Class for registering modded blocks.
 * <p>Also, responsible for registering cauldrons.</p>
 */
public class RegisterBlocks {
    public static final Block CURD_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "curd_block"), new CurdBlock(FabricBlockSettings.of(Material.SNOW_LAYER).mapColor(MapColor.YELLOW).noCollision().collidable(false)));
    public static final Block CREATINE_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "creatine_block"), new Block(FabricBlockSettings.of(Material.SNOW_BLOCK).mapColor(MapColor.LIGHT_BLUE).hardness(0.2f).resistance(0.2f).sounds(BlockSoundGroup.SNOW)));
    public static final Block WHEY_PROTEIN_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "whey_protein_block"), new Block(FabricBlockSettings.of(Material.SNOW_BLOCK).mapColor(MapColor.PALE_YELLOW).hardness(0.2f).resistance(0.2f).sounds(BlockSoundGroup.SNOW)));
    public static final Block REINFORCED_CARVED_PUMPKIN = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "reinforced_carved_pumpkin"), new PumpkinBlock(FabricBlockSettings.of(Material.GOURD).mapColor(MapColor.IRON_GRAY).sounds(BlockSoundGroup.METAL).hardness(1.0f).resistance(1.0f)) {
    });

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
