package foilfields.mcprotein.networking;

import foilfields.mcprotein.MCProtein;
import foilfields.mcprotein.networking.packet.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

/**
 * Class containing declarations of sync identifiers for the swole system
 */
public class SwoleMessages {
    public static final Identifier ATTACK_SYNC_ID = new Identifier(MCProtein.MOD_ID, "attack_sync");
    public static final Identifier SPRINT_SYNC_ID = new Identifier(MCProtein.MOD_ID, "sprint_sync");
    public static final Identifier SWIM_SYNC_ID = new Identifier(MCProtein.MOD_ID, "swim_sync");
    public static final Identifier JUMP_SYNC_ID = new Identifier(MCProtein.MOD_ID, "jump_sync");
    public static final Identifier MINE_SYNC_ID = new Identifier(MCProtein.MOD_ID, "mine_sync");
    public static final Identifier DEFENCE_SYNC_ID = new Identifier(MCProtein.MOD_ID, "defence_sync");

    /**
     * Registers server to client packets
     * <p>Should be called on client initialisation/p>
     */
    public static void RegisterS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ATTACK_SYNC_ID, AttackSyncDataS2CPacket::Receive);
        ClientPlayNetworking.registerGlobalReceiver(SWIM_SYNC_ID, SwimSyncDataS2CPacket::Receive);
        ClientPlayNetworking.registerGlobalReceiver(SPRINT_SYNC_ID, SprintSyncDataS2CPacket::Receive);
        ClientPlayNetworking.registerGlobalReceiver(JUMP_SYNC_ID, JumpSyncDataS2CPacket::Receive);
        ClientPlayNetworking.registerGlobalReceiver(MINE_SYNC_ID, MineSyncDataS2CPacket::Receive);
        ClientPlayNetworking.registerGlobalReceiver(DEFENCE_SYNC_ID, DefenceSyncDataS2CPacket::Receive);
    }

    /**
     * Registers client to server packets
     * <p>Should be called on server initialisation/p>
     */
    public static void RegisterC2SPackets() {

    }
}
