package foilfields.mcprotein.items;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.List;

/**
 * Item class for the fish oil bottle.
 */
public class FishOilBottleItem extends Item {

    /**
     * Constructor for the fish oil bottle
     * @param settings settings to construct with
     */
    public FishOilBottleItem(Settings settings) {
        super(settings);
    }

    /**
     * Called on item finish using
     * <p>Replaces the held item with a glass bottle and gives the player temporary regeneration</p>
     * @param stack the item being consumed
     * @param world the world the item is being consumed in
     * @param user the user who consumed the item
     * @return the item after it has been consumed
     */
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        if (user instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)user;
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            serverPlayerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20*60, 1));
        }
        if (!world.isClient) {
            List<StatusEffectInstance> list = PotionUtil.getPotionEffects(stack);
            for (StatusEffectInstance statusEffectInstance : list) {
                if (statusEffectInstance.getEffectType().isInstant()) {
                    statusEffectInstance.getEffectType().applyInstantEffect((ServerPlayerEntity)user, (ServerPlayerEntity)user, user, statusEffectInstance.getAmplifier(), 1.0);
                    continue;
                }
                user.addStatusEffect(new StatusEffectInstance(statusEffectInstance));
            }
        }
        if (stack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        }
        if (user instanceof PlayerEntity && !((PlayerEntity)user).getAbilities().creativeMode) {
            ItemStack itemStack = new ItemStack(Items.GLASS_BOTTLE);
            PlayerEntity playerEntity = (PlayerEntity)user;
            if (!playerEntity.getInventory().insertStack(itemStack)) {
                playerEntity.dropItem(itemStack, false);
            }
        }
        return stack;
    }

    /**
     * Gets the use time of the item
     * @param stack item to get use time of
     * @return always 40
     */
    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 40;
    }

    /**
     * Get the purpose of the item
     * <p>Set to drink</p>
     * @param stack item stack
     * @return the drink-action
     */
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    /**
     * Get the drink-sound of the food
     * @return the honey bottle drink sound
     */
    @Override
    public SoundEvent getDrinkSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    /**
     * Get the eat-sound of the food
     * @return the honey bottle drink sound
     */
    @Override
    public SoundEvent getEatSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
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
