import com.google.common.base.Charsets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.hypixel.api.HypixelAPI;
import net.hypixel.api.reply.FriendsReply;
import net.hypixel.api.util.ILeveling;
import netscape.javascript.JSObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import zone.nora.slothpixel.Slothpixel;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
public class HypixelAPIBot {
    public static HypixelAPI api = new HypixelAPI(UUID.fromString("c01096ed-ac1e-4ee8-84a0-3ec2196a0851"));
    public static Slothpixel sp = new Slothpixel();
    public static int getApiLevel(UUID uuid) {
        try {
            JsonObject player = api.getPlayerByUuid(uuid).get().getPlayer();
            double networkExp = player.get("networkExp").getAsDouble();
            return (int) (ILeveling.getLevel(networkExp));

        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    public static UUID getUUIDFromName(String name) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(Charsets.UTF_8));
    }
    public static String getName(UUID uuid) {
        try {
            JsonObject player = api.getPlayerByUuid(uuid).get().getPlayer();
            return player.get("playername").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
    }

    public static String FirstLogin(UUID uuid) {
        try {
            JsonObject player = api.getPlayerByUuid(uuid).get().getPlayer();

            long milis = player.get("firstLogin").getAsLong();
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
            Date resultdate = new Date(milis);
            return (sdf.format(resultdate));
        } catch (Exception e) {
            e.printStackTrace();
            return "error";

        }
    }

    public static double PercentToNextLevel(UUID uuid) {
        return Math.round(ILeveling.getPercentageToNextLevel(sp.getPlayer(uuid).getExp())*100);
    }

    public static int PlayerCount() {
        try {
            return api.getPlayerCount().get().getPlayerCount();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int BWGamesPlayed(UUID uuid) {
        return sp.getPlayer(uuid).getStats().getBedWars().getGamesPlayed();
    }

    public static int BWKills(UUID uuid) {
        return sp.getPlayer(uuid).getStats().getBedWars().getKills();
    }
    

}
