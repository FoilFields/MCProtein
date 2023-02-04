package foilfields.mcprotein.mixin;

import foilfields.mcprotein.util.EntityDataSaver;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
    @Shadow @Final public PlayerEntity player;

    @Inject(at = @At("RETURN"), method = "getBlockBreakingSpeed(Lnet/minecraft/block/BlockState;)F", cancellable = true)
    public void getBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir) {
        EntityDataSaver entityDataSaver = (EntityDataSaver) player;
        NbtCompound nbt = entityDataSaver.getPersistentData();
        float multiplier = (((float)nbt.getInt("mine")) / 900.0f + 1.0f);

        cir.setReturnValue(cir.getReturnValue() * multiplier);
    }
}
