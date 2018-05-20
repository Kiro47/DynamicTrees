package net.ddns.kiro47.dynamictrees.managers;

import net.ddns.kiro47.dynamictrees.utils.Configurations;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Sapling;
import org.bukkit.scheduler.BukkitRunnable;

import javax.swing.text.MutableAttributeSet;
import java.util.HashMap;
import java.util.Stack;

public class ReplanterManagager
{

    /**
     * Entity Stack of saplings, need to keep as an entity as we need access to lived ticks
     */
    private Stack<Entity> stack;

    /*
     * Materials that a sapling can grow on
     * COARSE_DIRT and PODZOL are included as DIRT with metadata
     */
    private static ReplanterManagager instance = new ReplanterManagager();

    public static ReplanterManagager getInstance()
    {
        return instance;
    }

    private ReplanterManagager()
    {
        this.stack = new Stack();
    }

    public void addItem(Entity entity)
    {
        this.stack.push(entity);
    }

    private class replantScheduler extends BukkitRunnable
    {

        /*
         *   Dev Thoughts:
         *   These maps are used to prevent constant duplicate checking
         *   of spaces, which will signifcantly lower overhead over time
         *   on check beyond a few blocks range.  However, the extra object
         *   instantion could also cause poor memory as often times saplings
         *   would drop in a perfectly acceptable place or directly next to one.
         *   This caching method should probably be tested to determine if
         *   the caching is good in the long run as most replant evals are
         *   likely going to be short range.
         */
        private HashMap<Location, Boolean> hasSapling = new HashMap<>();
        private HashMap<Location, Boolean> isValid = new HashMap<>();

        @Override
        public void run()
        {
            for (Entity entity : stack)
            {
                if (entity.getTicksLived() >= Configurations.REPLANT_TIME)
                {
                    replant(entity);
                }
            }
        }

        private void replant(Entity entity)
        {
            ItemStack itemStack = (ItemStack) entity;
            TreeSpecies species = ((Sapling) itemStack.getData()).getSpecies();
            int amount = itemStack.getAmount();
            Location location = entity.getLocation();

            for (int i = 0; i < amount; i++)
            {
                if (isValid(entity.getLocation()) && awayFromOthers(entity.getLocation()))
                {
                    Sapling sapling = (Sapling)  entity;
                    plant(entity.getLocation(), sapling.getSpecies());
                }
                // calculate distance from existance
                // If spot is empty plant();
                //Configurations.REPLANT_RANGE;
                //Configurations.NEAREST_TREE;
            }
        }

        private void plant(Location location , TreeSpecies species)
        {

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

            /**
             * The "block" space where the sapling would go
             */
            Block space = location.getBlock();

            if (!(space.equals(Material.AIR)))
            {
                return false;
            }


            /**
             * The block to be planted upon
             */
            Block plotBlock = location.subtract(0,1,0).getBlock();

            if (!(plotBlock.equals(Material.GRASS) || plotBlock.equals(Material.DIRT)))
            {
                return false;
            }

            return true;
        }

        private boolean awayFromOthers(Location location)
        {
            // check cache

            int nearestTreeRange = Configurations.NEAREST_TREE;
            int range = Configurations.REPLANT_RANGE;

            Stack<Location> radialSelection = new Stack();

            for (int i = 0, j = i + 1 ; i < nearestTreeRange; i++ )
            {
                // clockwise grab
                radialSelection.add(location.add(1, 0, 1));
                radialSelection.add(location.add(0, 0, 1));
                radialSelection.add(location.add(-1, 0, 1));
                radialSelection.add(location.add(-1, 0, 0));
                radialSelection.add(location.add(-1, 0, -1));
                radialSelection.add(location.add(0, 0, -1));
                radialSelection.add(location.add(1, 0, -1));
                radialSelection.add(location.add(1, 0, 0));
            }

            for (Location checkLocation : radialSelection)
            {
                // check if contains sapling or log
            }

        }
    }
}
