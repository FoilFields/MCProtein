package foilfields.mcprotein.entity.passive;

import foilfields.mcprotein.entity.projectile.thrown.WheyballEntity;
import foilfields.mcprotein.registers.RegisterItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

/** Whey golem entity class.
 * <p>Behaviour for a golem that throws protein powder at the player for a persistent protein status effect.</p>
 * @author woukie
 */
public class WheyGolemEntity extends GolemEntity implements RangedAttackMob {
    public WheyGolemEntity(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new ProjectileAttackGoal(this, 0.2, 20 * 15, 10.0f));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.2, 1.0000001E-5f));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, entity -> entity instanceof PlayerEntity));
    }

    public static DefaultAttributeContainer.Builder createWheyGolemAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }

    @Override
    public boolean hurtByWater() {
        return true;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient) {
            BlockPos blockPos = new BlockPos(MathHelper.floor(this.getX()), MathHelper.floor(this.getY()), MathHelper.floor(this.getZ()));
            Biome biome = this.world.getBiome(blockPos).value();
            if (biome.isHot(blockPos)) {
                this.damage(DamageSource.ON_FIRE, 1.0f);
            }
        }
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        WheyballEntity projectile = new WheyballEntity(this.world, this);
        double d = target.getEyeY() - (double)1.5f;
        double f = d - projectile.getY();
        double deltaX = target.getX() - this.getX();
        double deltaZ = target.getZ() - this.getZ();
        double h = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ) * (double)0.2f;
        projectile.setVelocity(deltaX, f + h, deltaZ, 1.6f, 0f);
        this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0f, 0.4f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
        this.world.spawnEntity(projectile);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 1.7f;
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player2, Hand hand) {
        ItemStack itemStack = player2.getStackInHand(hand);
        if (itemStack.isOf(RegisterItems.WHEY_PROTEIN)) {
            this.charge(SoundCategory.PLAYERS);

            if (!this.world.isClient) {
                itemStack.decrement(1);
            }
            return ActionResult.success(this.world.isClient);
        }
        return ActionResult.PASS;
    }

    public void charge(SoundCategory shearedSoundCategory) {
        this.world.playSoundFromEntity(null, this, SoundEvents.BLOCK_TUFF_BREAK, shearedSoundCategory, 1.0f, 1.0f);
        if (!this.world.isClient()) {
            // Increase charge stat
        }
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SNOW_GOLEM_AMBIENT;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SNOW_GOLEM_HURT;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SNOW_GOLEM_DEATH;
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, 0.75f * this.getStandingEyeHeight(), this.getWidth() * 0.4f);
    }
}
