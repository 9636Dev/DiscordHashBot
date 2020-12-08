import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.StatusChangeEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main extends ListenerAdapter {
    //SettingPanelFrame gui = new SettingPanelFrame("Hash Bot Setting Panel", 100, 100, 800, 800);
    public static ArrayList<TimeU> MuteList = new ArrayList<>();
    public static EmbedBuilder eb = new EmbedBuilder();
    public static ArrayList<NMSG> MuteListMsgList = new ArrayList<>();
    private static String[] StatusList, BWord, NWord, PosWord, RPos, RNeg, RAll;
    private Boolean DelUseMsg = true;
    private GiveAway GA;
    private static boolean Giveaway, Cancel, Quit, EMoteLoading;
    public static ArrayList<MSGU> MessagesReceived = new ArrayList<>();
    private boolean messageReceivedModule = true;
    public static String prefix = "!!";
    private String channelId = "none";
    public static TextChannel ch, nu,lvlChannel;
    private String EBFooter;
    public static JDABuilder builder = new JDABuilder();
    private final Color EBAlert = Color.GREEN;
    private final Color FailedEB = Color.RED;
    private long GiveAwayMessageId, APIMessageId;
    private ArrayList<User> GiveAwayReactionList = new ArrayList<>();
    private ArrayList<UserStat> UserStats = new ArrayList<>();
    private long GiveAwaySeconds;
    private static ArrayList<MessageEmbed.Field> ApiEB = new ArrayList<>();
    private int m, ApiN;
    private int h = 0;
    private boolean Purge = false;
    private Role GAPing,mutedRole;
    private Message msg;
    private UUID msgUUID;

    public static void main(String[] args) throws LoginException {
        StatusList = new String[]{" stuff", " ur server", " giveaways", " ur thoughts"};
        BWord = new String[]{"shit", "bad", "ban", "mute", "kill", "kick", "die", "suck", "dumb"};
        NWord = new String[]{"not", "un", "no"};
        RNeg = new String[]{"no", "i don't think so", "doubtfully", "i believe not"};
        RPos = new String[]{"yes", "i believe so", "i think so", "undoubted", "of course"};
        RAll = new String[]{"no", "i don't think so", "doubtfully", "i believe not", "yes", "i believe so", "i think so", "undoubted", "of course"};
        PosWord = new String[]{"be", "get", "i"};
        EMoteLoading = false;
        builder.setToken(SToken.getBotToken())
                .setRequestTimeoutRetry(true)
                .addEventListeners(new Main())
                .build()
                .getPresence().setActivity(Activity.listening("your messages"));
    }

    @Override
    public void onGuildUnban(@NotNull GuildUnbanEvent event) {
        super.onGuildUnban(event);
        eb.clear()
                .setTitle("UNBANNED! ")
                .setDescription(event.getUser().getAsTag() + " has been unbanned!")
                .setColor(EBAlert);
        ch.sendMessage(eb.build()).queue();
    }

    @Override
    public void onUserUpdateName(@NotNull UserUpdateNameEvent event) {
        super.onUserUpdateName(event);
        eb.clear()
                .setTitle("Name Change!")
                .setDescription(event.getOldName() + " Changed their name to " + event.getNewName())
                .setFooter(EBFooter)
                .setColor(EBAlert);
        nu.sendMessage(eb.build()).queue();
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) throws NullPointerException {
        super.onGuildJoin(event);
        try {
            eb.clear();
            eb.setTitle(event.getJDA().getSelfUser().getAsTag() + " has joined " + event.getGuild().getName())
                    .setDescription("My Default prefix is '!!'\nChange my prefix with !!setPrefix");
            Objects.requireNonNull(event.getGuild().getDefaultChannel()).sendMessage(eb.build()).queue();
        } catch (Exception e) {
            System.out.println("failed");
            e.printStackTrace();
        }
    }

    @Override
    public void onDisconnect(@NotNull DisconnectEvent event) {
        super.onDisconnect(event);
        System.out.println(event.getTimeDisconnected());
    }


    @Override
    public void onStatusChange(@NotNull StatusChangeEvent event) {
        super.onStatusChange(event);
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);
            mutedRole = event.getGuild().getRoleById(784439363819274240L);

        //Message Received Section
        //Mute section
        if (event.getMessage().getContentRaw().replaceAll(" ","").startsWith(prefix+"setup")) {
        }
        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();
                EmbedBuilder tEb = new EmbedBuilder();
                if (event.getMessage().getContentRaw().startsWith("!!pdf")&&event.getAuthor().getIdLong()==625736959850971146L) {
                    if (event.getMessage().getContentRaw().contains("addExp")) {
                        UserStats.get(containsUser(UserStats, event.getMessage().getMentionedUsers().get(0))).addExp(Integer.parseInt(event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf("Exp") + 4)));
                    }
                }
                if (!MuteList.isEmpty()) {

                    MuteList.removeIf(TimeU::checkIfTimeIsUp);
                    for (int i = 0; i < MuteList.size(); i++) {
                        if (event.getMessage().getAuthor().equals(MuteList.get(i).getU())) {
                            if (event.getGuild().getMemberById(MuteList.get(i).getU().getIdLong()).getRoles().contains(mutedRole)) event.getGuild().getMemberById(MuteList.get(i).getU().getIdLong()).getRoles().get(indexOfRole(event.getGuild().getMemberById(MuteList.get(i).getU().getIdLong()),mutedRole)).delete().queue();
                            MuteListMsgList.get(i).addMsgNum();
                            if (MuteListMsgList.get(i).getMsgNum() > 9) {
                                try {
                                    event.getGuild().kick(event.getMember(), "Spamming while being muted").queue();
                                } catch (Exception e) {
                                    event.getChannel().sendMessage("Error:" + e.getMessage()).queue();
                                    return;
                                }
                                tEb.clear()
                                        .setTitle("Kicked User: " + event.getMember().getUser().getAsTag())
                                        .setDescription("Reason: Spamming while being muted")
                                        .setFooter("Bot AutoKick")
                                        .setColor(Color.YELLOW);
                                event.getChannel().sendMessage(tEb.build()).queue();
                            }
                            tEb.clear()
                                    .setDescription("You are muted for " + MuteList.get(i).getReason() + "\nIf you send " + (10 - MuteListMsgList.get(i).getMsgNum()) + " more messages you will be kicked!!!");
                            MuteList.get(i).getU().openPrivateChannel()
                                    .flatMap(channel -> channel.sendMessage(tEb.build())).queue();
                            event.getMessage().delete().queue();

                            return;
                        }
                    }
                }
                if (event.getAuthor().isBot()) return;
                if (containsUser(UserStats,event.getAuthor())==-1) UserStats.add(new UserStat(1,0,event.getAuthor(),event.getAuthor().getIdLong()));
                else UserStats.get(containsUser(UserStats,event.getAuthor())).addMsgSent();
                if (UserStats.get(containsUser(UserStats,event.getAuthor())).addExp(10)) {
                    eb.clear()
                            .setAuthor(event.getAuthor().getAsTag()+" has leveled up to "+UserStats.get(containsUser(UserStats,event.getAuthor())).getLevel()+"("+UserStats.get(containsUser(UserStats,event.getAuthor())).getExp()+" exp)")
                            .setColor(Color.GREEN);
                    if (lvlChannel==null) {
                        eb.setDescription("Only Want Level messages In A Specific ChanneL? do !!lchannel in the channel you want the leveling messages");
                        event.getChannel().sendMessage(eb.build()).queue();
                    }
                    else {
                        eb.setDescription("Level messages Only In "+lvlChannel.getAsMention());
                        lvlChannel.sendMessage(eb.build()).queue();
                    }
                }
                for (User u:event.getMessage().getMentionedUsers()) {
                    if (containsUser(UserStats,u)!=-1) {
                        UserStats.get(containsUser(UserStats,u)).addMentionedByOthers();
                        UserStats.get(containsUser(UserStats,u)).addExp(30);
                    }
                    else UserStats.add(new UserStat(0,0,event.getAuthor(),event.getAuthor().getIdLong()));
                }
                System.out.println("User Level: "+UserStats.get(containsUser(UserStats,event.getAuthor())).getLevel()+" EXP: "+UserStats.get(containsUser(UserStats,event.getAuthor())).getExp());
                if (EBFooter != null) EBFooter = event.getJDA().getSelfUser().getAsMention();
                Message message = event.getMessage();
                boolean menUser = false;
                //channel stuff
                if (ch == null) ch = event.getGuild().getDefaultChannel();
                if (nu == null) nu = event.getChannel();
                //mention User for @ commands
                if (message.getMentionedUsers().contains(event.getJDA().getSelfUser())) menUser = true;
                //check if it is bot
                if (event.getAuthor().isBot()) return;
                if (Purge && CheckIfMemberHasPermission(event, Permission.MANAGE_CHANNEL)) {
                    Purge = false;
                    event.getChannel().sendMessage("Purging has stopped").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                }
                //Help menu with #ad

                if (menUser && message.getContentRaw().contains("help") || message.getContentRaw().toLowerCase().startsWith(prefix + " help") || message.getContentRaw().toLowerCase().startsWith(prefix + "help")) {
                    tEb.clear()
                            .setTitle(event.getJDA().getSelfUser().getAsTag())
                            .setDescription("My Prefix is: " + prefix + "\nBot Made By HCF_Pet\nCheck the list of commands using " + prefix + "list\nThis message auto deletes in 20 seconds")
                            .setAuthor("HCFPet", "https://www.youtube.com/channel/UCxz7yPJAm2RHaj5zQlrrwNw");
                    event.getChannel().sendMessage(tEb.build()).complete().delete().queueAfter(20, TimeUnit.SECONDS);
                }
                //Prints out message
                System.out.println(event.getChannel().getName() + ": " + event.getAuthor().getAsTag() + " sent " + event.getMessage().getContentRaw());

                //If messages received module is not on and it does not start wih prefix bot doesn't use
                if (!messageReceivedModule && !event.getMessage().getContentRaw().startsWith(prefix)) return;
                //adds message to message received module
                if (messageReceivedModule)
                    MessagesReceived.add(new MSGU(event.getChannel(), event.getAuthor(), message.getContentRaw()));
                //makes sure Channel works
                if ((event.getChannel().getId().equals(channelId) || channelId.equals("none"))) {
                    User user1;
                    if (event.getMessage().getTimeCreated().until(message.getTimeCreated(), ChronoUnit.MILLIS) > 300) {
                        tEb.clear()
                                .setTitle("Bot may to responding slow")
                                .setDescription("Current Ping: " + event.getMessage().getTimeCreated().until(message.getTimeCreated(), ChronoUnit.MILLIS))
                                .setColor(Color.RED);
                        event.getChannel().sendMessage(tEb.build()).complete().delete().queueAfter(10, TimeUnit.SECONDS);
                    }
                    if (message.getContentRaw().toLowerCase().startsWith(prefix + "setprefix") || message.getContentRaw().toLowerCase().startsWith(prefix + " setprefix")) {
                        if (!CheckIfMemberHasPermission(event, Permission.MANAGE_CHANNEL)) return;
                        prefix = event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf("setPrefix") + 10);
                        tEb.clear()
                                .setTitle("Prefix Changed!")
                                .setDescription("prefix Changed to " + prefix);
                        event.getChannel().sendMessage(tEb.build()).queue();
                    } else if (message.getContentRaw().replaceAll(" ","").startsWith(prefix+"stat")) {
                        User u;
                        if (message.getMentionedUsers().isEmpty()) u = event.getAuthor();
                        else u = message.getMentionedUsers().get(0);
                        if (u.isBot()) event.getChannel().sendMessage("Can't get stats of a bot").queue();
                        String[] userStatsString = userStatToString(u);
                        eb.clear()
                                .setTitle(u.getAsTag()+"\'s Stats")
                                .addField("Level Stats",userStatsString[1]+"\n"+userStatsString[2],false)
                                .addField("Message Stats",userStatsString[3]+"\n"+userStatsString[4]+"\n"+userStatsString[5],false)
                                .addField("Giveaway Stats",userStatsString[6]+":"+userStatsString[7]+"\n"+userStatsString[8],false)
                                .setColor(Color.YELLOW);
                        event.getChannel().sendMessage(eb.build()).queue();
                    }
                    else if (event.getMessage().getContentRaw().replaceAll(" ","").startsWith(prefix+"lchannel")) {
                        lvlChannel = event.getChannel();
                        eb.clear()
                                .setTitle("Leveling Channel Set!")
                                .setDescription("Set Leveling Channel to "+lvlChannel.getAsMention())
                                .setColor(Color.YELLOW);
                        event.getChannel().sendMessage(eb.build()).complete().delete().queueAfter(10,TimeUnit.SECONDS);
                    } else if (message.getContentRaw().toLowerCase().startsWith(prefix + " debug") || message.getContentRaw().toLowerCase().startsWith(prefix + "debug")) {
                        if (event.getMessage().getContentRaw().contains("debug setChannel")) {
                            channelId = event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf("setChannel") + 11);
                            tEb.clear()
                                    .setTitle("Channel Changed!")
                                    .setDescription("Channel Changed to " + channelId
                                            + "\nTo change back do " + prefix + " debug setChannel none");
                            event.getChannel().sendMessage(tEb.build()).queue();
                        }
                    } else if (event.getMessage().getContentRaw().startsWith(prefix + " showMessages") || message.getContentRaw().toLowerCase().startsWith(prefix + "showmessages")) {
                        if (!CheckIfMemberHasPermission(event, Permission.ADMINISTRATOR)) return;
                        StringBuilder msgSent = new StringBuilder();
                        for (MSGU s : MessagesReceived) {
                            System.out.println(s.getU().toString() + "\n" + s.getMsg());
                            msgSent.append(s.getChannel().getName()).append(": ").append(s.getU().getAsTag()).append(":   ").append(s.getMsg()).append("\n");
                        }
                        tEb.clear();
                        tEb.setTitle("ALL MESSAGES BOT RECEIVED")
                                .setDescription(msgSent.toString())
                                .setFooter(EBFooter)
                                .setColor(EBAlert);
                        event.getChannel().sendMessage(tEb.build()).complete().delete().queueAfter(1, TimeUnit.MINUTES);

                    } else if (message.getContentRaw().toLowerCase().startsWith(prefix + "ginfo") || message.getContentRaw().toLowerCase().startsWith(prefix + " ginfo")) {
                        tEb.clear()
                                .setTitle("Calculating")
                                .setColor(EBAlert);
                        long MSGID = event.getChannel().sendMessage(tEb.build()).complete().getIdLong();
                        UUID uuid;

                        try {
                            uuid = UUIDFetcher.getUUID(message.getContentRaw().substring(message.getContentRaw().toLowerCase().indexOf("info") + 5));
                        } catch (Exception e) {
                            e.printStackTrace();
                            uuid = UUID.fromString(message.getContentRaw().substring(message.getContentRaw().toLowerCase().indexOf("info") + 5));
                        }
                        try {
                            tEb.setTitle(Api.getName(uuid).replace("\"", "") + "'s guild stats on Hypixel (" + Api.PlayerCount() + " players)")
                                    .addField(Api.getField(1, uuid))
                                    .setColor(Color.RED)
                                    .setAuthor(EBFooter)
                                    .setFooter("Work In Progress");
//

                        } catch (Exception e) {
                            e.printStackTrace();
                            tEb.setTitle("FAILED");
                            if (Arrays.toString(e.getStackTrace()).toLowerCase().contains("not found")) {
                                tEb.setDescription("File not found: " + e.getMessage());
                            } else {
                                tEb.setDescription(e.getMessage());
                            }
                            tEb.setColor(Color.RED);
                            event.getChannel().editMessageById(MSGID, tEb.build()).queue();
                        }
                    } else if (event.getMessage().getContentRaw().startsWith(prefix) && event.getMessage().getContentRaw().contains("ping")) {
                        long ping = event.getMessage().getTimeCreated().until(message.getTimeCreated(), ChronoUnit.MILLIS);
                        event.getJDA().getPresence().setActivity(Activity.listening(StatusList[(int) (Math.random() * StatusList.length)]));
                        tEb.clear()
                                .setTitle("Latency / Ping");
                        if (ping > 0)
                            tEb.setDescription("The bots ping: " + ping + " ms | Websocket: " + event.getJDA().getGatewayPing() + "ms");
                        else tEb.setDescription("Websocket: " + event.getJDA().getGatewayPing() + "ms");
                        tEb.setAuthor(EBFooter)
                                .setColor(EBAlert);

                        event.getChannel().sendMessage(tEb.build()).queue();

                    } else if (message.getContentRaw().toLowerCase().startsWith(prefix + " ban") || message.getContentRaw().toLowerCase().startsWith(prefix + "ban")) {
                        if (!CheckIfMemberHasPermission(event, Permission.MANAGE_ROLES)) return;
                        try {
                            try {
                                user1 = event.getMessage().getMentionedUsers().get(event.getMessage().getMentionedUsers().size() - 1);
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("parse mentioned user1 failed");
                                user1 = event.getJDA().getUserById(event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf(" U") + 2, event.getMessage().getContentRaw().indexOf(" T")));
                            }

                            int days = Integer.parseInt(event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf(" T") + 2, event.getMessage().getContentRaw().indexOf(" R")));
                            String reason = event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf(" R") + 2);
                            assert user1 != null;
                            if (user1 == event.getJDA().getSelfUser()) {
                                event.getChannel().sendMessage("I cannot ban myself lol").queue();
                                return;
                            }
                            if (HigherRole(event, event.getGuild().getMemberById(user1.getIdLong()))) {
                                event.getGuild().ban(user1, days, reason).queue();
                                tEb.clear()
                                        .setTitle("Banned User!")
                                        .setDescription(event.getAuthor().getAsTag() + " has banned " + user1.getAsTag())
                                        .setAuthor(event.getJDA().getSelfUser().getAsTag())
                                        .setColor(FailedEB);
                                event.getChannel().sendMessage(tEb.build()).queue();
                            } else {
                                eb.clear()
                                        .setAuthor("No Permissions")
                                        .setDescription(event.getMember().getAsMention() + " cannot kick " + user1.getAsMention() + "\n because they have equal to higher roles")
                                        .setColor(Color.RED);
                                event.getChannel().sendMessage(eb.build()).queue();
                                return;
                            }
                        } catch (Exception e) {
                            tEb.clear()
                                    .setTitle("Failed")
                                    .setDescription("Use ban by doing U[user1] T[days (lower than 7)] R[reason]")
                                    .setFooter(event.getJDA().getSelfUser().getAsTag())
                                    .setColor(EBAlert);
                            event.getChannel().sendMessage(tEb.build()).queue();
                            e.printStackTrace();
                        }
                    } else if (message.getContentRaw().toLowerCase().startsWith(prefix + "cleanup") || message.getContentRaw().toLowerCase().startsWith(prefix + " cleanup")) {
                        if (!CheckIfMemberHasPermission(event, Permission.MANAGE_CHANNEL)) return;
                        try {
                            int amount = Integer.parseInt(event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf(" cleanup") + 11));
                            List<Message> Message = event.getChannel().getHistory().retrievePast(amount).complete();
                            event.getChannel().deleteMessages(Message).complete();


                            tEb.clear()
                                    .setTitle("DELETED MESSAGE!")
                                    .setDescription(event.getAuthor().getAsTag() + " has deleted " + amount + " messages\nAuto deletes this message in 10 minutes")
                                    .setAuthor(event.getJDA().getSelfUser().getAsTag())
                                    .setColor(FailedEB);
                            event.getChannel().sendMessage(tEb.build()).complete().delete().queueAfter(10, TimeUnit.MINUTES);
                        } catch (Exception e) {
                            e.printStackTrace();
                            tEb.clear()
                                    .setTitle("Failed")
                                    .setDescription("Use delete command by doing " + prefix + " cleanup [amount]")
                                    .setFooter(event.getJDA().getSelfUser().getAsTag())
                                    .setColor(EBAlert);
                            event.getChannel().sendMessage(tEb.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
                        }
                    } else if (message.getContentRaw().replaceAll(" ","").toLowerCase().startsWith(prefix+"google")) GoogleYTCommands.Google(event,prefix);
                    else if (message.getContentRaw().replaceAll(" ","").toLowerCase().startsWith(prefix+"youtube")) GoogleYTCommands.Youtube(event,prefix);
                    else if (event.getMessage().getContentRaw().startsWith(prefix) && event.getMessage().getContentRaw().contains("deletestoredmessages")) {
                        if (!CheckIfMemberHasPermission(event, Permission.VIEW_AUDIT_LOGS)) return;
                        try {

                            tEb.clear()
                                    .setTitle("Cleared Messages")
                                    .setDescription("Cleared " + MessagesReceived.size() + " messages")
                                    .setFooter(EBFooter)
                                    .setColor(EBAlert);
                            MessagesReceived.clear();
                            event.getChannel().sendMessage(tEb.build()).queue();
                        } catch (Exception e) {
                            tEb.clear()
                                    .setTitle("Failed")
                                    .setFooter(EBFooter)
                                    .setColor(FailedEB);
                            event.getChannel().sendMessage(tEb.build()).queue();
                            e.printStackTrace();
                        }
                    } else if (message.getContentRaw().toLowerCase().startsWith(prefix + "settings") || message.getContentRaw().toLowerCase().startsWith(prefix + " settings")) {
                        if (!CheckIfMemberHasPermission(event, Permission.ADMINISTRATOR)) return;
                        if (event.getMessage().getContentRaw().contains("settings list")) {
                            tEb.clear()
                                    .setTitle("Settings List")
                                    .setDescription("RecordChatMessages - true / false \nCurrently: " + messageReceivedModule + "\nDeleteUserCommands - true / false\nCurrently: " + DelUseMsg)
                                    .setFooter(EBFooter)
                                    .setColor(EBAlert);
                            event.getChannel().sendMessage(tEb.build()).complete().delete().queueAfter(20, TimeUnit.SECONDS);
                        } else if (event.getMessage().getContentRaw().contains("settings RecordChatMessages")) {
                            if (event.getMessage().getContentRaw().toLowerCase().contains("true")) {
                                EmbedBuilder eb1 = new EmbedBuilder()
                                        .setTitle("Message Received Module Enabled!")
                                        .setDescription("The Bot Now Records All Chat Commands.\nWatch out!");
                                event.getChannel().sendMessage(eb1.build()).complete().delete().queueAfter(10, TimeUnit.SECONDS);
                                messageReceivedModule = true;
                            } else if (event.getMessage().getContentRaw().toLowerCase().contains("false")) {
                                EmbedBuilder eb2 = new EmbedBuilder()
                                        .setTitle("Message Received Module Disabled!")
                                        .setDescription("The Bot Does Not Records Chat Commands.\nWatch out!");
                                event.getChannel().sendMessage(eb2.build()).complete().delete().queueAfter(10, TimeUnit.SECONDS);
                                messageReceivedModule = false;
                            }

                        } else if (message.getContentRaw().contains("settings DeleteUserCommands")) {
                            tEb.clear()
                                    .setColor(Color.RED);
                            if (message.getContentRaw().contains("true")) {
                                tEb.setTitle("Bot now deletes user commands");
                                event.getChannel().sendMessage(tEb.build()).complete().delete().queueAfter(10, TimeUnit.SECONDS);
                                DelUseMsg = true;
                            } else if (message.getContentRaw().contains("false")) {
                                tEb.setTitle("Bot now stops deleting user Commands");
                                event.getChannel().sendMessage(tEb.build()).complete().delete().queueAfter(10, TimeUnit.SECONDS);
                                DelUseMsg = false;
                            }
                        }
                    } else if (event.getMessage().getContentRaw().startsWith(prefix + "mute") || event.getMessage().getContentRaw().contains(prefix + " mute")) {
                        if (!CheckIfMemberHasPermission(event, Permission.MANAGE_CHANNEL)) return;
                        tEb.clear();
                        try {
                            try {
                                user1 = event.getMessage().getMentionedUsers().get(event.getMessage().getMentionedUsers().size() - 1);
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("parse mentioned user failed");
                                user1 = event.getJDA().getUserById(event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf(" U") + 2, event.getMessage().getContentRaw().indexOf(" T")));
                            }

                            int min = Integer.parseInt(event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf(" T") + 2, event.getMessage().getContentRaw().indexOf(" R")));
                            String reason = event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf(" R") + 2);
                            assert user1 != null;
                            Member m = event.getGuild().getMemberById(user1.getIdLong());
                            if (!HigherRole(event,m)) {
                                eb.clear()
                                        .setAuthor("No Permissions")
                                        .setDescription(event.getMember().getAsMention()+" cannot mute "+m.getAsMention()+"\n because they have equal to higher roles")
                                        .setColor(Color.RED);
                                event.getChannel().sendMessage(eb.build()).queue();
                                return;
                            }
                            if (user1 == event.getJDA().getSelfUser()) {
                                event.getChannel().sendMessage("I can't mute myself lol").complete().delete().queueAfter(10, TimeUnit.SECONDS);
                                return;
                            }
                                giveMemberRole(event.getGuild().getMemberById(user1.getIdLong()),event,mutedRole);
                                MuteList.add(new TimeU(user1, min, reason));
                                MuteListMsgList.add(new NMSG(user1));

                            tEb.setTitle("Muted User!")
                                    .setDescription(event.getAuthor().getAsTag() + " has Muted " + user1.getAsTag() + "\n for " + min + " mins")
                                    .setAuthor(event.getJDA().getSelfUser().getAsTag())
                                    .setColor(FailedEB);

                        } catch (Exception e) {
                            e.printStackTrace();
                            tEb.setTitle("Failed")
                                    .setDescription("Use Mute by doing U[user1] T[minutes] R[reason]")
                                    .setFooter(event.getJDA().getSelfUser().getAsTag())
                                    .setColor(EBAlert);
                        }
                        event.getChannel().sendMessage(tEb.build()).queue();

                    } else if (message.getContentRaw().toLowerCase().startsWith(prefix + "8ball") || message.getContentRaw().toLowerCase().startsWith(prefix + " 8ball")) {
                        boolean cA = (message.getContentRaw().contains("9636") || message.getContentRaw().contains("henny"));
                        boolean neg = contains(NWord, message);
                        boolean bW = contains(BWord, message);
                        boolean pos = contains(PosWord, message);
                        boolean NegR = cA && bW && !neg && pos;
                        boolean PosR = cA && bW && neg && pos;
                        if (NegR) {
                            event.getChannel().sendMessage(RNeg[(int) (Math.random() * RNeg.length)]).queue();
                            event.getChannel().sendMessage("Don't try to insult the creator lol\nU cant if u use correct english").queue();
                        } else if (PosR)
                            event.getChannel().sendMessage(RPos[(int) (Math.random() * RPos.length)]).queue();
                        else event.getChannel().sendMessage(RAll[(int) (Math.random() * RAll.length)]).queue();

                    } else if (message.getContentRaw().toLowerCase().startsWith(prefix + "what is life") || message.getContentRaw().toLowerCase().startsWith(prefix + " what is life")) {
                        String k = "k";
                        for (int i = (int) (Math.random() * 100); i > 0; i--) k = k + "k";
                        tEb.clear()
                                .setAuthor("Quack" + k)
                                .setFooter("requested command by " + event.getJDA().getUserById("498631075359358986").getAsTag())
                                .setDescription("Auto-deleting in 10 Seconds");
                        event.getChannel().sendMessage(tEb.build()).complete().delete().queueAfter(10, TimeUnit.SECONDS);
                    } else if (message.getContentRaw().toLowerCase().startsWith(prefix + "g") || message.getContentRaw().toLowerCase().startsWith(prefix + " g")) {
                        if (!CheckIfMemberHasPermission(event, Permission.MANAGE_CHANNEL)) return;
                        if (message.getContentRaw().contains("g start")) {
                            if (GAPing == null) {
                                if (!event.getGuild().getRolesByName("Giveaway Ping", true).isEmpty()) {
                                    GAPing = event.getGuild().getRolesByName("Giveaway Ping", true).get(0);
                                    event.getChannel().sendMessage("You already have a role named Giveaway Role, so we used that instead").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                                } else {
                                    GAPing = event.getGuild().createRole()
                                            .setName("Giveaway Ping")
                                            .setColor(Color.CYAN).complete();
                                    event.getChannel().sendMessage("Added Role Giveaway Ping\"" + GAPing.getAsMention() + "\"").complete().delete().queueAfter(10, TimeUnit.SECONDS);
                                }
                            }

                            GiveAwayReactionList.clear();
                            Cancel = false;
                            if (Giveaway) return;
                            Giveaway = true;
                            GA = Poll(event);
                            m = (int) (GA.getGiveawayTimeMS() / 60000);
                            while (m >= 60) {
                                h++;
                                m = m - 60;
                            }
                            event.getChannel().sendMessage(GAPing.getAsMention()).queue();
                            tEb.clear()
                                    .setTitle("\uD83C\uDF89\uD83C\uDF89Giveaway Time!!\uD83C\uDF89\uD83C\uDF89");

                            int dd = 0;
                            if (h > 24) {
                                dd = h / 24;
                            }
                            if (dd > 7)
                                tEb.setDescription("Giveaway for " + dd / 7 + " weeks " + dd % 7 + " days " + h % 24 + " hours " + m + " minutes\nFor " + GA.getMessage());
                            else if (h > 24)
                                tEb.setDescription("Giveaway for " + h / 24 + " days " + h % 24 + " hours " + m + " minutes\nFor " + GA.getMessage());
                            else if (h > 0) tEb.setDescription("Giveaway for " + h + " hours " + m + " minutes");
                            else tEb.setDescription("Giveaway for " + m + " minutes\nFor " + GA.getMessage());
                            tEb.setColor(Color.BLUE)
                                    .setFooter("started by " + event.getAuthor().getAsTag());

                            GiveAwayMessageId = event.getChannel().sendMessage(tEb.build()).complete().getIdLong();
                            event.getChannel().retrieveMessageById(GiveAwayMessageId).complete().addReaction("U+1F389").queue();
                            GiveAwaySeconds = GA.getGiveawayTimeMS() * 60;
                            Timer t = new Timer();
                            t.scheduleAtFixedRate(new TimerTask() {

                                int s, d;

                                @Override
                                public void run() {
                                    if ((h == 0 && m == 0 && s <= 10) || Cancel) {
                                        PickGiveAwayWinner(event, eb, GA);
                                        cancel();
                                    }
                                    if (Quit) {
                                        tEb.setTitle("Quited Giveaway");
                                        event.getChannel().retrieveMessageById(GiveAwayMessageId).complete().editMessage(tEb.build()).complete().delete().queueAfter(10, TimeUnit.SECONDS);
                                        Giveaway = false;
                                        cancel();
                                    }
                                    while (h > 24) {
                                        d++;
                                        h = h - 24;
                                    }
                                    if (m < 1 && h > 0) {
                                        h--;
                                        m = 60;
                                    }
                                    if (GiveAwaySeconds > 0) {

                                        s = s - 10;
                                        if (s < 10) {
                                            m--;
                                            s = 50;
                                        }
                                        GiveAwaySeconds = GiveAwaySeconds - 10;
                                        if (d > 7)
                                            tEb.setDescription("Giveaway for " + d / 7 + " weeks " + d % 7 + " days " + h % 24 + " hours " + m + " minutes and " + s + " seconds\nFor " + GA.getMessage());
                                        else if (d > 0)
                                            tEb.setDescription("Giveaway for " + d + " days " + h % 24 + " hours " + m + " minutes and " + s + " seconds\nFor " + GA.getMessage());
                                        else if (h == 0)
                                            tEb.setDescription("Giveaway for " + m + " minutes and " + s + " seconds\nFor " + GA.getMessage());
                                        else
                                            tEb.setDescription("Giveaway for " + h + " hours " + m + " minutes and " + s + " seconds\nFor " + GA.getMessage());
                                        event.getChannel().retrieveMessageById(GiveAwayMessageId).complete().editMessage(tEb.build()).queue();
                                    }
                                }
                            }, 10000, 10000);
                            return;
                        } else if (message.getContentRaw().contains("g reroll")) {
                            RerollGiveAwayWinner(event, new EmbedBuilder(), GA);
                        } else if (message.getContentRaw().contains("g stop")) {
                            if (Giveaway) Cancel = true;
                            else
                                event.getChannel().sendMessage("You can start a giveaway using " + prefix + " start").complete().delete().queueAfter(10, TimeUnit.SECONDS);
                        } else if (message.getContentRaw().contains("g quit")) {
                            if (Giveaway) Quit = true;
                            else
                                event.getChannel().sendMessage("You can start a giveaway using " + prefix + " start").complete().delete().queueAfter(10, TimeUnit.SECONDS);
                        } else {

                            tEb.clear()
                                    .setTitle("List of " + prefix + "g commands")
                                    .setDescription("" + prefix + " start [number]m/h [thing] - starts giveaway\n" +
                                            "" + prefix + " stop - stops the giveaway and roles the winner\n" +
                                            "" + prefix + " quit - Quits Giveaway and no winner\n" +
                                            "" + prefix + " reroll - rerolls winner\nAutodeletes in 20 seconds");
                            event.getChannel().sendMessage(tEb.build()).complete().delete().queueAfter(20, TimeUnit.SECONDS);
                        }
                    } else if (message.getContentRaw().toLowerCase().startsWith(prefix + "list") || message.getContentRaw().toLowerCase().startsWith(prefix + " list")) {
                        tEb.clear()
                                .setTitle("List of commands")
                                .setDescription("Commands Normal People Can Use: \n" +
                                        " help\n list\n what is life\n" +
                                        "What People with special perms can use: \n" +
                                        "mute\nban\nshowMessages\nsettings\ng\ndeleteStoredMessages")
                                .setColor(Color.BLUE)
                                .setFooter("autodeleting in 1 minute");
                        event.getChannel().sendMessage(tEb.build()).complete().delete().queueAfter(1, TimeUnit.MINUTES);
                    } else if (message.getContentRaw().toLowerCase().startsWith(prefix + " purge") || message.getContentRaw().toLowerCase().startsWith(prefix + "purge")) {
                        if (!CheckIfMemberHasPermission(event, Permission.VIEW_AUDIT_LOGS)) return;
                        event.getChannel().sendMessage("Purging Channel").queue();
                        int MsgSize = 0;

                        try {
                            while (true) {
                                MsgSize = MsgSize + event.getChannel().getHistoryFromBeginning(100).complete().size();
                                List<Message> Message = event.getChannel().getHistory().retrievePast(100).complete();
                                event.getChannel().deleteMessages(Message).complete();
                            }
                        } catch (Exception e) {
                            if (e.getMessage().contains("2 weeks")) {
                                Purge = true;
                                event.getChannel().sendMessage("The messages are too old to purge\nBot will slowpurge the channel. This will take some time. \nYou can cancel this by typing any !! command as an CHANNEL MANAGER").complete().delete().completeAfter(5, TimeUnit.SECONDS);
                                while (Purge) {
                                    if (event.getChannel().getHistory().retrievePast(1).complete().isEmpty()) break;
                                    event.getChannel().getHistory().retrievePast(1).complete().get(0).delete().queue();
                                }
                            }
                        }
                        tEb.clear()
                                .setTitle("Purged Channel")
                                .setDescription("Deleted " + MsgSize + " messages")
                                .setColor(Color.CYAN)
                                .setFooter(EBFooter);
                        event.getChannel().sendMessage(tEb.build()).queue();


                    } else if (message.getContentRaw().toLowerCase().startsWith(prefix + "userping") || message.getContentRaw().toLowerCase().startsWith(prefix + " userping"))
                        event.getChannel().sendMessage(message.getMentionedMembers().get(0).getAsMention()).queue();
                    else if (message.getContentRaw().toLowerCase().startsWith(prefix + "hinfo") || message.getContentRaw().toLowerCase().startsWith(prefix + " hinfo")) {
                        tEb.clear()
                                .setTitle("Calculating")
                                .setColor(EBAlert);
                        long MSGID = event.getChannel().sendMessage(tEb.build()).complete().getIdLong();
                        UUID uuid;

                        try {
                            uuid = UUIDFetcher.getUUID(message.getContentRaw().substring(message.getContentRaw().toLowerCase().indexOf("info") + 5));
                        } catch (Exception e) {
                            e.printStackTrace();
                            uuid = UUID.fromString(message.getContentRaw().substring(message.getContentRaw().toLowerCase().indexOf("info") + 5));
                        }
                        if (ApiEB.isEmpty()) ApiN = 0;
                        try {
                            tEb.setTitle(Api.getName(uuid).replace("\"", "") + "'s stats on Hypixel (" + Api.PlayerCount() + " players)")
                                    .addField(Api.getField(0, uuid))
                                    .setColor(Color.RED)
                                    .setAuthor(EBFooter)
                                    .setFooter("Work In Progress");
//                            msg = event.getChannel().editMessageById(MSGID, tEb.build()).complete();
//                            msg.addReaction("U+2B05").queue();
//                            msg.addReaction("U+27A1").queueAfter(1,TimeUnit.MILLISECONDS);
//                            msgUUID = uuid;
//                            APIMessageId = msg.getIdLong();

                        } catch (Exception e) {
                            e.printStackTrace();
                            tEb.setTitle("FAILED");
                            if (Arrays.toString(e.getStackTrace()).toLowerCase().contains("not found")) {
                                tEb.setDescription("File not found: " + e.getMessage());
                            } else {
                                tEb.setDescription(e.getMessage());
                            }
                            tEb.setColor(Color.RED);
                            event.getChannel().editMessageById(MSGID, tEb.build()).queue();
                        }
                    }
                } else if (message.getContentRaw().toLowerCase().startsWith(prefix + "ping") || message.getContentRaw().toLowerCase().startsWith(prefix + " ping"))
                    event.getChannel().sendMessage(message.getMentionedMembers().get(0).getAsMention()).queue();
                else if (message.getContentRaw().toLowerCase().startsWith(prefix + "hinfo") || message.getContentRaw().toLowerCase().startsWith(prefix + " hinfo")) {
                    tEb.clear()
                            .setTitle("Calculating")
                            .setColor(EBAlert);
                    long MSGID = event.getChannel().sendMessage(tEb.build()).complete().getIdLong();
                    UUID uuid;

                    try {
                        uuid = UUIDFetcher.getUUID(message.getContentRaw().substring(message.getContentRaw().toLowerCase().indexOf("info") + 5));
                    } catch (Exception e) {
                        e.printStackTrace();
                        uuid = UUID.fromString(message.getContentRaw().substring(message.getContentRaw().toLowerCase().indexOf("info") + 5));
                    }
                    if (ApiEB.isEmpty()) {
                        ApiN = 0;
                    }
                    try {
                        tEb.setTitle(Api.getName(uuid).replace("\"", "") + "'s stats on Hypixel (" + Api.PlayerCount() + " players)")
                                .addField(Api.getField(0, uuid))
                                .setColor(Color.RED)
                                .setAuthor(EBFooter)
                                .setFooter("Work In Progress");
//

                    } catch (Exception e) {
                        e.printStackTrace();
                        tEb.setTitle("FAILED");
                        if (Arrays.toString(e.getStackTrace()).toLowerCase().contains("not found")) {
                            tEb.setDescription("File not found: " + e.getMessage());
                        } else {
                            tEb.setDescription(e.getMessage());
                        }
                        tEb.setColor(Color.RED);
                        event.getChannel().editMessageById(MSGID, tEb.build()).queue();
                    }
                } else if (message.getContentRaw().toLowerCase().startsWith(prefix + "bwinfo") || message.getContentRaw().toLowerCase().startsWith(prefix + " bwinfo")) {
                    tEb.clear()
                            .setTitle("Calculating")
                            .setColor(EBAlert);
                    long MSGID = event.getChannel().sendMessage(tEb.build()).complete().getIdLong();
                    UUID uuid;

                    try {
                        uuid = UUIDFetcher.getUUID(message.getContentRaw().substring(message.getContentRaw().toLowerCase().indexOf("info") + 5));
                    } catch (Exception e) {
                        e.printStackTrace();
                        uuid = UUID.fromString(message.getContentRaw().substring(message.getContentRaw().toLowerCase().indexOf("info") + 5));
                    }
                    try {
                        tEb.setTitle(Api.getName(uuid).replace("\"", "") + "'s stats on Hypixel (" + Api.PlayerCount() + " players)")
                                .addField(Api.getField(2, uuid))
                                .setColor(Color.RED)
                                .setAuthor(EBFooter)
                                .setFooter("Work In Progress");
//

                    } catch (Exception e) {
                        e.printStackTrace();
                        tEb.setTitle("FAILED");
                        if (Arrays.toString(e.getStackTrace()).toLowerCase().contains("not found")) {
                            tEb.setDescription("File not found: " + e.getMessage());
                        } else {
                            tEb.setDescription(e.getMessage());
                        }
                        tEb.setColor(Color.RED);
                        event.getChannel().editMessageById(MSGID, tEb.build()).queue();
                    }
                } else if (message.getContentRaw().toLowerCase().startsWith(prefix + "kick") || message.getContentRaw().toLowerCase().startsWith(prefix + " kick")) {
                    CheckIfMemberHasPermission(event, Permission.ADMINISTRATOR);
                    Member member = event.getMessage().getMentionedMembers().get(0);
                    String reason = event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf(">") + 1);
                    if (HigherRole(event, member)) {
                        event.getChannel().sendMessage(tEb.clear().setTitle("KICKED").setDescription("kicked " + member.getUser().getAsTag() + " for " + reason).setColor(Color.RED).build()).queue();
                        event.getGuild().kick(member, reason).queue();
                    } else {
                        eb.clear()
                                .setAuthor("No Permissions")
                                .setDescription(event.getMember().getAsMention() + " cannot kick " + member.getAsMention() + "\n because they have equal to higher roles")
                                .setColor(Color.RED);
                        event.getChannel().sendMessage(eb.build()).queue();
                        return;
                    }
                }
                //Stuff
                if ((DelUseMsg == Boolean.TRUE && message.getContentRaw().toLowerCase().startsWith(prefix) && !message.getContentRaw().contains("8ball")))
                    event.getMessage().delete().queueAfter(5, TimeUnit.SECONDS);
                this.stop();

            }
        };
        t.start();
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
//        if (EMoteLoading) {
//            event.getChannel().sendMessage("Wait For Last Reaction to finish Loading").complete().delete().queueAfter(2,TimeUnit.SECONDS);
//            event.getReaction().removeReaction(event.getUser()).queue();
//            return;
//        }
        super.onGuildMessageReactionAdd(event);
        event.getChannel().retrieveMessageById(event.getMessageId()).queue(message -> msg = message);
        if (event.getUser().isBot()) return;
