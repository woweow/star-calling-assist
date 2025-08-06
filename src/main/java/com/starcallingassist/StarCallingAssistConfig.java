package com.starcallingassist;

import com.starcallingassist.constants.RegionKeyName;
import com.starcallingassist.enums.ChatLogLevel;
import com.starcallingassist.modules.sidepanel.enums.OrderBy;
import com.starcallingassist.modules.sidepanel.enums.TotalLevelType;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;
import net.runelite.client.config.Units;

@ConfigGroup("starcallingassistplugin")
public interface StarCallingAssistConfig extends Config
{
	@ConfigItem(
		keyName = "endpoint",
		position = 0,
		name = "Endpoint",
		description = "Endpoint to post calls to and fetch calls from")
	default String getEndpoint()
	{
		return "https://public.starminers.site/crowdsource";
	}

	@ConfigItem(
		keyName = "authorization",
		position = 1,
		name = "Authorization",
		description = "Used to set the http authorization header.")
	default String getAuthorization()
	{
		return "";
	}

	@ConfigSection(
		name = "Scout Settings",
		description = "Settings to configure the scouting part of the plugin.",
		position = 2,
		closedByDefault = true
	)
	String scoutingSection = "Scout Settings";

	@ConfigItem(
		keyName = "includeIgn",
		name = "Send in-game name",
		description = "Includes your in-game name with your calls. This is required if you want the stars you find " +
			"to count towards your called stars total.",
		position = 3,
		section = scoutingSection
	)
	default boolean includeIgn()
	{
		return false;
	}

	@ConfigItem(
		keyName = "autoCall",
		name = "Auto call stars",
		description = "Automatically call stars as they appear or fully depletes.",
		position = 4,
		section = scoutingSection
	)
	default boolean autoCall()
	{
		return true;
	}

	@ConfigItem(
		keyName = "updateStar",
		name = "Auto update stars",
		description = "Posts a new call when the tier of a star changes (Auto call must be enabled).",
		position = 5,
		section = scoutingSection
	)
	default boolean updateStar()
	{
		return true;
	}

	@ConfigItem(
		keyName = "callHorn",
		name = "Call Button",
		description = "Enables a button which can be used to call a star.",
		position = 6,
		section = scoutingSection
	)
	default boolean callHorn()
	{
		return false;
	}

	@ConfigItem(
		keyName = "scoutOverlay",
		name = "Scout Overlay",
		description = "Enables an overlay which helps with accurately and effortlessly scouting stars.",
		position = 7,
		section = scoutingSection
	)
	default boolean scoutOverlay()
	{
		return false;
	}

	@ConfigSection(
		name = "General Settings",
		description = "Customizable settings that augment your in-game experience.",
		position = 8
	)
	String generalSection = "General Settings";

	@ConfigItem(
		keyName = "starDetailsOverlay",
		name = "Show details overlay",
		description = "Whether or not to display information about a nearby star.",
		position = 9,
		section = generalSection
	)
	default boolean starDetailsOverlay()
	{
		return true;
	}

	@ConfigItem(
		keyName = "starOnWorldMap",
		name = "Show active star on world map",
		description = "Whether or not to display any active star on the world map.",
		position = 9,
		section = generalSection
	)
	default boolean starOnWorldMap()
	{
		return true;
	}

	@ConfigItem(
		keyName = "logLevel",
		name = "Chat Log Level",
		description = "To what extent you want to see log messages from the plugin in the game chat.",
		position = 10,
		section = generalSection
	)
	default ChatLogLevel logLevel()
	{
		return ChatLogLevel.NORMAL;
	}

	@ConfigSection(
		name = "Side-Panel Layout",
		description = "Settings to configure the layout of active stars within the side-panel.",
		position = 11,
		closedByDefault = true
	)
	String sidePanelLayoutSection = "Side Panel Layout";

