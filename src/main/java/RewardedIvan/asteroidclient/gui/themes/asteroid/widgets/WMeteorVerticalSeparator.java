/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.gui.themes.asteroid.widgets;

import RewardedIvan.asteroidclient.gui.renderer.GuiRenderer;
import RewardedIvan.asteroidclient.gui.widgets.WVerticalSeparator;
import RewardedIvan.asteroidclient.gui.themes.asteroid.AsteroidGuiTheme;
import RewardedIvan.asteroidclient.gui.themes.asteroid.AsteroidWidget;
import RewardedIvan.asteroidclient.utils.render.color.Color;

public class WMeteorVerticalSeparator extends WVerticalSeparator implements AsteroidWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        AsteroidGuiTheme theme = theme();
        Color colorEdges = theme.separatorEdges.get();
        Color colorCenter = theme.separatorCenter.get();

        double s = theme.scale(1);
        double offsetX = Math.round(width / 2.0);

        renderer.quad(x + offsetX, y, s, height / 2, colorEdges, colorEdges, colorCenter, colorCenter);
        renderer.quad(x + offsetX, y + height / 2, s, height / 2, colorCenter, colorCenter, colorEdges, colorEdges);
    }
}
