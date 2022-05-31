/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.gui.themes.asteroid.widgets;

import RewardedIvan.asteroidclient.gui.renderer.GuiRenderer;
import RewardedIvan.asteroidclient.gui.widgets.WWidget;
import RewardedIvan.asteroidclient.gui.widgets.containers.WWindow;
import RewardedIvan.asteroidclient.gui.themes.asteroid.AsteroidWidget;

public class WMeteorWindow extends WWindow implements AsteroidWidget {
    public WMeteorWindow(WWidget icon, String title) {
        super(icon, title);
    }

    @Override
    protected WHeader header(WWidget icon) {
        return new WMeteorHeader(icon);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (expanded || animProgress > 0) {
            renderer.quad(x, y + header.height, width, height - header.height, theme().backgroundColor.get());
        }
    }

    private class WMeteorHeader extends WHeader {
        public WMeteorHeader(WWidget icon) {
            super(icon);
        }

        @Override
        protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            renderer.quad(this, theme().accentColor.get());
        }
    }
}
