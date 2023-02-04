package foilfields.mcprotein.util;

import foilfields.mcprotein.entity.effect.SwoleEffect;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.concurrent.atomic.AtomicReference;

public class SwoleData {
    // Balanced to around 20 per second of an activity
    public static int addStat(EntityDataSaver player, int value, String stat, Identifier channelName) {
        NbtCompound nbt = player.getPersistentData();

        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;

        AtomicReference<Float> statusEffectMultiplier = new AtomicReference<>((float) 1);

        if (value > 0) {
            serverPlayerEntity.getStatusEffects().forEach(statusEffectInstance -> {
                StatusEffect effect = statusEffectInstance.getEffectType();
                if (effect instanceof SwoleEffect) {
                    statusEffectMultiplier.updateAndGet(v -> v + (statusEffectInstance.getAmplifier() + 1) / 4);
                }
            });
        }

        int originalData = nbt.getInt(stat);
        int addedData = value < 0.0 ? value : (int)((100.0f / (100.0f + originalData)) * value);
        addedData = (int) (addedData * statusEffectMultiplier.get());
        int newData = Math.max(0, originalData + addedData);

        nbt.putInt(stat, newData);

//        if (value > 0.0) ((ServerPlayerEntity) player).sendMessage(Text.of(stat + " " + newData), true);

        syncStat(newData, serverPlayerEntity, channelName);

        return newData;
    }

    public static void syncStat(int level, ServerPlayerEntity player, Identifier channelName) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(level);
        ServerPlayNetworking.send(player, channelName, buf);
    }
}
