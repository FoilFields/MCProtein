package foilfields.mcprotein.networking.packet;

import foilfields.mcprotein.util.EntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class AttackSyncDataS2CPacket {
    public static void Receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        int level = buf.readInt();
        ((EntityDataSaver) client.player).getPersistantData().putInt("attack", level);
    }
}
