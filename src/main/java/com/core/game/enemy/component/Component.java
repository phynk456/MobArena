package com.core.game.enemy.component;

import org.bukkit.entity.LivingEntity;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface Component<T extends LivingEntity>
{

    void applyComponent(T entity);

}