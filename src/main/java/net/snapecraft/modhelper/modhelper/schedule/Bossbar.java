package net.snapecraft.modhelper.modhelper.schedule;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.boss.*;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Bossbar {
    private static Integer i = 6;
    private static Integer currentTextIndex = 0;
    private static Integer randIntBefore = 0;
    public static ServerBossBar bar = ServerBossBar.builder()
            .color(BossBarColors.BLUE)
            .name(Text.of("Evil Boss Bar rawr!"))
            .overlay(BossBarOverlays.NOTCHED_6)
            .percent(0.75f)
            .build();


    private static List<String> textPool = new ArrayList<>(Arrays.asList("§2Heute schon gevoted? /vote", "§9Komm auf unseren Discord! /discord", "§eDiskutiere mit anderen! /forum", "§cMache dich mit den Regeln vertraut! /rules", "§7Gehe in einen Chat-Channel: /channel"));


    public static void bossbarSchedule() {

        Task.Builder taskBuilder = Task.builder();
        taskBuilder.execute(() -> {

            bar.setPercent(i * 0.1666666666666667f);
            if(i == 0) {
                i = 6;
            } else if(i == 6) {
                bar.setName(Text.of(textPool.get(currentTextIndex)));
                currentTextIndex++;
                i--;
            } else {
                i--;
            }


            // Resetting the text index after it exceeds the size of the TextPool
            if(currentTextIndex == textPool.size() - 1) {
                currentTextIndex = 0;
            }


        }).interval(2, TimeUnit.SECONDS).submit(Sponge.getPluginManager().getPlugin("modhelper").get().getInstance().get());
    }




}
