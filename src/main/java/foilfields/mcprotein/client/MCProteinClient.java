package foilfields.mcprotein.client;

import foilfields.mcprotein.MCProtein;
import foilfields.mcprotein.entity.passive.WheyGolemEntityModel;
import foilfields.mcprotein.entity.passive.WheyGolemRenderer;
import foilfields.mcprotein.networking.SwoleMessages;
import foilfields.mcprotein.registers.RegisterBlocks;
import foilfields.mcprotein.registers.RegisterEntities;
import foilfields.mcprotein.registers.RegisterFluids;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

/**
 * Client side entry point for MCProtein.
 * <p>A mod initializer ran only on {@link EnvType#CLIENT}.</p>
 * <p>Mainly handles initialising rendering and packets.</p>
 */
@Environment(EnvType.CLIENT)
public class MCProteinClient implements ClientModInitializer {
    /**
     * Model layer for the whey golem.
     */
    public static final EntityModelLayer MODEL_WHEY_GOLEM_LAYER = new EntityModelLayer(new Identifier(MCProtein.MOD_ID, "whey_golem"), "main");

    /**
     * Entry point for client
     * <p>Mainly handles initialising fluid rendering, entity renderers and S2C packets</p>
     */
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(RegisterEntities.WHEY_GOLEM, WheyGolemRenderer::new);
        EntityRendererRegistry.register(RegisterEntities.WHEYBALL, FlyingItemEntityRenderer::new);

        SwoleMessages.RegisterS2CPackets();

        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CURD_BLOCK, RenderLayer.getCutout());

        EntityModelLayerRegistry.registerModelLayer(MODEL_WHEY_GOLEM_LAYER, WheyGolemEntityModel::getTexturedModelData);

        FluidRenderHandlerRegistry.INSTANCE.register(RegisterFluids.STILL_FISH_OIL, RegisterFluids.FLOWING_FISH_OIL, new SimpleFluidRenderHandler(
                new Identifier("minecraft:block/water_still"),
                new Identifier("minecraft:block/water_flow"),
                0xffd700
        ));

        FluidRenderHandlerRegistry.INSTANCE.register(RegisterFluids.STILL_WHEY, RegisterFluids.FLOWING_WHEY, new SimpleFluidRenderHandler(
                new Identifier("minecraft:block/water_still"),
                new Identifier("minecraft:block/water_flow"),
                0xf6ebd8
        ));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), RegisterFluids.STILL_FISH_OIL, RegisterFluids.FLOWING_FISH_OIL);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), RegisterFluids.STILL_WHEY, RegisterFluids.FLOWING_WHEY);
    }
}
