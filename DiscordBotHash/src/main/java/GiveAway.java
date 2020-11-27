public class GiveAway {
    private long GiveawayTimeMS;
    private String Message;


    public long getGiveawayTimeMS() {
        return GiveawayTimeMS;
    }

    public void setGiveawayTimeMS(long giveawayTimeMS) {
        GiveawayTimeMS = giveawayTimeMS;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public GiveAway(long GiveawayTimeMS, String Message) {
        this.GiveawayTimeMS = GiveawayTimeMS;
        this.Message = Message;
    }

}
