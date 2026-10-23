package com.bootstrap;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import io.papermc.paper.registry.event.RegistryEvents;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickCallback;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;

@NullMarked
@RequiredArgsConstructor
final class CatalogPageFactory {

    private static final TextComponent RETURN_BUTTON_LABEL = text("Назад");
    private static final ClickCallback.Options CALLBACK_OPTIONS = ClickCallback.Options.builder().uses(-1).build();

    private final LifecycleEventManager<BootstrapContext> manager;

    private void registerDialog(PageData data, DialogType type) {
        manager.registerEventHandler(RegistryEvents.DIALOG.compose(), event -> event.registry().register(
            TypedKey.create(RegistryKey.DIALOG, data.key), builder -> builder
            .base(DialogBase.builder(empty()).canCloseWithEscape(false).pause(false).body(data.bodies).afterAction(DialogBase.DialogAfterAction.NONE).build())
            .type(type)
        ));
    }

    void createCatalog(Key root, PageData currentPage, PageData... subPages)
    {
        List<ActionButton> actions = new ArrayList<>();
        for (PageData data : subPages)
        {
            registerDialog(data, DialogType.notice(createReturnButton(currentPage.key)));
            actions.add(data.btnToCurrentDialog);
        }
        registerDialog(currentPage, DialogType.multiAction(actions, createReturnButton(root), 2));
    }

    record PageData(Key key, ActionButton btnToCurrentDialog, List<DialogBody> bodies)
    {
        PageData (Key key, TextComponent pageName, DialogBody... bodies)
        {

            this(key, createReturnButton(pageName, key), List.of(bodies));
        }
    }

    private static ActionButton createReturnButton(Key key)
    {
        return createReturnButton(RETURN_BUTTON_LABEL, key);
    }

    private static ActionButton createReturnButton(TextComponent label, Key key)
    {
        return ActionButton.create(label, empty(), 125, DialogAction.customClick(new ShowDialogCallback(key), CALLBACK_OPTIONS));
    }

}
