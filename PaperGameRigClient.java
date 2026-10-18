package com.papergamerig.client;

import com.papergamerig.client.config.ModConfig;
import com.papergamerig.client.gui.RigScreen;
import com.papergamerig.client.keybinds.ModKeybinds;
import com.papergamerig.client.rig.RigEngine;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class PaperGameRigClient implements ClientModInitializer {
    public static final String MOD_ID = "papergamerig";
    public static ModConfig CONFIG;
    public static RigEngine ENGINE;

    @Override
    public void onInitializeClient() {
        CONFIG = ModConfig.load();
        ENGINE = new RigEngine(CONFIG);
        ModKeybinds.register();

        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    private void onTick(Minecraft mc) {
        while (ModKeybinds.OPEN_SETTINGS.consumeClick()) {
            if (mc.player != null) mc.setScreen(new RigScreen());
        }
        while (ModKeybinds.TOGGLE_OVERLAY.consumeClick()) {
            CONFIG.overlayOn = !CONFIG.overlayOn;
            CONFIG.save();
        }
        while (ModKeybinds.TOGGLE_RIG.consumeClick()) {
            CONFIG.paperGameRigOn = !CONFIG.paperGameRigOn;
            CONFIG.save();
            if (mc.player != null) {
                mc.player.displayClientMessage(
                    Component.literal("§7[PGR] rig: " + (CONFIG.paperGameRigOn ? "§aON" : "§cOFF")),
                    true);
            }
        }
        while (ModKeybinds.SWITCH_SIDE.consumeClick()) {
            CONFIG.paperGameCurrentSide = (CONFIG.paperGameCurrentSide == 0) ? 1 : 0;
            CONFIG.save();
            if (mc.player != null) {
                String s = CONFIG.paperGameCurrentSide == 0 ? CONFIG.sideA : CONFIG.sideB;
                mc.player.displayClientMessage(
                    Component.literal("§7[PGR] side: §b" + s), true);
            }
        }
        ENGINE.tick(mc);
    }
}
