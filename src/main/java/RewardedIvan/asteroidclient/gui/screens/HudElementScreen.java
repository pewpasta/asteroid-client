/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.gui.screens;

import RewardedIvan.asteroidclient.gui.renderer.GuiRenderer;
import RewardedIvan.asteroidclient.gui.widgets.containers.WContainer;
import RewardedIvan.asteroidclient.gui.widgets.containers.WHorizontalList;
import RewardedIvan.asteroidclient.gui.widgets.pressable.WButton;
import RewardedIvan.asteroidclient.gui.widgets.pressable.WCheckbox;
import RewardedIvan.asteroidclient.utils.Utils;
import RewardedIvan.asteroidclient.utils.misc.NbtUtils;
import RewardedIvan.asteroidclient.gui.GuiTheme;
import RewardedIvan.asteroidclient.gui.WindowScreen;
import RewardedIvan.asteroidclient.systems.Systems;
import RewardedIvan.asteroidclient.systems.hud.HUD;
import RewardedIvan.asteroidclient.systems.hud.modules.HudElement;
import net.minecraft.nbt.NbtCompound;

public class HudElementScreen extends WindowScreen {
    public final HudElement element;
    private WContainer settings;

    public HudElementScreen(GuiTheme theme, HudElement element) {
        super(theme, element.title);

        this.element = element;
    }

    @Override
    public void initWidgets() {
        // Description
        add(theme.label(element.description, Utils.getWindowWidth() / 2.0));

        // Settings
        if (element.settings.sizeGroups() > 0) {
            settings = add(theme.verticalList()).expandX().widget();
            settings.add(theme.settings(element.settings)).expandX();

            add(theme.horizontalSeparator()).expandX();
        }

        // Bottom
        WHorizontalList bottomList = add(theme.horizontalList()).expandX().widget();

        //   Active
        bottomList.add(theme.label("Active:"));
        WCheckbox active = bottomList.add(theme.checkbox(element.active)).widget();
        active.action = () -> {
            if (element.active != active.checked) element.toggle();
        };

        WButton reset = bottomList.add(theme.button(GuiRenderer.RESET)).expandCellX().right().widget();
        reset.action = () -> {
            if (element.active != element.defaultActive) element.active = active.checked = element.defaultActive;
        };
    }

    @Override
    public void tick() {
        super.tick();

        if (settings != null) {
            element.settings.tick(settings, theme);
        }
    }

    @Override
    protected void onRenderBefore(float delta) {
        if (!Utils.canUpdate()) {
            Systems.get(HUD.class).render(delta, hudElement -> true);
        }
    }

    @Override
    public boolean toClipboard() {
        return NbtUtils.toClipboard(element.title, element.toTag());
    }

    @Override
    public boolean fromClipboard() {
        NbtCompound clipboard = NbtUtils.fromClipboard(element.toTag());

        if (clipboard != null) {
            element.fromTag(clipboard);
            return true;
        }

        return false;
    }
}
