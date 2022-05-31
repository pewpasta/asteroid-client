/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.gui.themes.asteroid.widgets.pressable;

import RewardedIvan.asteroidclient.gui.renderer.GuiRenderer;
import RewardedIvan.asteroidclient.gui.widgets.pressable.WCheckbox;
import RewardedIvan.asteroidclient.utils.Utils;
import RewardedIvan.asteroidclient.gui.themes.asteroid.AsteroidGuiTheme;
import RewardedIvan.asteroidclient.gui.themes.asteroid.AsteroidWidget;

public class WMeteorCheckbox extends WCheckbox implements AsteroidWidget {
    private double animProgress;

    public WMeteorCheckbox(boolean checked) {
        super(checked);
        animProgress = checked ? 1 : 0;
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        AsteroidGuiTheme theme = theme();

        animProgress += (checked ? 1 : -1) * delta * 14;
        animProgress = Utils.clamp(animProgress, 0, 1);

        renderBackground(renderer, this, pressed, mouseOver);

        if (animProgress > 0) {
            double cs = (width - theme.scale(2)) / 1.75 * animProgress;
            renderer.quad(x + (width - cs) / 2, y + (height - cs) / 2, cs, cs, theme.checkboxColor.get());
        }
    }
}
