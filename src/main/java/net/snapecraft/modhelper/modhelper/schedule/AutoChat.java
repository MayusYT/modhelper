package net.snapecraft.modhelper.modhelper.schedule;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import java.util.concurrent.TimeUnit;

public class AutoChat {



    public static void autoChat() {
        Task.Builder taskBuilder = Task.builder();
        taskBuilder.execute(() -> {
            for(Player p : Sponge.getServer().getOnlinePlayers()) {
                p.sendMessage(Text.of("Vote now!"));
            }

        }).interval(3, TimeUnit.SECONDS).submit(Sponge.getPluginManager().getPlugin("modhelper").get().getInstance().get());
    }
}
