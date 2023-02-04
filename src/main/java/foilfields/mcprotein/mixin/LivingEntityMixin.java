package foilfields.mcprotein.mixin;

import foilfields.mcprotein.util.EntityDataSaver;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("RETURN"), method = "getJumpVelocity()F", cancellable = true)
    protected void getJumpVelocity(CallbackInfoReturnable<Float> cir) {
        if (isPlayer()) {
            EntityDataSaver entityDataSaver = (EntityDataSaver) this;
            NbtCompound nbt = entityDataSaver.getPersistentData();
            float multiplier = (((float)nbt.getInt("jump")) / 4500.0f + 1.0f);
            cir.setReturnValue(cir.getReturnValueF() * multiplier);
        }

        cir.setReturnValue(cir.getReturnValueF());
    }

    @Inject(at = @At("HEAD"), method = "getNextAirUnderwater(I)I", cancellable = true)
    protected void getNextAirUnderwater(int air, CallbackInfoReturnable<Integer> cir) {
        int i = EnchantmentHelper.getRespiration((LivingEntity)(Object)this);

        EntityDataSaver entityDataSaver = (EntityDataSaver) this;
        NbtCompound nbt = entityDataSaver.getPersistentData();
        float multiplier = (((float)nbt.getInt("swim")) / 900.0f + 1.0f); // Value greater than 1 where player lasts x times longer under-water

        if (Math.random() < (1 - (1 / multiplier)) || i > 0 && this.random.nextInt(i + 1) > 0) {
            cir.setReturnValue(air);
        }

        cir.setReturnValue(air - 1);
    }
}
