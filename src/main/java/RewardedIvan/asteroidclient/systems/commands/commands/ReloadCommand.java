/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import RewardedIvan.asteroidclient.systems.Systems;
import RewardedIvan.asteroidclient.systems.commands.Command;
import RewardedIvan.asteroidclient.utils.network.Capes;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ReloadCommand extends Command {
    public ReloadCommand() {
        super("reload", "Reloads the config, modules, friends, accounts and capes.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            Systems.load();
            Capes.init();

            return SINGLE_SUCCESS;
        });
    }
}
