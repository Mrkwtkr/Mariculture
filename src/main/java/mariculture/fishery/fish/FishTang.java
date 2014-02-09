package mariculture.fishery.fish;

import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.Dye;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishTang extends FishSpecies {
	public FishTang(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.NEMO;
	}

	@Override
	public int getLifeSpan() {
		return 37;
	}

	@Override
	public int getFertility() {
		return 185;
	}

	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public double getFishOilVolume() {
		return 0.300;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 6.5D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_AQUA), 4.5D);
		addProduct(new ItemStack(Items.dye, 1, Dye.LAPIS), 2.0D);
	}

	@Override
	public int getTankLevel() {
		return 3;
	}

	@Override
	public int getCatchChance() {
		return 4;
	}
	
	@Override
	public EnumRodQuality getRodNeeded() {
		return EnumRodQuality.GOOD;
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 1 };
	}
	
	@Override
	public int getFishMealSize() {
		return 1;
	}
}
