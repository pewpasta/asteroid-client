/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.gui.themes.asteroid.widgets;

import RewardedIvan.asteroidclient.gui.widgets.WTopBar;
import RewardedIvan.asteroidclient.gui.themes.asteroid.AsteroidWidget;
import RewardedIvan.asteroidclient.utils.render.color.Color;

public class WMeteorTopBar extends WTopBar implements AsteroidWidget {
    @Override
    protected Color getButtonColor(boolean pressed, boolean hovered) {
        return theme().backgroundColor.get(pressed, hovered);
    }

    @Override
    protected Color getNameColor() {
        return theme().textColor.get();
    }
}
