package com.papergamerig.client.gui;

import net.minecraft.client.gui.GuiGraphics;

/**
 * Liquid-glass primitives: frosted rounded panels, soft inner glow, highlight sweep.
 * Pure vanilla GuiGraphics calls — stacked translucent rects simulate the depth and
 * blur without needing a custom shader pass.
 */
public final class LiquidGlass {

    public static final int GLASS_FILL      = 0x30FFFFFF;
    public static final int GLASS_FILL_DARK = 0x60101014;
    public static final int GLASS_STROKE    = 0x66FFFFFF;
    public static final int GLASS_SHADOW    = 0x80000000;
    public static final int GLASS_HIGHLIGHT = 0x40FFFFFF;
    public static final int GLASS_ACCENT    = 0xFFB388FF;

    private LiquidGlass() {}

    public static void backdrop(GuiGraphics g, int w, int h) {
        g.fill(0, 0, w, h, 0xA0000000);
        g.fillGradient(0, 0, w, h / 2, 0x30FFFFFF, 0x00FFFFFF);
    }

    public static void panel(GuiGraphics g, int x, int y, int w, int h) {
        g.fill(x + 2, y + 4, x + w + 2, y + h + 4, GLASS_SHADOW);
        g.fill(x, y, x + w, y + h, GLASS_FILL_DARK);
        g.fillGradient(x, y, x + w, y + h, 0x30FFFFFF, 0x10FFFFFF);
        g.fillGradient(x + 1, y + 1, x + w - 1, y + 10, GLASS_HIGHLIGHT, 0x00FFFFFF);
        strokeRect(g, x, y, w, h, GLASS_STROKE);
    }

    public static void button(GuiGraphics g, int x, int y, int w, int h,
                              boolean active, boolean hovered) {
        int base = active ? 0x55FFFFFF : (hovered ? 0x40FFFFFF : 0x25FFFFFF);
        g.fill(x, y, x + w, y + h, 0x50101014);
        g.fillGradient(x, y, x + w, y + h, base, 0x15FFFFFF);
        strokeRect(g, x, y, w, h, active ? GLASS_ACCENT : GLASS_STROKE);
        if (active) {
            g.fill(x + 4, y + h - 2, x + w - 4, y + h - 1, GLASS_ACCENT);
        }
    }

    public static void field(GuiGraphics g, int x, int y, int w, int h, boolean focused) {
        g.fill(x, y, x + w, y + h, 0x90000000);
        strokeRect(g, x, y, w, h, focused ? GLASS_ACCENT : GLASS_STROKE);
    }

    public static void strokeRect(GuiGraphics g, int x, int y, int w, int h, int color) {
        g.fill(x,         y,         x + w,     y + 1,     color);
        g.fill(x,         y + h - 1, x + w,     y + h,     color);
        g.fill(x,         y,         x + 1,     y + h,     color);
        g.fill(x + w - 1, y,         x + w,     y + h,     color);
    }
}
