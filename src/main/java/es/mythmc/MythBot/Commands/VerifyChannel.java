package es.mythmc.MythBot.Commands;

import es.mythmc.MythBot.Utility.DiscordRoles;
import es.mythmc.MythBot.Utility.Methods;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Objects;

public class VerifyChannel extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        Permission permission = Permission.ADMINISTRATOR;
        if (event.getMember() == null) return;

        if (event.getMember().hasPermission(permission)
                && event.getMessage().getContentRaw().equalsIgnoreCase("-verifypanel")) {
            event.getMessage().delete().queue();


            event.getChannel().sendMessage(Methods.embedWithImage(Color.decode("#E4B400"),
                    "Sistema de Verificacion", "https://cdn.discordapp.com/attachments/876214065708556298/908923100702343209/MythVerify2.png",
                    "En esta comunidad nos protegemos contra personas con malas intenciones ",
                    "asi que integramos un sistema de verificacion para poder protegernos de ellas ",
                    "verificate para poder acceder a nuestro discord\n\n",
                    "**Haz click en el boton de abajo para verificarte**").build()
            ).setActionRow(Button.primary("verify", "Verificate")
                    .withEmoji(Emoji.fromMarkdown("<:listo:907725221887356948>"))).queue();
        }
    }

    @Override
    public void onButtonClick(ButtonClickEvent event) {
        if (event.getComponentId().equals("verify")) {

            assert DiscordRoles.MIEMBRO != null;
            assert DiscordRoles.NO_VERIFICADO != null;

            Objects.requireNonNull(event.getGuild()).removeRoleFromMember(Objects.requireNonNull(event.getMember()).getId(), DiscordRoles.NO_VERIFICADO).queue();
            event.getGuild().addRoleToMember(event.getMember().getId(), DiscordRoles.MIEMBRO).queue();
        }
    }
}
