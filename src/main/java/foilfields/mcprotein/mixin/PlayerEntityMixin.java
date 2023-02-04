package foilfields.mcprotein.mixin;

import foilfields.mcprotein.networking.SwoleMessages;
import foilfields.mcprotein.util.EntityDataSaver;
import foilfields.mcprotein.util.SwoleData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    @Shadow public abstract float getAttackCooldownProgress(float baseTime);

    @Shadow protected boolean isSubmergedInWater;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/entity/player/PlayerEntity;tickMovement()V")
    public void tickMovement(CallbackInfo ci) {
        if (!world.isClient && isSubmergedInWater()) {
            EntityDataSaver entityDataSaver = (EntityDataSaver) this;
            if (Math.random() < 0.05) SwoleData.addStat(entityDataSaver, 20, "swim", SwoleMessages.SWIM_SYNC_ID);
        }
    }

    // Every time entity.damage is called in the attack method
    @ModifyArgs(method = "attack(Lnet/minecraft/entity/Entity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private void damageTarget(Args args) {
        float amount = args.get(1);

        EntityDataSaver entityDataSaver = (EntityDataSaver) this;
        NbtCompound nbt = entityDataSaver.getPersistentData();
        float damageMultiplier = (((float)nbt.getInt("attack")) / 900.0f + 1.0f);
        args.set(1, amount * damageMultiplier);
    }


    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/entity/player/PlayerEntity;attack(Lnet/minecraft/entity/Entity;)V")
    private void attack(Entity target, CallbackInfo ci) {
        if (!world.isClient && target.isAttackable()) {
            float cooldownProgress = getAttackCooldownProgress(0.5f); // Returns 1 if fully recharged

            EntityDataSaver entityDataSaver = (EntityDataSaver) this;
            SwoleData.addStat(entityDataSaver, MathHelper.floor(cooldownProgress * 20), "attack", SwoleMessages.ATTACK_SYNC_ID);
        }
    }
}
