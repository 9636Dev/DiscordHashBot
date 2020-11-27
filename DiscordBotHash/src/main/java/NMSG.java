import net.dv8tion.jda.api.entities.User;

public class NMSG {
    private int msgNum;
    private User u;

    public int getMsgNum() {
        return msgNum;
    }

    public void setMsgNum(int msgNum) {
        this.msgNum = msgNum;
    }

    public NMSG(User u) {
        this.u = u;
        this.msgNum = 0;
    }
    public void addMsgNum() {
        this.msgNum++;
    }

}
