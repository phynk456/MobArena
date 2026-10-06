package com.core.game.reward;

import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class SingleItemReward extends AbstractItemReward
{
    public SingleItemReward(ItemStack item)
    {
        item.setAmount(1);
        super(item);
    }
}
