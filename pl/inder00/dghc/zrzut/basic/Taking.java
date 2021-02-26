package pl.inder00.dghc.zrzut.basic;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import pl.inder00.dghc.zrzut.utils.Util;

public class Taking {

    private Zrzut zrzut;
    private OfflinePlayer player;
    private double percent;

    public Taking(Zrzut zrzut, OfflinePlayer player) {
        this.zrzut = zrzut;
        this.player = player;
        this.percent = 0;
    }

    public Zrzut getZrzut() {
        return zrzut;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public void setPlayer(OfflinePlayer player) {
        this.player = player;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public boolean take(Player takePlayer){
        if(this.percent >= 100) return true;
        boolean isIn = false;
        for(Location loc : zrzut.getTakingLocations()){
            if(Util.locationEquals(loc, takePlayer.getLocation())){
                isIn = true;
                break;
            }
        }
        if(isIn == true){
            if(this.player == null){
                this.player = takePlayer;
                this.percent = 0;
                this.zrzut.updateHologram();
                return true;
            }
            if(this.player.isOnline()){
                if(this.player.getPlayer().equals(takePlayer)){
                    if(this.percent < 100){
                        this.percent = percent+0.1;
                        this.zrzut.updateHologram();
                    }
                } else {
                    boolean isInTwo = false;
                    for(Location loc : zrzut.getTakingLocations()){
                        if(Util.locationEquals(loc, this.player.getPlayer().getLocation())){
                            isInTwo = true;
                            return true;
                        }
                    }
                    if(isInTwo == false){
                        this.player = takePlayer;
                        this.percent = 0;
                        this.zrzut.updateHologram();
                    }
                    return false;
                }
            } else {
                this.player = takePlayer;
                this.percent = 0;
                this.zrzut.updateHologram();
            }
        } else {
            this.percent = 0;
        }
        return true;
    }
}