	@ConfigItem(
		keyName = "showTier",
		name = "Show Tier",
		description = "Displays the current tier of the star in the list.",
		position = 12,
		section = sidePanelLayoutSection
	)
	default boolean showTier()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showFoundBy",
		name = "Show Found By",
		description = "Displays the name of the player who first discovered the star in the list.",
		position = 13,
		section = sidePanelLayoutSection
	)
	default boolean showFoundBy()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showDeadTime",
		name = "Show Dead Time Estimate",
		description = "Displays the estimated time (in minutes) until the star is dead.",
		position = 14,
		section = sidePanelLayoutSection
	)
	default boolean showDeadTime()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showWorldType",
		name = "Show World Type",
		description = "Displays the world type (e.g. PvP, 2200 total) in the list.",
		position = 15,
		section = sidePanelLayoutSection
	)
	default boolean showWorldType()
	{
		return true;
	}

	@ConfigItem(
		keyName = "orderBy",
		name = "Default Sorting",
		description = "In what order you want the stars to be sorted in the side-panel by default.",
		position = 16,
		section = sidePanelLayoutSection
	)
	default OrderBy orderBy()
	{
		return OrderBy.LOCATION;
	}

	@ConfigSection(
		name = "Star Filters",
		description = "Settings to filter out certain stars and tiers from the side-panel.",
		position = 17,
		closedByDefault = true
	)
	String starFilterSection = "Star Filters";

	@ConfigItem(
		keyName = "minTier",
		name = "Minimum Tier",
		description = "Lowest tier of stars to be displayed in the side-panel.",
		position = 18,
		section = starFilterSection
	)
	@Range(
		min = 1,
		max = 9
	)
	default int minTier()
	{
		return 1;
	}

	@ConfigItem(
		keyName = "maxTier",
		name = "Maximum Tier",
		description = "Highest tier of stars to be displayed in the side-panel.",
		position = 19,
		section = starFilterSection
	)
	@Range(
		min = 1,
		max = 9
	)
	default int maxTier()
	{
		return 9;
	}

	@ConfigItem(
		keyName = "estimateTier",
		name = "Use estimated tiers",
		description = "Estimates the current tier of stars in the list, instead of displaying the last known tier.",
		position = 20,
		section = starFilterSection
	)
	default boolean estimateTier()
	{
		return true;
	}

	@ConfigItem(
		keyName = "minDeadTime",
		name = "Minimum Dead Time",
		description = "Hides stars that are estimated to be depleted in less than the specified amount of minutes.",
		position = 21,
		section = starFilterSection
	)
	@Range(
		min = -90,
		max = 90
	)
	@Units(
		value = Units.MINUTES
	)
	default int minDeadTime()
	{
		return -5;
	}

	@ConfigSection(
		name = "World Filters",
		description = "Settings to filter out certain worlds from the side-panel.",
		position = 22,
		closedByDefault = true
	)
	String worldFilterSection = "World Filters";

	@ConfigItem(
		keyName = "showF2P",
		name = "Show Free-to-Play",
		description = "Show or hide F2P worlds.",
		position = 23,
		section = worldFilterSection
	)
	default boolean showF2P()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showMembers",
		name = "Show Members",
		description = "Show or hide members worlds.",
		position = 24,
		section = worldFilterSection
	)
	default boolean showMembers()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showPvp",
		name = "Show PvP",
		description = "Show or hide PvP worlds.",
		position = 25,
		section = worldFilterSection
	)
	default boolean showPvp()
	{
		return false;
	}

	@ConfigItem(
		keyName = "showHighRisk",
		name = "Show High-Risk PvP",
		description = "Show or hide high-risk PvP worlds.",
		position = 26,
		section = worldFilterSection
	)
	default boolean showHighRisk()
	{
		return false;
	}

	@ConfigItem(
		keyName = "totalLevelType",
		name = "Max Total World",
		description = "Hides worlds with a total level requirement higher than this.",
		position = 27,
		section = worldFilterSection
	)
	default TotalLevelType totalLevelType()
	{
		return TotalLevelType.TOTAL_2200;
	}

	@ConfigSection(
		name = "Region Filters",
		description = "Settings to filter out stars in certain regions from the side-panel.",
		position = 28,
		closedByDefault = true
	)
	String regionFilterSection = "Region Filters";

	@ConfigItem(
		keyName = RegionKeyName.KEY_ASGARNIA,
		name = "Asgarnia",
		description = "Show or hide this region.",
		position = 29,
		section = regionFilterSection
	)
	default boolean asgarnia()
	{
		return true;
	}

	@ConfigItem(
		keyName = RegionKeyName.KEY_KARAMJA,
		name = "Crandor/Karamja",
		description = "Show or hide this region.",
		position = 30,
		section = regionFilterSection
	)
	default boolean karamja()
	{
		return true;
	}

	@ConfigItem(
		keyName = RegionKeyName.KEY_FELDIP,
		name = "Feldip Hills/Isle Of Souls",
		description = "Show or hide this region.",
		position = 31,
		section = regionFilterSection
	)
	default boolean feldip()
	{
		return true;
	}

	@ConfigItem(
		keyName = RegionKeyName.KEY_FOSSIL,
		name = "Fossil Island/Mos Le Harmless",
		description = "Show or hide this region.",
		position = 32,
		section = regionFilterSection
	)
	default boolean fossil()
	{
		return true;
	}

	@ConfigItem(
		keyName = RegionKeyName.KEY_FREMMENIK,
		name = "Fremmenik/Lunar Isle",
		description = "Show or hide this region.",
		position = 33,
		section = regionFilterSection
	)
	default boolean fremmenik()
	{
		return true;
	}

	@ConfigItem(
		keyName = RegionKeyName.KEY_KOUREND,
		name = "Kourend",
		description = "Show or hide this region.",
		position = 34,
		section = regionFilterSection
	)
	default boolean kourend()
	{
		return true;
	}

	@ConfigItem(
		keyName = RegionKeyName.KEY_KANDARIN,
		name = "Kandarin",
		description = "Show or hide this region.",
		position = 35,
		section = regionFilterSection
	)
	default boolean kandarin()
	{
		return true;
	}

	@ConfigItem(
		keyName = RegionKeyName.KEY_KEBOS,
		name = "Kebos Lowlands",
		description = "Show or hide this region.",
		position = 36,
		section = regionFilterSection
	)
	default boolean kebos()
	{
		return true;
	}

	@ConfigItem(
		keyName = RegionKeyName.KEY_DESERT,
		name = "Desert",
		description = "Show or hide this region.",
		position = 37,
		section = regionFilterSection
	)
	default boolean desert()
	{
		return true;
	}

	@ConfigItem(
		keyName = RegionKeyName.KEY_MISTHALIN,
		name = "Misthalin",
		description = "Show or hide this region.",
		position = 38,
		section = regionFilterSection
	)
	default boolean misthalin()
	{
		return true;
	}

	@ConfigItem(
		keyName = RegionKeyName.KEY_MORYTANIA,
		name = "Morytania",
		description = "Show or hide this region.",
		position = 39,
		section = regionFilterSection
	)
	default boolean morytania()
	{
		return true;
	}

	@ConfigItem(
		keyName = RegionKeyName.KEY_GNOME,
		name = "Piscatoris/Gnome Stronghold",
		description = "Show or hide this region.",
		position = 40,
		section = regionFilterSection
	)
	default boolean gnome()
	{
		return true;
	}

	@ConfigItem(
		keyName = RegionKeyName.KEY_TIRANNWN,
		name = "Tirannwn",
		description = "Show or hide this region.",
		position = 41,
		section = regionFilterSection
	)
	default boolean tirannwn()
	{
		return true;
	}

	@ConfigItem(
		keyName = RegionKeyName.KEY_VARLAMORE,
		name = "Varlamore",
		description = "Show or hide this region.",
		position = 42,
		section = regionFilterSection
	)
	default boolean varlamore()
	{
		return true;
	}

	@ConfigItem(
		keyName = RegionKeyName.KEY_WILDERNESS,
		name = "Wilderness",
		description = "Show or hide this region.",
		position = 43,
		section = regionFilterSection
	)
	default boolean wilderness()
	{
		return true;
	}

	@ConfigItem(
		keyName = RegionKeyName.KEY_UNKNOWN,
		name = "Unknown / Unconfirmed",
		description = "Show or hide stars that haven't been confirmed / mapped to a region yet.",
		position = 44,
		section = regionFilterSection
	)
	default boolean unknown()
	{
		return false;
	}

	@ConfigSection(
		name = "Location Filters",
		description = "Settings to filter out specific star locations from the side-panel. More granular than region filtering.",
		position = 45,
		closedByDefault = true
	)
	String locationFilterSection = "Location Filters";

	@ConfigItem(
		keyName = "showUnknownLocations",
		name = "Show Unknown Locations",
		description = "Show or hide stars at locations that aren't in the predefined list (may have coordinate names or typos).",
		position = 46,
		section = locationFilterSection
	)
	default boolean showUnknownLocations()
	{
		return true;
	}

	// Individual Location Filters (alphabetically sorted by display name)
	
	@ConfigItem(
		keyName = "abandonedMineWestOfBurgh",
		name = "Abandoned Mine west of Burgh",
		description = "Show or hide this location.",
		position = 47,
		section = locationFilterSection
	)
	default boolean abandonedMineWestOfBurgh()
	{
		return true;
	}

	@ConfigItem(
		keyName = "agilityPyramidMine",
		name = "Agility Pyramid mine",
		description = "Show or hide this location.",
		position = 48,
		section = locationFilterSection
	)
	default boolean agilityPyramidMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "alKharidBank",
		name = "Al Kharid bank",
		description = "Show or hide this location.",
		position = 49,
		section = locationFilterSection
	)
	default boolean alKharidBank()
	{
		return true;
	}

	@ConfigItem(
		keyName = "alKharidMine",
		name = "Al Kharid mine",
		description = "Show or hide this location.",
		position = 50,
		section = locationFilterSection
	)
	default boolean alKharidMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "arandarMineNorthOfLletya",
		name = "Arandar mine north of Lletya",
		description = "Show or hide this location.",
		position = 51,
		section = locationFilterSection
	)
	default boolean arandarMineNorthOfLletya()
	{
		return true;
	}

	@ConfigItem(
		keyName = "arceuusDenseEssenceMine",
		name = "Arceuus dense essence mine",
		description = "Show or hide this location.",
		position = 52,
		section = locationFilterSection
	)
	default boolean arceuusDenseEssenceMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "ardougneMonastery",
		name = "Ardougne Monastery",
		description = "Show or hide this location.",
		position = 53,
		section = locationFilterSection
	)
	default boolean ardougneMonastery()
	{
		return true;
	}

	@ConfigItem(
		keyName = "brimhavenNorthwestGoldMine",
		name = "Brimhaven northwest gold mine",
		description = "Show or hide this location.",
		position = 54,
		section = locationFilterSection
	)
	default boolean brimhavenNorthwestGoldMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "burghDeRottBank",
		name = "Burgh de Rott bank",
		description = "Show or hide this location.",
		position = 55,
		section = locationFilterSection
	)
	default boolean burghDeRottBank()
	{
		return true;
	}

	@ConfigItem(
		keyName = "canifisBank",
		name = "Canifis bank",
		description = "Show or hide this location.",
		position = 56,
		section = locationFilterSection
	)
	default boolean canifisBank()
	{
		return true;
	}

	@ConfigItem(
		keyName = "catherbyBank",
		name = "Catherby bank",
		description = "Show or hide this location.",
		position = 57,
		section = locationFilterSection
	)
	default boolean catherbyBank()
	{
		return true;
	}

	@ConfigItem(
		keyName = "chambersOfXericBank",
		name = "Chambers of Xeric bank",
		description = "Show or hide this location.",
		position = 58,
		section = locationFilterSection
	)
	default boolean chambersOfXericBank()
	{
		return true;
	}

	@ConfigItem(
		keyName = "championsGuildMine",
		name = "Champions' Guild mine",
		description = "Show or hide this location.",
		position = 59,
		section = locationFilterSection
	)
	default boolean championsGuildMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "coalTrucksWestOfSeers",
		name = "Coal Trucks west of Seers'",
		description = "Show or hide this location.",
		position = 60,
		section = locationFilterSection
	)
	default boolean coalTrucksWestOfSeers()
	{
		return true;
	}

	@ConfigItem(
		keyName = "corsairCoveBank",
		name = "Corsair Cove bank",
		description = "Show or hide this location.",
		position = 61,
		section = locationFilterSection
	)
	default boolean corsairCoveBank()
	{
		return true;
	}

	@ConfigItem(
		keyName = "corsairResourceArea",
		name = "Corsair Resource Area",
		description = "Show or hide this location.",
		position = 62,
		section = locationFilterSection
	)
	default boolean corsairResourceArea()
	{
		return true;
	}

	@ConfigItem(
		keyName = "craftingGuild",
		name = "Crafting guild",
		description = "Show or hide this location.",
		position = 63,
		section = locationFilterSection
	)
	default boolean craftingGuild()
	{
		return true;
	}

	@ConfigItem(
		keyName = "darkmeyerEssMineEntrance",
		name = "Darkmeyer ess. mine entrance",
		description = "Show or hide this location.",
		position = 64,
		section = locationFilterSection
	)
	default boolean darkmeyerEssMineEntrance()
	{
		return true;
	}

	@ConfigItem(
		keyName = "desertQuarryMine",
		name = "Desert Quarry mine",
		description = "Show or hide this location.",
		position = 65,
		section = locationFilterSection
	)
	default boolean desertQuarryMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "draynorVillage",
		name = "Draynor Village",
		description = "Show or hide this location.",
		position = 66,
		section = locationFilterSection
	)
	default boolean draynorVillage()
	{
		return true;
	}

	@ConfigItem(
		keyName = "eastFaladorBank",
		name = "East Falador bank",
		description = "Show or hide this location.",
		position = 67,
		section = locationFilterSection
	)
	default boolean eastFaladorBank()
	{
		return true;
	}

	@ConfigItem(
		keyName = "eastLumbridgeSwampMine",
		name = "East Lumbridge Swamp mine",
		description = "Show or hide this location.",
		position = 68,
		section = locationFilterSection
	)
	default boolean eastLumbridgeSwampMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "feldipHillsAksFairyRing",
		name = "Feldip Hills (aks fairy ring)",
		description = "Show or hide this location.",
		position = 69,
		section = locationFilterSection
	)
	default boolean feldipHillsAksFairyRing()
	{
		return true;
	}

	@ConfigItem(
		keyName = "fossilIslandRuneRocks",
		name = "Fossil Island rune rocks",
		description = "Show or hide this location.",
		position = 70,
		section = locationFilterSection
	)
	default boolean fossilIslandRuneRocks()
	{
		return true;
	}

	@ConfigItem(
		keyName = "fossilIslandVolcanicMineEntrance",
		name = "Fossil Island Volcanic Mine entrance",
		description = "Show or hide this location.",
		position = 71,
		section = locationFilterSection
	)
	default boolean fossilIslandVolcanicMineEntrance()
	{
		return true;
	}

	@ConfigItem(
		keyName = "gnomeStrongholdSpiritTree",
		name = "Gnome Stronghold spirit tree",
		description = "Show or hide this location.",
		position = 72,
		section = locationFilterSection
	)
	default boolean gnomeStrongholdSpiritTree()
	{
		return true;
	}

	@ConfigItem(
		keyName = "hobgoblinMineLvl30Wildy",
		name = "Hobgoblin mine (lvl 30 Wildy)",
		description = "Show or hide this location.",
		position = 73,
		section = locationFilterSection
	)
	default boolean hobgoblinMineLvl30Wildy()
	{
		return true;
	}

	@ConfigItem(
		keyName = "hosidiusMine",
		name = "Hosidius mine",
		description = "Show or hide this location.",
		position = 74,
		section = locationFilterSection
	)
	default boolean hosidiusMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "isafdarRuniteRocks",
		name = "Isafdar runite rocks",
		description = "Show or hide this location.",
		position = 75,
		section = locationFilterSection
	)
	default boolean isafdarRuniteRocks()
	{
		return true;
	}

	@ConfigItem(
		keyName = "jatizsoMineEntrance",
		name = "Jatizso mine entrance",
		description = "Show or hide this location.",
		position = 76,
		section = locationFilterSection
	)
	default boolean jatizsoMineEntrance()
	{
		return true;
	}

	@ConfigItem(
		keyName = "kebosSwampMine",
		name = "Kebos Swamp mine",
		description = "Show or hide this location.",
		position = 77,
		section = locationFilterSection
	)
	default boolean kebosSwampMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "keldagrimEntranceMine",
		name = "Keldagrim entrance mine",
		description = "Show or hide this location.",
		position = 78,
		section = locationFilterSection
	)
	default boolean keldagrimEntranceMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "lavaMazeRuniteMineLvl46Wildy",
		name = "Lava maze runite mine (lvl 46 Wildy)",
		description = "Show or hide this location.",
		position = 79,
		section = locationFilterSection
	)
	default boolean lavaMazeRuniteMineLvl46Wildy()
	{
		return true;
	}

	@ConfigItem(
		keyName = "lletya",
		name = "Lletya",
		description = "Show or hide this location.",
		position = 80,
		section = locationFilterSection
	)
	default boolean lletya()
	{
		return true;
	}

	@ConfigItem(
		keyName = "lovakiteMine",
		name = "Lovakite mine",
		description = "Show or hide this location.",
		position = 81,
		section = locationFilterSection
	)
	default boolean lovakiteMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "lunarIsleMineEntrance",
		name = "Lunar Isle mine entrance",
		description = "Show or hide this location.",
		position = 82,
		section = locationFilterSection
	)
	default boolean lunarIsleMineEntrance()
	{
		return true;
	}

	@ConfigItem(
		keyName = "mageArenaBankLvl56Wildy",
		name = "Mage Arena bank (lvl 56 Wildy)",
		description = "Show or hide this location.",
		position = 83,
		section = locationFilterSection
	)
	default boolean mageArenaBankLvl56Wildy()
	{
		return true;
	}

	@ConfigItem(
		keyName = "mageOfZamorakMineLvl7Wildy",
		name = "Mage of Zamorak mine (lvl 7 Wildy)",
		description = "Show or hide this location.",
		position = 84,
		section = locationFilterSection
	)
	default boolean mageOfZamorakMineLvl7Wildy()
	{
		return true;
	}

	@ConfigItem(
		keyName = "mineNorthWestOfHunterGuild",
		name = "Mine north-west of hunter guild",
		description = "Show or hide this location.",
		position = 85,
		section = locationFilterSection
	)
	default boolean mineNorthWestOfHunterGuild()
	{
		return true;
	}

	@ConfigItem(
		keyName = "miscellaniaMine",
		name = "Miscellania mine (cip fairy ring)",
		description = "Show or hide this location.",
		position = 86,
		section = locationFilterSection
	)
	default boolean miscellaniaMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "mosLeHarmlessWestBank",
		name = "Mos Le'Harmless west bank",
		description = "Show or hide this location.",
		position = 87,
		section = locationFilterSection
	)
	default boolean mosLeHarmlessWestBank()
	{
		return true;
	}

	@ConfigItem(
		keyName = "mountKaruulmBank",
		name = "Mount Karuulm bank",
		description = "Show or hide this location.",
		position = 88,
		section = locationFilterSection
	)
	default boolean mountKaruulmBank()
	{
		return true;
	}

	@ConfigItem(
		keyName = "mountKaruulmMine",
		name = "Mount Karuulm mine",
		description = "Show or hide this location.",
		position = 89,
		section = locationFilterSection
	)
	default boolean mountKaruulmMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "mynyddNwOfPrifddinas",
		name = "Mynydd nw of Prifddinas",
		description = "Show or hide this location.",
		position = 90,
		section = locationFilterSection
	)
	default boolean mynyddNwOfPrifddinas()
	{
		return true;
	}

	@ConfigItem(
		keyName = "mythsGuild",
		name = "Myths' Guild",
		description = "Show or hide this location.",
		position = 91,
		section = locationFilterSection
	)
	default boolean mythsGuild()
	{
		return true;
	}

	@ConfigItem(
		keyName = "nardahBank",
		name = "Nardah bank",
		description = "Show or hide this location.",
		position = 92,
		section = locationFilterSection
	)
	default boolean nardahBank()
	{
		return true;
	}

	@ConfigItem(
		keyName = "natureAltarMineNorthOfShilo",
		name = "Nature Altar mine north of Shilo",
		description = "Show or hide this location.",
		position = 93,
		section = locationFilterSection
	)
	default boolean natureAltarMineNorthOfShilo()
	{
		return true;
	}

	@ConfigItem(
		keyName = "neitiznotSouthOfRuneRock",
		name = "Neitiznot south of rune rock",
		description = "Show or hide this location.",
		position = 94,
		section = locationFilterSection
	)
	default boolean neitiznotSouthOfRuneRock()
	{
		return true;
	}

	@ConfigItem(
		keyName = "northCrandor",
		name = "North Crandor",
		description = "Show or hide this location.",
		position = 95,
		section = locationFilterSection
	)
	default boolean northCrandor()
	{
		return true;
	}

	@ConfigItem(
		keyName = "northDwarvenMineEntrance",
		name = "North Dwarven Mine entrance",
		description = "Show or hide this location.",
		position = 96,
		section = locationFilterSection
	)
	default boolean northDwarvenMineEntrance()
	{
		return true;
	}

	@ConfigItem(
		keyName = "northOfAlKharidPvPArena",
		name = "North of Al Kharid PvP Arena",
		description = "Show or hide this location.",
		position = 97,
		section = locationFilterSection
	)
	default boolean northOfAlKharidPvPArena()
	{
		return true;
	}

	@ConfigItem(
		keyName = "nwOfUzerEaglesEyrie",
		name = "Nw of Uzer (Eagle's Eyrie)",
		description = "Show or hide this location.",
		position = 98,
		section = locationFilterSection
	)
	default boolean nwOfUzerEaglesEyrie()
	{
		return true;
	}

	@ConfigItem(
		keyName = "piratesHideoutLvl53Wildy",
		name = "Pirates' Hideout (lvl 53 Wildy)",
		description = "Show or hide this location.",
		position = 99,
		section = locationFilterSection
	)
	default boolean piratesHideoutLvl53Wildy()
	{
		return true;
	}

	@ConfigItem(
		keyName = "piscatorisAkqFairyRing",
		name = "Piscatoris (akq fairy ring)",
		description = "Show or hide this location.",
		position = 100,
		section = locationFilterSection
	)
	default boolean piscatorisAkqFairyRing()
	{
		return true;
	}

	@ConfigItem(
		keyName = "portKhazardMine",
		name = "Port Khazard mine",
		description = "Show or hide this location.",
		position = 101,
		section = locationFilterSection
	)
	default boolean portKhazardMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "portPiscariliusMineInKourend",
		name = "Port Piscarilius mine in Kourend",
		description = "Show or hide this location.",
		position = 102,
		section = locationFilterSection
	)
	default boolean portPiscariliusMineInKourend()
	{
		return true;
	}

	@ConfigItem(
		keyName = "prifddinas",
		name = "Prifddinas Zalcano entrance",
		description = "Show or hide this location.",
		position = 103,
		section = locationFilterSection
	)
	default boolean prifddinas()
	{
		return true;
	}

	@ConfigItem(
		keyName = "rantzCave",
		name = "Rantz cave",
		description = "Show or hide this location.",
		position = 104,
		section = locationFilterSection
	)
	default boolean rantzCave()
	{
		return true;
	}

	@ConfigItem(
		keyName = "rellekkaMine",
		name = "Rellekka mine",
		description = "Show or hide this location.",
		position = 105,
		section = locationFilterSection
	)
	default boolean rellekkaMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "rimmingtonMine",
		name = "Rimmington mine",
		description = "Show or hide this location.",
		position = 106,
		section = locationFilterSection
	)
	default boolean rimmingtonMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "shayzienMineSouthOfKourendCastle",
		name = "Shayzien mine south of Kourend Castle",
		description = "Show or hide this location.",
		position = 107,
		section = locationFilterSection
	)
	default boolean shayzienMineSouthOfKourendCastle()
	{
		return true;
	}

	@ConfigItem(
		keyName = "shiloVillageGemMine",
		name = "Shilo Village gem mine",
		description = "Show or hide this location.",
		position = 108,
		section = locationFilterSection
	)
	default boolean shiloVillageGemMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "skeletonMineLvl10Wildy",
		name = "Skeleton mine (lvl 10 Wildy)",
		description = "Show or hide this location.",
		position = 109,
		section = locationFilterSection
	)
	default boolean skeletonMineLvl10Wildy()
	{
		return true;
	}

	@ConfigItem(
		keyName = "soulWarsSouthMine",
		name = "Soul Wars south mine",
		description = "Show or hide this location.",
		position = 110,
		section = locationFilterSection
	)
	default boolean soulWarsSouthMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "southCrandor",
		name = "South Crandor",
		description = "Show or hide this location.",
		position = 111,
		section = locationFilterSection
	)
	default boolean southCrandor()
	{
		return true;
	}

	@ConfigItem(
		keyName = "southLovakengj",
		name = "South Lovakengj bank",
		description = "Show or hide this location.",
		position = 112,
		section = locationFilterSection
	)
	default boolean southLovakengj()
	{
		return true;
	}

	@ConfigItem(
		keyName = "southOfLegendsGuild",
		name = "South of Legends' Guild",
		description = "Show or hide this location.",
		position = 113,
		section = locationFilterSection
	)
	default boolean southOfLegendsGuild()
	{
		return true;
	}

	@ConfigItem(
		keyName = "southeastVarrockMine",
		name = "Southeast Varrock mine",
		description = "Show or hide this location.",
		position = 114,
		section = locationFilterSection
	)
	default boolean southeastVarrockMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "southwestOfBrimhavenPoh",
		name = "Southwest of Brimhaven Poh",
		description = "Show or hide this location.",
		position = 115,
		section = locationFilterSection
	)
	default boolean southwestOfBrimhavenPoh()
	{
		return true;
	}

	@ConfigItem(
		keyName = "taverleyHousePortal",
		name = "Taverley house portal",
		description = "Show or hide this location.",
		position = 116,
		section = locationFilterSection
	)
	default boolean taverleyHousePortal()
	{
		return true;
	}

	@ConfigItem(
		keyName = "theatreOfBloodBank",
		name = "Theatre of Blood bank",
		description = "Show or hide this location.",
		position = 117,
		section = locationFilterSection
	)
	default boolean theatreOfBloodBank()
	{
		return true;
	}

	@ConfigItem(
		keyName = "varlamoreColosseumEntranceBank",
		name = "Varlamore colosseum entrance bank",
		description = "Show or hide this location.",
		position = 118,
		section = locationFilterSection
	)
	default boolean varlamoreColosseumEntranceBank()
	{
		return true;
	}

	@ConfigItem(
		keyName = "varlamoreSouthEastMine",
		name = "Varlamore South East mine",
		description = "Show or hide this location.",
		position = 119,
		section = locationFilterSection
	)
	default boolean varlamoreSouthEastMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "varrockEastBank",
		name = "Varrock east bank",
		description = "Show or hide this location.",
		position = 120,
		section = locationFilterSection
	)
	default boolean varrockEastBank()
	{
		return true;
	}

	@ConfigItem(
		keyName = "westFaladorMine",
		name = "West Falador mine",
		description = "Show or hide this location.",
		position = 121,
		section = locationFilterSection
	)
	default boolean westFaladorMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "westLumbridgeSwampMine",
		name = "West Lumbridge Swamp mine",
		description = "Show or hide this location.",
		position = 122,
		section = locationFilterSection
	)
	default boolean westLumbridgeSwampMine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "westOfGrandTree",
		name = "West of Grand Tree",
		description = "Show or hide this location.",
		position = 123,
		section = locationFilterSection
	)
	default boolean westOfGrandTree()
	{
		return true;
	}

	@ConfigItem(
		keyName = "wildernessResourceArea",
		name = "Wilderness Resource Area",
		description = "Show or hide this location.",
		position = 124,
		section = locationFilterSection
	)
	default boolean wildernessResourceArea()
	{
		return true;
	}

	@ConfigItem(
		keyName = "yanilleBank",
		name = "Yanille bank",
		description = "Show or hide this location.",
		position = 125,
		section = locationFilterSection
	)
	default boolean yanilleBank()
	{
		return true;
	}
}
