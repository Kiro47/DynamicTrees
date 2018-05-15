package net.ddns.kiro47.dynamictrees.managers;

import net.ddns.kiro47.dynamictrees.utils.Configurations;
import org.bukkit.Location;
import org.bukkit.TreeSpecies;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Sapling;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Stack;

public class ReplanterManagager
{

    private Stack<Entity> stack;

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
        ItemStack itemStack = (entity).getItemStack();
        Sapling sapling = (Sapling) itemStack.getData();
        this.stack.push(entity);
    }

    private class replantScheduler extends BukkitRunnable
    {

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
                // calculate distance from existance
                // If spot is empty plant();
                //Configurations.REPLANT_RANGE;
                //Configurations.NEAREST_TREE;
            }
        }

        private void plant(Location location , TreeSpecies species)
        {

        }

    }
}
