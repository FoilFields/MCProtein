package foilfields.mcprotein.util;

import foilfields.mcprotein.MCProtein;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.event.GameEvent;

import java.util.Map;

import static net.minecraft.block.Blocks.CAULDRON;
import static net.minecraft.block.cauldron.CauldronBehavior.*;

public class RegisterCauldrons {

    //      CAULDRONS

    // MILK
    public static final Map<Item, CauldronBehavior> MILK_CAULDRON_BEHAVIOUR = CauldronBehavior.createMap();
    public static final Block MILK_CAULDRON = Registry.register(Registry.BLOCK, new Identifier(MCProtein.MOD_ID, "milk_cauldron"), new LeveledCauldronBlock(AbstractBlock.Settings.copy(CAULDRON), LeveledCauldronBlock.RAIN_PREDICATE, MILK_CAULDRON_BEHAVIOUR));
    public static final CauldronBehavior FILL_WITH_MILK = (state, world, pos, player, hand, stack) -> net.minecraft.block.cauldron.CauldronBehavior.fillCauldron(world, pos, player, hand, stack, MILK_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY);

    // FISH OIL
    public static final Map<Item, CauldronBehavior> FISH_OIL_CAULDRON_BEHAVIOUR = CauldronBehavior.createMap();
    public static final Block FISH_OIL_CAULDRON = Registry.register(Registry.BLOCK, new Identifier(MCProtein.MOD_ID, "fish_oil_cauldron"), new LeveledCauldronBlock(AbstractBlock.Settings.copy(CAULDRON), LeveledCauldronBlock.RAIN_PREDICATE, FISH_OIL_CAULDRON_BEHAVIOUR));
    public static final CauldronBehavior FILL_WITH_FISH_OIL = (state, world, pos, player, hand, stack) -> CauldronBehavior.fillCauldron(world, pos, player, hand, stack, FISH_OIL_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY);

    public static void registerBucketBehavior(Map<Item, CauldronBehavior> behavior) {
        behavior.put(Items.LAVA_BUCKET, FILL_WITH_LAVA);
        behavior.put(Items.WATER_BUCKET, FILL_WITH_WATER);
        behavior.put(Items.MILK_BUCKET, RegisterCauldrons.FILL_WITH_MILK);
        behavior.put(MCProtein.FISH_OIL_BUCKET, RegisterCauldrons.FILL_WITH_FISH_OIL);
        behavior.put(Items.POWDER_SNOW_BUCKET, FILL_WITH_POWDER_SNOW);
    }

    public static void registerModdedBucketBehavior(Map<Item, CauldronBehavior> behavior) {
        behavior.put(Items.MILK_BUCKET, RegisterCauldrons.FILL_WITH_MILK);
        behavior.put(MCProtein.FISH_OIL_BUCKET, RegisterCauldrons.FILL_WITH_FISH_OIL);
    }

    public static void registerFillWithBottle(Map<Item, CauldronBehavior> emptyBehavior, Map<Item, CauldronBehavior> fullBehaviour, Item bottleItem, Block cauldron) {
        emptyBehavior.put(bottleItem, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient) {
                Item item = stack.getItem();
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(item));
                world.setBlockState(pos, cauldron.getDefaultState());
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
            }
            return ActionResult.success(world.isClient);
        });

        fullBehaviour.put(bottleItem, (state, world, pos, player, hand, stack) -> {
            if (state.get(LeveledCauldronBlock.LEVEL) == 3) {
                return ActionResult.PASS;
            }
            if (!world.isClient) {
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                world.setBlockState(pos, state.cycle(LeveledCauldronBlock.LEVEL));
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
            }
            return ActionResult.success(world.isClient);
        });
    }

    public static void registerFillBottle(Map<Item, CauldronBehavior> behavior, Item bottleItem) {
        behavior.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient) {
                Item item = stack.getItem();
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(bottleItem)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(item));
                LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
            }
            return ActionResult.success(world.isClient);
        });
    }

    public static void register() {
        // Right click with any bucket to fill
        registerBucketBehavior(MILK_CAULDRON_BEHAVIOUR);
        registerBucketBehavior(FISH_OIL_CAULDRON_BEHAVIOUR);

        // Add behaviour for filling with modded bucket
        registerModdedBucketBehavior(WATER_CAULDRON_BEHAVIOR);
        registerModdedBucketBehavior(EMPTY_CAULDRON_BEHAVIOR);
        registerModdedBucketBehavior(LAVA_CAULDRON_BEHAVIOR);

        // Right click with bottle to fill
        registerFillWithBottle(EMPTY_CAULDRON_BEHAVIOR, FISH_OIL_CAULDRON_BEHAVIOUR, MCProtein.FISH_OIL_BOTTLE, FISH_OIL_CAULDRON);
        registerFillWithBottle(EMPTY_CAULDRON_BEHAVIOR, MILK_CAULDRON_BEHAVIOUR, MCProtein.MILK_BOTTLE, MILK_CAULDRON);

        // Empty when clicked with bucket
        MILK_CAULDRON_BEHAVIOUR.put(Items.BUCKET, (state2, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state2, world, pos, player, hand, stack, new ItemStack(Items.MILK_BUCKET), state -> state.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL));
        FISH_OIL_CAULDRON_BEHAVIOUR.put(Items.BUCKET, (state2, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state2, world, pos, player, hand, stack, new ItemStack(MCProtein.FISH_OIL_BUCKET), state -> state.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL));

        // Fill a bottle when used
        registerFillBottle(MILK_CAULDRON_BEHAVIOUR, MCProtein.MILK_BOTTLE);
        registerFillBottle(FISH_OIL_CAULDRON_BEHAVIOUR, MCProtein.FISH_OIL_BOTTLE);
    }
}
