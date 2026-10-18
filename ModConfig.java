package com.papergamerig.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("papergamerig.json");

    // Settings tab
    public boolean overlayOn = true;
    public int overlayKey = 79;         // GLFW O
    public int openSettingsKey = 80;    // GLFW P

    // PaperGame tab
    public boolean paperGameRigOn = true;
    public int toggleRigKey = 54;       // GLFW 6
    public int switchSideKey = 55;      // GLFW 7
    public String sideA = "raft:totem_of_undying";
    public String sideB = "minecraft:end_crystal";
    public int paperGameCurrentSide = 0; // 0 = A, 1 = B

    // 50/50 tab
    public FiftyFiftyMode fiftyMode = FiftyFiftyMode.FORCE_HEADS;
    public int fiftyWinStreakTarget = 5;

    // Blackjack tab
    public boolean bjForceDealerBust = false;
    public boolean bjForcePlayer21 = false;
    public boolean bjCountAssist = true;

    public enum FiftyFiftyMode { OFF, FORCE_HEADS, FORCE_TAILS, ALTERNATE, RANDOM_WIN }

    public static ModConfig load() {
        try {
            if (Files.exists(PATH)) {
                return GSON.fromJson(Files.readString(PATH), ModConfig.class);
            }
        } catch (Exception ignored) {}
        ModConfig c = new ModConfig();
        c.save();
        return c;
    }

    public void save() {
        try {
            Files.writeString(PATH, GSON.toJson(this));
        } catch (IOException ignored) {}
    }
}
