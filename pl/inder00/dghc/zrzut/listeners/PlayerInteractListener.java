package pl.inder00.dghc.zrzut.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.inder00.dghc.zrzut.basic.Zrzut;
import pl.inder00.dghc.zrzut.basic.utils.ZrzutUtils;
import pl.inder00.dghc.zrzut.storage.Config;
import pl.inder00.dghc.zrzut.utils.Util;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Block block = e.getClickedBlock();
        Action action = e.getAction();
        if(action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_AIR)){
            ItemStack itemStack = e.getItem();
            Config cfg = Config.getInstance();
            if(itemStack != null && itemStack.getType().equals(cfg.flara.getType())){
                if(itemStack.hasItemMeta()){
                    if(itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(cfg.flara.getItemMeta().getDisplayName())){

                        //BROADCAST
                        Bukkit.broadcastMessage(cfg.used$flara.replace("{PLAYER}", p.getName()));

                        //CREATE DROP
                        Zrzut zrzut = new Zrzut(Util.randomLocationSquare(p));

                        //BROADCAST COORDINATIONS
                        zrzut.broadcast();

                        //REMOVE FLARA
                        p.getInventory().removeItem(cfg.flara);

                        //CANCEL EVENT
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        } else if(action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.LEFT_CLICK_BLOCK)){
            if(block != null){
                if(block.getType().equals(Material.ENDER_CHEST)){
                    Location loc = block.getLocation();
                    Zrzut zrzut = ZrzutUtils.get(loc);
                    if(zrzut != null){

                        if(zrzut.getTakingOver().getPercent() < 100){
                            e.setCancelled(true);
                            p.sendMessage("§cAby otworzyc zrzut, musisz go przejac!");
                            return;
                        }

                        //DROP ITEMS
                        Config cfg = Config.getInstance();
                        int drops = Util.randomInt(cfg.min$drops,cfg.max$drops);
                        for(int i=0;i<drops;i++){
                            loc.getWorld().dropItem(loc,cfg.randomItem());
                        }

                        //DELETE DROP
                        zrzut.delete(true);

                        //CANCEL EVENT
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }

}
