package com.papergamerig.client.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import com.papergamerig.client.PaperGameRigClient;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

public class ModKeybinds {
    public static KeyMapping OPEN_SETTINGS;
    public static KeyMapping TOGGLE_OVERLAY;
    public static KeyMapping TOGGLE_RIG;
    public static KeyMapping SWITCH_SIDE;

    public static void register() {
        OPEN_SETTINGS  = reg("open_settings",  PaperGameRigClient.CONFIG.openSettingsKey);
        TOGGLE_OVERLAY = reg("toggle_overlay", PaperGameRigClient.CONFIG.overlayKey);
        TOGGLE_RIG     = reg("toggle_rig",     PaperGameRigClient.CONFIG.toggleRigKey);
        SWITCH_SIDE    = reg("switch_side",    PaperGameRigClient.CONFIG.switchSideKey);
    }

    private static KeyMapping reg(String name, int glfwCode) {
        return KeyBindingHelper.registerKeyBinding(new KeyMapping(
            "key.papergamerig." + name,
            InputConstants.Type.KEYSYM,
            glfwCode,
            "category.papergamerig"
        ));
    }
}
