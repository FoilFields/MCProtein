package foilfields.mcprotein.mixin;

import foilfields.mcprotein.util.EntityDataSaver;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityDataSaverMixin implements EntityDataSaver {
    private NbtCompound persistantData;

    @Override
    public NbtCompound getPersistentData() {
        if (this.persistantData == null) {
            this.persistantData = new NbtCompound();
        }

        return persistantData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void writeNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if (persistantData != null) {
            nbt.put("mcprotein.swole_data", persistantData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void readNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("mcprotein.swole_data", NbtElement.COMPOUND_TYPE)) {
            persistantData = nbt.getCompound("mcprotein.swole_data");
        }
    }
}
