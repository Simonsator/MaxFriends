package de.simonsator.partyandfriends.maxfriends;

import de.simonsator.partyandfriends.api.events.command.FriendshipCommandEvent;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.utilities.ConfigurationCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public abstract class MFFriendshipCommandListener {
	private final List<PermissionPackage> permissionPackages = new ArrayList<>();
	private final String TOO_MANY_FRIENDS_MESSAGE;
	private final String OTHER_PERSON_TOO_MANY_FRIENDS_MESSAGE;
	private final String UNLIMITED_SLOTS_PERMISSION;
	private final int DEFAULT_MAX_SLOTS;

	public MFFriendshipCommandListener(ConfigurationCreator pConfig) {
		for (String pContent : pConfig.getStringList("General.AddMaxFriendsPermission")) {
			StringTokenizer st = new StringTokenizer(pContent, "|");
			permissionPackages.add(new PermissionPackage(st.nextToken(), Integer.parseInt(st.nextToken())));
		}
		String friendPrefix = Friends.getInstance().getPrefix();
		TOO_MANY_FRIENDS_MESSAGE = friendPrefix + pConfig.getString("Messages.YouTooManyFriends");
		OTHER_PERSON_TOO_MANY_FRIENDS_MESSAGE = friendPrefix + pConfig.getString("Messages.OtherTooManyFriends");
		UNLIMITED_SLOTS_PERMISSION = pConfig.getString("General.UnlimitedFriendsPermission");
		DEFAULT_MAX_SLOTS = pConfig.getInt("General.DefaultMaxFriends");
	}

	protected void onFriendshipCommand(FriendshipCommandEvent pEvent) {
		if (tooManyFriends(pEvent.getExecutor())) {
			pEvent.getExecutor().sendMessage(TOO_MANY_FRIENDS_MESSAGE);
			pEvent.setCancelled(true);
			return;
		}
		if (pEvent.getInteractPlayer().isOnline()) {
			if (tooManyFriends((OnlinePAFPlayer) pEvent.getInteractPlayer())) {
				pEvent.getExecutor().sendMessage(OTHER_PERSON_TOO_MANY_FRIENDS_MESSAGE);
				pEvent.setCancelled(true);
			}
		}
	}

	private boolean tooManyFriends(OnlinePAFPlayer pPlayer) {
		if (!pPlayer.hasPermission(UNLIMITED_SLOTS_PERMISSION)) {
			return pPlayer.getFriends().size() >= getMaxFriends(pPlayer);
		}
		return false;
	}

	private int getMaxFriends(OnlinePAFPlayer pPlayer) {
		int maxFriends = DEFAULT_MAX_SLOTS;
		for (PermissionPackage permissionPackage : permissionPackages)
			if (pPlayer.hasPermission(permissionPackage.PERMISSION))
				maxFriends += permissionPackage.AMOUNT;
		return maxFriends;
	}

	private static class PermissionPackage {
		private final String PERMISSION;
		private final int AMOUNT;

		private PermissionPackage(String pPermission, int pAmount) {
			PERMISSION = pPermission;
			AMOUNT = pAmount;
		}
	}
}
