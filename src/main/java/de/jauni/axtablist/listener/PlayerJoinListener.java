package de.jauni.axtablist.listener;

import de.jauni.axtablist.AxTabList;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

public class PlayerJoinListener implements Listener {
    AxTabList reference;

    public PlayerJoinListener(AxTabList reference) {
        this.reference = reference;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective("tablist", "dummy");
        objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        objective.getScore(p.getName());

        p.setScoreboard(board);
        p.setPlayerListHeader(PlaceholderAPI.setPlaceholders(p, reference.getMessage("tablist.header")));
        p.setPlayerListFooter(PlaceholderAPI.setPlaceholders(p, reference.getMessage("tablist.footer")));
    }
}
