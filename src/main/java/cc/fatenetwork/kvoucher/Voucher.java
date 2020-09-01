package cc.fatenetwork.kvoucher;

import cc.fatenetwork.kvoucher.configurations.ConfigFile;
import cc.fatenetwork.kvoucher.vouch.VouchListener;
import cc.fatenetwork.kvoucher.vouch.VouchManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public final class Voucher extends JavaPlugin {
    private VouchManager vouchManager;
   @Getter private static Voucher plugin;
    private List<ConfigFile> files = new ArrayList<>();

    @Override
    public void onEnable() {
        plugin = this;
        loadConfiguration();
        vouchManager = new VouchManager(this);
        Bukkit.getPluginManager().registerEvents(new VouchListener(this), this);
    }

    @Override
    public void onDisable() {


        plugin = null;
    }

    public ConfigFile getConfiguration(String name) {
        return files.stream().filter(config -> config.getName().equals(name)).findFirst().orElse(null);
    }

    void loadConfiguration() {
        files.addAll(Arrays.asList(
                new ConfigFile("config")
        ));
    }
}
