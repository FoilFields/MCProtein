/*
 * Decompiled with CFR 0.2.0 (FabricMC d28b102d).
 */
package foilfields.mcprotein.entity.projectile.thrown;

import foilfields.mcprotein.registers.RegisterEntities;
import foilfields.mcprotein.registers.RegisterItems;
import foilfields.mcprotein.registers.RegisterStatusEffects;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

/** Wheyball entity class.
 * <p>Thrown by the whey golem, applies a 20 second level 0 protein status effect to its target.</p>
 * @author woukie
 */
public class WheyballEntity extends ThrownItemEntity {
    public WheyballEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public WheyballEntity(World world, LivingEntity owner) {
        super(RegisterEntities.WHEYBALL, owner, world);
    }

    public WheyballEntity(World world, double x, double y, double z) {
        super(RegisterEntities.WHEYBALL, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return RegisterItems.WHEY_PROTEIN;
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            for (int i = 0; i < 8; ++i) {
                this.world.addParticle(ParticleTypes.CLOUD, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
            serverPlayerEntity.addStatusEffect(new StatusEffectInstance(RegisterStatusEffects.PROTEIN, 20 * 20, 0, false, false, true));
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }
}

