package cc.fatenetwork.kvoucher.vouch;

import cc.fatenetwork.kvoucher.Voucher;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VoucherAPI {

    public static void giveVoucherItem(Player player, String voucher) {
        if (Voucher.getPlugin().getVouchManager().getVouchers().get(voucher) != null) {
            Vouch vouch = Voucher.getPlugin().getVouchManager().getVouchers().get(voucher);
            player.getInventory().addItem(Voucher.getPlugin().getVouchManager().constructVoucher(vouch));
        }
    }

    public static ItemStack getVoucherItem(String voucher) {
        if (Voucher.getPlugin().getVouchManager().getVouchers().get(voucher) != null) {
            Vouch vouch = Voucher.getPlugin().getVouchManager().getVouchers().get(voucher);
            return Voucher.getPlugin().getVouchManager().constructVoucher(vouch);
        }
        return null;
    }

}
