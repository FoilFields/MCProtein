package foilfields.mcprotein.registers;

import foilfields.mcprotein.MCProtein;
import foilfields.mcprotein.block.FishCauldronBlock;
import foilfields.mcprotein.block.MilkCauldronBlock;
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

// Steps for adding a cauldron
// 1) Declare cauldron behaviour, block and fill behaviour
// 2) Add a line to the 'registerModdedBucketBehavior()' method
// 3) Add these lines to the register method
//      a) Call registerBucketBehavior(CAULDRON_BEHAVIOUR);
//      b) Call registerFillWithBottle(CAULDRON_BEHAVIOUR, YOUR_BOTTLE, CAULDRON_BLOCK);
//      c) See other lines for adding additional bucket behaviour
//      d) Call registerFillBottle(CAULDRON_BEHAVIOUR, YOUR_BOTTLE);

public class RegisterCauldrons {

    //      CAULDRONS

    // MILK
    // moved to its own class due to additional age behaviour
    public static final Map<Item, CauldronBehavior> MILK_CAULDRON_BEHAVIOUR = CauldronBehavior.createMap();
    public static final Block MILK_CAULDRON = Registry.register(Registry.BLOCK, new Identifier(MCProtein.MOD_ID, "milk_cauldron"), new MilkCauldronBlock(AbstractBlock.Settings.copy(CAULDRON), LeveledCauldronBlock.RAIN_PREDICATE, MILK_CAULDRON_BEHAVIOUR));
    public static final CauldronBehavior FILL_WITH_MILK = (state, world, pos, player, hand, stack) -> CauldronBehavior.fillCauldron(world, pos, player, hand, stack, MILK_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY);

    // FISH
    // Also uses its own class like milk (also has no bucket behaviour)
    public static final Map<Item, CauldronBehavior> FISH_CAULDRON_BEHAVIOUR = CauldronBehavior.createMap();
    public static final Block FISH_CAULDRON = Registry.register(Registry.BLOCK, new Identifier(MCProtein.MOD_ID, "fish_cauldron"), new FishCauldronBlock(AbstractBlock.Settings.copy(CAULDRON), LeveledCauldronBlock.RAIN_PREDICATE, FISH_CAULDRON_BEHAVIOUR));

    // FISH OIL
    public static final Map<Item, CauldronBehavior> FISH_OIL_CAULDRON_BEHAVIOUR = CauldronBehavior.createMap();
    public static final Block FISH_OIL_CAULDRON = Registry.register(Registry.BLOCK, new Identifier(MCProtein.MOD_ID, "fish_oil_cauldron"), new LeveledCauldronBlock(AbstractBlock.Settings.copy(CAULDRON), LeveledCauldronBlock.RAIN_PREDICATE, FISH_OIL_CAULDRON_BEHAVIOUR));
    public static final CauldronBehavior FILL_WITH_FISH_OIL = (state, world, pos, player, hand, stack) -> CauldronBehavior.fillCauldron(world, pos, player, hand, stack, FISH_OIL_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY);

    // WHEY
    public static final Map<Item, CauldronBehavior> WHEY_CAULDRON_BEHAVIOUR = CauldronBehavior.createMap();
    public static final Block WHEY_CAULDRON = Registry.register(Registry.BLOCK, new Identifier(MCProtein.MOD_ID, "whey_cauldron"), new LeveledCauldronBlock(AbstractBlock.Settings.copy(CAULDRON), LeveledCauldronBlock.RAIN_PREDICATE, WHEY_CAULDRON_BEHAVIOUR));
    public static final CauldronBehavior FILL_WITH_WHEY = (state, world, pos, player, hand, stack) -> CauldronBehavior.fillCauldron(world, pos, player, hand, stack, WHEY_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY);

    public static void registerBucketBehavior(Map<Item, CauldronBehavior> behavior) {
        behavior.put(Items.LAVA_BUCKET, FILL_WITH_LAVA);
        behavior.put(Items.WATER_BUCKET, FILL_WITH_WATER);
        behavior.put(Items.POWDER_SNOW_BUCKET, FILL_WITH_POWDER_SNOW);
        registerModdedBucketBehavior(behavior);
    }

    public static void registerModdedBucketBehavior(Map<Item, CauldronBehavior> behavior) {
        behavior.put(Items.MILK_BUCKET, FILL_WITH_MILK);
        behavior.put(MCProtein.FISH_OIL_BUCKET, RegisterCauldrons.FILL_WITH_FISH_OIL);
        behavior.put(MCProtein.WHEY_BUCKET, RegisterCauldrons.FILL_WITH_WHEY);
    }

    public static void registerFillWithBottle(Map<Item, CauldronBehavior> fullBehaviour, Item bottleItem, Block cauldron) {
        EMPTY_CAULDRON_BEHAVIOR.put(bottleItem, (state, world, pos, player, hand, stack) -> {
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
        // Add behaviour for filling cauldron with modded bucket
        registerModdedBucketBehavior(WATER_CAULDRON_BEHAVIOR);
        registerModdedBucketBehavior(EMPTY_CAULDRON_BEHAVIOR);
        registerModdedBucketBehavior(LAVA_CAULDRON_BEHAVIOR);

        // Right click with any bucket to fill cauldron
        registerBucketBehavior(FISH_OIL_CAULDRON_BEHAVIOUR);
        registerBucketBehavior(WHEY_CAULDRON_BEHAVIOUR);

        // Right click with bottle to fill cauldron
        registerFillWithBottle(FISH_OIL_CAULDRON_BEHAVIOUR, MCProtein.FISH_OIL_BOTTLE, FISH_OIL_CAULDRON);
        registerFillWithBottle(WHEY_CAULDRON_BEHAVIOUR, MCProtein.WHEY_BOTTLE, WHEY_CAULDRON);

        // Empty when clicked with bucket
        FISH_OIL_CAULDRON_BEHAVIOUR.put(Items.BUCKET, (state2, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state2, world, pos, player, hand, stack, new ItemStack(MCProtein.FISH_OIL_BUCKET), state -> state.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL));
        WHEY_CAULDRON_BEHAVIOUR.put(Items.BUCKET, (state2, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state2, world, pos, player, hand, stack, new ItemStack(MCProtein.WHEY_BUCKET), state -> state.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL));

        // Fill a bottle when used
        registerFillBottle(FISH_OIL_CAULDRON_BEHAVIOUR, MCProtein.FISH_OIL_BOTTLE);
        registerFillBottle(WHEY_CAULDRON_BEHAVIOUR, MCProtein.WHEY_BOTTLE);
    }
}
