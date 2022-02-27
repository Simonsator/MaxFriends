package de.simonsator.partyandfriends.maxfriends;

import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.utilities.ConfigurationCreator;

import java.io.File;
import java.io.IOException;

public class MFConfig extends ConfigurationCreator {
	protected MFConfig(File file, PAFExtension pPlugin) throws IOException {
		super(file, pPlugin);
		copyFromJar();
		readFile();
		loadDefaultValues();
		saveFile();
		process();
	}

	private void loadDefaultValues() {
		set("General.DefaultMaxFriends", 10);
		set("General.AddMaxFriendsPermission", "partyandfriends.maxfriends.10|10",
				"partyandfriends.maxfriends.20|20", "partyandfriends.maxfriends.50|50");
		set("General.UnlimitedFriendsPermission", "partyandfriends.maxfriends.unlimited");
		set("Messages.YouTooManyFriends", " &7You already have too many Friends. Too add new Friends you need to delete old once.");
		set("Messages.OtherTooManyFriends", " &7The requested person already has too many Friends. " +
				"He needs first to delete some of his old friends so you can add him as a friend.");
	}
}
