/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.systems.commands.commands;

import RewardedIvan.asteroidclient.systems.commands.arguments.ModuleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import RewardedIvan.asteroidclient.systems.commands.Command;
import RewardedIvan.asteroidclient.systems.hud.HUD;
import RewardedIvan.asteroidclient.systems.modules.Module;
import RewardedIvan.asteroidclient.systems.modules.Modules;
import net.minecraft.command.CommandSource;

import java.util.ArrayList;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("toggle", "Toggles a module.", "t");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder
            .then(literal("all")
                .then(literal("on")
                    .executes(context -> {
                        new ArrayList<>(Modules.get().getAll()).forEach(module -> {
                            if (!module.isActive()) module.toggle();
                        });
                        HUD.get().active = true;
                        return SINGLE_SUCCESS;
                    })
                )
                .then(literal("off")
                    .executes(context -> {
                        new ArrayList<>(Modules.get().getActive()).forEach(Module::toggle);
                        HUD.get().active = false;
                        return SINGLE_SUCCESS;
                    })
                )
            )
            .then(argument("module", ModuleArgumentType.module())
                .executes(context -> {
                    Module m = ModuleArgumentType.getModule(context, "module");
                    m.toggle();
                    return SINGLE_SUCCESS;
                })
                .then(literal("on")
                    .executes(context -> {
                        Module m = ModuleArgumentType.getModule(context, "module");
                        if (!m.isActive()) m.toggle();
                        return SINGLE_SUCCESS;
                    }))
                .then(literal("off")
                    .executes(context -> {
                        Module m = ModuleArgumentType.getModule(context, "module");
                        if (m.isActive()) m.toggle();
                        return SINGLE_SUCCESS;
                    })
                )
            );
    }
}
