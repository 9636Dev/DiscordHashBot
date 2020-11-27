import net.dv8tion.jda.api.entities.User;

import java.util.Calendar;
import java.util.Date;

public class TimeU {
    private User u;
    private int Min;
    private String reason;
    private long d;
    public TimeU(User u, int Min, String reason) {
        this.u = u;
        this.Min = Min;
        this.reason = reason;
        d = System.currentTimeMillis();
    }

    public User getU() {
        return u;
    }

    public String getReason() {
        return reason;
    }
    public long getD() {
        return d;
    }
    public int getMin() {
        return Min;
    }

    public void setMin(int min) {
        Min = min;
    }
    public boolean checkIfTimeIsUp () {
        System.out.println(d+(Min*60000)<System.currentTimeMillis());
        return d+(Min*60000)<System.currentTimeMillis();
    }
}
