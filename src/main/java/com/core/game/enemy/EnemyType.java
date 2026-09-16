package com.core.game.enemy;

import com.core.game.enemy.component.*;
import com.core.game.enemy.component.attribute.AttributeComponent;
import com.core.game.enemy.component.attribute.HealthAttributeComponent;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;


@NullMarked
@RequiredArgsConstructor
public enum EnemyType
{

    ZOMBIE_ADULT(Enemy.of(6, .2, 4)),
    ZOMBIE_BABY(Enemy.of(4, .25, 5).add(new AgeableComponent(true))),
    ZOMBIE_GIANT(Enemy.of(15, .19, 6.5).add(new AttributeComponent<>(1.5, Attribute.SCALE))),
    ;

    private final Enemy<?> enemy;

    public void spawn(Location location)
    {
        enemy.spawn(location);
    }

    @NullMarked
    private record Enemy<T extends LivingEntity>(Class<T> clazz, ArrayList<Component<? super T>> components)
    {

        @SafeVarargs
        private Enemy(Class<T> clazz, Component<? super T>... components)
        {
            this(clazz, new ArrayList<>(List.of(components)));
        }

        private static Enemy<Zombie> of(double health, double speed, double damage)
        {
            return new Enemy<>(
                Zombie.class,
                new AttributeComponent<>(100, Attribute.KNOCKBACK_RESISTANCE),
                new AttributeComponent<>(speed, Attribute.MOVEMENT_SPEED),
                new AttributeComponent<>(damage, Attribute.ATTACK_DAMAGE),
                new HealthAttributeComponent(health)
            );
        }

        @SafeVarargs
        private Enemy<T> add(Component<? super T>... components)
        {
            this.components.addAll(List.of(components));
            return this;
        }

        public void spawn(Location location)
        {
            location.getWorld().spawn(
                location,
                clazz,
                false,
                entity -> {
                    for (Component<? super T> component : components)
                        component.applyComponent(entity);
                }
            );
        }

    }

}
