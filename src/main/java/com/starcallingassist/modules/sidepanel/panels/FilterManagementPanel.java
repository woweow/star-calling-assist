package com.starcallingassist.modules.sidepanel.panels;

import com.starcallingassist.constants.PluginColors;
import com.starcallingassist.enums.Region;
import com.starcallingassist.modules.sidepanel.LocationPreferenceManager;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class FilterManagementPanel extends JPanel
{
	private final LocationPreferenceManager preferenceManager;
	private final JPanel contentPanel;
	private final List<RegionGroupPanel> regionGroups;
	private final Runnable onPreferencesChanged;

	public FilterManagementPanel(LocationPreferenceManager preferenceManager, Runnable onPreferencesChanged)
	{
		this.preferenceManager = preferenceManager;
		this.onPreferencesChanged = onPreferencesChanged;
		this.regionGroups = new ArrayList<>();

		setLayout(new BorderLayout());
		setBackground(PluginColors.SCROLLBOX_BACKGROUND);

		// Content panel for region groups
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBorder(new EmptyBorder(4, 3, 4, 4));
		contentPanel.setBackground(PluginColors.SCROLLBOX_BACKGROUND);

		// Wrapper panel for proper layout
		JPanel wrapper = new JPanel(new BorderLayout());
		wrapper.setBackground(PluginColors.SCROLLBOX_BACKGROUND);
		wrapper.add(contentPanel, BorderLayout.NORTH);

		// Scroll pane
		JScrollPane scrollPane = new JScrollPane(wrapper);
		scrollPane.setBackground(PluginColors.SCROLLBOX_BACKGROUND);
		scrollPane.setBorder(null);
		add(scrollPane, BorderLayout.CENTER);

		populateRegions();
	}

	private void populateRegions()
	{
		// Create region groups for all regions except UNKNOWN (which we'll put at the end)
		List<Region> orderedRegions = Arrays.asList(
			Region.ASGARNIA,
			Region.MISTHALIN,
			Region.KANDARIN,
			Region.DESERT,
			Region.MORYTANIA,
			Region.FREMMENIK,
			Region.KOUREND,
			Region.KEBOS,
			Region.KARAMJA,
			Region.GNOME,
			Region.TIRANNWN,
			Region.VARLAMORE,
			Region.FELDIP,
			Region.FOSSIL,
			Region.WILDERNESS,
			Region.UNKNOWN
		);

		for (Region region : orderedRegions)
		{
			RegionGroupPanel regionGroup = new RegionGroupPanel(region, preferenceManager, onPreferencesChanged);
			regionGroups.add(regionGroup);
			contentPanel.add(regionGroup);
			contentPanel.add(Box.createVerticalStrut(2));
		}
	}

	public void refresh()
	{
		// Refresh all region groups to update their states
		for (RegionGroupPanel regionGroup : regionGroups)
		{
			regionGroup.repaint();
		}
		revalidate();
		repaint();
	}
}