package cc.fatenetwork.kvoucher.vouch;

import cc.fatenetwork.kvoucher.Voucher;
import cc.fatenetwork.kvoucher.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class VouchListener implements Listener {
    private final Voucher plugin;

    public VouchListener(Voucher plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
            return;
        }
        ItemStack itemStack = player.getItemInHand();
        if (plugin.getVouchManager().getVoucherDisplayMap().get(itemStack.getItemMeta().getDisplayName()) != null) {
            Vouch vouch = plugin.getVouchManager().getVoucherDisplayMap().get(itemStack.getItemMeta().getDisplayName());
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), parse(vouch.getCommand(), player));
            removeItem(player, itemStack.getType(), 1);
            player.updateInventory();
            player.sendMessage(StringUtil.format("&aYou have redeemed your voucher!"));
        }
    }

    private String parse(String command, Player player) {
        command = command.replace("%PLAYER%", player.getName());
        return command;
    }

    private void removeItem(Player p, Material mat, int removeAmount) {
        for (int i = 0; i < p.getInventory().getContents().length; i++) {
            ItemStack item = p.getInventory().getItem(i);
            if (item == null) continue;
            if (item.getAmount() == -1) continue;
            if (item.getType() != mat) continue;
            int itemAmount = item.getAmount();
            if (p.getItemInHand().getAmount() <= removeAmount) {
                p.getInventory().setItem(p.getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
            } else {
                item.setAmount(itemAmount - removeAmount);
            }
        }
    }
}
