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

/**
 * Mixin for player inventory class.
 * <p>Used for swole system</p>
 */
@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
    /**
     * Shadow variable
     */
    @Shadow @Final public PlayerEntity player;

    /**
     * Gets a scaled breaking speed based off the mining level of the player
     * @param block block to be mined
     * @param cir callback return info
     */
    @Inject(at = @At("RETURN"), method = "getBlockBreakingSpeed(Lnet/minecraft/block/BlockState;)F", cancellable = true)
    public void getBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir) {
        EntityDataSaver entityDataSaver = (EntityDataSaver) player;
        NbtCompound nbt = entityDataSaver.getPersistentData();
        float multiplier = (((float)nbt.getInt("mine")) / 900.0f + 1.0f);

        cir.setReturnValue(cir.getReturnValue() * multiplier);
    }
}
