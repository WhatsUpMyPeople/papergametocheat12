package com.papergamerig.client.gui;

import com.papergamerig.client.PaperGameRigClient;
import com.papergamerig.client.gui.tabs.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class RigScreen extends Screen {

    public enum Tab { SETTINGS, PAPERGAME, FIFTY_FIFTY, BLACKJACK }
    private static Tab currentTab = Tab.PAPERGAME;

    private final String[] tabLabels = {"Settings", "PaperGame", "50/50", "BlackJack"};
    private final int[] tabX = new int[4];
    private final int[] tabW = new int[4];
    private int panelX, panelY, panelW, panelH;

    private TabPanel settingsPanel, paperPanel, fiftyPanel, bjPanel;

    public RigScreen() { super(Component.literal("PaperGameRig")); }

    @Override
    protected void init() {
        panelW = Math.min(540, this.width - 60);
        panelH = Math.min(420, this.height - 60);
        panelX = (this.width - panelW) / 2;
        panelY = (this.height - panelH) / 2;

        int tabBarY = panelY + 44;
        int cursor = panelX + 20;
        for (int i = 0; i < 4; i++) {
            int w = font.width(tabLabels[i]) + 24;
            tabX[i] = cursor;
            tabW[i] = w;
            cursor += w + 8;
        }

        int contentX = panelX + 20;
        int contentY = tabBarY + 36;
        int contentW = panelW - 40;
        int contentH = panelH - (contentY - panelY) - 20;

        settingsPanel = new SettingsTab(this, contentX, contentY, contentW, contentH);
        paperPanel    = new PaperGameTab(this, contentX, contentY, contentW, contentH);
        fiftyPanel    = new FiftyFiftyTab(this, contentX, contentY, contentW, contentH);
        bjPanel       = new BlackjackTab(this, contentX, contentY, contentW, contentH);

        rebuildCurrent();
    }

    private void rebuildCurrent() {
        this.clearWidgets();
        TabPanel p = active();
        p.init();
        p.widgets().forEach(this::addRenderableWidget);
    }

    private TabPanel active() {
        return switch (currentTab) {
            case SETTINGS   -> settingsPanel;
            case PAPERGAME  -> paperPanel;
            case FIFTY_FIFTY-> fiftyPanel;
            case BLACKJACK  -> bjPanel;
        };
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float delta) {
        this.renderBackground(g, mouseX, mouseY, delta);
        LiquidGlass.backdrop(g, this.width, this.height);
        LiquidGlass.panel(g, panelX, panelY, panelW, panelH);

        String title = switch (currentTab) {
            case SETTINGS   -> "Settings";
            case PAPERGAME  -> "PaperGame — rig settings";
            case FIFTY_FIFTY-> "50/50 — rig settings";
            case BLACKJACK  -> "BlackJack — rig settings";
        };
        int tw = font.width(title);
        g.drawString(font, title, panelX + (panelW - tw) / 2, panelY + 16, 0xFFFFFFFF, true);

        int tabBarY = panelY + 44;
        for (int i = 0; i < 4; i++) {
            boolean active = i == currentTab.ordinal();
            boolean hover = mouseX >= tabX[i] && mouseX < tabX[i] + tabW[i]
                         && mouseY >= tabBarY && mouseY < tabBarY + 22;
            LiquidGlass.button(g, tabX[i], tabBarY, tabW[i], 22, active, hover);
            int lw = font.width(tabLabels[i]);
            g.drawString(font, tabLabels[i],
                tabX[i] + (tabW[i] - lw) / 2, tabBarY + 7,
                active ? 0xFFFFFFFF : 0xFFDDDDDD, false);
        }

        active().render(g, mouseX, mouseY, delta);

        super.render(g, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int tabBarY = panelY + 44;
        for (int i = 0; i < 4; i++) {
            if (mouseX >= tabX[i] && mouseX < tabX[i] + tabW[i]
             && mouseY >= tabBarY && mouseY < tabBarY + 22) {
                currentTab = Tab.values()[i];
                rebuildCurrent();
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void onClose() {
        PaperGameRigClient.CONFIG.save();
        super.onClose();
    }

    @Override
    public boolean isPauseScreen() { return false; }
}
