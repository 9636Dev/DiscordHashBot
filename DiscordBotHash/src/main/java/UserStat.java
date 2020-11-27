import net.dv8tion.jda.api.entities.User;

public class UserStat {
    private int MsgSent, mentionedByOthers, exp, level;
    private User user;
    private long userId;

    public UserStat(int MsgSent, int mentionedByOthers, User user, long userId, int exp,int level) {
        this.MsgSent = MsgSent;
        this.mentionedByOthers = mentionedByOthers;
        this.user = user;
        this.userId = userId;
        this.exp = exp;
        this.level = level;
    }

    public int getMsgSent() {
        return MsgSent;
    }

    public User getUser() {
        return user;
    }

    public int getExp() {
        return exp;
    }

    public int getMentionedByOthers() {
        return mentionedByOthers;
    }

    public long getUserId() {
        return userId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setMentionedByOthers(int mentionedByOthers) {
        this.mentionedByOthers = mentionedByOthers;
    }

    public void setMsgSent(int msgSent) {
        MsgSent = msgSent;
    }
    public void addMsgSent() {
        MsgSent++;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
