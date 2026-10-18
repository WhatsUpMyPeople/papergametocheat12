package com.papergamerig.client.gui.tabs;

import com.papergamerig.client.PaperGameRigClient;
import com.papergamerig.client.gui.RigScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class SettingsTab extends TabPanel {

    private Button overlayToggle, overlayKey, settingsKey;

    public SettingsTab(RigScreen p, int x, int y, int w, int h) { super(p, x, y, w, h); }

    @Override
    public void init() {
        widgets.clear();
        var cfg = PaperGameRigClient.CONFIG;
        int bx = x + w / 2 - 100;
        int bw = 200;

        overlayToggle = Button.builder(
            Component.literal(cfg.overlayOn ? "ON" : "OFF"),
            b -> { cfg.overlayOn = !cfg.overlayOn; cfg.save();
                   b.setMessage(Component.literal(cfg.overlayOn ? "ON" : "OFF")); })
            .bounds(bx, y + 36, bw, 20).build();

        overlayKey = Button.builder(
            Component.literal("[" + glfwName(cfg.overlayKey) + "]"),
            b -> {})
            .bounds(bx, y + 96, bw, 20).build();

        settingsKey = Button.builder(
            Component.literal("[" + glfwName(cfg.openSettingsKey) + "]"),
            b -> {})
            .bounds(bx, y + 156, bw, 20).build();

        widgets.add(overlayToggle);
        widgets.add(overlayKey);
        widgets.add(settingsKey);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float delta) {
        int cx = x + w / 2;
        drawCentered(g, "Overlay:",           cx, y + 22);
        drawCentered(g, "Overlay key:",       cx, y + 82);
        drawCentered(g, "Open settings key:", cx, y + 142);
    }

    private void drawCentered(GuiGraphics g, String s, int cx, int cy) {
        int tw = mc.font.width(s);
        g.drawString(mc.font, s, cx - tw / 2, cy, 0xFFEEEEEE, false);
    }

    static String glfwName(int code) {
        return switch (code) {
            case 79 -> "O"; case 80 -> "P"; case 54 -> "6"; case 55 -> "7";
            default -> "KEY " + code;
        };
    }
}
