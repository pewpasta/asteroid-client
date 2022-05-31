/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.gui.themes.asteroid.widgets;

import RewardedIvan.asteroidclient.gui.widgets.WAccount;
import RewardedIvan.asteroidclient.gui.WidgetScreen;
import RewardedIvan.asteroidclient.gui.themes.asteroid.AsteroidWidget;
import RewardedIvan.asteroidclient.systems.accounts.Account;
import RewardedIvan.asteroidclient.utils.render.color.Color;

public class WMeteorAccount extends WAccount implements AsteroidWidget {
    public WMeteorAccount(WidgetScreen screen, Account<?> account) {
        super(screen, account);
    }

    @Override
    protected Color loggedInColor() {
        return theme().loggedInColor.get();
    }

    @Override
    protected Color accountTypeColor() {
        return theme().textSecondaryColor.get();
    }
}
