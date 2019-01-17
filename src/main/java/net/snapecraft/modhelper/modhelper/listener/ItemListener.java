package net.snapecraft.modhelper.modhelper.listener;

import com.flowpowered.math.vector.Vector3d;
import net.snapecraft.modhelper.modhelper.util.ConfigurationManager;
import org.spongepowered.api.effect.sound.PitchModulation;
import org.spongepowered.api.effect.sound.SoundCategories;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.living.humanoid.HandInteractEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.event.item.inventory.UseItemStackEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.crafting.CraftingOutput;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.translation.locale.Locales;

import java.util.Locale;

public class ItemListener {


    /**
     * Prevents the crafting of blacklisted Items.
     */
    @Listener
    public void onItemCraft(ClickInventoryEvent event, @First Player player, @Getter("getTargetInventory") Inventory inventory) {

        Inventory craftingOutputs = inventory.query(QueryOperationTypes.INVENTORY_TYPE.of(CraftingOutput.class));

        craftingOutputs.slots().forEach(slot -> slot.peek().ifPresent(itemStack -> {

            ItemType itemType = itemStack.getType();
            String itemId = itemType.getId();


            if (ConfigurationManager.getInstance().getCraftingBlacklist().contains(itemId)) {
                event.setCancelled(true);
                player.playSound(SoundTypes.BLOCK_NOTE_BASS, SoundCategories.HOSTILE, player.getPosition(), 1, PitchModulation.C1);
                player.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.RED, "Du darfst dieses Item nicht craften!"));
            }
        }));
    }


    /**
     * Prevents the use of blacklisted Items
     */
    @Listener
    public void onItemUse(InteractItemEvent event, @Root Player player) {
        if (ConfigurationManager.getInstance().getUseBlacklist().contains(event.getItemStack().getType().getId())) {
            event.setCancelled(true);
            player.playSound(SoundTypes.BLOCK_NOTE_BASS, SoundCategories.HOSTILE, player.getPosition(), 1, PitchModulation.C1);
            player.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.RED, "Du darfst dieses Item nicht benutzen!"));
        }
    }


}





