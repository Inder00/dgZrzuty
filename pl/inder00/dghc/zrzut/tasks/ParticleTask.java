package pl.inder00.dghc.zrzut.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import pl.inder00.dghc.zrzut.basic.Zrzut;
import pl.inder00.dghc.zrzut.basic.utils.ZrzutUtils;
import pl.inder00.dghc.zrzut.packets.ParticleEffect;

import java.util.ArrayList;

public class ParticleTask extends BukkitRunnable {

    public ParticleTask() {
    }

    @Override
    public void run() {

        if(!Bukkit.getOnlinePlayers().isEmpty()){

            for(Zrzut z : ZrzutUtils.getZrzuty()){

                Location loc = z.getLocation();
                World world = loc.getWorld();

            /*for(int i=0;i<=120;i++){
                world.playEffect(loc, Effect.WITCH_MAGIC,0,2);
            }*/
                ParticleEffect.SPELL_WITCH.display(0.4F, 0.6F, 0.4F, 0, 160, loc, new ArrayList<>(Bukkit.getOnlinePlayers()));

                if(z.getTakingOver() != null && z.getTakingOver().getPlayer() != null){
                    if(z.getTakingOver().getPlayer().isOnline()){
                        z.getTakingOver().take(z.getTakingOver().getPlayer().getPlayer());
                    }
                }

            }

        }
    }
}
