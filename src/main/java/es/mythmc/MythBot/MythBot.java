package es.mythmc.MythBot;

import es.mythmc.MythBot.Commands.MicsCommands;
import es.mythmc.MythBot.Commands.TicketChannel;
import es.mythmc.MythBot.Commands.VerifyChannel;
import es.mythmc.MythBot.Managers.FilterUrl;
import es.mythmc.MythBot.Managers.JoinandLeave;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;

public class MythBot extends JavaPlugin {

    private static MythBot PLUGIN;
    private JDA jda;

    @Override
    public void onEnable() {

        PLUGIN = this;

        try {
            String TOKEN = "ODY4Mjg2NzY2MDk5MDc5MTc4.YPtdEQ.XvWq3DadgvZOc-Xw5sfNoO7jHYk";
            this.jda = JDABuilder.createDefault(TOKEN)
                    .setActivity(Activity.playing("mc.mythmc.es"))
                    .setMemberCachePolicy(MemberCachePolicy.ALL)

                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .enableIntents(GatewayIntent.GUILD_BANS)
                    .enableIntents(GatewayIntent.GUILD_EMOJIS)
                    .enableIntents(GatewayIntent.GUILD_WEBHOOKS)
                    .enableIntents(GatewayIntent.GUILD_INVITES)
                    .enableIntents(GatewayIntent.GUILD_VOICE_STATES)
                    .enableIntents(GatewayIntent.GUILD_PRESENCES)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES)
                    .enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS)
                    .enableIntents(GatewayIntent.GUILD_MESSAGE_TYPING)
                    .enableIntents(GatewayIntent.DIRECT_MESSAGES)
                    .enableIntents(GatewayIntent.DIRECT_MESSAGE_REACTIONS)
                    .enableIntents(GatewayIntent.DIRECT_MESSAGE_TYPING)

                    .enableCache(CacheFlag.MEMBER_OVERRIDES)
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        try {
            File filterwords = new File(getDataFolder(), "filterwords.yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(filterwords);
            config.options().copyDefaults(true);
            config.save(filterwords);

        } catch (IOException e) {
            e.printStackTrace();
        }
        registerEvents();
    }

    @Override
    public void onDisable() {
        PLUGIN = null;
        jda.shutdown();
    }

    private void registerEvents(){
        jda.addEventListener(new JoinandLeave());
        jda.addEventListener(new VerifyChannel());
        jda.addEventListener(new TicketChannel());
        jda.addEventListener(new FilterUrl());
        jda.addEventListener(new MicsCommands());
    }

    public static MythBot getPlugin() {
        return PLUGIN;
    }

    public Guild getDiscordServer(){
        return jda.getGuildById(862505868191727627L);
    }

    public JDA getJda() {
        return jda;
    }

}
