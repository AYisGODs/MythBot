package es.mythmc.MythBot.Utility;

import es.mythmc.MythBot.MythBot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;

public class DiscordRoles {

    private static final Guild guild = MythBot.getPlugin().getDiscordServer();

    public static final Role CEO = guild.getRoleById(862508826656636970L);
    public static final Role MANAGER = guild.getRoleById(868704380768092190L);
    public static final Role ADMIN = guild.getRoleById(862563465436463124L);
    public static final Role MODERATOR = guild.getRoleById(862563233944305664L);
    public static final Role SOPORTE = guild.getRoleById(862565190841925632L);
    public static final Role CONSTRUCTOR = guild.getRoleById(862561668088070174L);
    public static final Role YOUTUBER = guild.getRoleById(875941329937518662L);
    public static final Role MINIYT = guild.getRoleById(875941095748567080L);
    public static final Role EMPERADOR = guild.getRoleById(891095675259998239L);
    public static final Role REY = guild.getRoleById(880208064194691147L);
    public static final Role DUQUE = guild.getRoleById(875812584736034867L);
    public static final Role CONDE = guild.getRoleById(875812672879362108L);
    public static final Role MIEMBRO = guild.getRoleById(862561113664389130L);
    public static final Role NO_VERIFICADO = guild.getRoleById(870036579278811147L);
    public static final Role MUTEADO = guild.getRoleById(862919219535544341L);
    public static final Role LINKED = guild.getRoleById(891097398066167829L);
    public static final Role STAFF = guild.getRoleById(870041581825179689L);
    public static final Role BOT_STAFF_CHANNEL = guild.getRoleById(870041581825179689L);
}
