package net.snapecraft.modhelper.modhelper.listener;

import net.snapecraft.modhelper.modhelper.schedule.Bossbar;
import org.spongepowered.api.boss.BossBarColors;
import org.spongepowered.api.boss.BossBarOverlays;
import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;

public class CommonListener {

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event, @Root Player player) {
        Bossbar.bar.addPlayer(player);
    }
}
