package foilfields.mcprotein.networking.packet;

import foilfields.mcprotein.util.EntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class DefenceSyncDataS2CPacket {
    public static void Receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        int level = buf.readInt();
        if (client.player != null) ((EntityDataSaver) client.player).getPersistentData().putInt("defence", level);
    }
}
