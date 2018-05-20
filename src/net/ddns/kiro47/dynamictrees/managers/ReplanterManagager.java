package net.ddns.kiro47.dynamictrees.managers;

import net.ddns.kiro47.dynamictrees.utils.Configurations;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Sapling;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.LinkedList;

public class ReplanterManagager
{

    /**
     * Entity Linked List of saplings, need to keep as an entity as we need access to lived ticks
     */
    private LinkedList<Entity> list;

    private boolean schedulerRunning = false;

    private static ReplanterManagager instance = new ReplanterManagager();

    public static ReplanterManagager getInstance()
    {
        return instance;
    }

    private ReplanterManagager()
    {
        this.list = new LinkedList<>();
    }

    /**
     *
     * Adds a sapling entity to the list to be replanted
     *
     * @param entity  Sapling entity to be replanted.
     *                This is expected to be a sapling if not it will cause issues.
     */
    public static void addItem(Entity entity)
    {
        // decided on addLast instead of add as it is void
        // and there's no need for a return bool
        instance.list.addLast(entity);
    }

    /**
     * Starts the replant scheduler
     *
     */
    public void startScheduler(Plugin plugin)
    {
        if (!(schedulerRunning))
        {

            BukkitScheduler scheduler = plugin.getServer().getScheduler();
            scheduler.scheduleSyncRepeatingTask(
                    plugin,
                    new ReplantScheduler(),
                    100,
                    Configurations.REPLANT_TIME
                    );
            schedulerRunning = true;
        }
    }

    private class ReplantScheduler implements Runnable
    {

        /**
         * Runnable that executes a replant
         */
        public void run()
        {

            // using normal for instead of for each to maintain a location control

            for (int i = 0; i < list.size(); i++)
            {
                Entity entity = list.get(i);
                if (entity.getTicksLived() >= Configurations.REPLANT_TIME)
                {
                    replant(entity);
                    list.remove(i);
                }
            }

        }

        /**
         * Begin proccess of evalulating places to replant the sapling into the ground
         *
         * @param entity The entity (which is a sapling) to be replanted
         */
        private void replant(Entity entity) throws ClassCastException
        {
            ItemStack itemStack = (ItemStack) entity;
            TreeSpecies species = ((Sapling) itemStack.getData()).getSpecies();
            int amount = itemStack.getAmount();
            Location location = entity.getLocation();

            for (int i = 0; i < amount; i++)
            {
                if (isValid(location))
                {
                    plant(location, species);
                }
            }
        }

        /**
         * Plants a tree sapling at the specified location
         *
         * @param location Location to plant the sapling
         * @param species Type of tree species to plant
         */
        private void plant(Location location , TreeSpecies species)
        {
            Block block = location.getBlock();

            block.setType(Material.SAPLING);

            Sapling sapling = (Sapling) block;

            sapling.setSpecies(species);
        }

        /**
         * Tests if the spot is suitable for planting a sapling
         *
         * @param location The location to test
         * @return Returns true is suitable for planting a sapling, false otherwise
         */
        private boolean isValid(Location location)
        {
            // check cache

            /*
             * The "block" space where the sapling would go
             */
            Block space = location.getBlock();

            if (!(space.getType().equals(Material.AIR)))
            {
                return false;
            }


            /*
             * The block to be planted upon
             */
            Block plotBlock = location.subtract(0,1,0).getBlock();

            /*
             * Materials that a sapling can grow on
             * COARSE_DIRT and PODZOL are included as DIRT with metadata
             * Grass path can't support trees surprisingly
             */
            if (!(plotBlock.getType().equals(Material.GRASS) || plotBlock.getType().equals(Material.DIRT)))
            {
                return false;
            }

            return true;
        }

    }
}
