/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.gui.themes.asteroid.widgets.pressable;

import RewardedIvan.asteroidclient.gui.renderer.GuiRenderer;
import RewardedIvan.asteroidclient.gui.widgets.pressable.WMinus;
import RewardedIvan.asteroidclient.gui.themes.asteroid.AsteroidWidget;

public class WMeteorMinus extends WMinus implements AsteroidWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        double pad = pad();
        double s = theme.scale(3);

        renderBackground(renderer, this, pressed, mouseOver);
        renderer.quad(x + pad, y + height / 2 - s / 2, width - pad * 2, s, theme().minusColor.get());
    }
}
