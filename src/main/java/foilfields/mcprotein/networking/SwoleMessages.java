package foilfields.mcprotein.networking;

import foilfields.mcprotein.MCProtein;
import foilfields.mcprotein.networking.packet.AttackSyncDataS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

public class SwoleMessages {
    public static final Identifier ATTACK_SYNC_ID = new Identifier(MCProtein.MOD_ID, "attack_sync");

    public static void RegisterS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ATTACK_SYNC_ID, AttackSyncDataS2CPacket::Receive);
    }
}
