package me.alex.faygo.ladder;

import lombok.*;
import me.alex.faygo.utility.BukkitSerialization;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Ladder {

    @NonNull
    @Getter
    @Setter
    private String name;

    private String item;
    private String inventory;
    private String armor;

    @SneakyThrows
    public Inventory getInventory() {
        return BukkitSerialization.fromBase64(this.inventory);
    }

    public void setInventory(Inventory inventory) {
        this.inventory = BukkitSerialization.toBase64(inventory);
    }

    @SneakyThrows
    public ItemStack[] getArmor() {
        return BukkitSerialization.itemStackArrayFromBase64(this.armor);
    }

    public void setArmor(ItemStack[] armor) {
        this.armor = BukkitSerialization.itemStackArrayToBase64(armor);
    }

    @SneakyThrows
    public ItemStack getItem() {
        return BukkitSerialization.itemStackArrayFromBase64(this.item)[0];
    }

    public void setItem(ItemStack item) {
        this.item = BukkitSerialization.itemStackArrayToBase64(new ItemStack[]{ item });
    }


}
