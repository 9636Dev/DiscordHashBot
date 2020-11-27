import jdk.jfr.Event;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.hooks.IEventManager;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.managers.DirectAudioController;
import net.dv8tion.jda.api.managers.Presence;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.GuildAction;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheView;
import net.dv8tion.jda.api.utils.cache.SnowflakeCacheView;
import okhttp3.OkHttpClient;
import org.apache.commons.collections4.functors.FalsePredicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.channels.Channel;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;


public class SettingPanelPanel extends JPanel implements ActionListener{
    public static Boolean DelUseMsg = false;
    private JTextField Prefix,Message,LatestMessage;
    private JButton Save,SendMessage,StartT,StopT,DelUserMsg;
    private javax.swing.Timer t;

    private int TR = 0;


    public SettingPanelPanel() {

        t = new javax.swing.Timer(1000,this);
        setBackground(Color.GRAY);
        JLabel PrefixJLabel = new JLabel("Prefix: ");
        Prefix = new JTextField(Main.prefix,3);
        JLabel DelUserMsgLabel = new JLabel("Delete user message: ");
        DelUserMsg = new JButton("OFF");

        JLabel MessageJLabel = new JLabel("Message: ");
        Message = new JTextField("",20);
        Save = new JButton("Save Settings");
        SendMessage = new JButton("Send Message");
        JLabel LatestMessageLabel = new JLabel("Latest Message: ");
        LatestMessage = new JTextField("",30);
        StartT = new JButton("Start Timer");
        StopT = new JButton("Stop Timer");

        Save.addActionListener(this);
        SendMessage.addActionListener(this);
        StartT.addActionListener(this);
        StopT.addActionListener(this);
        DelUserMsg.addActionListener(this);

        add(PrefixJLabel);
        add(Prefix);
        add(DelUserMsgLabel);
        add(DelUserMsg);
        add(MessageJLabel);
        add(Message);
        add(LatestMessageLabel);
        add(LatestMessage);
        add(Save);
        add(SendMessage);
        add(StartT);
        add(StopT);



    }


    public void actionPerformed (ActionEvent e) {
       if (e.getSource()==Save) {
           if (!Prefix.getText().equals(Main.prefix)) Main.setPrefix(Prefix.getText());
       }
       if (e.getSource()==DelUserMsg) {
           if (DelUseMsg) {
               DelUseMsg = Boolean.FALSE;
               DelUserMsg.setText("OFF");
           }
           else {
               DelUseMsg = Boolean.TRUE;
               DelUserMsg.setText("ON");
           }
       }
       if (e.getSource()==SendMessage) {
           EmbedBuilder eb = new EmbedBuilder()
                   .setDescription(Message.getText())
                   .setAuthor("9636#3774's Message");
           Main.ch.sendMessage(eb.build()).queue();
           JOptionPane.showMessageDialog(this, "Message Sent");
       }

       if (e.getSource()==StartT) {
           t.start();
           JOptionPane.showMessageDialog(this, "Timer Started");
       }
        if (e.getSource()==StopT) {
            t.stop();

            JOptionPane.showMessageDialog(this, "Timer Stopped\nRan "+TR+" times");
        }
    }


}
