package com.bootstrap;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.keys.tags.DialogTagKeys;
import io.papermc.paper.registry.tag.TagKey;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.object.ObjectContents;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.key.Key.key;
import static com.bootstrap.CatalogPageFactory.PageData;

@NullMarked
@SuppressWarnings("UnstableApiUsage")
public final class Bootstrapper implements PluginBootstrap
{

    @Override
    public void bootstrap(BootstrapContext context)
    {
        LifecycleEventManager<BootstrapContext> manager = context.getLifecycleManager();

        TypedKey<Dialog> serverMenu = TypedKey.create(RegistryKey.DIALOG, Key.key("mob_arena:server_menu"));
        overwriteTagKeyForDialog(context.getLifecycleManager(), DialogTagKeys.QUICK_ACTIONS, serverMenu);
        overwriteTagKeyForDialog(context.getLifecycleManager(), DialogTagKeys.PAUSE_SCREEN_ADDITIONS, serverMenu);

        CatalogPageFactory factory = new CatalogPageFactory(manager);
         factory.createCatalog(
             key("mob_arena:server_menu"),
             new PageData(
                 key("mob_arena:multi_catalog"),
                 text("Справочник"),
                 DialogBody.plainMessage(text("Каталог страниц"))
             ),
             new PageData(key("mob_arena:test"), text("Страница 1"))
         );

        try
        {
            registerDatapacks(manager, Objects.requireNonNull(getClass().getResource("/MobArena")).toURI());
        }
        catch (URISyntaxException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static void registerDatapacks(LifecycleEventManager<BootstrapContext> manager, URI... uris)
    {
        manager.registerEventHandler(
            LifecycleEvents.DATAPACK_DISCOVERY.newHandler(
                handler ->
                {
                    for (URI uri : uris)
                    {
                        try
                        {
                            handler.registrar().discoverPack(uri, "path");
                        }
                        catch (IOException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                }
            )
        );
    }

    private static void overwriteTagKeyForDialog(LifecycleEventManager<BootstrapContext> manager, TagKey<Dialog> tag, TypedKey<Dialog> typedKey)
    {
        manager.registerEventHandler(
            LifecycleEvents.TAGS.postFlatten(RegistryKey.DIALOG),
            event -> event.registrar().setTag(tag, Set.of(typedKey))
        );
    }

}

