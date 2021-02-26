package pl.inder00.dghc.zrzut.storage;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.inder00.dghc.zrzut.Core;
import pl.inder00.dghc.zrzut.basic.Enchantments;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Config {

    //=========================================================================
    private static Config inst;
    public FileConfiguration cfg = Core.getInstance().getConfig();

    public Random random;

    public List<String> hologramic;
    public String takingOver;
    public String broadcast;

    public String zrzut$nothing;
    public String zrzut$style;
    public List<String> zrzut$prefix;

    public int map$size;
    public World world;

    public int min$drops;
    public int max$drops;
    public List<ItemStack> drops = new ArrayList<>();

    public int min$players;
    public int max$zrzuty;

    public int interval;

    public ItemStack flara;

    public String[] got$player = new String[2];
    public String[] got$everyone = new String[2];

    public String used$flara;

    public Config() {
        if(inst == null) inst = this;
    }

    public void reload(){
        Core.getInstance().reloadConfig();
        this.cfg = Core.getInstance().getConfig();
        this.zrzut$prefix = null;
        this.hologramic = null;
        this.drops = new ArrayList<>();
        this.got$player = new String[2];
        this.got$everyone = new String[2];
        this.load();
    }

    public void load(){

        this.random = new Random();

        this.hologramic = fixColor(cfg.getStringList("hologramic"));
        this.broadcast = fixColor(cfg.getString("broadcast"));
        this.takingOver = fixColor(cfg.getString("takingOver"));

        this.zrzut$nothing = fixColor(cfg.getString("zrzut-command.nothing"));
        this.zrzut$style = fixColor(cfg.getString("zrzut-command.style"));

        this.map$size = cfg.getInt("world.map-size");
        this.world = Bukkit.getWorld(cfg.getString("world.world"));

        this.min$drops = cfg.getInt("drop.amount.min");
        this.max$drops = cfg.getInt("drop.amount.max");

        this.min$players = cfg.getInt("zrzuty.min-players");
        this.max$zrzuty = cfg.getInt("zrzuty.max-drops");
        this.interval = cfg.getInt("zrzuty.interval");

        this.zrzut$prefix = fixColor(cfg.getStringList("zrzut-command.behind-zrzut"));

        this.got$player[0] = fixColor(cfg.getString("got-player.title"));
        this.got$player[1] = fixColor(cfg.getString("got-player.subtitle"));

        this.got$everyone[0] = fixColor(cfg.getString("got-everyone.title"));
        this.got$everyone[1] = fixColor(cfg.getString("got-everyone.subtitle"));

        this.used$flara = fixColor(cfg.getString("used-flara"));

        try {

            flara = new ItemStack(Material.getMaterial(cfg.getString("flara.id").toUpperCase()),1,(short) cfg.getInt("flara.data"));
            ItemMeta meta = flara.getItemMeta();
            if(cfg.getString("flara.name") != null){
                meta.setDisplayName(fixColor(cfg.getString("flara.name")));
            }
            if(cfg.getStringList("flara.lore") != null){
                meta.setLore(fixColor(cfg.getStringList("flara.lore")));
            }
            flara.setItemMeta(meta);

        } catch (Throwable e){
            Core.getInstance().getLogger().severe("Nie udalo sie stworzyc flary!");
            e.printStackTrace();
        }

        for(String key : cfg.getConfigurationSection("drop.list").getKeys(false)){

            try {

                ItemStack item = new ItemStack(Material.getMaterial(cfg.getString("drop.list."+key+".id").toUpperCase()), cfg.getInt("drop.list."+key+".amount"), (short) cfg.getInt("drop.list."+key+".data"));
                ItemMeta meta = item.getItemMeta();

                if(cfg.getString("drop.list."+key+".name") != null){
                    meta.setDisplayName(fixColor(cfg.getString("drop.list."+key+".name")));
                }
                if(cfg.getStringList("drop.list."+key+".enchantments") != null){
                    for(String ench : cfg.getStringList("drop.list."+key+".enchantments")){
                        String[] enchant = ench.split(":");
                        meta.addEnchant(Enchantments.getEnchantment(enchant[0]),Integer.parseInt(enchant[1]),true);
                    }
                }

                item.setItemMeta(meta);
                drops.add(item);
                Core.getInstance().getLogger().info("Wladowano przedmiot x"+item.getAmount()+" "+item.getType().name());

            } catch(Throwable e){
                Core.getInstance().getLogger().severe("Nie udalo sie dodac przedmiotu z kluczem: "+key);
                e.printStackTrace();
            }

        }

    }

    public ItemStack randomItem(){
        return drops.get(this.random.nextInt(drops.size()));
    }

    private String fixColor(String input){
        return ChatColor.translateAlternateColorCodes('&',input
                .replace("{>}", "»")
                .replace("{.}", "•")
        );
    }
    private List<String> fixColor(List<String> input){
        List<String> out = Lists.newArrayList();
        for(String s : input){
            out.add(fixColor(s));
        }
        return out;
    }

    //Instance
    public static Config getInstance(){
        return inst;
    }

}