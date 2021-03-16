package de.simonsator.partyandfriends.maxfriends.bungee;

import de.simonsator.partyandfriends.api.events.command.FriendshipCommandEvent;
import de.simonsator.partyandfriends.maxfriends.MFFriendshipCommandListener;
import de.simonsator.partyandfriends.utilities.ConfigurationCreator;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MFBungeeFriendshipCommandListener extends MFFriendshipCommandListener implements Listener {
	public MFBungeeFriendshipCommandListener(ConfigurationCreator pConfig) {
		super(pConfig);
	}

	@EventHandler
	public void onFriendshipCommandBungee(FriendshipCommandEvent pEvent) {
		onFriendshipCommand(pEvent);
	}

}
