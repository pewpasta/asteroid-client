/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.gui.themes.asteroid.widgets.pressable;

import RewardedIvan.asteroidclient.gui.widgets.pressable.WFavorite;
import RewardedIvan.asteroidclient.gui.themes.asteroid.AsteroidWidget;
import RewardedIvan.asteroidclient.utils.render.color.Color;

public class WMeteorFavorite extends WFavorite implements AsteroidWidget {
    public WMeteorFavorite(boolean checked) {
        super(checked);
    }

    @Override
    protected Color getColor() {
        return theme().favoriteColor.get();
    }
}
