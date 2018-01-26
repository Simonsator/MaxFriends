package de.simonsator.partyandfriends.maxfriends;

import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.api.events.command.FriendshipCommandEvent;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.friends.commands.Friends;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author simonbrungs
 * @version 1.0.0 09.12.16
 */
public class MFMain extends PAFExtension implements Listener {
	private MFConfig config;
	private List<PermissionPackage> permissionPackages = new ArrayList<>();

	@Override
	public void onEnable() {
		try {
			config = new MFConfig(new File(getConfigFolder(), "config.yml"));
			for (String pContent : getConfig().getStringList("General.AddMaxFriendsPermission")) {
				StringTokenizer st = new StringTokenizer(pContent, "|");
				permissionPackages.add(new PermissionPackage(st.nextToken(), Integer.valueOf(st.nextToken())));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		ProxyServer.getInstance().getPluginManager().registerListener(this, this);
	}

	@EventHandler
	public void onFriendshipCommand(FriendshipCommandEvent pEvent) {
		if (tooManyFriends(pEvent.getExecutor())) {
			pEvent.getExecutor().sendMessage(Friends.getInstance().getPrefix() +
					getConfig().getString("Messages.YouTooManyFriends"));
			pEvent.setCancelled(true);
			return;
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
			return pPlayer.getFriends().size() >= maxFriends;
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

	@Override
	public void reload() {
		onDisable();
		onEnable();
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
