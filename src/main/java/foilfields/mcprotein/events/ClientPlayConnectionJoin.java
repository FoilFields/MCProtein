package foilfields.mcprotein.events;

import foilfields.mcprotein.networking.SwoleMessages;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;

public class ClientPlayConnectionJoin implements ClientPlayConnectionEvents.Join {
    @Override
    public void onPlayReady(ClientPlayNetworkHandler handler, PacketSender sender, MinecraftClient client) {
        ClientPlayNetworking.send(SwoleMessages.ATTACK_SYNC_ID, PacketByteBufs.create());
        ClientPlayNetworking.send(SwoleMessages.SWIM_SYNC_ID, PacketByteBufs.create());
    }
}