//        if (event.getMessageIdLong()==APIMessageId) {
//            ApiN = Api.getNumOfField(msg.getEmbeds().get(0).getFields().get(0));
//            EMoteLoading = true;
//            EmbedBuilder eb1 = new EmbedBuilder()
//                    .setTitle("LOADING...")
//                    .setColor(Color.GREEN);
//            eb.clear()
//                    .setTitle(msg.getEmbeds().get(0).getTitle())
//                    .setFooter(msg.getEmbeds().get(0).getFooter().getText())
//                    .setColor(msg.getEmbeds().get(0).getColor());
//            if (event.getReactionEmote().getEmoji().equals("⬅")) {
//                System.out.println("left Button pressed");
//                if (ApiN==0) {
//                    ApiN=3;
//                }
//                else ApiN--;
//
//            }
//            else if (event.getReactionEmote().getEmoji().equals("➡")) {
//                System.out.println("right button pressed");
//                if (ApiN==3) {
//                    ApiN=0;
//                }
//                else ApiN++;
//            }
//            else {
//                event.getReaction().removeReaction().queue();
//                return;
//            }
//            eb1.setDescription("loading page "+(ApiN+1));
//            msg.editMessage(eb1.build()).queue();
//            eb.addField(Api.getField(ApiN,msgUUID));
//            msg.editMessage(eb.build()).queue();
//            event.getReaction().removeReaction(event.getUser()).queue();
//            EMoteLoading = false;
//        }
        if (event.getMessageIdLong() == GiveAwayMessageId) {
            GiveAwayReactionList.add(event.getUser());
            eb.clear()
                    .setDescription("to leave, just un react the massage")
                    .setFooter("You Joined The Giveaway");
            event.getUser().openPrivateChannel()
                    .flatMap(channel -> channel.sendMessage(eb.build())).queue();
        }

    }


    @Override
    public void onGuildMessageReactionRemove(@NotNull GuildMessageReactionRemoveEvent event) {
        super.onGuildMessageReactionRemove(event);
        if (event.getUser().isBot()) return;
        if (event.getMessageIdLong() == (GiveAwayMessageId)) {
            GiveAwayReactionList.remove(event.getUser());
            eb.clear()
                    .setDescription("You left the giveaway");
            event.getUser().openPrivateChannel()
                    .flatMap(channel -> channel.sendMessage(eb.build())).queue();
        }
    }

    /**
     * Sets the bot prefix to
     *
     * @param prefix The prefix
     */
    public static void setPrefix(String prefix) {
        Main.prefix = prefix;
        eb.clear()
                .setTitle("Changed Prefix")
                .setDescription("Prefix Changed to " + prefix);
        ch.sendMessage(eb.build()).queue();

    }

    /**
     * @param event The GuildMessageReceivedEvent
     * @return returns the GiveAway item
     */
    public static GiveAway Poll(GuildMessageReceivedEvent event) {
        int d = 0;
        int h = 0;
        int m = 0;
        String thing = "";
        long millisecs = 0;
        try {
            try {
                d = Integer.parseInt(event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf(" ", prefix.length() + 6) + 1, event.getMessage().getContentRaw().indexOf("d")));
                System.out.println(d + "daus");
            } catch (IndexOutOfBoundsException e) {
                h = Integer.parseInt(event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf(" ", prefix.length() + 6) + 1, event.getMessage().getContentRaw().indexOf("h")));
                System.out.println(h + "hours");
            }
        } catch (IndexOutOfBoundsException e) {
            m = Integer.parseInt(event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf(" ", prefix.length() + 6) + 1, event.getMessage().getContentRaw().indexOf("m")));
            System.out.println(m + "min");

        }
        try {
            if (d != 0) {
                millisecs = (long) h * 24 * 0x36ee80;
                thing = event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf("d") + 2);
            } else if (h != 0) {
                millisecs = h * 0x36ee80L;
                thing = event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf("h") + 2);
            } else if (m != 0) {
                millisecs = m * 0xea60L;
                thing = event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf("m") + 2);
            }
            System.out.println("giveaway with " + millisecs + "\nAbout " + thing);
            return new GiveAway(millisecs, thing);
        } catch (Exception e) {
            event.getChannel().sendMessage("Error, the biggest amount of hours is \"2562047788015\"").queue();
            return null;
        }
    }

    private void PickGiveAwayWinner(GuildMessageReceivedEvent event, EmbedBuilder eb, GiveAway GA) {
        eb.setDescription("Giveaway Ended For " + GA.getMessage());
        int r = (int) (Math.random() * GiveAwayReactionList.size());
        eb.setTitle("Giveaway winner is: " + GiveAwayReactionList.get(r).getAsTag());
        event.getChannel().retrieveMessageById(GiveAwayMessageId).complete().editMessage(eb.build()).queue();
        event.getChannel().sendMessage(GiveAwayReactionList.get(r).getAsMention() + " you have won the " + GA.getMessage() + " giveaway!!!").queue();
        EmbedBuilder eb2 = new EmbedBuilder()
                .setTitle("You Won " + GA.getMessage() + " in " + event.getGuild().getName())
                .setDescription("go to the " + event.getChannel().getName() + " channel!!!")
                .setThumbnail("https://www.nicepng.com/png/full/34-341879_win-top-image-600-300-transparent-png-you.png");
        GiveAwayReactionList.get(r).openPrivateChannel().complete().sendMessage(eb2.build()).queue();
        if (containsUser(UserStats,GiveAwayReactionList.get(r))==-1) UserStats.add(new UserStat(1,0,event.getAuthor(),event.getAuthor().getIdLong()));
        UserStats.get(containsUser(UserStats,event.getAuthor())).addGiveawaysWon();
        addToAllPeopleJoined();
        Giveaway = false;

    }

    private void RerollGiveAwayWinner(GuildMessageReceivedEvent event, EmbedBuilder eb, GiveAway GA) {
        eb.setDescription("Rerolling for " + GA.getMessage());
        int r = (int) (Math.random() * GiveAwayReactionList.size());
        eb.setTitle("Rerolled giveaway winner is: " + GiveAwayReactionList.get(r).getAsTag());
        event.getChannel().retrieveMessageById(GiveAwayMessageId).complete().editMessage(eb.build()).queue();
        event.getChannel().sendMessage(GiveAwayReactionList.get(r).getAsMention() + " you have won the " + GA.getMessage() + " giveaway!!!").queue();
        EmbedBuilder eb2 = new EmbedBuilder()
                .setTitle("You Won " + GA.getMessage() + " in " + event.getGuild().getName())
                .setDescription("go to the " + event.getChannel().getName() + " channel!!!")
                .setThumbnail("https://www.nicepng.com/png/full/34-341879_win-top-image-600-300-transparent-png-you.png");
        GiveAwayReactionList.get(r).openPrivateChannel().complete().sendMessage(eb2.build()).queue();
        if (containsUser(UserStats,GiveAwayReactionList.get(r))==-1) UserStats.add(new UserStat(1,0,event.getAuthor(),event.getAuthor().getIdLong()));
        UserStats.get(containsUser(UserStats,event.getAuthor())).addGiveawaysWon();
        addToAllPeopleJoined();
        Giveaway = false;

    }

    private boolean CheckIfMemberHasPermission(GuildMessageReceivedEvent event, Permission perm) {
        if (!Objects.requireNonNull(event.getMember()).hasPermission(perm)) {
            eb.clear()
                    .setTitle("You don't have permission")
                    .setDescription("You need the " + perm.toString().toLowerCase() + " \npermission to run this command")
                    .setFooter(EBFooter)
                    .setColor(EBAlert);
            event.getChannel().sendMessage(eb.build()).complete().delete().queueAfter(10, TimeUnit.SECONDS);
            System.out.println(event.getAuthor().getAsTag() + ": no perms");
            return false;

        }
        return true;
    }

    /**
     * @param str
     * @param message
     * @return
     */
    private boolean contains(String[] str, Message message) {
        for (String s : str) {
            if (message.getContentRaw().toLowerCase().trim().contains(s)) return true;
        }
        return false;
    }

    private int containsUser(ArrayList<UserStat> userStats, User user) {
        for (int i = 0; i < UserStats.size(); i++) {
            if (UserStats.get(i).getUser().equals(user)) return i;
        }
        return -1;
    }
    private void addToAllPeopleJoined() {
        for (User g:GiveAwayReactionList) {
            if (containsUser(UserStats,g)==-1) UserStats.add(new UserStat(1,0,g,g.getIdLong()));
            UserStats.get(containsUser(UserStats,g)).addGiveawaysJoined();
        }
    }

    /**
     * Gives a role to the param member
     * @param member the member who will get the role
     * @param event The GuildMessageReceivedEvent for the method
     * @param role The role to give (not cAsE sEnSiTiVe)
     */
    private void giveMemberRole(Member member,GuildMessageReceivedEvent event,Role role) {
        event.getGuild().addRoleToMember(member,role).queue();
    }
    private int indexOfRole(Member member,Role role) {
        for (int i = 0;i<member.getRoles().size();i++) {
            if (member.getRoles().get(i)==role) return i;
        }
        return -1;
    }
    private boolean HigherRole(GuildMessageReceivedEvent event,Member m) {
        Role HighestRoleMember = event.getGuild().getPublicRole();
        Role HighestRoleM = event.getGuild().getPublicRole();
        for (Role r : event.getMember().getRoles()) if (r.getPositionRaw()>HighestRoleMember.getPositionRaw()) HighestRoleMember = r;
        for (Role r : m.getRoles()) if (r.getPositionRaw()>HighestRoleM.getPositionRaw()) HighestRoleM = r;
        System.out.println(HighestRoleMember.getPositionRaw()+" : "+HighestRoleM.getPositionRaw());
        return HighestRoleMember.getPositionRaw() > HighestRoleM.getPositionRaw();
    }

    /**
     * get Stats of users
     * @param user user to get stats
     * @return [name,exp,msgsent,mentioned,givejoined,givewon,givewinpercent]
     */
    private String[] userStatToString(User user) {

        for (UserStat us:UserStats) {
            if (us.getUser().equals(user)) {
                double Percent = ((double)us.getGiveawaysWon() / us.getGiveawaysJoined());
                return new String[]{"Name: " + us.getUser().getAsTag()," (exp:" + us.getExp() + ")\n",
                        "Accurate Level: " + us.getAccurateLevel(),"\nExp to next lvl: NaN" ,
                        "Messages Sent: " + us.getMsgSent() , "Mentioned Times: " + us.getMentionedByOthers(),
                        "Giveaways Joined"+us.getGiveawaysJoined(),"Giveaways Won: "+us.getGiveawaysWon(),"Percentage:"+(int)Percent*100,
                };
            }
        }
        return null;
    }

}
