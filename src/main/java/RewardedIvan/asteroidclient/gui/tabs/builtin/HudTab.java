/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.gui.tabs.builtin;

import RewardedIvan.asteroidclient.gui.renderer.GuiRenderer;
import RewardedIvan.asteroidclient.gui.screens.HudEditorScreen;
import RewardedIvan.asteroidclient.gui.widgets.containers.WHorizontalList;
import RewardedIvan.asteroidclient.gui.widgets.pressable.WButton;
import RewardedIvan.asteroidclient.gui.widgets.pressable.WCheckbox;
import RewardedIvan.asteroidclient.utils.misc.NbtUtils;
import RewardedIvan.asteroidclient.gui.GuiTheme;
import RewardedIvan.asteroidclient.gui.tabs.Tab;
import RewardedIvan.asteroidclient.gui.tabs.TabScreen;
import RewardedIvan.asteroidclient.gui.tabs.WindowTabScreen;
import RewardedIvan.asteroidclient.systems.Systems;
import RewardedIvan.asteroidclient.systems.hud.HUD;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.NbtCompound;

import static RewardedIvan.asteroidclient.AsteroidClient.mc;

public class HudTab extends Tab {
    public HudTab() {
        super("HUD");
    }

    @Override
    public TabScreen createScreen(GuiTheme theme) {
        return new HudScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof HudScreen;
    }

    public static class HudScreen extends WindowTabScreen {
        private final HUD hud;

        public HudScreen(GuiTheme theme, Tab tab) {
            super(theme, tab);

            hud = Systems.get(HUD.class);
            hud.settings.onActivated();
        }

        @Override
        public void initWidgets() {
            add(theme.settings(hud.settings)).expandX();

            add(theme.horizontalSeparator()).expandX();

            WButton openEditor = add(theme.button("Edit")).expandX().widget();
            openEditor.action = () -> mc.setScreen(new HudEditorScreen(theme, this));

            WButton resetHud = add(theme.button("Reset")).expandX().widget();
            resetHud.action = hud.reset;

            add(theme.horizontalSeparator()).expandX();

            WHorizontalList bottom = add(theme.horizontalList()).expandX().widget();

            bottom.add(theme.label("Active: "));
            WCheckbox active = bottom.add(theme.checkbox(hud.active)).expandCellX().widget();
            active.action = () -> hud.active = active.checked;

            WButton resetSettings = bottom.add(theme.button(GuiRenderer.RESET)).widget();
            resetSettings.action = hud.settings::reset;
        }

        @Override
        public boolean toClipboard() {
            return NbtUtils.toClipboard("hud-settings", hud.settings.toTag());
        }

        @Override
        public boolean fromClipboard() {
            NbtCompound clipboard = NbtUtils.fromClipboard(hud.settings.toTag());

            if (clipboard != null) {
                hud.settings.fromTag(clipboard);
                return true;
            }

            return false;
        }
    }
}
