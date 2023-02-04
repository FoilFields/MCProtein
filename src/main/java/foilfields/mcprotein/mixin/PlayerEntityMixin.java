package foilfields.mcprotein.mixin;

import foilfields.mcprotein.networking.SwoleMessages;
import foilfields.mcprotein.util.EntityDataSaver;
import foilfields.mcprotein.util.SwoleData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    @Shadow public abstract float getAttackCooldownProgress(float baseTime);

    @Shadow public abstract void sendMessage(Text message, boolean actionBar);

    @Shadow public abstract boolean isInvulnerableTo(DamageSource damageSource);

    @Shadow @Final private PlayerAbilities abilities;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(at = @At("HEAD"), method = "applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V", index = 2, argsOnly = true)
    protected float applyDamage(float value) {
        if (this.isPlayer()) {
            EntityDataSaver entityDataSaver = (EntityDataSaver) this;

            if (!world.isClient()) {
                SwoleData.addStat(entityDataSaver, (int) value * 5, "defence", SwoleMessages.DEFENCE_SYNC_ID);
            }

            NbtCompound nbt = entityDataSaver.getPersistentData();
            float multiplier = (((float)nbt.getInt("defence")) / 900.0f + 1.0f);

            return value / multiplier;
        }
        return value;
    }

    @Inject(at = @At("RETURN"), method = "Lnet/minecraft/entity/player/PlayerEntity;getMovementSpeed()F", cancellable = true)
    public void getMovementSpeed(CallbackInfoReturnable<Float> cir) {
        EntityDataSaver entityDataSaver = (EntityDataSaver) this;
        NbtCompound nbt = entityDataSaver.getPersistentData();
        float multiplier = (((float)nbt.getInt("sprint")) / 2700.0f + 1.0f);

        cir.setReturnValue(cir.getReturnValueF() * multiplier);
    }

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/entity/player/PlayerEntity;tickMovement()V")
    public void tickMovement(CallbackInfo ci) {
        if (!world.isClient) {
            EntityDataSaver entityDataSaver = (EntityDataSaver) this;
            if (isSprinting()) {
                if (Math.random() < 0.05) SwoleData.addStat(entityDataSaver, 20, "sprint", SwoleMessages.SPRINT_SYNC_ID);
            }

            if (isSubmergedInWater()) {
                if (Math.random() < 0.05) SwoleData.addStat(entityDataSaver, 20, "swim", SwoleMessages.SWIM_SYNC_ID);
            }
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
