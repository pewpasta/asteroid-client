/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.gui.screens;

import RewardedIvan.asteroidclient.gui.widgets.WKeybind;
import RewardedIvan.asteroidclient.gui.widgets.WWidget;
import RewardedIvan.asteroidclient.gui.widgets.containers.WContainer;
import RewardedIvan.asteroidclient.gui.widgets.containers.WHorizontalList;
import RewardedIvan.asteroidclient.gui.widgets.containers.WSection;
import RewardedIvan.asteroidclient.gui.widgets.pressable.WCheckbox;
import RewardedIvan.asteroidclient.gui.widgets.pressable.WFavorite;
import RewardedIvan.asteroidclient.utils.Utils;
import RewardedIvan.asteroidclient.utils.misc.NbtUtils;
import RewardedIvan.asteroidclient.events.asteroid.ModuleBindChangedEvent;
import RewardedIvan.asteroidclient.gui.GuiTheme;
import RewardedIvan.asteroidclient.gui.WindowScreen;
import RewardedIvan.asteroidclient.gui.utils.Cell;
import RewardedIvan.asteroidclient.systems.modules.Module;
import RewardedIvan.asteroidclient.systems.modules.Modules;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.nbt.NbtCompound;

public class ModuleScreen extends WindowScreen {
    private final Module module;

    private WContainer settingsContainer;
    private WKeybind keybind;

    public ModuleScreen(GuiTheme theme, Module module) {
        super(theme, theme.favorite(module.favorite), module.title);
        ((WFavorite) window.icon).action = () -> module.favorite = ((WFavorite) window.icon).checked;

        this.module = module;
    }

    @Override
    public void initWidgets() {
        // Description
        add(theme.label(module.description, Utils.getWindowWidth() / 2.0));

        // Settings
        if (module.settings.groups.size() > 0) {
            settingsContainer = add(theme.verticalList()).expandX().widget();
            settingsContainer.add(theme.settings(module.settings)).expandX();
        }

        // Custom widget
        WWidget widget = module.getWidget(theme);

        if (widget != null) {
            add(theme.horizontalSeparator()).expandX();
            Cell<WWidget> cell = add(widget);
            if (widget instanceof WContainer) cell.expandX();
        }

        // Bind
        WSection section = add(theme.section("Bind", true)).expandX().widget();
        keybind = section.add(theme.keybind(module.keybind)).expandX().widget();
        keybind.actionOnSet = () -> Modules.get().setModuleToBind(module);

        // Toggle on bind release
        WHorizontalList tobr = section.add(theme.horizontalList()).widget();

        tobr.add(theme.label("Toggle on bind release: "));
        WCheckbox tobrC = tobr.add(theme.checkbox(module.toggleOnBindRelease)).widget();
        tobrC.action = () -> module.toggleOnBindRelease = tobrC.checked;

        // Chat feedback
        WHorizontalList cf = section.add(theme.horizontalList()).widget();

        cf.add(theme.label("Chat Feedback: "));
        WCheckbox cfC = cf.add(theme.checkbox(module.chatFeedback)).widget();
        cfC.action = () -> module.chatFeedback = cfC.checked;

        add(theme.horizontalSeparator()).expandX();

        // Bottom
        WHorizontalList bottom = add(theme.horizontalList()).expandX().widget();

        //   Active
        bottom.add(theme.label("Active: "));
        WCheckbox active = bottom.add(theme.checkbox(module.isActive())).expandCellX().widget();
        active.action = () -> {
            if (module.isActive() != active.checked) module.toggle();
        };
    }

    @Override
    public void tick() {
        super.tick();

        module.settings.tick(settingsContainer, theme);
    }

    @EventHandler
    private void onModuleBindChanged(ModuleBindChangedEvent event) {
        keybind.reset();
    }

    @Override
    public boolean toClipboard() {
        return NbtUtils.toClipboard(module.title, module.toTag());
    }

    @Override
    public boolean fromClipboard() {
        NbtCompound clipboard = NbtUtils.fromClipboard(module.toTag());

        if (clipboard != null) {
            module.fromTag(clipboard);
            return true;
        }

        return false;
    }
}
