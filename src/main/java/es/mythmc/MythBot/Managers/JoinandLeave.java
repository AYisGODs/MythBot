package es.mythmc.MythBot.Managers;

import es.mythmc.MythBot.Utility.DiscordRoles;
import es.mythmc.MythBot.Utility.Methods;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Objects;

public class JoinandLeave extends ListenerAdapter {


    @Override
    @Deprecated
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        assert DiscordRoles.MIEMBRO != null;
        assert DiscordRoles.NO_VERIFICADO != null;

        event.getGuild().addRoleToMember(event.getUser().getId(), DiscordRoles.NO_VERIFICADO).queue();

        Objects.requireNonNull(event.getGuild().getTextChannelById(908921684503953499L))
                .sendMessage("<@" + event.getUser().getId() + ">").queue();


        Objects.requireNonNull(event.getGuild().getTextChannelById(908921684503953499L)).sendMessage(
                Methods.embedWithThumb(Color.decode("#E4B400"), ":wave: Bienvenido a MythMC",
                        event.getUser().getAvatarUrl(),
                        "Acabas de entrar al discord ",
                        "oficial de la comunidad para el servidor\n" + "MythMC de Minecraft " +
                                "organizamos eventos y sorteos. Disfruta\n\n",
                        ":book: **Recuerda leer las reglas en** <#" + 867273245254483968L + ">\n",
                        ":white_check_mark: **Verificate en el canal** <#" + 867273924400381982L + ">").build()).queue();
    }

    @Override @Deprecated
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
        Objects.requireNonNull(event.getGuild().getTextChannelById(862918630064914432L)).sendMessage(
                Methods.embedBuilder(Color.decode("#E4B400"), ":reminder_ribbon: Salida del usuario",
                        event.getUser().getName() + "#" + event.getUser().getDiscriminator()
                                + "\n").build()
        ).queue();
    }


}
