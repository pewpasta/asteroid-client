/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.renderer;

import RewardedIvan.asteroidclient.AsteroidClient;
import RewardedIvan.asteroidclient.gui.WidgetScreen;
import RewardedIvan.asteroidclient.renderer.text.CustomTextRenderer;
import RewardedIvan.asteroidclient.utils.Init;
import RewardedIvan.asteroidclient.utils.InitStage;
import RewardedIvan.asteroidclient.utils.files.StreamUtils;
import RewardedIvan.asteroidclient.systems.config.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Fonts {
    private static final String[] BUILTIN_FONTS = { "JetBrains Mono.ttf", "Comfortaa.ttf", "Tw Cen MT.ttf", "Pixelation.ttf" };
    public static final String DEFAULT_FONT = "JetBrains Mono";
    private static final File FOLDER = new File(AsteroidClient.FOLDER, "fonts");

    public static CustomTextRenderer CUSTOM_FONT;

    private static String lastFont = "";

    @Init(stage = InitStage.Pre)
    public static void init() {
        FOLDER.mkdirs();

        // Copy built in fonts if they not exist
        for (String font : BUILTIN_FONTS) {
            File file = new File(FOLDER, font);
            if (!file.exists()) {
                StreamUtils.copy(Fonts.class.getResourceAsStream("/assets/" + AsteroidClient.MOD_ID + "/fonts/" + font), file);
            }
        }

        // Load default font
        CUSTOM_FONT = new CustomTextRenderer(new File(FOLDER, DEFAULT_FONT + ".ttf"));
        lastFont = DEFAULT_FONT;
    }

    @Init(stage = InitStage.Post)
    public static void load() {
        if (lastFont.equals(Config.get().font.get())) return;

        File file = new File(FOLDER, Config.get().font + ".ttf");
        if (!file.exists()) {
            Config.get().font.set(DEFAULT_FONT);
            file = new File(FOLDER, Config.get().font + ".ttf");
        }

        try {
            CUSTOM_FONT = new CustomTextRenderer(file);
        } catch (Exception ignored) {
            Config.get().font.set(DEFAULT_FONT);
            file = new File(FOLDER, Config.get().font + ".ttf");

            CUSTOM_FONT = new CustomTextRenderer(file);
        }

        if (AsteroidClient.mc.currentScreen instanceof WidgetScreen && Config.get().customFont.get()) {
            ((WidgetScreen) AsteroidClient.mc.currentScreen).invalidate();
        }

        lastFont = Config.get().font.get();
    }

    public static String[] getAvailableFonts() {
        List<String> fonts = new ArrayList<>(4);

        File[] files = FOLDER.listFiles(File::isFile);
        if (files != null) {
            for (File file : files) {
                int i = file.getName().lastIndexOf('.');
                if (file.getName().substring(i).equals(".ttf")) {
                    fonts.add(file.getName().substring(0, i));
                }
            }
        }

        return fonts.toArray(new String[0]);
    }
}
