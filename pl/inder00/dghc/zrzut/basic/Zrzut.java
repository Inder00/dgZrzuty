package pl.inder00.dghc.zrzut.basic;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import pl.inder00.dghc.zrzut.Core;
import pl.inder00.dghc.zrzut.basic.utils.ZrzutUtils;
import pl.inder00.dghc.zrzut.storage.Config;
import pl.inder00.dghc.zrzut.utils.Util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Zrzut {

    private Location location;
    private Hologram hologram;
    private Taking takingOver;
    private List<Location> takingLocations;

    public Zrzut(Location location){
        this.location = location;
        this.takingLocations = Lists.newArrayList();
        this.append();
        this.circle();
        this.takingOver = new Taking(this,null);
        ZrzutUtils.add(this);
    }

    private void circle(){

        for(Location l : Util.circle(this.location, 5, 7, false, true, -2)){
            this.takingLocations.add(l);
        }

    }

    private void append(){

        //BLOCK
        Block block = this.location.getBlock();
        block.setType(Material.ENDER_CHEST);

        //CREATE HOLOGRAM
        this.hologram = HologramsAPI.createHologram(Core.getInstance(), block.getLocation());

        //SET HOLOGRAM
        Config cfg = Config.getInstance();
        for(String s : cfg.hologramic){
            this.hologram.appendTextLine(s);
        }

        //TELEPORT HOLOGRAM
        this.hologram.teleport(block.getLocation().add(0.5,2.75D,0));
    }

    public void updateHologram(){
        if(this.hologram != null){
            if(this.hologram.size() != Config.getInstance().hologramic.size()){
                this.hologram.removeLine(this.hologram.size()-1);
            }
            this.hologram.appendTextLine(Config.getInstance().takingOver.replace("{PERCENT}", ""+(new BigDecimal(this.takingOver.getPercent()).setScale(2, RoundingMode.HALF_EVEN).doubleValue())));
        }
    }

    public void broadcast(){
        Config cfg = Config.getInstance();
        String msg = cfg.broadcast.replace("{X}", this.location.getBlockX()+"").replace("{Y}", this.location.getBlockY()+"").replace("{Z}", this.location.getBlockZ()+"");
        for(Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(msg);
        }
    }

    public List<Location> getTakingLocations() {
        return takingLocations;
    }

    public Taking getTakingOver() {
        return takingOver;
    }

    public void setTakingOver(Taking takingOver) {
        this.takingOver = takingOver;
    }

    public Location getLocation() {
        return location;
    }

    public void delete(boolean object){
        if(object == true){
            ZrzutUtils.del(this);
        }
        this.location.getBlock().setType(Material.AIR);
        this.location = null;
        this.hologram.delete();
        this.takingLocations.clear();
        this.takingLocations = null;
        this.takingOver = null;
    }

}
