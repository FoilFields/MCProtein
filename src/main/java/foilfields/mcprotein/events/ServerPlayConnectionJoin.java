package foilfields.mcprotein.events;

import foilfields.mcprotein.networking.SwoleMessages;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;

public class ServerPlayConnectionJoin implements ServerPlayConnectionEvents.Join {
    @Override
    public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        ClientPlayNetworking.send(SwoleMessages.ATTACK_SYNC_ID, PacketByteBufs.create());
        ClientPlayNetworking.send(SwoleMessages.SWIM_SYNC_ID, PacketByteBufs.create());
        ClientPlayNetworking.send(SwoleMessages.SPRINT_SYNC_ID, PacketByteBufs.create());
        ClientPlayNetworking.send(SwoleMessages.DEFENCE_SYNC_ID, PacketByteBufs.create());
        ClientPlayNetworking.send(SwoleMessages.MINE_SYNC_ID, PacketByteBufs.create());
        ClientPlayNetworking.send(SwoleMessages.JUMP_SYNC_ID, PacketByteBufs.create());
    }

}
