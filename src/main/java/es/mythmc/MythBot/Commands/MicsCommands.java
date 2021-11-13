package es.mythmc.MythBot.Commands;

import es.mythmc.MythBot.Utility.Methods;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MicsCommands extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase("-clean")) {

            if (args.length > 1) {

                if (Integer.parseInt(args[1]) > 100 || Integer.parseInt(args[1]) < 1) {
                    event.getChannel().sendMessage(Methods.embedBuilder(Color.RED,
                            "El numero es muy grande", "El maximo es 100").build()).queue(m ->
                            m.delete().queueAfter(2, TimeUnit.SECONDS));
                    return;
                }

                if (Integer.parseInt(args[1]) < 100 && Integer.parseInt(args[1]) > 1) {
                    int values = Integer.parseInt(args[1]);
                    event.getMessage().delete().queue();
                    List<Message> messages = event.getChannel().getHistory().retrievePast(values).complete();

                    try {
                        event.getTextChannel().deleteMessages(messages).queue();
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }
                    event.getChannel().sendMessage(Methods.embedBuilder(Color.RED,
                            "Borrando mensajes", "espera un momento").build()).queue(m ->
                            m.delete().queueAfter(2, TimeUnit.SECONDS));

                }
            }else{
                event.getChannel().sendMessage(Methods.embedBuilder(Color.RED,
                        "Debes especificar un numero", "Usa: -clean (num)").build()).queue(m ->
                        m.delete().queueAfter(2, TimeUnit.SECONDS));
            }
        }

    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        if (event.getGuild() == null)
            return;
        switch (event.getName()) {
            case "say":
                say(event, event.getOption("content").getAsString()); // content is required so no null-check here
                break;
            default:
                event.reply("I can't handle that command right now :(").setEphemeral(true).queue();
        }
    }

    public void say(SlashCommandEvent event, String content) {
        event.reply(content).queue(); // This requires no permissions!
    }
}
