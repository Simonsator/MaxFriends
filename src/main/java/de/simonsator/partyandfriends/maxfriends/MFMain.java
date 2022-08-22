package de.simonsator.partyandfriends.maxfriends;

import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.maxfriends.bungee.MFBungeeFriendshipCommandListener;
import de.simonsator.partyandfriends.maxfriends.spigot.MFSpigotFriendshipCommandListener;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class MFMain extends PAFExtension {
	@Override
	public void onEnable() {
		try {
			MFConfig config = new MFConfig(new File(getConfigFolder(), "config.yml"), this);
			switch (BukkitBungeeAdapter.getInstance().getServerSoftware()) {
				case BUNGEECORD:
					BukkitBungeeAdapter.getInstance().registerListener(new MFBungeeFriendshipCommandListener(config), this);
					break;
				case SPIGOT:
					BukkitBungeeAdapter.getInstance().registerListener(new MFSpigotFriendshipCommandListener(config), this);
					break;
				default:
					for (int i = 0; i < 10; i++) {
						System.out.println("Max Friends does not yet support this server type!!!");
					}
					break;
			}
			registerAsExtension();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
