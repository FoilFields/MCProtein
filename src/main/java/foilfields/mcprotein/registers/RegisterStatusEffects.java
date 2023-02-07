package foilfields.mcprotein.registers;

import foilfields.mcprotein.entity.effect.SwoleEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static foilfields.mcprotein.MCProtein.MOD_ID;

/**
 * Class for registering modded status effects.
 */
public class RegisterStatusEffects {
    public static StatusEffect PROTEIN = new SwoleEffect();
    public static StatusEffect CREATINE = new SwoleEffect();
    public static StatusEffect BCAA = new SwoleEffect();
    public static StatusEffect NITRIC_OXIDE_BOOSTER = new SwoleEffect();

    /** Registers status effects.
     * <p>Should be called once when the server initialises.</p>
     */
    public static void register() {
        PROTEIN = Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "protein"), PROTEIN);
        CREATINE = Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "creatine"), CREATINE);
        BCAA = Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "bcaa"), BCAA);
        NITRIC_OXIDE_BOOSTER = Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "nitric_oxide_booster"), NITRIC_OXIDE_BOOSTER);
    }
}
