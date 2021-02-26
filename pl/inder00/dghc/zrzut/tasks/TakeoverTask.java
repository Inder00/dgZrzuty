package pl.inder00.dghc.zrzut.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.inder00.dghc.zrzut.basic.Zrzut;
import pl.inder00.dghc.zrzut.basic.utils.ZrzutUtils;

public class TakeoverTask extends BukkitRunnable {

    @Override
    public void run() {

        if(true){

            if(Bukkit.getOnlinePlayers().size() == 0) return;
            if(ZrzutUtils.getZrzuty().size() == 0) return;

            for(Zrzut z : ZrzutUtils.getZrzuty()){
                if(z.getTakingOver() != null && z.getTakingOver().getPlayer() != null){
                    if(z.getTakingOver().getPlayer().isOnline()){
                        z.getTakingOver().take(z.getTakingOver().getPlayer().getPlayer());
                    }
                }
            }

        }
    }
}
