package com.core;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.event.RegistryEvents;
import io.papermc.paper.registry.keys.tags.DialogTagKeys;
import io.papermc.paper.registry.tag.TagKey;
import net.kyori.adventure.key.Key;
import org.jspecify.annotations.NullMarked;

import java.util.Set;

@NullMarked
@SuppressWarnings("UnstableApiUsage")
public final class Bootstrapper implements PluginBootstrap
{

    @Override
    public void bootstrap(BootstrapContext context)
    {
        TypedKey<Dialog> serverMenu = TypedKey.create(RegistryKey.DIALOG, Key.key("mob_arena:server_menu"));
        overwriteTagKeyForDialog(context.getLifecycleManager(), DialogTagKeys.QUICK_ACTIONS, serverMenu);
        overwriteTagKeyForDialog(context.getLifecycleManager(), DialogTagKeys.PAUSE_SCREEN_ADDITIONS, serverMenu);
    }

    private static void overwriteTagKeyForDialog(LifecycleEventManager<BootstrapContext> manager, TagKey<Dialog> tag, TypedKey<Dialog> typedKey)
    {
        manager.registerEventHandler(
            LifecycleEvents.TAGS.postFlatten(RegistryKey.DIALOG),
            event -> event.registrar().setTag(tag, Set.of(typedKey))
        );
    }

}

