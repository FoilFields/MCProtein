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
    public static StatusEffect WHEY_PROTEIN = new SwoleEffect();
    public static StatusEffect CASEIN_PROTEIN = new SwoleEffect();
    public static StatusEffect CREATINE = new SwoleEffect();
    public static StatusEffect BCAA = new SwoleEffect();
    public static StatusEffect NITRIC_OXIDE_BOOSTER = new SwoleEffect();
    public static StatusEffect GLUTAMINE = new SwoleEffect();
    public static StatusEffect DEXTROSE = new SwoleEffect();
    public static StatusEffect CAFFEINE = new SwoleEffect();

    /** Registers status effects.
     * <p>Should be called once when the server initialises.</p>
     */
    public static void register() {
        WHEY_PROTEIN = Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "whey_protein"), WHEY_PROTEIN);
        CASEIN_PROTEIN = Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "casein_protein"), CASEIN_PROTEIN);
        CREATINE = Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "creatine"), CREATINE);
        BCAA = Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "bcaa"), BCAA);
        NITRIC_OXIDE_BOOSTER = Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "nitric_oxide_booster"), NITRIC_OXIDE_BOOSTER);
        GLUTAMINE = Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "glutamine"), GLUTAMINE);
        DEXTROSE = Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "dextrose"), DEXTROSE);
        CAFFEINE = Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "caffeine"), CAFFEINE);
    }
}
