package pl.inder00.dghc.zrzut;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.inder00.dghc.zrzut.basic.Enchantments;
import pl.inder00.dghc.zrzut.basic.Zrzut;
import pl.inder00.dghc.zrzut.basic.utils.ZrzutUtils;
import pl.inder00.dghc.zrzut.commands.ZrzutCommand;
import pl.inder00.dghc.zrzut.listeners.PlayerInteractListener;
import pl.inder00.dghc.zrzut.listeners.PlayerMoveListener;
import pl.inder00.dghc.zrzut.storage.Config;
import pl.inder00.dghc.zrzut.tasks.IntervalTask;
import pl.inder00.dghc.zrzut.tasks.ParticleTask;

import java.io.File;

public final class Core extends JavaPlugin {

    private static Core instance;

    private static File mainDir;
    private static File configFile;

    @Override
    public void onEnable() {

        //HOLOGRAMIC
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }

        //INSTANCE
        instance = this;

        //FILES
        mainDir = this.getDataFolder();
        configFile = new File(mainDir, "config.yml");
        if(!mainDir.exists()) mainDir.mkdir();
        if(!configFile.exists()) this.saveDefaultConfig();

        //CONFIG
        new Enchantments(this);
        Config cfg = new Config();
        cfg.load();

        //COMMANDS
        new ZrzutCommand(this);

        //LISTENERS
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(),this);

        //TASKS
        new IntervalTask().runTaskTimer(this,cfg.interval*20,cfg.interval*20);
        new ParticleTask().runTaskTimer(this,0,5);

    }

    @Override
    public void onDisable() {
        for(Zrzut z : ZrzutUtils.getZrzuty()){
            z.delete(false);
        }

    }

    public static Core getInstance() {
        return instance;
    }
}
