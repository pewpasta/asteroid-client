/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.systems.commands.commands;

import RewardedIvan.asteroidclient.gui.GuiThemes;
import RewardedIvan.asteroidclient.settings.Setting;
import RewardedIvan.asteroidclient.systems.commands.arguments.ModuleArgumentType;
import RewardedIvan.asteroidclient.utils.player.ChatUtils;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import RewardedIvan.asteroidclient.systems.Systems;
import RewardedIvan.asteroidclient.systems.commands.Command;
import RewardedIvan.asteroidclient.systems.hud.HUD;
import RewardedIvan.asteroidclient.systems.modules.Module;
import RewardedIvan.asteroidclient.systems.modules.Modules;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ResetCommand extends Command {

    public ResetCommand() {
        super("reset", "Resets specified settings.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("settings")
                .then(argument("module", ModuleArgumentType.module()).executes(context -> {
                    Module module = context.getArgument("module", Module.class);
                    module.settings.forEach(group -> group.forEach(Setting::reset));
                    module.info("Reset all settings.");
                    return SINGLE_SUCCESS;
                }))
                .then(literal("all").executes(context -> {
                    Modules.get().getAll().forEach(module -> module.settings.forEach(group -> group.forEach(Setting::reset)));
                    ChatUtils.info("Modules", "Reset all module settings");
                    return SINGLE_SUCCESS;
                }))
        ).then(literal("gui").executes(context -> {
            GuiThemes.get().clearWindowConfigs();
            ChatUtils.info("Reset GUI positioning.");
            return SINGLE_SUCCESS;
        })).then(literal("bind")
                .then(argument("module", ModuleArgumentType.module()).executes(context -> {
                    Module module = context.getArgument("module", Module.class);

                    module.keybind.set(true, -1);
                    module.info("Reset bind.");

                    return SINGLE_SUCCESS;
                }))
                .then(literal("all").executes(context -> {
                    Modules.get().getAll().forEach(module -> module.keybind.set(true, -1));
                    ChatUtils.info("Modules", "Reset all binds.");
                    return SINGLE_SUCCESS;
                }))
        ).then(literal("hud").executes(context -> {
            Systems.get(HUD.class).reset.run();
            ChatUtils.info("HUD", "Reset all elements.");
            return SINGLE_SUCCESS;
        }));
    }
}
