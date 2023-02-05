package foilfields.mcprotein;

import foilfields.mcprotein.events.ServerPlayConnectionJoin;
import foilfields.mcprotein.networking.SwoleMessages;
import foilfields.mcprotein.registers.*;
import foilfields.mcprotein.util.EntityDataSaver;
import foilfields.mcprotein.util.SwoleData;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;

import static foilfields.mcprotein.networking.SwoleMessages.*;

public class MCProtein implements ModInitializer {
    public static final String MOD_ID = "mcprotein";

    public static ItemGroup MOD_ITEM_GROUP = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "mcprotein")).icon(() -> new ItemStack(Blocks.CAULDRON)).build();

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
