package pl.inder00.dghc.zrzut.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.inder00.dghc.zrzut.basic.Zrzut;
import pl.inder00.dghc.zrzut.basic.utils.ZrzutUtils;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event){

        if(event.isCancelled()) return;

        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if(from.getBlockX() != to.getBlockX() || from.getBlockZ() != to.getBlockZ()){

            Zrzut z  = ZrzutUtils.isInTakingOver(to);

            if(z != null){
                if(z.getTakingOver() != null){
                    if(z.getTakingOver().getPlayer() != null){
                        if(z.getTakingOver().getPlayer().isOnline()){
                            if(z.getTakingOver().getPlayer().getPlayer().equals(player)){
                                return;
                            }
                        }
                    }
                    z.getTakingOver().take(player);
                    return;
                }
            }

        }

    }

}
