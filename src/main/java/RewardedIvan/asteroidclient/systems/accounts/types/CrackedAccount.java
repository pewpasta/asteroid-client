/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.systems.accounts.types;

import RewardedIvan.asteroidclient.systems.accounts.AccountType;
import RewardedIvan.asteroidclient.systems.accounts.Account;
import net.minecraft.client.util.Session;

import java.util.Optional;

public class CrackedAccount extends Account<CrackedAccount> {
    public CrackedAccount(String name) {
        super(AccountType.Cracked, name);
    }

    @Override
    public boolean fetchInfo() {
        cache.username = name;
        return true;
    }

    @Override
    public boolean login() {
        super.login();

        setSession(new Session(name, "", "", Optional.empty(), Optional.empty(), Session.AccountType.MOJANG));
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CrackedAccount)) return false;
        return ((CrackedAccount) o).getUsername().equals(this.getUsername());
    }
}
