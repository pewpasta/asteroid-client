/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.gui.themes.asteroid.widgets;

import RewardedIvan.asteroidclient.gui.renderer.GuiRenderer;
import RewardedIvan.asteroidclient.gui.widgets.WLabel;
import RewardedIvan.asteroidclient.gui.themes.asteroid.AsteroidWidget;

public class WMeteorLabel extends WLabel implements AsteroidWidget {
    public WMeteorLabel(String text, boolean title) {
        super(text, title);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (!text.isEmpty()) {
            renderer.text(text, x, y, color != null ? color : (title ? theme().titleTextColor.get() : theme().textColor.get()), title);
        }
    }
}
