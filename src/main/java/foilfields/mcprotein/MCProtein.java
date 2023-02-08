package foilfields.mcprotein;

import foilfields.mcprotein.networking.SwoleMessages;
import foilfields.mcprotein.plants.CustomCrop;
import foilfields.mcprotein.registers.RegisterCauldrons;
import foilfields.mcprotein.registers.*;
import foilfields.mcprotein.util.EntityDataSaver;
import foilfields.mcprotein.util.SwoleData;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
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
    @Override
    public void onInitialize() {
        FuelRegistry.INSTANCE.add(RegisterItems.FISH_OIL_BUCKET, 200*32);

        SwoleMessages.RegisterC2SPackets();

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
