/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.gui.tabs;

import RewardedIvan.asteroidclient.gui.WidgetScreen;
import RewardedIvan.asteroidclient.gui.utils.Cell;
import RewardedIvan.asteroidclient.gui.widgets.WWidget;
import RewardedIvan.asteroidclient.gui.GuiTheme;

public abstract class TabScreen extends WidgetScreen {
    public final Tab tab;

    public TabScreen(GuiTheme theme, Tab tab) {
        super(theme, tab.name);

        this.tab = tab;
    }

    public <T extends WWidget> Cell<T> addDirect(T widget) {
        return super.add(widget);
    }
}
