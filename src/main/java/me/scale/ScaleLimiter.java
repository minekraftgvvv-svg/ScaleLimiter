package me.scale;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ScaleLimiter extends JavaPlugin implements Listener {

    private double maxScale;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        maxScale = getConfig().getDouble("max-scale", 2.0);

        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("ScaleLimiter enabled");
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String msg = e.getMessage().toLowerCase();

        if (msg.startsWith("/attribute") && msg.contains("minecraft:scale")) {

            String[] parts = msg.split(" ");

            try {
                double value = Double.parseDouble(parts[parts.length - 1]);

                if (value > maxScale) {
                    e.setCancelled(true);

                    String player = e.getPlayer().getName();

                    getServer().dispatchCommand(
                            getServer().getConsoleSender(),
                            "attribute " + player + " minecraft:scale base set " + maxScale
                    );

                    e.getPlayer().sendMessage("§cМаксимальный размер: " + maxScale);
                }

            } catch (Exception ignored) {}
        }
    }
}
