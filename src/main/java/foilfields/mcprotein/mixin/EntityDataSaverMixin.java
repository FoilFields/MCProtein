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

/** Class for custom entity data for the player.
 * <p>Used entirely for storing players swole data.</p>
 * @author woukie
 */
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

    /** Injected into writeNbt method of entity.
     * <p>Responsible for writing players swole data.</p>
     * @param nbt nbt
     * @param cir cir
     * @author woukie
     */
    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void writeNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if (persistantData != null) {
            nbt.put("mcprotein.swole_data", persistantData);
        }
    }

    /** Injected into readNbt method of entity.
     * <p>Responsible for reading players swole data.</p>
     * @param nbt nbt
     * @param ci ci
     * @author woukie
     */
    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void readNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("mcprotein.swole_data", NbtElement.COMPOUND_TYPE)) {
            persistantData = nbt.getCompound("mcprotein.swole_data");
        }
    }
}
