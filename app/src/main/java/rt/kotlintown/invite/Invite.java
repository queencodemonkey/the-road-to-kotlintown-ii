package rt.kotlintown.invite;

/**
 *
 */
public final class Invite {
    public final String sender;
    public final String receiver;
    public final long timeSent;
    public final long timeAccepted;
    public final Status status;

    public Invite(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.timeSent = 0;
        this.timeAccepted = 0;
        this.status = Status.UNSENT;
    }

    public Invite(String sender, String receiver, long timeSent, long timeAccepted, Status status) {
        this.sender = sender;
        this.receiver = receiver;
        this.timeSent = timeSent;
        this.timeAccepted = timeAccepted;
        this.status = status;
    }

    enum Status {
        UNSENT,
        SENT,
        ACCEPTED,
        REJECTED,
        ERROR
    }
}
