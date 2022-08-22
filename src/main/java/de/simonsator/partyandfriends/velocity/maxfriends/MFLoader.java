package de.simonsator.partyandfriends.velocity.maxfriends;


import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import de.simonsator.partyandfriends.velocity.VelocityExtensionLoadingInfo;
import de.simonsator.partyandfriends.velocity.main.PAFPlugin;

import java.nio.file.Path;

@Plugin(id = "max-friends", name = "Max-Friends", version = "1.0.6-SNAPSHOT",
url = "https://www.spigotmc.org/resources/max-friends-for-party-and-friends.32978/",
description = "An add-on for party and friends extended to limit the amount of friends that somebody can have"
		, authors = {"JT122406", "Simonsator"}, dependencies = {@Dependency(id = "partyandfriends")})
public class MFLoader {
	private final Path folder;

	@Inject
	public MFLoader(@DataDirectory final Path folder) {
		this.folder = folder;
	}

	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event) {
		PAFPlugin.loadExtension(new VelocityExtensionLoadingInfo(new MFMain(folder),
				"max-friends",
				"Max-Friends",
				"1.0.6-RELEASE", "JT122406, Simonsator"));
	}

}
