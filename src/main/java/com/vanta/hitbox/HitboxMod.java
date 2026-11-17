package com.vanta.hitbox;

import com.vanta.hitbox.gui.HitboxScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class HitboxMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Keybinds.register();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (Keybinds.openMenu.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new HitboxScreen());
                }
            }
        });
    }
}
