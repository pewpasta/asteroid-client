/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.gui.screens;

import RewardedIvan.asteroidclient.gui.widgets.containers.WVerticalList;
import RewardedIvan.asteroidclient.gui.widgets.pressable.WButton;
import RewardedIvan.asteroidclient.utils.Utils;
import RewardedIvan.asteroidclient.gui.GuiTheme;
import RewardedIvan.asteroidclient.gui.WindowScreen;
import RewardedIvan.asteroidclient.systems.proxies.Proxies;
import RewardedIvan.asteroidclient.systems.proxies.Proxy;
import RewardedIvan.asteroidclient.systems.proxies.ProxyType;
import RewardedIvan.asteroidclient.utils.render.color.Color;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;

public class ProxiesImportScreen extends WindowScreen {

    private final File file;
    public ProxiesImportScreen(GuiTheme theme, File file) {
        super(theme, "Import Proxies");
        this.file = file;
        this.onClosed(() -> {
            if (parent instanceof ProxiesScreen screen) {
                screen.dirty = true;
            }
        });
    }

    @Override
    public void initWidgets() {
        if (file.exists() && file.isFile()) {
            add(theme.label("Importing proxies from " + file.getName() + "...").color(Color.GREEN));
            WVerticalList list = add(theme.section("Log", false)).widget().add(theme.verticalList()).expandX().widget();
            Proxies proxies = Proxies.get();
            try {
                int pog = 0, bruh = 0;
                for (String line : Files.readAllLines(file.toPath())) {
                    Matcher matcher = Proxies.PROXY_PATTERN.matcher(line);
                    if (matcher.matches()) {
                        Proxy proxy = new Proxy();
                        proxy.address = matcher.group(2).replaceAll("\\b0+\\B", "");
                        // should be safe to parse because of regex matching
                        proxy.port = Integer.parseInt(matcher.group(3));
                        proxy.name = matcher.group(1) != null ? matcher.group(1) : proxy.address + ":" + proxy.port;
                        proxy.type = matcher.group(4) != null ? ProxyType.parse(matcher.group(4)) : ProxyType.Socks4;
                        if (proxies.add(proxy)) {
                            list.add(theme.label("Imported proxy: " + proxy.name).color(Color.GREEN));
                            pog++;
                        } else {
                            list.add(theme.label("Proxy already exists: " + proxy.name).color(Color.ORANGE));
                            bruh++;
                        }
                    } else {
                        list.add(theme.label("Invalid proxy: " + line).color(Color.RED));
                        bruh++;
                    }
                }
                add(theme
                    .label("Successfully imported " + pog + "/" + (bruh + pog) + " proxies.")
                    .color(Utils.lerp(Color.RED, Color.GREEN, (float) pog / (pog + bruh)))
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            add(theme.label("Invalid File!"));
        }
        add(theme.horizontalSeparator()).expandX();
        WButton btnBack = add(theme.button("Back")).expandX().widget();
        btnBack.action = this::close;
    }
}
