package com.starcallingassist.modules.sidepanel;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import net.runelite.client.config.ConfigManager;

@Singleton
public class LocationPreferenceManager
{
	private static final String CONFIG_GROUP = "starcallingassistplugin";
	private static final String FAVORITES_KEY = "favorites";
	private static final String HIDDEN_KEY = "hidden";
	private static final String DELIMITER = ",";

	@Inject
	private ConfigManager configManager;

	public Set<String> getFavoriteLocations()
	{
		String favoritesConfig = configManager.getConfiguration(CONFIG_GROUP, FAVORITES_KEY);
		if (favoritesConfig == null || favoritesConfig.trim().isEmpty())
		{
			return new HashSet<>();
		}

		return Arrays.stream(favoritesConfig.split(DELIMITER))
			.map(String::trim)
			.filter(s -> !s.isEmpty())
			.collect(Collectors.toSet());
	}

	public Set<String> getHiddenLocations()
	{
		String hiddenConfig = configManager.getConfiguration(CONFIG_GROUP, HIDDEN_KEY);
		if (hiddenConfig == null || hiddenConfig.trim().isEmpty())
		{
			return new HashSet<>();
		}

		return Arrays.stream(hiddenConfig.split(DELIMITER))
			.map(String::trim)
			.filter(s -> !s.isEmpty())
			.collect(Collectors.toSet());
	}

	public boolean isLocationFavorite(String locationName)
	{
		if (locationName == null)
		{
			return false;
		}
		return getFavoriteLocations().contains(locationName);
	}

	public boolean isLocationHidden(String locationName)
	{
		if (locationName == null)
		{
			return false;
		}
		return getHiddenLocations().contains(locationName);
	}

	public void setLocationFavorite(String locationName, boolean favorite)
	{
		if (locationName == null)
		{
			return;
		}

		Set<String> favorites = getFavoriteLocations();
		if (favorite)
		{
			favorites.add(locationName);
		}
		else
		{
			favorites.remove(locationName);
		}

		saveFavorites(favorites);
	}

	public void setLocationHidden(String locationName, boolean hidden)
	{
		if (locationName == null)
		{
			return;
		}

		Set<String> hiddenLocations = getHiddenLocations();
		if (hidden)
		{
			hiddenLocations.add(locationName);
		}
		else
		{
			hiddenLocations.remove(locationName);
		}

		saveHidden(hiddenLocations);
	}

	public void toggleLocationFavorite(String locationName)
	{
		setLocationFavorite(locationName, !isLocationFavorite(locationName));
	}

	public void toggleLocationHidden(String locationName)
	{
		setLocationHidden(locationName, !isLocationHidden(locationName));
	}

	public void setAllLocationsInRegionHidden(Set<String> locationNames, boolean hidden)
	{
		Set<String> hiddenLocations = getHiddenLocations();
		
		if (hidden)
		{
			hiddenLocations.addAll(locationNames);
		}
		else
		{
			hiddenLocations.removeAll(locationNames);
		}

		saveHidden(hiddenLocations);
	}

	private void saveFavorites(Set<String> favorites)
	{
		String favoritesString = String.join(DELIMITER, favorites);
		configManager.setConfiguration(CONFIG_GROUP, FAVORITES_KEY, favoritesString);
	}

	private void saveHidden(Set<String> hidden)
	{
		String hiddenString = String.join(DELIMITER, hidden);
		configManager.setConfiguration(CONFIG_GROUP, HIDDEN_KEY, hiddenString);
	}
}