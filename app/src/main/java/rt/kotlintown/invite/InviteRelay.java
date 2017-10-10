package rt.kotlintown.invite;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static rt.kotlintown.invite.Invite.Status.ACCEPTED;
import static rt.kotlintown.invite.Invite.Status.ERROR;
import static rt.kotlintown.invite.Invite.Status.REJECTED;
import static rt.kotlintown.invite.Invite.Status.SENT;
import static rt.kotlintown.invite.Invite.Status.UNSENT;

/**
 *
 */

public class InviteRelay {

    private final InviteService service;
    private final InviteRepo repo;

    InviteRelay(InviteService service, InviteRepo repo) {
        this.service = service;
        this.repo = repo;
    }

    public Invite acceptInviteFrom(String sender) {
        for (Invite invite : repo.getReceived()) {
            if (Objects.equals(invite.sender, sender)) {
                return service.acceptInvite(invite);
            }
        }

        return null;
    }

    public Invite sendInvite(String sender, String receiver) {
        final List<String> receivers = repo.getReceivers();
        int index = receivers.indexOf(receiver);
        if (index == -1) {
            return null;
        }

        return service.sendInvite(new Invite(sender, receivers.get(index), 0, 0, SENT));
    }

    public List<Invite> acceptInvites(List<Invite> invites) {
        final LinkedList<Invite> accepted = new LinkedList<>();
        for (Invite invite : repo.getReceived()) {
            if (invite.status == SENT) {
                accepted.add(service.acceptInvite(invite));
            }
        }
        return accepted;
    }

    public Map<Invite.Status, List<Invite>> invitesByStatus(List<Invite> invites) {
        final LinkedList<Invite> unsent = new LinkedList<>();
        final LinkedList<Invite> sent = new LinkedList<>();
        final LinkedList<Invite> accepted = new LinkedList<>();
        final LinkedList<Invite> rejected = new LinkedList<>();
        final LinkedList<Invite> error = new LinkedList<>();
        for (Invite invite : repo.getSent()) {
            switch (invite.status) {
                case UNSENT:
                    unsent.add(invite);
                    break;
                case SENT:
                    sent.add(invite);
                    break;
                case ACCEPTED:
                    accepted.add(invite);
                    break;
                case REJECTED:
                    rejected.add(invite);
                    break;
                case ERROR:
                    error.add(invite);
                    break;
            }
        }
        HashMap<Invite.Status, List<Invite>> statuses = new HashMap<>();
        statuses.put(UNSENT, unsent);
        statuses.put(SENT, sent);
        statuses.put(ACCEPTED, accepted);
        statuses.put(REJECTED, rejected);
        statuses.put(ERROR, error);
        return statuses;
    }
}
