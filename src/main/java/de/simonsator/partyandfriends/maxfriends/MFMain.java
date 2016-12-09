package de.simonsator.partyandfriends.maxfriends;

import de.simonsator.partyandfriends.api.events.command.FriendshipCommandEvent;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.friends.commands.Friends;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author simonbrungs
 * @version 1.0.0 09.12.16
 */
public class MFMain extends Plugin implements Listener {
	private MFConfig config;
	private List<PermissionPackage> permissionPackages = new ArrayList<>();

	@Override
	public void onEnable() {
		try {
			MFConfig config = new MFConfig(new File(getDataFolder().getPath(), "config.yml"));
			for (String pContent : getConfig().getStringList("General.AddMaxFriendsPermission")) {
				String[] permissionPackage = pContent.split("|");
				permissionPackages.add(new PermissionPackage(permissionPackage[0], new Integer(permissionPackage[1])));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void onFriendshipCommand(FriendshipCommandEvent pEvent) {
		if (tooManyFriends(pEvent.getExecutor())) {
			pEvent.getExecutor().sendMessage(Friends.getInstance().getPrefix() +
					getConfig().getString("Messages.YouTooManyFriends"));
			pEvent.setCancelled(true);
		}
		if (pEvent.getInteractPlayer().isOnline()) {
			if (tooManyFriends((OnlinePAFPlayer) pEvent.getInteractPlayer())) {
				pEvent.getExecutor().sendMessage(Friends.getInstance().getPrefix() +
						getConfig().getString("Messages.OtherTooManyFriends"));
				pEvent.setCancelled(true);
			}
		}
	}

	private boolean tooManyFriends(OnlinePAFPlayer pPlayer) {
		if (!pPlayer.hasPermission(getConfig().getString("General.UnlimitedFriendsPermission"))) {
			int maxFriends = getMaxFriends(pPlayer);
			return pPlayer.getFriends().size() < maxFriends;
		}
		return false;
	}

	private int getMaxFriends(OnlinePAFPlayer pPlayer) {
		int maxFriends = getConfig().getInt("General.DefaultMaxFriends");
		for (PermissionPackage permissionPackage : permissionPackages)
			if (pPlayer.hasPermission(permissionPackage.PERMISSION))
				maxFriends += permissionPackage.AMOUNT;
		return maxFriends;
	}

	public Configuration getConfig() {
		return config.getCreatedConfiguration();
	}

	private class PermissionPackage {
		private final String PERMISSION;
		private final int AMOUNT;

		private PermissionPackage(String pPermission, int pAmount) {
			PERMISSION = pPermission;
			AMOUNT = pAmount;
		}
	}
}
