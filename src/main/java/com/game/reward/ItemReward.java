package com.game.reward;

import com.game.reward.strategy.UpdateCountStrategy;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

import static com.game.reward.ItemReward.*;

@NullMarked
@RequiredArgsConstructor
public sealed abstract class ItemReward implements Reward permits SingleItemReward, NumerousItemsReward {

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

    @NullMarked
    public static final class NumerousItemsReward extends ItemReward
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

}
