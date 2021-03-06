package mariculture.diving;

import mariculture.core.Core;
import mariculture.core.config.GeneralStuff;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.ArmorSlot;
import mariculture.core.lib.WaterMeta;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DivingEventHandler {
    private int tick;
    private static UnderwaterVision vision;

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            tick++;
            if (!player.worldObj.isRemote) {
                Snorkel.init(player);
                if (tick % 5 == 0) {
                    ScubaTank.init(player);
                    ScubaMask.damage(player);
                    if (GeneralStuff.HARDCORE_DIVING > 0) {
                        HardcoreDiving.init(player);
                    }
                }
            } else {
                vision = vision == null ? new UnderwaterVision() : vision.onUpdate(player);
                DivingBoots.init(player);
                LifeJacket.init(player);
                ScubaFin.init(player);
            }
        }
    }

    @SubscribeEvent
    public void onBreaking(BreakSpeed event) {
        EntityPlayer player = event.entityPlayer;
        boolean isValid = false;

        if (ForgeHooks.canHarvestBlock(event.block, player, event.metadata)) if (player.isInsideOfMaterial(Material.water)) {
            // Scuba Suit
            if (PlayerHelper.hasArmor(player, ArmorSlot.LEG, Diving.scubaSuit)) {
                event.newSpeed = event.originalSpeed * 4;

                if (event.block == Core.water && event.metadata == WaterMeta.OYSTER) {
                    event.newSpeed = event.originalSpeed * 128;
                }
            }

            if (PlayerHelper.hasArmor(player, ArmorSlot.FEET, Diving.swimfin) && !player.onGround) {
                event.newSpeed *= 5;
            }

            // Diving Pants
            if (PlayerHelper.hasArmor(player, ArmorSlot.LEG, Diving.divingPants)) {
                event.newSpeed = event.originalSpeed * 2;
                if (event.block == Core.water && event.metadata == WaterMeta.OYSTER) {
                    event.newSpeed = event.originalSpeed * 64;
                }
            }
        }
    }

    private boolean isAllowed(ItemStack stack, ItemStack stack2) {
        if (stack != null && stack.getItem() == Diving.snorkel) {
            return stack2 == null || stack2.getItem() != Items.enchanted_book;
        }

        return true;
    }

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        if (!isAllowed(event.left, event.right) || !isAllowed(event.right, event.left)) {
            event.setCanceled(true);
        }
    }
}
