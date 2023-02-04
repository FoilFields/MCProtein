package foilfields.mcprotein.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SwoleData {
    // Balanced to around 20 per second of an activity
    public static int addStat(EntityDataSaver player, int value, String stat, Identifier channelName) {
        NbtCompound nbt = player.getPersistentData();

        int originalData = nbt.getInt(stat);
        int addedData = value < 0.0 ? value : (int)((100.0f / (100.0f + originalData)) * value);
        int newData = Math.max(0, originalData + addedData);

        nbt.putInt(stat, newData);

//        if (value > 0.0) ((ServerPlayerEntity) player).sendMessage(Text.of(stat + " " + newData), true);

        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;

        syncStat(newData, serverPlayerEntity, channelName);

        return newData;
    }

    public static void syncStat(int level, ServerPlayerEntity player, Identifier channelName) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(level);
        ServerPlayNetworking.send(player, channelName, buf);
    }
}
