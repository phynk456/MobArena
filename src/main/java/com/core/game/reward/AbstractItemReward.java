package com.core.game.reward;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

@NullMarked
@RequiredArgsConstructor
public abstract class AbstractItemReward implements Reward
{

    protected final ItemStack item;

    @Override
    public void giveReward(Player player)
    {
        player.give(item);
    }

}
