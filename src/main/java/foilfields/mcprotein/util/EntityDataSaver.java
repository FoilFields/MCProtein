package foilfields.mcprotein.util;

import net.minecraft.nbt.NbtCompound;

/** Interface for custom entity data for the player.
 * <p>Used entirely for storing players swole data.</p>
 * @author woukie
 */
public interface EntityDataSaver {
    /** Gets the entities persistent data.
     * @return players persistent data
     * @author woukie
     */
    NbtCompound getPersistentData();
}
