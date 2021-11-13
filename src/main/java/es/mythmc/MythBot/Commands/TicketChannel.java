package es.mythmc.MythBot.Commands;

import es.mythmc.MythBot.Utility.DiscordRoles;
import es.mythmc.MythBot.Utility.Methods;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TicketChannel extends ListenerAdapter {

    private List<Member> memberList = new ArrayList<>();

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        Permission permission = Permission.ADMINISTRATOR;

        if (event.getMember() == null) return;

        if (event.getMember().hasPermission(permission)
                && event.getMessage().getContentRaw().equalsIgnoreCase("-ticketpanel")) {

            if (!event.getChannel().getId().equals("867471680544702485")) return;

            event.getMessage().delete().queue();


            event.getChannel().sendMessage(Methods.embedWithImage(Color.decode("#E4B400"), "Soporte",
                    "https://cdn.discordapp.com/attachments/876214065708556298/908928568787079218/MythVeSrify.png",
                    "¿Tienes problemas en el servidor? o tienes preguntas sin responder\n",
                    "abre un ticket para que un miembro del staff te resuelva tu problema y todas tus dudas\n",
                    "\n\n",
                    "**Haz click para abrir un ticket**").build()
            ).setActionRow(Button.primary("support", "Abrir Ticket")
                    .withEmoji(Emoji.fromMarkdown("<:question:908928906466304020>"))).queue();
        }

        if (event.getMessage().getContentRaw().equalsIgnoreCase("-cerrar")) {

            if (event.getChannel().getName().startsWith("soporte-")) {

                event.getMessage().delete().queue();

                if (!Methods.isStaff(event.getMember())) {

                    event.getChannel().sendMessage(Methods.noPerms().build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                    return;
                }

                for (PermissionOverride override : event.getChannel().getMemberPermissionOverrides()) {
                    if (override.isMemberOverride()) {
                        event.getChannel().getManager()
                                .removePermissionOverride(override.getPermissionHolder()).queue();

                        override.delete().queue();
                    }
                }

                event.getChannel().sendMessage(Methods.embedBuilder(
                        Color.RED, "**Ticket Cerrado**",
                        ":red_circle: **Estado** Cerrado\n",
                        ":robot: **Cerrado por** <@" + event.getAuthor().getId() + ">\n\n",
                                "Este ticket fue cerrado, El autor del ticket ya no tiene permisos"
                ).build()).queue();

                event.getChannel().getManager().setName(
                        "tcerrado-" + event.getChannel().getName().replace("soporte-", "")
                ).queueAfter(1, TimeUnit.SECONDS);

                event.getChannel().getManager().setTopic("Ticket Cerrado.").queue();
            }

            if (event.getChannel().getName().startsWith("tcerrado-")) {

                event.getMessage().delete().queue();

                if (!Methods.isStaff(event.getMember())) {
                    event.getChannel().sendMessage(Methods.noPerms().build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                    return;
                }

                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.RED);
                embed.setAuthor("Este ticket ya esta cerrado!");
                event.getChannel().sendMessage(embed.build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
            }
        }

        if (event.getMessage().getContentRaw().equalsIgnoreCase("-borrar")) {
            if (event.getChannel().getName().startsWith("support-") || event.getChannel().getName().startsWith("tcerrado-")) {

                event.getMessage().delete().queue();

                if (!Methods.isStaff(event.getMember())) {
                    event.getChannel().sendMessage(Methods.noPerms().build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                    return;
                }

                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.GREEN);
                embed.setAuthor("Borrando ticket...");
                event.getChannel().sendMessage(embed.build())
                        .queue(message -> event.getChannel().delete()
                                .queueAfter(2, TimeUnit.SECONDS));
            }

            for (Member m : memberList){
                if (memberList.contains(m)){
                    memberList.remove(m);
                }
            }
        }
    }

    @Override
    public void onButtonClick(ButtonClickEvent event) {

        if (event.getComponentId().equals("support")) {

            Guild guild = event.getGuild();

            if (memberList.contains(event.getMember())){
                event.getChannel().sendMessage(Methods.embedBuilder(Color.RED, "Ya tienes un ticket",
                        "No puedes tener mas de 1 ticket").build()).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
                return;
            }
            assert DiscordRoles.SOPORTE != null;
            assert DiscordRoles.MODERATOR != null;
            assert DiscordRoles.STAFF != null;
            assert DiscordRoles.ADMIN != null;
            assert DiscordRoles.CEO != null;


            assert guild != null;
            TextChannel channel = guild.createTextChannel("soporte-" + event.getUser().getName() + "#" + event.getUser().getDiscriminator())
                    .setNSFW(false)
                    .setTopic("Ticket de Soporte! Estado: Abierto")
                    .setParent(guild.getCategoryById(862550815851675690L))
                    .addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL))
                    .addPermissionOverride(Objects.requireNonNull(event.getMember()), EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_ADD_REACTION, Permission.MESSAGE_ATTACH_FILES), null)
                    .addPermissionOverride(DiscordRoles.SOPORTE, EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_ADD_REACTION, Permission.MESSAGE_ATTACH_FILES), null)
                    .addPermissionOverride(DiscordRoles.MODERATOR, EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_ADD_REACTION, Permission.MESSAGE_ATTACH_FILES), null)
                    .addPermissionOverride(DiscordRoles.STAFF, EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_ADD_REACTION, Permission.MESSAGE_ATTACH_FILES), null)
                    .addPermissionOverride(DiscordRoles.ADMIN, EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_ADD_REACTION, Permission.MESSAGE_ATTACH_FILES), null)
                    .addPermissionOverride(DiscordRoles.CEO, EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_ADD_REACTION, Permission.MESSAGE_ATTACH_FILES), null)

                    .complete();
            channel.sendMessage("<@" + event.getUser().getId() + ">").queue();

            memberList.add(event.getMember());

        }
    }
}
