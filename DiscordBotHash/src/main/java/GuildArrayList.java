import net.dv8tion.jda.api.entities.Guild;

import java.util.ArrayList;

public class GuildArrayList {
    private Guild guild;
    private ArrayList arrayList;

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public ArrayList getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList arrayList) {
        this.arrayList = arrayList;
    }

    public GuildArrayList(Guild guild, ArrayList arrayList) {
        this.guild = guild;
        this.arrayList = arrayList;
    }
}
