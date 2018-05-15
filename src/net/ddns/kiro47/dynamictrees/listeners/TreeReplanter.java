package net.ddns.kiro47.dynamictrees.listeners;

import net.ddns.kiro47.dynamictrees.managers.ReplanterManagager;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Sapling;
import org.bukkit.material.Tree;

public class TreeReplanter implements Listener
{

    /**
     *
     * Adds new sapling
     *
     * @param entitySpawnEvent Event when entities spawn in minecraft, used to capture saplings spawning from tree
     */
    @EventHandler
    public void onSaplingDrop(EntitySpawnEvent entitySpawnEvent)
    {
        if (entitySpawnEvent.getEntityType().equals(EntityType.DROPPED_ITEM))
        {
            Item entity = (( Item )entitySpawnEvent.getEntity());

            if (entity.getItemStack().getType().equals(Material.SAPLING))
            {
                ReplanterManagager.getInstance().addItem(entity);
            }
        }
    }


}
