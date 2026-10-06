package com.core.game.reward;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class NumerousItemsReward extends AbstractItemReward
{

    private int count = 0;
    private final int maxStackSize;

    public NumerousItemsReward(ItemStack item)
    {
        super(item);
        maxStackSize = item.getMaxStackSize();
        item.setAmount(maxStackSize);
    }

    public void add(int qty)
    {
        count += qty;
    }

    @Override
    public void giveReward(Player player)
    {
        int cloneCount = count;
        while (cloneCount >= maxStackSize)
        {
            cloneCount -= maxStackSize;
            player.give(item.asQuantity(maxStackSize));
        }
        player.give(item.asQuantity(cloneCount));
    }

}
