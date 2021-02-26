package pl.inder00.dghc.zrzut.basic.utils;

import org.bukkit.Location;
import pl.inder00.dghc.zrzut.basic.Zrzut;
import pl.inder00.dghc.zrzut.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class ZrzutUtils {

    private static List<Zrzut> zrzuty = new ArrayList<>();

    public static List<Zrzut> getZrzuty() {
        return zrzuty;
    }

    public static void add(Zrzut zrzut){
        zrzuty.add(zrzut);
    }
    public static void del(Zrzut zrzut){
        zrzuty.remove(zrzut);
    }
    public static Zrzut get(Location location){

        for(Zrzut z : zrzuty){

            if(z.getLocation().getBlockX() == location.getBlockX() &&
                    z.getLocation().getBlockY() == location.getBlockY() &&
                    z.getLocation().getBlockZ() == location.getBlockZ()){
                return z;
            }

        }
        return null;

    }

    public static Zrzut isInTakingOver(Location location){
        for(Zrzut z : zrzuty){
            for(Location l : z.getTakingLocations()){
                if(Util.locationEquals(l,location)){
                    return z;
                }
            }
        }
        return null;
    }
}
