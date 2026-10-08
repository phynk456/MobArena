package com.core.game.reward;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

@NullMarked
@RequiredArgsConstructor
public sealed abstract class ItemReward implements Reward permits NumerousItemsReward, ItemReward.SingleItemReward
{

    protected final ItemStack item;

    @Override
    public void giveReward(Player player)
    {
        player.give(item);
    }

    @NullMarked
    public static final class SingleItemReward extends ItemReward
    {

        public SingleItemReward(ItemStack item)
        {
            item.setAmount(1);
            super(item);
        }

    }

}
