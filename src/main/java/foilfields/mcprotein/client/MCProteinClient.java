package foilfields.mcprotein.client;

import foilfields.mcprotein.registers.RegisterFluids;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MCProteinClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
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
