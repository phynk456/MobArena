package com.core.game;

import com.core.Core;
import com.core.game.enemy.EnemyType;
import lombok.Getter;
import org.bukkit.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public final class GameArena
{

    private final static Path GAME_DIMENSIONS_PATH = Core.getServerFromInstance().getLevelDirectory().resolve("dimensions", "game");
    private static final Path REFERENCE_PATH = GAME_DIMENSIONS_PATH.resolve("reference");

    @Getter private final Location entitySpawn;
    @Getter private final Location playerSpawn;
    @Nullable private World world;

    public GameArena(String folderName) throws IOException
    {
        try (Stream<Path> stream = Files.walk(REFERENCE_PATH))
        {
            Path targetPath = GAME_DIMENSIONS_PATH.resolve(folderName);
            for (Path current : (Iterable<Path>) stream::iterator)
            {
                Path target = targetPath.resolve(REFERENCE_PATH.relativize(current));
                if (Files.isDirectory(current))
                {
                    Files.createDirectories(target);
                }
                else
                {
                    Files.copy(current, target, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
        this.world = Objects.requireNonNull(
            Bukkit.createWorld(
                new WorldCreator(
                    new NamespacedKey("game", folderName.toLowerCase())
                )
            )
        );
        this.playerSpawn = world.getSpawnLocation();
        this.entitySpawn = new Location(world, 0, 0, 0);
    }

    public void spawnEntities(List<EnemyType> enemies)
    {
        for (EnemyType enemy : enemies)
        {
            enemy.spawn(entitySpawn);
        }
    }

    public void delete(boolean save)
    {
        if (world == null)
        {
            throw new IllegalStateException();
        }
        Bukkit.getScheduler().runTaskLater(Core.getInstance(), _ -> {
            Bukkit.unloadWorld(world, save);
            deleteFolder(world.getWorldFolder());
            world = null;
        }, 1L);
    }

    private static void deleteFolder(File folder)
    {
        for (File file : Objects.requireNonNull(folder.listFiles()))
        {
            if (file.isDirectory())
            {
                deleteFolder(file);
            }
            file.delete();
        }
        folder.delete();
    }

}
