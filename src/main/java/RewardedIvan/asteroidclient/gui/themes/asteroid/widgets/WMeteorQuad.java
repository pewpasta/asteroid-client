/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.gui.themes.asteroid.widgets;

import RewardedIvan.asteroidclient.gui.renderer.GuiRenderer;
import RewardedIvan.asteroidclient.gui.widgets.WQuad;
import RewardedIvan.asteroidclient.utils.render.color.Color;

public class WMeteorQuad extends WQuad {
    public WMeteorQuad(Color color) {
        super(color);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.quad(x, y, width, height, color);
    }
}
