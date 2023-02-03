package foilfields.mcprotein.entity.passive;

import foilfields.mcprotein.MCProtein;
import foilfields.mcprotein.client.MCProteinClient;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class WheyGolemRenderer extends MobEntityRenderer<WheyGolemEntity, WheyGolemEntityModel<WheyGolemEntity>> {

    public WheyGolemRenderer(EntityRendererFactory.Context context) {
        super(context, new WheyGolemEntityModel<>(context.getPart(MCProteinClient.MODEL_WHEY_GOLEM_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(WheyGolemEntity entity) {
        return new Identifier(MCProtein.MOD_ID, "textures/entity/whey_golem.png");
    }
}
