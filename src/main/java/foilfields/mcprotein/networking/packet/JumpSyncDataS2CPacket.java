package foilfields.mcprotein.networking.packet;

import foilfields.mcprotein.util.EntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

/** Jump sync packet class.
 * <p>Used to sync stat with the client.</p>
 * @author woukie
 */
public class JumpSyncDataS2CPacket {

    /** Updates entities data.
     * @param client client to update
     * @param handler client network handler
     * @param buf level to update the player with
     * @param responseSender sender
     * @author woukie
     */
    public static void Receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        int level = buf.readInt();
        if (client.player != null) ((EntityDataSaver) client.player).getPersistentData().putInt("jump", level);
    }
}
