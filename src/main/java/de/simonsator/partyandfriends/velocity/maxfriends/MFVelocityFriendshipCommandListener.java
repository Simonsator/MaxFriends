package de.simonsator.partyandfriends.velocity.maxfriends;


import com.velocitypowered.api.event.Subscribe;
import de.simonsator.partyandfriends.velocity.api.events.command.FriendshipCommandEvent;
import de.simonsator.partyandfriends.velocity.utilities.ConfigurationCreator;

public class MFVelocityFriendshipCommandListener extends MFFriendshipCommandListener {
	public MFVelocityFriendshipCommandListener(ConfigurationCreator pConfig) {
		super(pConfig);
	}

	@Subscribe
	public void onFriendshipCommandVelocity(FriendshipCommandEvent pEvent) {
		onFriendshipCommand(pEvent);
	}

}
