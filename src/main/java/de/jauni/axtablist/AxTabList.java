package de.jauni.axtablist;

import de.jauni.axtablist.command.ReloadCommand;
import de.jauni.axtablist.listener.PlayerJoinListener;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.io.File;

public final class AxTabList extends JavaPlugin {

    private File langFile;
    private FileConfiguration langConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        createLangFile();
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getCommand("reload").setExecutor(new ReloadCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void createLangFile() {
        langFile = new File(getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            saveResource("lang.yml", false);
        }
        langConfig = YamlConfiguration.loadConfiguration(langFile);
    }

    public String getMessage(String path) {
        return langConfig.getString(path);
    }

    public void setScoreboard(Player p) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective("tablist", "dummy");
        objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        objective.getScore(p.getName());

        p.setScoreboard(board);
        p.setPlayerListHeader(PlaceholderAPI.setPlaceholders(p, getMessage("tablist.header")));
        p.setPlayerListFooter(PlaceholderAPI.setPlaceholders(p, getMessage("tablist.footer")));
    }

    public void reloadLangFile() {
        langConfig = YamlConfiguration.loadConfiguration(langFile);
    }
}
