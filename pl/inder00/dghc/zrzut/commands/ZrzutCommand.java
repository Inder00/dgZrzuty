package pl.inder00.dghc.zrzut.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.inder00.dghc.zrzut.Core;
import pl.inder00.dghc.zrzut.basic.Zrzut;
import pl.inder00.dghc.zrzut.basic.utils.ZrzutUtils;
import pl.inder00.dghc.zrzut.storage.Config;
import pl.inder00.dghc.zrzut.utils.Util;

public class ZrzutCommand implements CommandExecutor {

    public ZrzutCommand(Core plugin){
        plugin.getCommand("zrzut").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Config cfg = Config.getInstance();
        if(args.length == 1 || args.length == 2){
            if(sender.hasPermission("zrzut.admin")){
                if(args.length == 1){
                    if(args[0].equalsIgnoreCase("spawn")){
                        Zrzut zrzut = new Zrzut(Util.randomLocation(cfg.world,cfg.map$size));
                        zrzut.broadcast();
                        return false;
                    }
                    if(args[0].equalsIgnoreCase("reload")){
                        long start = System.currentTimeMillis();
                        Config.getInstance().reload();
                        long end = System.currentTimeMillis();
                        sender.sendMessage("§8» §7Config zostal przeladowany w §6"+(end-start)+"§7ms");
                        return false;
                    }
                } else if(args.length == 2){
                    if(args[0].equalsIgnoreCase("give")){
                        if(args[1].equalsIgnoreCase("all")){

                            for(Player player : Bukkit.getOnlinePlayers()){
                                player.getInventory().addItem(cfg.flara);
                                player.sendTitle(cfg.got$everyone[0].replace("{ADMIN}", sender.getName()),cfg.got$everyone[1].replace("{ADMIN}", sender.getName()));
                            }
                            return false;
                        }
                        Player player = Bukkit.getPlayer(args[1]);
                        if(player == null){
                            sender.sendMessage("§4Blad: §cPodany gracz jest offline!");
                            return false;
                        }
                        player.getInventory().addItem(cfg.flara);
                        player.sendTitle(cfg.got$player[0].replace("{ADMIN}", sender.getName()),cfg.got$player[1].replace("{ADMIN}", sender.getName()));
                        sender.sendMessage("§8» §7Gracz §6"+player.getName()+" §7orzymal §6Flare zrzutu");
                        return false;
                    }
                }
            }
        }
        if(ZrzutUtils.getZrzuty().size() <= 0){
            sender.sendMessage(cfg.zrzut$nothing);
            return false;
        }
        for(String s : cfg.zrzut$prefix){
            sender.sendMessage(s);
        }
        int i = 1;
        for(Zrzut z : ZrzutUtils.getZrzuty()){
            sender.sendMessage(cfg.zrzut$style
                    .replace("{NUMBER}", ""+i)
                    .replace("{X}", ""+(z.getLocation().getBlockX()+Util.randomInt(-100,100)))
                    .replace("{Y}", ""+z.getLocation().getBlockY())
                    .replace("{Z}", ""+(z.getLocation().getBlockZ()+Util.randomInt(-100,100)))
            );
            i++;
        }
        return false;
    }
}
