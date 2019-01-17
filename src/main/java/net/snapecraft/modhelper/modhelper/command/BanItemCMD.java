package net.snapecraft.modhelper.modhelper.command;

import net.snapecraft.modhelper.modhelper.util.ConfigurationManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.effect.sound.PitchModulation;
import org.spongepowered.api.effect.sound.SoundCategories;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class BanItemCMD implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String option = args.<String>getOne("option").get();
        Boolean undo = false;

        if(args.<String>getOne("undo").isPresent()) { if(args.<String>getOne("undo").get().equalsIgnoreCase("undo")) { undo = true; } }


        if(!undo) {
            if(option.equalsIgnoreCase("craft")) {
                if(!((Player) src).getItemInHand(HandTypes.MAIN_HAND).isPresent()) {
                    src.sendMessage(Text.of("Du musst ein Item in der Hand halten!"));
                } else {
                    ConfigurationManager.getInstance().addItemToCraftingBlacklist(((Player)src).getItemInHand(HandTypes.MAIN_HAND).get().getType().getId());
                    src.sendMessage(Text.of("Folgendes Item wurde gebannt: " + ((Player) src).getItemInHand(HandTypes.MAIN_HAND).get().getType().getId()));
                    ((Player)src).playSound(SoundTypes.ENTITY_PLAYER_LEVELUP, SoundCategories.PLAYER, ((Player)src).getPosition(), 1);
                }
            } else if(option.equalsIgnoreCase("use")) {
                if(!((Player) src).getItemInHand(HandTypes.MAIN_HAND).isPresent()) {
                    src.sendMessage(Text.of("Du musst ein Item in der Hand halten!"));
                } else {
                    ConfigurationManager.getInstance().addItemToUseBlacklist(((Player)src).getItemInHand(HandTypes.MAIN_HAND).get().getType().getId());
                    src.sendMessage(Text.of("Folgendes Item wurde gebannt: "  + ((Player) src).getItemInHand(HandTypes.MAIN_HAND).get().getType().getId()));
                    ((Player)src).playSound(SoundTypes.ENTITY_PLAYER_LEVELUP, SoundCategories.PLAYER, ((Player)src).getPosition(), 1);
                }
            }

        } else {
            if(option.equalsIgnoreCase("craft")) {
                if(!((Player) src).getItemInHand(HandTypes.MAIN_HAND).isPresent()) {
                    src.sendMessage(Text.of("Du musst ein Item in der Hand halten!"));
                } else {
                    ConfigurationManager.getInstance().removeItemFromCraftingBlacklist(((Player)src).getItemInHand(HandTypes.MAIN_HAND).get().getType().getId());
                    src.sendMessage(Text.of("Folgendes Item wurde entbannt: " + ((Player) src).getItemInHand(HandTypes.MAIN_HAND).get().getType().getId()));
                    ((Player)src).playSound(SoundTypes.ENTITY_PLAYER_LEVELUP, SoundCategories.PLAYER, ((Player)src).getPosition(), 1);
                }
            } else if(option.equalsIgnoreCase("use")) {
                if(!((Player) src).getItemInHand(HandTypes.MAIN_HAND).isPresent()) {
                    src.sendMessage(Text.of("Du musst ein Item in der Hand halten!"));
                } else {
                    ConfigurationManager.getInstance().removeItemFromUseBlacklist(((Player)src).getItemInHand(HandTypes.MAIN_HAND).get().getType().getId());
                    src.sendMessage(Text.of("Folgendes Item wurde entbannt: " + ((Player) src).getItemInHand(HandTypes.MAIN_HAND).get().getType().getId()));
                    ((Player)src).playSound(SoundTypes.ENTITY_PLAYER_LEVELUP, SoundCategories.PLAYER, ((Player)src).getPosition(), 1);
                }
            }
        }


        return CommandResult.success();
    }
}
