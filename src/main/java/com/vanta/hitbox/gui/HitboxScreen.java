package com.vanta.hitbox.gui;

import com.vanta.hitbox.HitboxConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class HitboxScreen extends Screen {
    public HitboxScreen() { super(Text.literal("Hitbox Expander")); }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int y = this.height / 2 - 40;

        addDrawableChild(ButtonWidget.builder(
            Text.literal("Enabled: " + (HitboxConfig.enabled ? "ON" : "OFF")),
            b -> {
                HitboxConfig.enabled = !HitboxConfig.enabled;
                b.setMessage(Text.literal("Enabled: " + (HitboxConfig.enabled ? "ON" : "OFF")));
            }).dimensions(cx - 100, y, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(
            Text.literal("Multiplier: " + String.format("%.2f", HitboxConfig.multiplier)),
            b -> {}).dimensions(cx - 100, y + 30, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("-0.05"), b -> {
            HitboxConfig.multiplier = Math.max(1.0f, HitboxConfig.multiplier - 0.05f);
            refresh();
        }).dimensions(cx - 100, y + 55, 95, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("+0.05"), b -> {
            HitboxConfig.multiplier = Math.min(2.0f, HitboxConfig.multiplier + 0.05f);
            refresh();
        }).dimensions(cx + 5, y + 55, 95, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Close"), b -> close())
            .dimensions(cx - 100, y + 90, 200, 20).build());
    }

    private void refresh() {
        this.clearChildren();
        this.init();
    }

    @Override
    public void render(DrawContext ctx, int mx, int my, float d) {
        this.renderBackground(ctx, mx, my, d);
        ctx.drawCenteredTextWithShadow(this.textRenderer,
            "Hitbox Expander — Right Shift", this.width / 2, 20, 0xFFFFFF);
        super.render(ctx, mx, my, d);
    }

    @Override
    public boolean shouldPause() { return false; }
}
