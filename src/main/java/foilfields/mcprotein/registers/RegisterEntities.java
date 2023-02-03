package foilfields.mcprotein.registers;

import foilfields.mcprotein.entity.passive.WheyGolemEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static foilfields.mcprotein.MCProtein.MOD_ID;

public class RegisterEntities {
    public static final EntityType<WheyGolemEntity> WHEY_GOLEM = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MOD_ID, "whey_golem"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WheyGolemEntity::new).dimensions(EntityDimensions.fixed(0.7f, 1.9f)).build());

    public static void register() {
        FabricDefaultAttributeRegistry.register(WHEY_GOLEM, WheyGolemEntity.createMobAttributes());
    }
}
