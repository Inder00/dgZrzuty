package pl.inder00.dghc.zrzut.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import pl.inder00.dghc.zrzut.basic.Zrzut;
import pl.inder00.dghc.zrzut.basic.utils.ZrzutUtils;
import pl.inder00.dghc.zrzut.storage.Config;
import pl.inder00.dghc.zrzut.utils.Util;

public class IntervalTask extends BukkitRunnable {

    Config cfg;

    public IntervalTask() {
        cfg = Config.getInstance();
    }

    @Override
    public void run() {

        if(ZrzutUtils.getZrzuty().size() <= cfg.max$zrzuty){

            if(Bukkit.getOnlinePlayers().size() >= cfg.min$players){

                Location location = Util.randomLocation(cfg.world,cfg.map$size);
                Zrzut zrzut = new Zrzut(location);
                zrzut.broadcast();

            }

        }

    }
}
