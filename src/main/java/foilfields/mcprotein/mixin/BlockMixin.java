package foilfields.mcprotein.mixin;

import foilfields.mcprotein.entity.passive.WheyGolemSpawner;
import foilfields.mcprotein.networking.SwoleMessages;
import foilfields.mcprotein.util.EntityDataSaver;
import foilfields.mcprotein.util.SwoleData;
import net.fabricmc.fabric.api.block.v1.FabricBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Block mixin.
 * <p>Responsible for adding stats to the player and spawning golems</p>
 * @author woukie
 */
@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock
        implements ItemConvertible,
        FabricBlock {

    /** Shadow constructor.
     * @param settings settings
     */
    public BlockMixin(Settings settings) {
        super(settings);
    }

    /** Checks for if a golem should be spawned.
     * @param world world
     * @param pos position of block place
     * @param state block state when placed
     * @param placer entity responsible for placing the block
     * @param itemStack the itemstack
     * @param ci callback info
     * @author woukie
     */
    @Inject(at = @At("HEAD"), method = "onPlaced(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V", cancellable = true)
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        WheyGolemSpawner.onPlaced(world, pos, state, placer, itemStack, ci);
    }

    /** Adds points to the user when a block us broken.
     * <p>Points added are loosely proportional to how long it took to mine the block</p>
     * @param world world
     * @param pos position of block place
     * @param state block state when placed
     * @param player player who broke the block
     * @param ci callback info
     * @author woukie
     */
    @Inject(at = @At("HEAD"), method = "onBreak(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;)V")
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci) {
        if (!world.isClient()) {
            SwoleData.addStat((EntityDataSaver) player, (int) (1.0f / calcBlockBreakingDelta(state, player, world, pos)), "mine", SwoleMessages.MINE_SYNC_ID);
        }
    }
}
