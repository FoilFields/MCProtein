package foilfields.mcprotein.util;

import foilfields.mcprotein.networking.SwoleMessages;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class SwoleData {
    public static int addAttack(EntityDataSaver player, int value) {
        NbtCompound nbt = player.getPersistantData();
        int attack = nbt.getInt("attack") + value;
        nbt.putInt("attack", attack);

        syncAttack(attack, (ServerPlayerEntity) player);

        return attack;
    }

    public static void syncAttack(int level, ServerPlayerEntity player) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(level);
        ServerPlayNetworking.send(player, SwoleMessages.ATTACK_SYNC_ID, buf);
    }
}
