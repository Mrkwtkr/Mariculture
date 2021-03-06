package mariculture.plugins;

import static mariculture.api.fishery.Loot.Rarity.GOOD;
import static mariculture.api.fishery.Loot.Rarity.JUNK;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.Loot;
import mariculture.api.fishery.Loot.Rarity;
import mariculture.api.fishery.RodType;
import mariculture.api.util.Text;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.Modules;
import mariculture.core.util.Fluids;
import mariculture.fishery.Fishery;
import mariculture.magic.Magic;
import mariculture.plugins.Plugins.Plugin;
import mariculture.plugins.bloodmagic.BloodRodType;
import mariculture.plugins.bloodmagic.ItemBoundRod;
import mariculture.plugins.bloodmagic.ItemMobMagnetBloodEdition;
import mariculture.plugins.bloodmagic.RitualOfTheBloodRiver;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRegistry;
import WayofTime.alchemicalWizardry.api.rituals.Rituals;
import WayofTime.alchemicalWizardry.common.items.BoundArmour;

public class PluginBloodMagic extends Plugin {
    public PluginBloodMagic(String name) {
        super(name);
    }

    public static final RodType BLOOD = new BloodRodType(95, 15D, 20D, 5D, 45);
    public static Item rodBlood;

    public static ItemStack demonBloodShard;
    public static ItemStack weakBloodShard;

    @Override
    public void preInit() {
        Fluids.add("blood", FluidRegistry.getFluid("life essence"), 250, true);
        rodBlood = new ItemBoundRod().setUnlocalizedName("rodBlood");
        RegistryHelper.registerItems(new Item[] { rodBlood });
        Fishing.fishing.registerRod(rodBlood, BLOOD);
    }

    private ItemStack getUndamaged(String str) {
        return getItem(str, 0);
    }

    @Override
    public void init() {
        if (Modules.isActive(Modules.fishery)) {
            demonBloodShard = getUndamaged("demonBloodShard");
            weakBloodShard = getUndamaged("weakBloodShard");

            // Fishing
            Fishing.fishing.addBait(new ItemStack(Items.rotten_flesh), 35);
            Fishing.fishing.addBait(weakBloodShard, 75);
            Fishing.fishing.addBait(demonBloodShard, 100);
            Fishing.fishing.addBaitForQuality(new ItemStack(Items.rotten_flesh), BLOOD);
            Fishing.fishing.addBaitForQuality(weakBloodShard, BLOOD);
            Fishing.fishing.addBaitForQuality(demonBloodShard, BLOOD);

            addLoot(getUndamaged("blankSlate"), JUNK, 10);
            addLoot(new ItemStack(Blocks.web), JUNK, 10);
            addLoot(getItem("baseItems", 3), GOOD, 10);
            addLoot(getItem("baseAlchemyItems", 5), GOOD, 10);

            addLoot(getUndamaged("simpleCatalyst"), GOOD, 7);
            addLoot(getItem("baseItems", 0), GOOD, 7);

            addLoot(getUndamaged("weakFillingAgent"), JUNK, 15);
            addLoot(getItem("baseAlchemyItems", 3), JUNK, 15);
            addLoot(getItem("baseItems", 1), GOOD, 15);
            addLoot(getUndamaged("reinforcedSlate"), JUNK, 20);
            addLoot(getUndamaged("alchemyFlask"), GOOD, 40);

            addLoot(getUndamaged("standardFillingAgent"), JUNK, 50);
            addLoot(getUndamaged("aether"), GOOD, 50);
            addLoot(getItem("baseAlchemyItems", 4), GOOD, 45);
            addLoot(getItem("baseItems", 4), GOOD, 45);

            addLoot(getUndamaged("imbuedSlate"), JUNK, 75);
            addLoot(getUndamaged("enhancedFillingAgent"), GOOD, 75);
            addLoot(getUndamaged("itemKeyOfDiablo"), GOOD, 75);

            ItemStack loot = getUndamaged("boundBoots");
            loot.setTagCompound(new NBTTagCompound());
            ((BoundArmour) loot.getItem()).saveInternalInventory(loot, new ItemStack[9]);
            addLoot(loot, GOOD, 200);

            // Rituals
            Rituals.ritualList.add(new Rituals(1, 50000, new RitualOfTheBloodRiver(), Text.translate("ritual")));
            BindingRegistry.registerRecipe(new ItemStack(rodBlood), new ItemStack(Fishery.rodTitanium));
        }

        if (Modules.isActive(Modules.magic)) {
            Magic.magnet = new ItemMobMagnetBloodEdition(0).setUnlocalizedName("mobMagnet");
        }
    }

    private void addLoot(ItemStack stack, Rarity rarity, double chance) {
        Fishing.fishing.addLoot(new Loot(stack, chance, rarity, Short.MAX_VALUE, BLOOD, true));
    }

    @Override
    public void postInit() {

    }
}
