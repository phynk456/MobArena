package com.core.game.reward;

import com.core.game.reward.strategy.UpdateCountStrategy;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class NumerousItemsReward extends ItemReward
{
    private int count = 0;
    private final int maxStackSize;
    private final UpdateCountStrategy strategy;

    public NumerousItemsReward(ItemStack item, UpdateCountStrategy strategy)
    {
        super(item);
        this.strategy = strategy;
        this.maxStackSize = item.getMaxStackSize();
    }

    public void update()
    {
        count = strategy.update(count);
    }

    @Override
    public void giveReward(Player player)
    {
        if (count <= 0)
        {
            return;
        }

        int cloneCount = count;
        while (cloneCount >= maxStackSize)
        {
            cloneCount -= maxStackSize;
            player.give(item.asQuantity(maxStackSize));
        }
        player.give(item.asQuantity(cloneCount));
    }

}