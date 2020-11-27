import net.dv8tion.jda.api.entities.User;

public class MSGU {
    private User u;
    private String msg;
    public MSGU (User u,String msg) {
       this.u = u;
       this.msg = msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setU(User u) {
        this.u = u;
    }

    public String getMsg() {
        return msg;
    }

    public User getU() {
        return u;
    }
    
}
