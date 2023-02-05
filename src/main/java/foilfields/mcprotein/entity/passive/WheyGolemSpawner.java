package foilfields.mcprotein.entity.passive;

import foilfields.mcprotein.MCProtein;
import foilfields.mcprotein.registers.RegisterBlocks;
import foilfields.mcprotein.registers.RegisterEntities;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Whey golem spawner class.
 * <p>Checks for a specific configuration when a reinforced pumpkin is placed. Spawns a golem if the conditions are met.</p>
 * @author woukie
 */
public class WheyGolemSpawner {
    public static void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack, CallbackInfo info) {
        if (state.getBlock() != RegisterBlocks.REINFORCED_CARVED_PUMPKIN) {
            return;
        }

        var pattern = BlockPatternBuilder.start().aisle("^", "#", "#")
                .where('^', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(RegisterBlocks.REINFORCED_CARVED_PUMPKIN)))
                .where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(RegisterBlocks.WHEY_PROTEIN_BLOCK)))
                .build();

        BlockPattern.Result result = pattern.searchAround(world, pos);

        if (result != null) {
            int width = pattern.getWidth();
            int height = pattern.getHeight();

            for(int x = 0; x < width; ++x) {
                for(int y = 0; y < height; ++y) {
                    CachedBlockPosition cachedBlockPosition3 = result.translate(x, y, 0);
                    world.setBlockState(cachedBlockPosition3.getBlockPos(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
                    world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, cachedBlockPosition3.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition3.getBlockState()));
                }
            }

            BlockPos blockPos2 = result.translate(0, 2, 0).getBlockPos();
            WheyGolemEntity golem = RegisterEntities.WHEY_GOLEM.create(world);

            assert golem != null;
            golem.refreshPositionAndAngles((double)blockPos2.getX() + 0.5D, (double)blockPos2.getY() + 0.05D, (double)blockPos2.getZ() + 0.5D, 0.0F, 0.0F);
            world.spawnEntity(golem);

            world.getNonSpectatingEntities(ServerPlayerEntity.class, golem.getBoundingBox().expand(5.0D)).forEach(player -> {
                Criteria.SUMMONED_ENTITY.trigger(player, golem);
            });

            for(var x = 0; x < pattern.getWidth(); ++x) {
                for(int y = 0; y < pattern.getHeight(); ++y) {
                    CachedBlockPosition position = result.translate(x, y, 0);
                    world.updateNeighbors(position.getBlockPos(), Blocks.AIR);
                }
            }

            info.cancel();
        }
    }
}
