package com.core;

import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.jspecify.annotations.NullMarked;

import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * class for small utilities when working with {@link Bukkit}
 */
@NullMarked
// Singleton
public final class Utility
{

    private Utility()
    {
        throw new AssertionError();
    }

    public static World getMinecraftWorld(String name)
    {
        return Objects.requireNonNull(Bukkit.getWorld(NamespacedKey.minecraft(name)));
    }

    public static Path getLevelDir()
    {
        return Bukkit.getWorldContainer().toPath().resolve("world", "dimensions");
    }

    // for testing mechanics
    public static void broadcast(String msg)
    {
        Bukkit.broadcast(Component.text(msg));
    }

}
