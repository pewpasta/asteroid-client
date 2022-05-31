/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.systems.commands.commands;

import RewardedIvan.asteroidclient.systems.commands.arguments.ProfileArgumentType;
import RewardedIvan.asteroidclient.systems.profiles.Profile;
import RewardedIvan.asteroidclient.systems.profiles.Profiles;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import RewardedIvan.asteroidclient.systems.commands.Command;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ProfilesCommand extends Command {

    public ProfilesCommand() {
        super("profiles", "Loads and saves profiles.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("load").then(argument("profile", ProfileArgumentType.profile()).executes(context -> {
            Profile profile = ProfileArgumentType.getProfile(context, "profile");

            if (profile != null) {
             profile.load();
                info("Loaded profile (highlight)%s(default).", profile.name);
            }

            return SINGLE_SUCCESS;
        })));

        builder.then(literal("save").then(argument("profile", ProfileArgumentType.profile()).executes(context -> {
            Profile profile = ProfileArgumentType.getProfile(context, "profile");

            if (profile != null) {
                profile.save();
                info("Saved profile (highlight)%s(default).", profile.name);
            }

            return SINGLE_SUCCESS;
        })));

        builder.then(literal("delete").then(argument("profile", ProfileArgumentType.profile()).executes(context -> {
            Profile profile = ProfileArgumentType.getProfile(context, "profile");

            if (profile != null) {
                Profiles.get().remove(profile);
                info("Deleted profile (highlight)%s(default).", profile.name);
            }

            return SINGLE_SUCCESS;
        })));
    }
}
