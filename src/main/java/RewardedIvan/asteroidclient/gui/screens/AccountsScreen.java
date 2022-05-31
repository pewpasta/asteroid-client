/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.gui.screens;

import RewardedIvan.asteroidclient.gui.widgets.WAccount;
import RewardedIvan.asteroidclient.gui.widgets.containers.WContainer;
import RewardedIvan.asteroidclient.gui.widgets.containers.WHorizontalList;
import RewardedIvan.asteroidclient.gui.widgets.pressable.WButton;
import RewardedIvan.asteroidclient.utils.misc.NbtUtils;
import RewardedIvan.asteroidclient.gui.GuiTheme;
import RewardedIvan.asteroidclient.gui.WindowScreen;
import RewardedIvan.asteroidclient.systems.accounts.Account;
import RewardedIvan.asteroidclient.systems.accounts.Accounts;
import RewardedIvan.asteroidclient.systems.accounts.MicrosoftLogin;
import RewardedIvan.asteroidclient.systems.accounts.types.MicrosoftAccount;
import RewardedIvan.asteroidclient.utils.network.MeteorExecutor;
import org.jetbrains.annotations.Nullable;

import static RewardedIvan.asteroidclient.AsteroidClient.mc;

public class AccountsScreen extends WindowScreen {
    public AccountsScreen(GuiTheme theme) {
        super(theme, "Accounts");
    }

    @Override
    public void initWidgets() {
        // Accounts
        for (Account<?> account : Accounts.get()) {
            WAccount wAccount = add(theme.account(this, account)).expandX().widget();
            wAccount.refreshScreenAction = this::reload;
        }

        // Add account
        WHorizontalList l = add(theme.horizontalList()).expandX().widget();

        addButton(l, "Cracked", () -> mc.setScreen(new AddCrackedAccountScreen(theme, this)));
        addButton(l, "Altening", () -> mc.setScreen(new AddAlteningAccountScreen(theme, this)));
        addButton(l, "Microsoft", () -> {
            locked = true;

            MicrosoftLogin.getRefreshToken(refreshToken -> {
                locked = false;

                if (refreshToken != null) {
                    MicrosoftAccount account = new MicrosoftAccount(refreshToken);
                    addAccount(null, this, account);
                }
            });
        });
    }

    private void addButton(WContainer c, String text, Runnable action) {
        WButton button = c.add(theme.button(text)).expandX().widget();
        button.action = action;
    }

    public static void addAccount(@Nullable AddAccountScreen screen, AccountsScreen parent, Account<?> account) {
        if (screen != null) screen.locked = true;

        MeteorExecutor.execute(() -> {
            if (account.fetchInfo() && account.fetchHead()) {
                Accounts.get().add(account);
                if (account.login()) Accounts.get().save();

                if (screen != null) {
                    screen.locked = false;
                    screen.close();
                }

                parent.reload();

                return;
            }

            if (screen != null) screen.locked = false;
        });
    }

    @Override
    public boolean toClipboard() {
        return NbtUtils.toClipboard(Accounts.get());
    }

    @Override
    public boolean fromClipboard() {
        return NbtUtils.fromClipboard(Accounts.get());
    }
}
