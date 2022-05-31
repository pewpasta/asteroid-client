/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.addons;

import RewardedIvan.asteroidclient.utils.render.color.Color;

public abstract class AsteroidAddon {
    /** This field is automatically assigned from fabric.mod.json file. */
    public String name;

    /** This field is automatically assigned from fabric.mod.json file. */
    public String[] authors;

    /** This field is automatically assigned from the asteroid-client:color property in fabric.mod.json file. */
    public final Color color = new Color(255, 255, 255);

    public abstract void onInitialize();

    public void onRegisterCategories() {}

    public String getWebsite() {
        return null;
    }

    public GithubRepo getRepo() {
        return null;
    }

    public String getCommit() {
        return null;
    }
}
