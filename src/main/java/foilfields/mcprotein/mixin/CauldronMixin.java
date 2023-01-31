package foilfields.mcprotein.mixin;

import foilfields.mcprotein.MCProtein;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractCauldronBlock.class)
public abstract class CauldronMixin {
    @Shadow public abstract boolean isFull(BlockState state);

    //on use cauldron
    @Inject(at=@At("HEAD"), method = "onUse(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ActionResult;")
    protected void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir){
        if (!player.getStackInHand(hand).isEmpty() && !isFull(state)){
            if (player.getStackInHand(hand).getItem() ==  MCProtein.FISH_OIL_BUCKET){
                player.getStackInHand(hand).decrement(1);
                ItemStack newBucket = new ItemStack(Items.BUCKET);
                player.setStackInHand(hand, newBucket);
                Block.replace(state, MCProtein.FISH_OIL_CAULDRON.getDefaultState(), world, pos, 0);

            } else if (player.getStackInHand(hand).getItem() == Items.SALMON){
                player.getStackInHand(hand).decrement(1);
                Block.replace(state, MCProtein.FISH_OIL_CAULDRON.getDefaultState(), world, pos, 0);

            }
        }
    }


}
