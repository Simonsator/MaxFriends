package de.simonsator.partyandfriends.velocity.maxfriends;

import de.simonsator.partyandfriends.velocity.api.PAFExtension;
import de.simonsator.partyandfriends.velocity.api.adapter.BukkitBungeeAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class MFMain extends PAFExtension {
	public MFMain(Path folder) {
		super(folder);
	}

	@Override
	public void onEnable() {
		try {
			MFConfig config = new MFConfig(new File(getConfigFolder(), "config.yml"), this);
			BukkitBungeeAdapter.getInstance().registerListener(new MFVelocityFriendshipCommandListener(config), this);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		registerAsExtension();
	}

	@Override
	public String getName() {
		return "MaxFriends-PAF";
	}
}
