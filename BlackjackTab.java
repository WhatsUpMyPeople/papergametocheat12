package com.papergamerig.client.gui.tabs;

import com.papergamerig.client.PaperGameRigClient;
import com.papergamerig.client.gui.RigScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class BlackjackTab extends TabPanel {

    private Button bustBtn, winBtn, countBtn, resetCount;

    public BlackjackTab(RigScreen p, int x, int y, int w, int h) { super(p, x, y, w, h); }

    @Override
    public void init() {
        widgets.clear();
        var cfg = PaperGameRigClient.CONFIG;
        int cx = x + w / 2;
        int bw = 240;
        int bx = cx - bw / 2;

        bustBtn = Button.builder(
            Component.literal("Force dealer bust: " + (cfg.bjForceDealerBust ? "ON" : "OFF")),
            b -> { cfg.bjForceDealerBust = !cfg.bjForceDealerBust; cfg.save();
                   b.setMessage(Component.literal("Force dealer bust: " + (cfg.bjForceDealerBust ? "ON" : "OFF"))); })
            .bounds(bx, y + 22, bw, 20).build();

        winBtn = Button.builder(
            Component.literal("Force player 21: " + (cfg.bjForcePlayer21 ? "ON" : "OFF")),
            b -> { cfg.bjForcePlayer21 = !cfg.bjForcePlayer21; cfg.save();
                   b.setMessage(Component.literal("Force player 21: " + (cfg.bjForcePlayer21 ? "ON" : "OFF"))); })
            .bounds(bx, y + 62, bw, 20).build();

        countBtn = Button.builder(
            Component.literal("Card count overlay: " + (cfg.bjCountAssist ? "ON" : "OFF")),
            b -> { cfg.bjCountAssist = !cfg.bjCountAssist; cfg.save();
                   b.setMessage(Component.literal("Card count overlay: " + (cfg.bjCountAssist ? "ON" : "OFF"))); })
            .bounds(bx, y + 102, bw, 20).build();

        resetCount = Button.builder(
            Component.literal("Reset running count"),
            b -> PaperGameRigClient.ENGINE.resetCount())
            .bounds(bx, y + 142, bw, 20).build();

        widgets.add(bustBtn);
        widgets.add(winBtn);
        widgets.add(countBtn);
        widgets.add(resetCount);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float delta) {
        int cx = x + w / 2;
        String rc = "Running count: " + PaperGameRigClient.ENGINE.runningCount();
        int tw = mc.font.width(rc);
        g.drawString(mc.font, rc, cx - tw / 2, y + 180, 0xFFB388FF, false);
    }
}
