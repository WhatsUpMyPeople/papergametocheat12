package com.papergamerig.client.gui.tabs;

import com.papergamerig.client.gui.RigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;

import java.util.ArrayList;
import java.util.List;

public abstract class TabPanel {
    protected final RigScreen parent;
    protected final int x, y, w, h;
    protected final List<AbstractWidget> widgets = new ArrayList<>();
    protected final Minecraft mc = Minecraft.getInstance();

    protected TabPanel(RigScreen parent, int x, int y, int w, int h) {
        this.parent = parent; this.x = x; this.y = y; this.w = w; this.h = h;
    }

    public List<AbstractWidget> widgets() { return widgets; }
    public abstract void init();
    public abstract void render(GuiGraphics g, int mouseX, int mouseY, float delta);

    protected void label(GuiGraphics g, String text, int lx, int ly) {
        g.drawString(mc.font, text, lx, ly, 0xFFDDDDDD, false);
    }
}
