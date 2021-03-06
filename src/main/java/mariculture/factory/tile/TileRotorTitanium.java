package mariculture.factory.tile;

import mariculture.factory.Factory;
import net.minecraft.item.ItemStack;

public class TileRotorTitanium extends TileRotor {
    @Override
    protected double getTier() {
        return 5D;
    }

    @Override
    protected int getMaxDamage() {
        return 5760000;
    }

    @Override
    public ItemStack getDrop() {
        ItemStack stack = new ItemStack(Factory.turbineTitanium);
        stack.setItemDamage((int) Math.floor(damage / 200));
        return stack;
    }
}
