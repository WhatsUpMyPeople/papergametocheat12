package com.papergamerig.client.gui.tabs;

import com.papergamerig.client.PaperGameRigClient;
import com.papergamerig.client.gui.RigScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class PaperGameTab extends TabPanel {

    private Button rigToggle, toggleKey, switchKey;
    private EditBox sideAField, sideBField;
    private Button sideAHand, sideBHand;

    public PaperGameTab(RigScreen p, int x, int y, int w, int h) { super(p, x, y, w, h); }

    @Override
    public void init() {
        widgets.clear();
        var cfg = PaperGameRigClient.CONFIG;

        int cx = x + w / 2;
        int bw = 240;
        int bx = cx - bw / 2;

        rigToggle = Button.builder(
            Component.literal("Rig: " + (cfg.paperGameRigOn ? "ON" : "OFF")),
            b -> { cfg.paperGameRigOn = !cfg.paperGameRigOn; cfg.save();
                   b.setMessage(Component.literal("Rig: " + (cfg.paperGameRigOn ? "ON" : "OFF"))); })
            .bounds(bx, y + 22, bw, 20).build();

        toggleKey = Button.builder(
            Component.literal("[" + SettingsTab.glfwName(cfg.toggleRigKey) + "]"), b -> {})
            .bounds(bx, y + 72, bw, 20).build();

        sideAField = new EditBox(mc.font, bx, y + 122, bw - 80, 20, Component.literal(""));
        sideAField.setMaxLength(80);
        sideAField.setValue(cfg.sideA);
        sideAField.setResponder(s -> { cfg.sideA = s; cfg.save(); });

        sideAHand = Button.builder(
            Component.literal("From hand"), b -> grab(0))
            .bounds(bx + bw - 76, y + 122, 76, 20).build();

        sideBField = new EditBox(mc.font, bx, y + 172, bw - 80, 20, Component.literal(""));
        sideBField.setMaxLength(80);
        sideBField.setValue(cfg.sideB);
        sideBField.setResponder(s -> { cfg.sideB = s; cfg.save(); });

        sideBHand = Button.builder(
            Component.literal("From hand"), b -> grab(1))
            .bounds(bx + bw - 76, y + 172, 76, 20).build();

        switchKey = Button.builder(
            Component.literal("[" + SettingsTab.glfwName(cfg.switchSideKey) + "]"), b -> {})
            .bounds(bx, y + 222, bw, 20).build();

        widgets.add(rigToggle);
        widgets.add(toggleKey);
        widgets.add(sideAField);
        widgets.add(sideAHand);
        widgets.add(sideBField);
        widgets.add(sideBHand);
        widgets.add(switchKey);
    }

    private void grab(int side) {
        if (mc.player == null) return;
        var stack = mc.player.getMainHandItem();
        if (stack.isEmpty()) return;
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (side == 0) { PaperGameRigClient.CONFIG.sideA = id.toString(); sideAField.setValue(id.toString()); }
        else           { PaperGameRigClient.CONFIG.sideB = id.toString(); sideBField.setValue(id.toString()); }
        PaperGameRigClient.CONFIG.save();
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float delta) {
        int cx = x + w / 2;
        drawCentered(g, "Rig:",                   cx, y + 10);
        drawCentered(g, "Toggle rig key:",        cx, y + 60);
        drawCentered(g, "Side A:",                cx, y + 110);
        drawCentered(g, "Side B:",                cx, y + 160);
        drawCentered(g, "Switch rig side key:",   cx, y + 210);
    }

    private void drawCentered(GuiGraphics g, String s, int cx, int cy) {
        int tw = mc.font.width(s);
        g.drawString(mc.font, s, cx - tw / 2, cy, 0xFFEEEEEE, false);
    }
}
