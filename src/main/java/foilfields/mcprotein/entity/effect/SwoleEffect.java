package foilfields.mcprotein.entity.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

/** Base class for swole effects.
 * <p>Any registered swole effect will have a positive effect on the players activity leveling.</p>
 * @author woukie
 */
public class SwoleEffect extends StatusEffect {
    /**
     * Constructs the effect as a beneficial effect with a beige colour
     */
    public SwoleEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xE0D1A3);
    }

    /**
     * Gets whether the effect should be updated
     * @param duration how long the effect lasts
     * @param amplifier how much the effect effects levelling
     * @return whether the effect should be applied (true always)
     */
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    /**
     * Method for applying the effect
     * @param entity the entity to apply the effects effect to
     * @param amplifier the amplifier of the effect
     */
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

    }
}
