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
    private static Integer i = 4;
    private static Integer randIntBefore = 0;
    public static ServerBossBar bar = ServerBossBar.builder()
            .color(BossBarColors.GREEN)
            .name(Text.of("Evil Boss Bar rawr!"))
            .overlay(BossBarOverlays.PROGRESS)
            .percent(0.75f)
            .build();


    private static List<String> textPool = new ArrayList<>(Arrays.asList("Heute schon gevoted? /vote", "Komm auf unseren Discord! /discord", "Diskutiere mit anderen! /forum", "Mache dich mit den Regeln vertraut! /rules", "Gehe in einen Chat-Channel: /channel"));


    public static void bossbarSchedule() {

        Random rand = new Random();

        Task.Builder taskBuilder = Task.builder();
        taskBuilder.execute(() -> {

            Integer randInt;

            //Prevent choosing the same text as before
            while (true) {
                randInt = rand.nextInt(textPool.size());
                if(randInt != randIntBefore) {
                    randIntBefore = randInt;
                    break;
                }
            }


            bar.setPercent(i * 0.25f);
            if(i == 0) {
                i = 4;
            } else if(i == 4) {
                bar.setName(Text.of(textPool.get(randInt)));
                bar.setColor(chooseRandomBossBarColor(rand));
                i--;
            } else {
                i--;
            }


        }).interval(1, TimeUnit.SECONDS).submit(Sponge.getPluginManager().getPlugin("modhelper").get().getInstance().get());
    }


    public static BossBarColor chooseRandomBossBarColor(Random rand) {
        int randInt = rand.nextInt(6);


        switch (randInt) {
            case 0:
                return BossBarColors.BLUE;
            case 1:
                return BossBarColors.GREEN;
            case 2:
                return BossBarColors.PINK;
            case 3:
                return BossBarColors.PURPLE;
            case 4:
                return BossBarColors.RED;
            case 5:
                return BossBarColors.WHITE;
            case 6:
                return BossBarColors.YELLOW;
        }

        return null;
    }


}
