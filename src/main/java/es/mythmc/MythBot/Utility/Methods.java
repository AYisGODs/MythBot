package es.mythmc.MythBot.Utility;

import es.mythmc.MythBot.MythBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Methods {

    private static final Guild guild = MythBot.getPlugin().getDiscordServer();

    public static EmbedBuilder embedBuilder(Color color, String title, String... appendDesc) {

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(color);
        builder.setTitle(title);
        for (String s : appendDesc){
            builder.appendDescription(s);
        }

        return builder;
    }

    public static EmbedBuilder embedWithThumb(Color color, String title, String url, String... appendDesc) {

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(color);
        builder.setTitle(title);
        builder.setThumbnail(url);
        for (String s : appendDesc){
            builder.appendDescription(s);
        }

        return builder;
    }
    public static EmbedBuilder embedWithImage(Color color, String title, String url, String... appendDesc) {

        EmbedBuilder builder = new EmbedBuilder();
        builder.setImage(url);
        builder.setColor(color);
        builder.setTitle(title);
        for (String s : appendDesc){
            builder.appendDescription(s);
        }

        return builder;
    }

    public static boolean hasRole(Member member, Role discordRole) {
        for (int i=0; i<member.getRoles().size(); i++){
            if (discordRole == member.getRoles().get(i)) {
                return true;
            }
        }
        return false;
    }


    public static boolean isStaff(Member member){
        return hasRole(member, DiscordRoles.CEO) ||  hasRole(member, DiscordRoles.MANAGER) ||
                hasRole(member, DiscordRoles.MODERATOR) ||
                hasRole(member, DiscordRoles.ADMIN) || hasRole(member, DiscordRoles.MODERATOR) ||
                hasRole(member, DiscordRoles.SOPORTE) || hasRole(member, DiscordRoles.STAFF);
    }

    public static boolean isStaffChannel(TextChannel channel) {
        try {
            assert DiscordRoles.BOT_STAFF_CHANNEL != null;
            return Objects.requireNonNull(channel.getPermissionOverride(DiscordRoles.BOT_STAFF_CHANNEL)).isRoleOverride();
        } catch (NullPointerException exception) {
            return false;
        }
    }

    /*public static boolean hasChatLogging(TextChannel channel) {
        try {
            assert DiscordRole.BOT_NO_CHAT_LOGGING != null;

            return !channel.getPermissionOverride(DiscordRole.BOT_NO_CHAT_LOGGING).isRoleOverride();
        } catch (NullPointerException exception) {
            return true;
        }
    }

    public static boolean hasChatFilter(TextChannel channel) {
        try {
            assert DiscordRole.BOT_NO_CHAT_FILTER != null;

            return !channel.getPermissionOverride(DiscordRole.BOT_NO_CHAT_FILTER).isRoleOverride();
        } catch (NullPointerException exception) {
            return true;
        }
    }

    public static boolean isPermanentChannel(GuildChannel channel) {
        try {
            assert DiscordRole.BOT_PERMANENT_CHANNEL != null;

            return channel.getPermissionOverride(DiscordRole.BOT_PERMANENT_CHANNEL).isRoleOverride();
        } catch (NullPointerException exception) {
            return false;
        }
    }*/

    public static User getUserFromID(String userId) {
        return MythBot.getPlugin().getJda().retrieveUserById(userId).complete();
    }

    public static User getUserFromID(long userId) {
        return MythBot.getPlugin().getJda().retrieveUserById(userId).complete();
    }

    public static Member getUserFromMember(User user) {
        return MythBot.getPlugin().getDiscordServer().getMember(user);
    }


    public static TextChannel getDiscordLogsChannel() {
        final ArrayList<TextChannel> list = new ArrayList<TextChannel>(guild.getTextChannelsByName("discord", true));
        TextChannel value = null;

        for (TextChannel channel : list) {
            if (Objects.requireNonNull(channel.getParent()).getName().equalsIgnoreCase("logs")) {
                value = channel;
                break;
            }
        }

        return value;
    }

    public static EmbedBuilder noPerms(){

        return embedBuilder(Color.RED, "Permisos Insuficientes", "No tienes permisos suficientes");
    }
}
