package de.simonsator.partyandfriends.maxfriends.spigot;

import de.simonsator.partyandfriends.api.events.command.FriendshipCommandEvent;
import de.simonsator.partyandfriends.maxfriends.MFFriendshipCommandListener;
import de.simonsator.partyandfriends.utilities.ConfigurationCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MFSpigotFriendshipCommandListener extends MFFriendshipCommandListener implements Listener {
	public MFSpigotFriendshipCommandListener(ConfigurationCreator pConfig) {
		super(pConfig);
	}

	@EventHandler
	public void onFriendshipCommandSpigot(FriendshipCommandEvent pEvent) {
		onFriendshipCommand(pEvent);
	}

}
