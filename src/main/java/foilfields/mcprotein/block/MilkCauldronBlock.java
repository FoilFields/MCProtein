package foilfields.mcprotein.block;

import foilfields.mcprotein.MCProtein;
import foilfields.mcprotein.registers.RegisterBlocks;
import foilfields.mcprotein.registers.RegisterCauldrons;
import foilfields.mcprotein.registers.RegisterItems;
import foilfields.mcprotein.util.BlockCheck;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;

import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import static foilfields.mcprotein.registers.RegisterCauldrons.*;
import static net.minecraft.block.cauldron.CauldronBehavior.*;

/** Milk cauldron block class.
 * <p>Cauldron the player fills with milk to get curd and whey.</p>
 * @author woukie
 */
public class MilkCauldronBlock extends LeveledCauldronBlock {
    /** Age of the fish cauldron.
     * <p>Resets if the level is disturbed and only increases when full.</p>
     */
    public static final IntProperty AGE = Properties.AGE_7;

    /** Registers all behaviours.
     * <p>Should be called when server first initialises.</p>
     */
    public static void register() {
        MILK_CAULDRON_BEHAVIOUR.put(Items.LAVA_BUCKET, FILL_WITH_LAVA);
        MILK_CAULDRON_BEHAVIOUR.put(Items.WATER_BUCKET, FILL_WITH_WATER);
        MILK_CAULDRON_BEHAVIOUR.put(Items.POWDER_SNOW_BUCKET, FILL_WITH_POWDER_SNOW);

        MILK_CAULDRON_BEHAVIOUR.put(Items.MILK_BUCKET, FILL_WITH_MILK);

        MILK_CAULDRON_BEHAVIOUR.put(RegisterItems.FISH_OIL_BUCKET, RegisterCauldrons.FILL_WITH_FISH_OIL);
        MILK_CAULDRON_BEHAVIOUR.put(RegisterItems.WHEY_BUCKET, RegisterCauldrons.FILL_WITH_WHEY);

        // Right-clicking an empty cauldron with a bottle
        EMPTY_CAULDRON_BEHAVIOR.put(RegisterItems.MILK_BOTTLE, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient) {
                Item item = stack.getItem();
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(item));
                world.setBlockState(pos, MILK_CAULDRON.getDefaultState());
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
            }
            return ActionResult.success(world.isClient);
        });

        // Right-clicking a cauldron with a milk bottle
        MILK_CAULDRON_BEHAVIOUR.put(RegisterItems.MILK_BOTTLE, (state, world, pos, player, hand, stack) -> {
            if (state.get(LeveledCauldronBlock.LEVEL) == 3) {
                return ActionResult.PASS;
            }
            if (!world.isClient) {
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));

                // The reason I separated this register
                world.setBlockState(pos, state.cycle(LeveledCauldronBlock.LEVEL).with(AGE, 0), Block.NOTIFY_LISTENERS);
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
            }
            return ActionResult.success(world.isClient);
        });

        // Right-clicking cauldron with a glass bottle
        MILK_CAULDRON_BEHAVIOUR.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient) {
                Item item = stack.getItem();
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(RegisterItems.MILK_BOTTLE)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(item));

                int i = state.get(LEVEL) - 1;
                // The reason I separated this register
                world.setBlockState(pos, i == 0 ? Blocks.CAULDRON.getDefaultState() : state.with(LEVEL, i).with(AGE, 0));

                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
            }
            return ActionResult.success(world.isClient);
        });

        MILK_CAULDRON_BEHAVIOUR.put(Items.BUCKET, (state2, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state2, world, pos, player, hand, stack, new ItemStack(Items.MILK_BUCKET), state -> state.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL));
    }

    /** Default milk cauldron constructor.
     * <p>Registers default state as well.</p>
     * @param settings block settings
     * @param precipitationPredicate precipitation predicate, unused
     * @param behaviorMap behaviour map
     */
    public MilkCauldronBlock(Settings settings, Predicate<Biome.Precipitation> precipitationPredicate, Map<Item, CauldronBehavior> behaviorMap) {
        super(settings, precipitationPredicate, behaviorMap);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    /** Adds properties to the cauldron.
     * @param builder builder
     */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
        builder.add(LEVEL);
    }

    /** Get BlockState with a specific age.
     * @param age age of block to get
     * @return the BlockState with the specified age
     */
    public BlockState withAge(int age) {
        return getDefaultState().with(AGE, age);
    }

    /** Handles leveling the cauldron.
     * <p>Calculates whether the block should age on a random tick.</p>
     * <p>Chance to age increases if heated.</p>
     * <p>Also manages switching to aged state.</p>
     * @param state BlockState of the block
     * @param world world the block is in
     * @param pos position of the block
     * @param random randomness
     */
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int age = state.get(AGE);
        int level = state.get(LEVEL);

        BlockPos blockBelow = new BlockPos(pos).add(0, -1, 0);
        if (age < 7 && (BlockCheck.isHeatSource(world.getBlockState(blockBelow)) || random.nextInt(0, 3) == 0) && level == 3) {
            world.spawnParticles(ParticleTypes.POOF, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 10, 0.2, 0, 0.2, 0);
            world.playSound(null, pos, SoundEvents.BLOCK_SLIME_BLOCK_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
            world.setBlockState(pos, this.withAge(age + 1).with(LEVEL, level), Block.NOTIFY_LISTENERS);
        }

        if (age >= 7) {
            world.spawnParticles(ParticleTypes.POOF, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 10, 0.2, 0, 0.2, 0);
            world.playSound(null, pos, SoundEvents.BLOCK_HONEY_BLOCK_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);

            world.setBlockState(pos, RegisterCauldrons.WHEY_CAULDRON.getDefaultState());
            BlockPos blockAbove = new BlockPos(pos).add(0, 1, 0);
            if (world.getBlockState(blockAbove).isAir()) {
                world.setBlockState(blockAbove, RegisterBlocks.CURD_BLOCK.getDefaultState());
            }
        }

        super.randomTick(state, world, pos, random);
    }

    /**
     * Enables random ticks for this block
     * @param state BlockState of the block
     * @return whether the block has random ticks
     */
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }
}

