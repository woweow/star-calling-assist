package com.starcallingassist.modules.sidepanel.panels;

import com.starcallingassist.constants.PluginColors;
import com.starcallingassist.enums.Region;
import com.starcallingassist.modules.sidepanel.LocationPreferenceManager;
import com.starcallingassist.objects.StarLocation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class RegionGroupPanel extends JPanel
{
	private final Region region;
	private final LocationPreferenceManager preferenceManager;
	private final JPanel headerPanel;
	private final JPanel contentPanel;
	private final JButton toggleButton;
	private final JButton eyeButton;
	private final List<LocationEntryPanel> locationEntries;
	private final Runnable onPreferencesChanged;
	private boolean isExpanded = false;

	public RegionGroupPanel(Region region, LocationPreferenceManager preferenceManager, Runnable onPreferencesChanged)
	{
		this.region = region;
		this.preferenceManager = preferenceManager;
		this.onPreferencesChanged = onPreferencesChanged;
		this.locationEntries = new ArrayList<>();

		setLayout(new BorderLayout());
		setOpaque(true);
		setBackground(PluginColors.SCROLLBOX_BACKGROUND);
		setBorder(new EmptyBorder(1, 0, 1, 0));

		// Create toggle and eye buttons first
		toggleButton = createToggleButton();
		eyeButton = createEyeButton();

		// Header panel with region name, toggle, and eye button
		headerPanel = createHeaderPanel();
		add(headerPanel, BorderLayout.NORTH);

		// Content panel for location entries
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setOpaque(false);
		contentPanel.setVisible(isExpanded);
		add(contentPanel, BorderLayout.CENTER);

		populateLocations();
		updateEyeButtonState();
	}

	private JPanel createHeaderPanel()
	{
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(true);
		panel.setBackground(PluginColors.PRIMARY_BACKGROUND);
		panel.setBorder(new EmptyBorder(5, 10, 5, 5));

		// Region name with toggle
		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.setOpaque(false);
		
		leftPanel.add(toggleButton, BorderLayout.WEST);
		
		JLabel regionLabel = new JLabel(" " + getRegionDisplayName());
		regionLabel.setForeground(PluginColors.STAR_LIST_GROUP_LABEL);
		leftPanel.add(regionLabel, BorderLayout.CENTER);
		
		panel.add(leftPanel, BorderLayout.CENTER);

		// Eye button on the right
		panel.add(eyeButton, BorderLayout.EAST);

		return panel;
	}

	private JButton createToggleButton()
	{
		JButton button = new JButton(isExpanded ? "▼" : "▶");
		button.setBorder(null);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setPreferredSize(new Dimension(15, 15));
		button.setForeground(PluginColors.STAR_LIST_GROUP_LABEL);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));

		button.addActionListener(e -> toggleExpanded());

		return button;
	}

	private JButton createEyeButton()
	{
		JButton button = new JButton();
		button.setBorder(null);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setPreferredSize(new Dimension(20, 20));
		button.setToolTipText("Toggle visibility for all locations in this region");
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));

		button.addActionListener(e -> toggleAllLocationsVisibility());

		return button;
	}

	private void toggleExpanded()
	{
		isExpanded = !isExpanded;
		contentPanel.setVisible(isExpanded);
		
		// Update toggle button text
		toggleButton.setText(isExpanded ? "▼" : "▶");
		
		revalidate();
		repaint();
	}

	private void toggleAllLocationsVisibility()
	{
		Set<String> locationNames = getLocationNamesInRegion();
		
		// Check if any locations are currently visible (not hidden)
		boolean anyVisible = locationNames.stream()
			.anyMatch(name -> !preferenceManager.isLocationHidden(name));
		
		// If any are visible, hide all. If all are hidden, show all.
		preferenceManager.setAllLocationsInRegionHidden(locationNames, anyVisible);
		
		updateEyeButtonState();
		updateLocationEntries();
		
		if (onPreferencesChanged != null)
		{
			onPreferencesChanged.run();
		}
	}

	private void updateEyeButtonState()
	{
		Set<String> locationNames = getLocationNamesInRegion();
		
		if (locationNames.isEmpty())
		{
			eyeButton.setText("👁");
			eyeButton.setForeground(PluginColors.STAR_LIST_GROUP_LABEL);
			return;
		}
		
		boolean allHidden = locationNames.stream()
			.allMatch(name -> preferenceManager.isLocationHidden(name));
		boolean anyHidden = locationNames.stream()
			.anyMatch(name -> preferenceManager.isLocationHidden(name));
		
		if (allHidden)
		{
			eyeButton.setText("👁‍🗨");
			eyeButton.setForeground(Color.GRAY);
		}
		else if (anyHidden)
		{
			eyeButton.setText("👁");
			eyeButton.setForeground(Color.ORANGE);
		}
		else
		{
			eyeButton.setText("👁");
			eyeButton.setForeground(PluginColors.STAR_LIST_GROUP_LABEL);
		}
	}

	private void populateLocations()
	{
		Set<String> locationNames = getLocationNamesInRegion();
		
		for (String locationName : locationNames.stream().sorted().collect(Collectors.toList()))
		{
			LocationEntryPanel entry = new LocationEntryPanel(locationName, preferenceManager, () -> {
				updateEyeButtonState();
				if (onPreferencesChanged != null)
				{
					onPreferencesChanged.run();
				}
			});
			locationEntries.add(entry);
			contentPanel.add(entry);
		}
	}

	private void updateLocationEntries()
	{
		for (LocationEntryPanel entry : locationEntries)
		{
			entry.repaint();
		}
	}

	private Set<String> getLocationNamesInRegion()
	{
		// Since StarLocationDetails is private, we need to create StarLocation instances
		// and use their public methods to get region and name information
		return StarLocation.LOCATIONS.keySet().stream()
			.map(point -> new StarLocation(point))
			.filter(starLocation -> starLocation.getRegion() == region)
			.map(StarLocation::getName)
			.collect(Collectors.toSet());
	}

	private String getRegionDisplayName()
	{
		// Convert enum name to display name
		switch (region)
		{
			case ASGARNIA:
				return "Asgarnia";
			case DESERT:
				return "Desert";
			case FELDIP:
				return "Feldip Hills/Isle Of Souls";
			case FOSSIL:
				return "Fossil Island/Mos Le Harmless";
			case FREMMENIK:
				return "Fremmenik/Lunar Isle";
			case GNOME:
				return "Piscatoris/Gnome Stronghold";
			case KANDARIN:
				return "Kandarin";
			case KARAMJA:
				return "Crandor/Karamja";
			case KEBOS:
				return "Kebos Lowlands";
			case KOUREND:
				return "Kourend";
			case MISTHALIN:
				return "Misthalin";
			case MORYTANIA:
				return "Morytania";
			case TIRANNWN:
				return "Tirannwn";
			case VARLAMORE:
				return "Varlamore";
			case WILDERNESS:
				return "Wilderness";
			case UNKNOWN:
				return "Unknown / Unconfirmed";
			default:
				return region.name();
		}
	}
}