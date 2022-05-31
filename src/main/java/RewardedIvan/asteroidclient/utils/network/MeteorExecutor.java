/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.utils.network;

import RewardedIvan.asteroidclient.utils.Init;
import RewardedIvan.asteroidclient.utils.InitStage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MeteorExecutor {
    public static ExecutorService executor;

    @Init(stage = InitStage.Pre)
    public static void init() {
        executor = Executors.newSingleThreadExecutor();
    }

    public static void execute(Runnable task) {
        executor.execute(task);
    }
}
