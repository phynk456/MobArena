package com.bootstrap;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.data.dialog.action.DialogActionCallback;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
@RequiredArgsConstructor
final class ShowDialogCallback implements DialogActionCallback
{

    private final Key key;
    @Nullable private Dialog dialog;

    @Override
    public void accept(DialogResponseView response, Audience audience)
    {
        if (dialog == null)
        {
            dialog = Objects.requireNonNull(RegistryAccess.registryAccess().getRegistry(RegistryKey.DIALOG).get(key));
        }
        audience.showDialog(dialog);
    }

}
