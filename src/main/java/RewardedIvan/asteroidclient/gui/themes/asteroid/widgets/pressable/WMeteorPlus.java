/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.gui.themes.asteroid.widgets.pressable;

import RewardedIvan.asteroidclient.gui.renderer.GuiRenderer;
import RewardedIvan.asteroidclient.gui.widgets.pressable.WPlus;
import RewardedIvan.asteroidclient.gui.themes.asteroid.AsteroidGuiTheme;
import RewardedIvan.asteroidclient.gui.themes.asteroid.AsteroidWidget;

public class WMeteorPlus extends WPlus implements AsteroidWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        AsteroidGuiTheme theme = theme();
        double pad = pad();
        double s = theme.scale(3);

        renderBackground(renderer, this, pressed, mouseOver);
        renderer.quad(x + pad, y + height / 2 - s / 2, width - pad * 2, s, theme.plusColor.get());
        renderer.quad(x + width / 2 - s / 2, y + pad, s, height - pad * 2, theme.plusColor.get());
    }
}
