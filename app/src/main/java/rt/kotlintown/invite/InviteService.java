package rt.kotlintown.invite;

/**
 *
 */

public interface InviteService {
    Invite acceptInvite(Invite invite);

    Invite sendInvite(Invite invite);
}
