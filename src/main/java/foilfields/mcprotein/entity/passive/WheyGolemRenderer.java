package foilfields.mcprotein.entity.passive;

import foilfields.mcprotein.MCProtein;
import foilfields.mcprotein.client.MCProteinClient;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

/** Whey golem renderer class.
 * @author woukie
 */
public class WheyGolemRenderer extends MobEntityRenderer<WheyGolemEntity, WheyGolemEntityModel<WheyGolemEntity>> {

    /**
     * Calls super and registers the golem layer
     * @param context context
     */
    public WheyGolemRenderer(EntityRendererFactory.Context context) {
        super(context, new WheyGolemEntityModel<>(context.getPart(MCProteinClient.MODEL_WHEY_GOLEM_LAYER)), 0.5f);
    }

    /**
     * Gets the texture of the whey golem entity
     * @param entity entity
     * @return an identifier pointing to the texture
     */
    @Override
    public Identifier getTexture(WheyGolemEntity entity) {
        return new Identifier(MCProtein.MOD_ID, "textures/entity/whey_golem.png");
    }
}
