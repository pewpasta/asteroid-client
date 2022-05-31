/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.addons;

import RewardedIvan.asteroidclient.AsteroidClient;
import RewardedIvan.asteroidclient.utils.Init;
import RewardedIvan.asteroidclient.utils.InitStage;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;

import java.util.ArrayList;
import java.util.List;

public class AddonManager {
    public static AsteroidAddon METEOR;
    public static final List<AsteroidAddon> ADDONS = new ArrayList<>();

    @Init(stage = InitStage.Pre)
    public static void init() {
        // Meteor pseudo addon
        {
            METEOR = new AsteroidAddon() {
                @Override
                public void onInitialize() {}

                @Override
                public String getWebsite() {
                    return "https://meteorclient.com";
                }

                @Override
                public GithubRepo getRepo() {
                    return new GithubRepo("RewardedIvan", "asteroid-client");
                }

                @Override
                public String getCommit() {
                    String commit = AsteroidClient.MOD_META.getCustomValue("asteroid-client:commit").getAsString();
                    return commit.isEmpty() ? null : commit;
                }
            };

            ModMetadata metadata = FabricLoader.getInstance().getModContainer("asteroid-client").get().getMetadata();

            METEOR.name = metadata.getName();
            METEOR.authors = new String[metadata.getAuthors().size()];
            if (metadata.containsCustomValue("asteroid-client:color")) METEOR.color.parse(metadata.getCustomValue("asteroid-client:color").getAsString());

            int i = 0;
            for (Person author : metadata.getAuthors()) {
                METEOR.authors[i++] = author.getName();
            }
        }

        // Addons
        for (EntrypointContainer<AsteroidAddon> entrypoint : FabricLoader.getInstance().getEntrypointContainers("asteroid", AsteroidAddon.class)) {
            ModMetadata metadata = entrypoint.getProvider().getMetadata();
            AsteroidAddon addon = entrypoint.getEntrypoint();

            addon.name = metadata.getName();
            addon.authors = new String[metadata.getAuthors().size()];
            if (metadata.containsCustomValue("asteroid-client:color")) addon.color.parse(metadata.getCustomValue("asteroid-client:color").getAsString());

            int i = 0;
            for (Person author : metadata.getAuthors()) {
                addon.authors[i++] = author.getName();
            }

            ADDONS.add(addon);
        }
    }
}
