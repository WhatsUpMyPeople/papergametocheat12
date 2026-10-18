package com.papergamerig.client.gui.tabs;

import com.papergamerig.client.PaperGameRigClient;
import com.papergamerig.client.config.ModConfig;
import com.papergamerig.client.gui.RigScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class FiftyFiftyTab extends TabPanel {

    private Button modeBtn, streakMinus, streakPlus;

    public FiftyFiftyTab(RigScreen p, int x, int y, int w, int h) { super(p, x, y, w, h); }

    @Override
    public void init() {
        widgets.clear();
        var cfg = PaperGameRigClient.CONFIG;
        int cx = x + w / 2;
        int bw = 240;
        int bx = cx - bw / 2;

        modeBtn = Button.builder(
            Component.literal(modeLabel(cfg.fiftyMode)),
            b -> {
                var vals = ModConfig.FiftyFiftyMode.values();
                cfg.fiftyMode = vals[(cfg.fiftyMode.ordinal() + 1) % vals.length];
                cfg.save();
                b.setMessage(Component.literal(modeLabel(cfg.fiftyMode)));
            }).bounds(bx, y + 22, bw, 20).build();

        streakMinus = Button.builder(Component.literal("−"),
            b -> { if (cfg.fiftyWinStreakTarget > 1) { cfg.fiftyWinStreakTarget--; cfg.save(); } })
            .bounds(bx, y + 82, 60, 20).build();

        streakPlus = Button.builder(Component.literal("+"),
            b -> { if (cfg.fiftyWinStreakTarget < 99) { cfg.fiftyWinStreakTarget++; cfg.save(); } })
            .bounds(bx + bw - 60, y + 82, 60, 20).build();

        widgets.add(modeBtn);
        widgets.add(streakMinus);
        widgets.add(streakPlus);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float delta) {
        var cfg = PaperGameRigClient.CONFIG;
        int cx = x + w / 2;
        drawCentered(g, "Mode:", cx, y + 10);
        drawCentered(g, "Win streak target:", cx, y + 70);
        drawCentered(g, String.valueOf(cfg.fiftyWinStreakTarget), cx, y + 88);
        drawCentered(g, "Simulated rolls will cash out at this streak.", cx, y + 130);
    }

    private String modeLabel(ModConfig.FiftyFiftyMode m) {
        return switch (m) {
            case OFF -> "Mode: OFF";
            case FORCE_HEADS -> "Mode: Force HEADS";
            case FORCE_TAILS -> "Mode: Force TAILS";
            case ALTERNATE -> "Mode: Alternate";
            case RANDOM_WIN -> "Mode: Biased random";
        };
    }

    private void drawCentered(GuiGraphics g, String s, int cx, int cy) {
        int tw = mc.font.width(s);
        g.drawString(mc.font, s, cx - tw / 2, cy, 0xFFEEEEEE, false);
    }
}
