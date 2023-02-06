package foilfields.mcprotein.items;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

/**
 * Item class for all foods with swole effects.
 */
public class SwoleFood extends Item {

    StatusEffect effect;
    int duration, amplifier;

    /**
     * Constructor for creating a swole food,
     * @param settings item settings
     * @param effect effect to apply to the user
     * @param duration how long it should be applied for
     * @param amplifier the amplitude of the effect
     */
    public SwoleFood(Settings settings, StatusEffect effect, int duration, int amplifier) {
        super(settings);
        this.effect = effect;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    /**
     * Called on item finish using
     * <p>Applies the effect specified in the constructor</p>
     * @param stack the item being consumed
     * @param world the world the item is being consumed in
     * @param user the user who consumed the item
     * @return the item after it has been consumed
     */
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);

        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            serverPlayerEntity.addStatusEffect(new StatusEffectInstance(effect, duration, amplifier, false, false, true));
        }

        return stack;
    }

    /**
     * Gets the use time of the item
     * @param stack item to get use time of
     * @return always 35
     */
    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 35;
    }

    /**
     * Get the purpose of the item
     * <p>Set to eat</p>
     * @param stack item stack
     * @return the eat-action
     */
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    /**
     * Get the eat-sound of the food
     * @return the powder snow break sound
     */
    @Override
    public SoundEvent getEatSound() {
        return SoundEvents.BLOCK_POWDER_SNOW_BREAK;
    }

    /**
     * Get whether the item is a food
     * @return always true
     */
    @Override
    public boolean isFood() {
        return true;
    }

    /**
     * Triggers consuming the item
     * @param world world the item is used in
     * @param user user who triggered using
     * @param hand hand of the user
     * @return result of using
     */
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }
}
