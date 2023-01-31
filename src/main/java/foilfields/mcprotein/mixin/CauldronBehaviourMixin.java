package foilfields.mcprotein.mixin;

import foilfields.mcprotein.MCProtein;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

import static net.minecraft.block.cauldron.CauldronBehavior.*;

@Mixin(CauldronBehavior.class)
public interface CauldronBehaviourMixin {

    @Inject(at = @At("HEAD"), method = "registerBucketBehavior(Ljava/util/Map;)V")
    private static void registerBucketBehavior(Map<Item, CauldronBehavior> behavior, CallbackInfo ci) {
        behavior.put(Items.MILK_BUCKET, MCProtein.FILL_WITH_MILK);
    }

    @Inject(at = @At("TAIL"), method = "Lnet/minecraft/block/cauldron/CauldronBehavior;registerBehavior()V")
    private static void registerBehavior(CallbackInfo ci) {
        EMPTY_CAULDRON_BEHAVIOR.put(Items.POTION, (state, world, pos, player, hand, stack) -> {
            if (PotionUtil.getPotion(stack) != Potions.WATER) {
                return ActionResult.PASS;
            }
            if (!world.isClient) {
                Item item = stack.getItem();
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(item));
                world.setBlockState(pos, Blocks.WATER_CAULDRON.getDefaultState());
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
            }
            return ActionResult.success(world.isClient);
        });

        MCProtein.MILK_CAULDRON_BEHAVIOUR.put(Items.MILK_BUCKET, MCProtein.FILL_WITH_MILK);
        MCProtein.MILK_CAULDRON_BEHAVIOUR.put(Items.LAVA_BUCKET, FILL_WITH_LAVA);
        MCProtein.MILK_CAULDRON_BEHAVIOUR.put(Items.WATER_BUCKET, FILL_WITH_WATER);
        MCProtein.MILK_CAULDRON_BEHAVIOUR.put(Items.POWDER_SNOW_BUCKET, FILL_WITH_POWDER_SNOW);

        MCProtein.MILK_CAULDRON_BEHAVIOUR.put(Items.BUCKET, (state2, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state2, world, pos, player, hand, stack, new ItemStack(Items.MILK_BUCKET), state -> state.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL));

        MCProtein.MILK_CAULDRON_BEHAVIOUR.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient) {
                Item item = stack.getItem();
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.SWIFTNESS)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(item));
                LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
            }
            return ActionResult.success(world.isClient);
        });

        MCProtein.MILK_CAULDRON_BEHAVIOUR.put(Items.POTION, (state, world, pos, player, hand, stack) -> {
            if (state.get(LeveledCauldronBlock.LEVEL) == 3 || PotionUtil.getPotion(stack) != Potions.SWIFTNESS) {
                return ActionResult.PASS;
            }
            if (!world.isClient) {
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                world.setBlockState(pos, (BlockState)state.cycle(LeveledCauldronBlock.LEVEL));
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
            }
            return ActionResult.success(world.isClient);
        });
    }
}
