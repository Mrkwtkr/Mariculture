package mariculture.factory.items;

import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.helpers.cofh.BlockHelper;
import mariculture.core.items.ItemDamageable;
import mariculture.factory.tile.TileGenerator;
import mariculture.factory.tile.TileRotor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemRotor extends ItemDamageable {
    private int meta;

    public ItemRotor(int dmg, int meta) {
        super(dmg);
        this.meta = meta;
        setCreativeTab(MaricultureTab.tabFactory);
    }

    public boolean setBlock(ItemStack stack, World world, int x, int y, int z, ForgeDirection orientation) {
        if (!world.setBlock(x, y, z, Core.renderedMachines, meta, 2)) return false;
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileRotor) {
            ((TileRotor) tile).setFacing(orientation);
            ((TileRotor) tile).setDamage(stack.getItemDamage());
            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                TileEntity other = BlockHelper.getAdjacentTileEntity(tile, dir);
                if (other instanceof TileRotor) {
                    ((TileRotor) other).recheck();
                } else if (other instanceof TileGenerator) {
                    ((TileGenerator) other).reset();
                }
            }
        }

        return true;
    }
}
