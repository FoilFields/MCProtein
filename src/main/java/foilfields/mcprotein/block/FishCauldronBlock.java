package foilfields.mcprotein.block;

import foilfields.mcprotein.MCProtein;
import foilfields.mcprotein.registers.RegisterCauldrons;
import foilfields.mcprotein.registers.RegisterItems;
import foilfields.mcprotein.util.BlockCheck;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.client.particle.Particle;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
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

public class FishCauldronBlock extends LeveledCauldronBlock {
    public static final IntProperty AGE = Properties.AGE_7;

    public static void addFish(Item fish) {
        EMPTY_CAULDRON_BEHAVIOR.put(fish, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient) {
                Item item = stack.getItem();
                stack.decrement(1);
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(item));
                world.setBlockState(pos, FISH_CAULDRON.getDefaultState());
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY_FISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
            }
            return ActionResult.success(world.isClient);
        });

        FISH_CAULDRON_BEHAVIOUR.put(fish, (state, world, pos, player, hand, stack) -> {
            if (state.get(LeveledCauldronBlock.LEVEL) == 3) {
                return ActionResult.PASS;
            }
            if (!world.isClient) {
                stack.decrement(1);
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));

                // The reason I separated this register
                world.setBlockState(pos, state.cycle(LeveledCauldronBlock.LEVEL).with(AGE, 0), Block.NOTIFY_LISTENERS);
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY_FISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
            }
            return ActionResult.success(world.isClient);
        });
    }

    public static void addBucket(Item bucket) {
        EMPTY_CAULDRON_BEHAVIOR.put(bucket, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient) {
                Item item = stack.getItem();
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BUCKET)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(item));
                world.setBlockState(pos, FISH_CAULDRON.getDefaultState());
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY_FISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
            }
            return ActionResult.success(world.isClient);
        });

        FISH_CAULDRON_BEHAVIOUR.put(bucket, (state, world, pos, player, hand, stack) -> {
            if (state.get(LeveledCauldronBlock.LEVEL) == 3) {
                return ActionResult.PASS;
            }
            if (!world.isClient) {
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BUCKET)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));

                // The reason I separated this register
                world.setBlockState(pos, state.cycle(LeveledCauldronBlock.LEVEL).with(AGE, 0), Block.NOTIFY_LISTENERS);
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY_FISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
            }
            return ActionResult.success(world.isClient);
        });
    }

    public static void register() {
        FISH_CAULDRON_BEHAVIOUR.put(Items.LAVA_BUCKET, FILL_WITH_LAVA);
        FISH_CAULDRON_BEHAVIOUR.put(Items.WATER_BUCKET, FILL_WITH_WATER);
        FISH_CAULDRON_BEHAVIOUR.put(Items.POWDER_SNOW_BUCKET, FILL_WITH_POWDER_SNOW);

        FISH_CAULDRON_BEHAVIOUR.put(Items.MILK_BUCKET, FILL_WITH_MILK);

        FISH_CAULDRON_BEHAVIOUR.put(RegisterItems.FISH_OIL_BUCKET, RegisterCauldrons.FILL_WITH_FISH_OIL);
        FISH_CAULDRON_BEHAVIOUR.put(RegisterItems.WHEY_BUCKET, RegisterCauldrons.FILL_WITH_WHEY);

        // Right-clicking a cauldron with a fish
        addFish(Items.TROPICAL_FISH);
        addFish(Items.PUFFERFISH);
        addFish(Items.SALMON);
        addFish(Items.COD);

        // Right-clicking a cauldron with a fish in a bucket
        addBucket(Items.AXOLOTL_BUCKET);
        addBucket(Items.COD_BUCKET);
        addBucket(Items.PUFFERFISH_BUCKET);
        addBucket(Items.SALMON_BUCKET);
        addBucket(Items.TROPICAL_FISH_BUCKET);
    }

    public FishCauldronBlock(Settings settings, Predicate<Biome.Precipitation> precipitationPredicate, Map<Item, CauldronBehavior> behaviorMap) {
        super(settings, precipitationPredicate, behaviorMap);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
        builder.add(LEVEL);
    }

    public BlockState withAge(int age) {
        return getDefaultState().with(AGE, age);
    }

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
            world.setBlockState(pos, FISH_OIL_CAULDRON.getDefaultState());
        }

        super.randomTick(state, world, pos, random);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }
}

