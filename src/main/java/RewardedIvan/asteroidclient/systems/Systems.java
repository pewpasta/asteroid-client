/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.systems;

import RewardedIvan.asteroidclient.AsteroidClient;
import RewardedIvan.asteroidclient.systems.accounts.Accounts;
import RewardedIvan.asteroidclient.systems.commands.Commands;
import RewardedIvan.asteroidclient.systems.config.Config;
import RewardedIvan.asteroidclient.systems.friends.Friends;
import RewardedIvan.asteroidclient.systems.hud.HUD;
import RewardedIvan.asteroidclient.systems.modules.Modules;
import RewardedIvan.asteroidclient.systems.profiles.Profiles;
import RewardedIvan.asteroidclient.systems.proxies.Proxies;
import RewardedIvan.asteroidclient.systems.waypoints.Waypoints;
import RewardedIvan.asteroidclient.events.game.GameLeftEvent;
import meteordevelopment.orbit.EventHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Systems {
    @SuppressWarnings("rawtypes")
    private static final Map<Class<? extends System>, System<?>> systems = new HashMap<>();
    private static final List<Runnable> preLoadTasks = new ArrayList<>(1);

    public static void addPreLoadTask(Runnable task) {
        preLoadTasks.add(task);
    }

    public static void init() {
        System<?> config = add(new Config());
        config.init();
        config.load();

        add(new Modules());
        add(new Commands());
        add(new Friends());
        add(new Accounts());
        add(new Waypoints());
        add(new Profiles());
        add(new Proxies());
        add(new HUD());

        AsteroidClient.EVENT_BUS.subscribe(Systems.class);
    }

    private static System<?> add(System<?> system) {
        systems.put(system.getClass(), system);
        AsteroidClient.EVENT_BUS.subscribe(system);
        system.init();

        return system;
    }

    // save/load

    @EventHandler
    private static void onGameLeft(GameLeftEvent event) {
        save();
    }

    public static void save(File folder) {
        long start = java.lang.System.currentTimeMillis();
        AsteroidClient.LOG.info("Saving");

        for (System<?> system : systems.values()) system.save(folder);

        AsteroidClient.LOG.info("Saved in {} milliseconds.", java.lang.System.currentTimeMillis() - start);
    }

    public static void save() {
        save(null);
    }

    public static void load(File folder) {
        long start = java.lang.System.currentTimeMillis();
        AsteroidClient.LOG.info("Loading");

        for (Runnable task : preLoadTasks) task.run();
        for (System<?> system : systems.values()) system.load(folder);

        AsteroidClient.LOG.info("Loaded in {} milliseconds", java.lang.System.currentTimeMillis() - start);
    }

    public static void load() {
        load(null);
    }

    @SuppressWarnings("unchecked")
    public static <T extends System<?>> T get(Class<T> klass) {
        return (T) systems.get(klass);
    }
}
