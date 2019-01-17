package net.snapecraft.modhelper.modhelper;

import com.google.inject.Inject;
import net.snapecraft.modhelper.modhelper.command.BanItemCMD;
import net.snapecraft.modhelper.modhelper.listener.CommonListener;
import net.snapecraft.modhelper.modhelper.listener.ItemListener;
import net.snapecraft.modhelper.modhelper.schedule.AutoChat;
import net.snapecraft.modhelper.modhelper.schedule.Bossbar;
import net.snapecraft.modhelper.modhelper.util.ConfigurationManager;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import java.io.File;

@Plugin(
        id = "modhelper",
        name = "Modhelper",
        description = "SpongeForge plugin for modded Minecraft Servers that handles world creation, item banning and much more.",
        url = "http://snapecraft.ddns.net",
        authors = {
                "MayusYT"
        }
)
public class Modhelper {

    @Inject
    @DefaultConfig(sharedRoot = true)
    private File configFile;


    @Inject
    @DefaultConfig(sharedRoot = true)
    ConfigurationLoader<CommentedConfigurationNode> configManager;

    private String version = "1.0-SNAPSHOT";


    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {

        //Fabulous ASCII-art: http://modhelper.ascii.uk/
        logger.info("The server is ready!\n                     _ _          _                 \n" +
                "                    | | |        | |                \n" +
                " _ __ ___   ___   __| | |__   ___| |_ __   ___ _ __ \n" +
                "| '_ ` _ \\ / _ \\ / _` | '_ \\ / _ \\ | '_ \\ / _ \\ '__|\n" +
                "| | | | | | (_) | (_| | | | |  __/ | |_) |  __/ |   \n" +
                "|_| |_| |_|\\___/ \\__,_|_| |_|\\___|_| .__/ \\___|_| v." + version + "\n" +
                "                                   | |              \n" +
                "                                   |_|              ");

        init();
        logger.info(ConfigurationManager.getInstance().getCraftingBlacklist().toString());
    }

    private void init() {
        AutoChat.autoChat();
        Bossbar.bossbarSchedule();

        ConfigurationManager.getInstance().setup(configFile, configManager);

        Sponge.getEventManager().registerListeners(this, new CommonListener());
        Sponge.getEventManager().registerListeners(this, new ItemListener());

        CommandSpec myCommandSpec = CommandSpec.builder()
                .description(Text.of("Item ban command. Options: [craft, use], Reasons: 1=Duping 2=Lag 3=Overkill 4=Other Bugs, Optional: undo"))
                .permission("modhelper.banItem")
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("option")))/*, GenericArguments.integer(Text.of("reason"))*/, GenericArguments.optional(GenericArguments.string(Text.of("undo"))))
                .executor(new BanItemCMD())
                .build();

        Sponge.getCommandManager().register(this, myCommandSpec, "banitem", "blockitem");
    }

    public static Object getPlugin() {
        return Modhelper.class;
    }

}
