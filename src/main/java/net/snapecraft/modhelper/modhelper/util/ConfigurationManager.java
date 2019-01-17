package net.snapecraft.modhelper.modhelper.util;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigurationManager {

    private static ConfigurationManager instance = new ConfigurationManager();

    public static ConfigurationManager getInstance() {
        return instance;
    }

    private ConfigurationLoader<CommentedConfigurationNode> configLoader;
    private CommentedConfigurationNode config;

    public void setup(File configFile, ConfigurationLoader<CommentedConfigurationNode> configLoader) {
        this.configLoader = configLoader;

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                loadConfig();
                config.getNode("craftingBlacklist").setComment("Items that are listed here aren't able to be crafted.").setValue(new ArrayList<>(Arrays.asList("dummy1", "dummy2")));
                config.getNode("useBlacklist").setComment("Items that are listed here aren't able to be placed/used.").setValue(new ArrayList<>(Arrays.asList("neat", "is a mod by vazkii")));
                saveConfig();
            }

            catch (Exception e) {
                e.printStackTrace();
            }
        }

        else {
            loadConfig();
        }
    }

    public CommentedConfigurationNode getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            configLoader.save(config);
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            config = configLoader.load();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getCraftingBlacklist() {
        try {
            return config.getNode("craftingBlacklist").getList(TypeToken.of(String.class));
        } catch (Exception e) {
            return null;
        }
    }

    public void addItemToCraftingBlacklist(String itemID) {
        List<String> craftlist = getCraftingBlacklist();
        craftlist.add(itemID);

        try{
            config.getNode("craftingBlacklist").setValue(craftlist);
            saveConfig();
            loadConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeItemFromCraftingBlacklist(String itemID) {


        try{
            List<String> craftlist = getCraftingBlacklist();
            craftlist.remove(itemID);
            config.getNode("craftingBlacklist").setValue(craftlist);
            saveConfig();
            loadConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getUseBlacklist() {
        try {
            return config.getNode("useBlacklist").getList(TypeToken.of(String.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addItemToUseBlacklist(String itemID) {
        List<String> uselist = getUseBlacklist();
        uselist.add(itemID);
        try{
            config.getNode("useBlacklist").setValue(uselist);
            saveConfig();
            loadConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeItemFromUseBlacklist(String itemID) {

        try{
            List<String> uselist = getUseBlacklist();
            uselist.remove(itemID);
            config.getNode("useBlacklist").setValue(uselist);
            saveConfig();
            loadConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
