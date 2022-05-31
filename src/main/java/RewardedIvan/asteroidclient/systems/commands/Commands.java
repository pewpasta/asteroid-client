/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.systems.commands;

import RewardedIvan.asteroidclient.AsteroidClient;
import RewardedIvan.asteroidclient.systems.commands.commands.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import RewardedIvan.asteroidclient.systems.System;
import RewardedIvan.asteroidclient.systems.Systems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.command.CommandSource;

import java.util.*;

public class Commands extends System<Commands> {
    private final CommandDispatcher<CommandSource> DISPATCHER = new CommandDispatcher<>();
    private final CommandSource COMMAND_SOURCE = new ChatCommandSource(AsteroidClient.mc);
    private final List<Command> commands = new ArrayList<>();
    private final Map<Class<? extends Command>, Command> commandInstances = new HashMap<>();

    public Commands() {
        super(null);
    }

    public static Commands get() {
        return Systems.get(Commands.class);
    }

    @Override
    public void init() {
        add(new DamageCommand());
        add(new EnchantCommand());
        add(new FakePlayerCommand());
        add(new FriendsCommand());
        add(new CommandsCommand());
        add(new ProfilesCommand());
        add(new ReloadCommand());
        add(new ResetCommand());
        add(new ServerCommand());
        add(new ToggleCommand());
        add(new SettingCommand());
        add(new SpectateCommand());
        add(new GamemodeCommand());
        add(new ModulesCommand());
        add(new GiveCommand());
        add(new NameHistoryCommand());
        add(new FOVCommand());
        add(new RotationCommand());
        add(new WaypointCommand());

        commands.sort(Comparator.comparing(Command::getName));
    }

    public void dispatch(String message) throws CommandSyntaxException {
        dispatch(message, new ChatCommandSource(AsteroidClient.mc));
    }

    public void dispatch(String message, CommandSource source) throws CommandSyntaxException {
        ParseResults<CommandSource> results = DISPATCHER.parse(message, source);
        DISPATCHER.execute(results);
    }

    public CommandDispatcher<CommandSource> getDispatcher() {
        return DISPATCHER;
    }

    public CommandSource getCommandSource() {
        return COMMAND_SOURCE;
    }

    private final static class ChatCommandSource extends ClientCommandSource {
        public ChatCommandSource(MinecraftClient client) {
            super(null, client);
        }
    }

    public void add(Command command) {
        commands.removeIf(command1 -> command1.getName().equals(command.getName()));
        commandInstances.values().removeIf(command1 -> command1.getName().equals(command.getName()));

        command.registerTo(DISPATCHER);
        commands.add(command);
        commandInstances.put(command.getClass(), command);
    }

    public int getCount() {
        return commands.size();
    }

    public List<Command> getAll() {
        return commands;
    }

    @SuppressWarnings("unchecked")
    public <T extends Command> T get(Class<T> klass) {
        return (T) commandInstances.get(klass);
    }
}
