package de.simonsator.partyandfriends.maxfriends;

import de.simonsator.partyandfriends.utilities.ConfigurationCreator;

import java.io.File;
import java.io.IOException;

/**
 * @author simonbrungs
 * @version 1.0.0 09.12.16
 */
public class MFConfig extends ConfigurationCreator {
	protected MFConfig(File file) throws IOException {
		super(file);
		readFile();
		loadDefaultValues();
		saveFile();
		process(configuration);
	}

	private void loadDefaultValues() {
		set("General.DefaultMaxFriends", 10);
		set("General.AddMaxFriendsPermission", "partyandfriends.maxfriends.10|10",
				"partyandfriends.maxfriends.20|20", "partyandfriends.maxfriends.50|50");
		set("General.UnlimitedFriendsPermission", "partyandfriends.maxfriends.unlimited");
		set("Messages.YouTooManyFriends", "&7You already have too many Friends. Too add new Friends you need to delete old once.");
		set("Messages.OtherTooManyFriends", "&7The requested person already has too many Friends. " +
				"He needs first to delete some of his old friends so you can add him as a friend.");
	}

	@Override
	public void reloadConfiguration() throws IOException {
		configuration = (new MFConfig(FILE)).getCreatedConfiguration();
	}
}
