package foilfields.mcprotein.registers;

import foilfields.mcprotein.entity.effect.SwoleEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static foilfields.mcprotein.MCProtein.MOD_ID;

public class RegisterStatusEffects {
    public static StatusEffect PROTEIN = new SwoleEffect();
    public static StatusEffect CREATINE = new SwoleEffect();

    public static void register() {
        PROTEIN = Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "protein"), PROTEIN);
        CREATINE = Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "creatine"), CREATINE);
    }
}
