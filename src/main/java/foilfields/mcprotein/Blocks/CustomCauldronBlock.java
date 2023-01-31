package foilfields.mcprotein.Blocks;

import foilfields.mcprotein.MCProtein;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public abstract class CustomCauldronBlock extends CauldronBlock {

    public CustomCauldronBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(player.getStackInHand(hand).getItem() ==  Items.BUCKET) {
            player.getStackInHand(hand).decrement(1);
            ItemStack newBucket = new ItemStack(MCProtein.FISH_OIL_BUCKET);
            player.setStackInHand(hand, newBucket);
            Block.replace(state, Blocks.CAULDRON.getDefaultState(), world, pos, 0);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean isFull(BlockState state) {
        return false;
    }

}
