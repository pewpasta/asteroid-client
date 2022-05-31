/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.gui.utils;

import RewardedIvan.asteroidclient.gui.widgets.WWidget;
import RewardedIvan.asteroidclient.gui.GuiTheme;
import RewardedIvan.asteroidclient.settings.Settings;

public interface SettingsWidgetFactory {
    WWidget create(GuiTheme theme, Settings settings, String filter);
}
