package es.mythmc.MythBot.Managers;

import es.mythmc.MythBot.Utility.DiscordRoles;
import es.mythmc.MythBot.Utility.Methods;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class FilterUrl extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getMember() == null) return;
        if (Methods.hasRole(event.getMember(), DiscordRoles.CEO)
                || Methods.hasRole(event.getMember(), DiscordRoles.ADMIN)) return;
        if (Methods.isStaffChannel(event.getChannel())) return;
        //if (!Methods.hasChatFilter(event.getChannel())) return;

        if (event.getMessage().getContentRaw().startsWith("https://")
        || event.getMessage().getContentRaw().startsWith("www")
        || event.getMessage().getContentRaw().contains(".com")){
            event.getMessage().delete().queue();

            event.getChannel().sendMessage(Methods.embedBuilder(Color.RED, "No puedes enviar links a menos que tengas el permiso Youtuber").build())
                    .queue(message -> event.getMessage().delete()
                            .queueAfter(2, TimeUnit.SECONDS));
        }
    }
}
