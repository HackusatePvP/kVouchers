package cc.fatenetwork.kvoucher.vouch;

import cc.fatenetwork.kvoucher.Voucher;
import cc.fatenetwork.kvoucher.configurations.ConfigFile;
import cc.fatenetwork.kvoucher.utils.StringUtil;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.HashMap;
import java.util.Map;

public class VouchManager {
    @Getter private Map<String, Vouch> vouchers = new HashMap<>();
    @Getter private Map<String, Vouch> voucherDisplayMap = new HashMap<>();
    private final Voucher plugin;

    public VouchManager(Voucher plugin) {
        this.plugin = plugin;
        loadVouchers();
    }

    void loadVouchers() {
        ConfigFile config = plugin.getConfiguration("config");
        for (String s : config.getConfiguration().getConfigurationSection("vouchers").getKeys(false)) {
            String path = "vouchers." + s + ".";
            if (config.getBoolean(path + "enabled")) {
                Vouch vouch = new Vouch(config.getString(path + "name"), config.getString(path + "material").toUpperCase(), config.getString(path + "command"));
                vouch.setDisplayName(config.getString(path + "display-name"));
                vouch.setLore(config.getStringList(path + "lore"));
                vouchers.put(vouch.getName(), vouch);
                ItemStack itemStack = new ItemStack(Material.getMaterial(vouch.getMaterial()));
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(StringUtil.format(vouch.getDisplayName()));
                itemMeta.setLore(StringUtil.format(vouch.getLore()));
                itemStack.setItemMeta(itemMeta);
                voucherDisplayMap.put(itemMeta.getDisplayName(), vouch);
            }
        }
    }

    public ItemStack constructVoucher(Vouch vouch) {
        ItemStack itemStack = new ItemStack(Material.getMaterial(vouch.getMaterial().toUpperCase()));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(StringUtil.format(vouch.getDisplayName()));
        itemMeta.setLore(StringUtil.format(vouch.getLore()));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
