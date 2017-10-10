package rt.kotlintown.invite;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class InviteRepo {

    private static InviteRepo instance = null;

    private static final String[] senders = {
            "Betsy Braddock",
            "Piotr Rasputin",
            "Wade Wilson",
            "Ororo Munroe",
            "Kurt Wagner",
            "Jubilation Lee",
            "Hank McCoy",
            "Clarice Ferguson",
            "Victor Creed",
            "Ellie Phimister",
            "Sam Guthrie",
            "Jonothon Starsmore"
    };

    private static final String[] receivers = {
            "Katie Willert",
            "Dan O'Brien",
            "Soren Bowie",
            "Michael Swaim",
            "Sean Evans",
            "Dan Olson",
            "Lindsay Ellis",
            "Linus Sebastian",
            "Luke Lafreniere",
            "Anne McLaughlin",
            "Mark Schroeder",
            "Greg Edwards"
    };

    private static final int maxLength = 20;

    private static final long startTime;

    private static final long diffTime;

    static {
        final Calendar calendar = Calendar.getInstance();
        final long endTime = calendar.getTimeInMillis();
        calendar.set(2017, 1, 1);
        startTime = calendar.getTimeInMillis();
        diffTime = endTime - startTime + 1;
    }

    public static InviteRepo getInstance() {
        if (instance == null) {
            instance = new InviteRepo();
        }
        return instance;
    }

    public List<Invite> getSent() {
        return generateInviteList(generateListLength());
    }

    public List<Invite> getReceived() {
        return generateInviteList(generateListLength());
    }

    public List<String> getReceivers() { return Arrays.asList(receivers); }

    private List<Invite> generateInviteList(int length) {
        final Invite[] invites = new Invite[length];
        for (int i = 0; i < length; i++) {
            invites[i] = generateInvite();
        }
        return Arrays.asList(invites);
    }

    private Invite generateInvite() {
        final String sender = getSender();
        final String receiver = getReceiver();
        final long time01 = getRandomTime();
        final Invite.Status status = getStatus();
        switch (status) {
            case ACCEPTED:
            case REJECTED:
                final long time02 = getRandomTime();
                final long start = Math.min(time01, time02);
                final long end = Math.max(time01, time02);
                return new Invite(sender, receiver, start, end, status);
            case SENT:
            case ERROR:
            default:
                return new Invite(sender, receiver, time01, 0, status);

        }
    }

    public String getSender() {
        return getRandomElement(senders);
    }

    public String getReceiver() {
        return getRandomElement(receivers);
    }

    private long getRandomTime() {
        return startTime + (long) (Math.random() * diffTime);
    }

    private Invite.Status getStatus() {
        return getRandomElement(Invite.Status.values());
    }

    private int generateListLength() {
        return (int) (Math.random() * maxLength);
    }

    private <T> T getRandomElement(T[] values) {
        return values[(int) (Math.random() * values.length) % values.length];
    }
}
