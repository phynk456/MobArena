package com.core.game.enemy.component;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Ageable;
import org.jspecify.annotations.NullMarked;

@NullMarked
@RequiredArgsConstructor
public final class AgeableComponent implements Component<Ageable>
{

    private final boolean isBaby;

    @Override
    public void applyComponent(Ageable entity)
    {
        if (isBaby)
        {
            entity.setBaby();
        }
        else
        {
            entity.setAdult();
        }
        entity.setAgeLock(true);
    }

}
