package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.ItemLib.dropletEarth;
import static mariculture.core.lib.ItemLib.rottenFlesh;
import static mariculture.core.lib.ItemLib.zombie;

import java.util.ArrayList;

import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.api.util.CachedCoords;
import mariculture.core.lib.BaitMeta;
import mariculture.fishery.Fishery;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FishUndead extends FishSpecies {
    @Override
    public int[] setSuitableTemperature() {
        return new int[] { -5, 50 };
    }

    @Override
    public Salinity[] setSuitableSalinity() {
        return new Salinity[] { FRESH, BRACKISH };
    }

    @Override
    public boolean isDominant() {
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 15;
    }

    @Override
    public int getFertility() {
        return 350;
    }

    @Override
    public boolean requiresFood() {
        return true;
    }

    @Override
    public int getWaterRequired() {
        return 55;
    }

    @Override
    public int getAreaOfEffectBonus(ForgeDirection dir) {
        return dir == ForgeDirection.UP || dir == ForgeDirection.DOWN ? 1 : 0;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletEarth, 7.5D);
        addProduct(new ItemStack(Fishery.bait, 1, BaitMeta.MAGGOT), 15D);
        addProduct(zombie, 1D);
    }

    @Override
    public double getFishOilVolume() {
        return 1D;
    }

    @Override
    public ItemStack getLiquifiedProduct() {
        return new ItemStack(rottenFlesh);
    }

    @Override
    public int getLiquifiedProductChance() {
        return 1;
    }

    @Override
    public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        if (world.rand.nextInt(500) == 0) {
            EntityZombie zombie = new EntityZombie(world);
            zombie.setPosition(x, y, z);
            world.spawnEntityInWorld(zombie);
        }
    }

    @Override
    public void affectLiving(EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            entity.addPotionEffect(new PotionEffect(Potion.hunger.id, 150, 1, true));
        }
    }

    @Override
    public boolean canWorkAtThisTime(boolean isDay) {
        return !isDay;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.OLD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return Height.isDeep(height) || !world.isDaytime() ? 15D : 0D;
    }
}
