package pl.inder00.dghc.zrzut.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Util {

    private static Random r = new Random();

    public static Location randomLocation(World world, int size){
        int x = r.nextInt((size*2)+1)-size;
        int z = r.nextInt((size*2)+1)-size;
        int y = world.getHighestBlockYAt(x, z);
        if(world.getBlockAt(x, y, z).getBiome() == Biome.OCEAN){
            return randomLocation(world, size+10);
        }
        if(world.getBlockAt(x, y, z).getBiome() == Biome.DEEP_OCEAN){
            return randomLocation(world, size+10);
        }
        if(world.getBlockAt(x, y, z).getBiome() == Biome.RIVER){
            return randomLocation(world, size+10);
        }
        if(world.getBlockAt(x, y, z).getBiome() == Biome.BEACH){
            return randomLocation(world, size+10);
        }
        return new Location(world,x,y,z).add(0.5D,0D,0.5D);
    }

    public static Location randomLocationSquare(Player player){

        Location loc = player.getLocation();

        int x = (loc.getBlockX()+randomInt(-100,100));
        int z = (loc.getBlockZ()+randomInt(-100,100));
        int y = loc.getWorld().getHighestBlockYAt(x,z);

        return new Location(loc.getWorld(),x,y,z).add(0.5D,0D,0.5D);

    }

    public static int randomInt(int min, int max){
        return (int) (Math.random()*(max-min))+min;
    }

    public static List<Location> circle (Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
        List<Location> circleblocks = new ArrayList<Location>();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        for (int x = cx - r; x <= cx +r; x++)
            for (int z = cz - r; z <= cz +r; z++)
                for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r*r && !(hollow && dist < (r-1)*(r-1))) {
                        Location l = new Location(loc.getWorld(), x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }

        return circleblocks;
    }

    public static boolean locationEquals(Location loc1, Location loc2){
        return (loc1.getBlockX() == loc2.getBlockX() && loc1.getBlockY() == loc2.getBlockY() && loc1.getBlockZ() == loc2.getBlockZ());
    }

}
