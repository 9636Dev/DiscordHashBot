import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.net.URL;

public class GoogleYTCommands {
    public static void Google(GuildMessageReceivedEvent event,String Prefix) {
         if (event.getMessage().getContentRaw().toLowerCase().startsWith(Prefix+" google")) {
            String spaceReplacedWithPlusSigns = "";
            for (int i = Prefix.length()+7; i < event.getMessage().getContentRaw().length(); i++) {
                System.out.print(event.getMessage().getContentRaw().charAt(i));
                if (event.getMessage().getContentRaw().charAt(i) == ' ') {
                    spaceReplacedWithPlusSigns = spaceReplacedWithPlusSigns + "+";
                   
                } else {
                    spaceReplacedWithPlusSigns = spaceReplacedWithPlusSigns + event.getMessage().getContentRaw().charAt(i);
                }

            }
            System.out.println(spaceReplacedWithPlusSigns);
            System.out.print("Sending");
             EmbedBuilder eb = new EmbedBuilder()
                     .setTitle("Your Search Results")
                     .setDescription("https://www.google.com/search?source=hp&ei=nYx9X74D2YaMuw_egafYCQ&q="
                             + spaceReplacedWithPlusSigns + "&oq=" + spaceReplacedWithPlusSigns +
                             "&gs_lcp=CgZwc3ktYWIQAzILCC4QsQMQyQMQkwIyBQgAELEDMgUIABCxAzICCAAyAggAMgIIADICCAAyAggAM" +
                             "gIIADIICC4QxwEQrwE6CAgAELEDEIMBOgsILhCxAxDHARCjAjoLCC4QsQMQxwEQrwE6AgguOgQIABADOhEILhCxA" +
                             "xDHARCjAhDJAxCTAjoFCC4QsQM6DgguELEDEIMBEMcBEKMCOggILhCxAxCDAUoFCAkSATFKBQgKEgE1UNkIWLEMYKcOa" +
                             "ABwAHgBgAG2AogBogmSAQcwLjIuMi4xmAEAoAEBqgEHZ3dzLXdpeg&sclient=psy-ab&ved=0ahUKEwj-xdrWl6LsAhVZA2M" +
                             "BHd7ACZsQ4dUDCAc&uact=5&safe=active&ssui=on");
             event.getChannel().sendMessage(eb.build()).queue();
        }

    }
    public static void Youtube(GuildMessageReceivedEvent event,String Prefix) {
            String spaceReplacedWithPlusSigns = event.getMessage().getContentRaw().substring(Prefix.length()+8).replaceAll(" ","+");
            System.out.println(spaceReplacedWithPlusSigns);
            System.out.print("Sending");

        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("Your Search Results")
                .setThumbnail("https://www.youtube.com/results?search_query=" + spaceReplacedWithPlusSigns)
                .setDescription("https://www.youtube.com/results?search_query=" + spaceReplacedWithPlusSigns);
            event.getChannel().sendMessage(eb.build()).queue();
        }
}
