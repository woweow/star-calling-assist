package com.starcallingassist.modules.sidepanel.panels;

import com.starcallingassist.constants.PluginColors;
import com.starcallingassist.modules.sidepanel.LocationPreferenceManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class LocationEntryPanel extends JPanel
{
	private final String locationName;
	private final LocationPreferenceManager preferenceManager;
	private final JButton eyeButton;
	private final JButton starButton;
	private final Runnable onPreferencesChanged;

	public LocationEntryPanel(String locationName, LocationPreferenceManager preferenceManager, Runnable onPreferencesChanged)
	{
		this.locationName = locationName;
		this.preferenceManager = preferenceManager;
		this.onPreferencesChanged = onPreferencesChanged;

		setLayout(new BorderLayout());
		setOpaque(true);
		setBackground(PluginColors.SCROLLBOX_BACKGROUND);
		setBorder(new EmptyBorder(2, 10, 2, 5));

		// Location name label
		JLabel nameLabel = new JLabel(locationName);
		nameLabel.setForeground(PluginColors.STAR_LIST_GROUP_LABEL);
		add(nameLabel, BorderLayout.CENTER);

		// Button panel for eye and star icons
		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.setOpaque(false);
		
		eyeButton = createEyeButton();
		starButton = createStarButton();
		
		buttonPanel.add(eyeButton, BorderLayout.WEST);
		buttonPanel.add(starButton, BorderLayout.EAST);
		
		add(buttonPanel, BorderLayout.EAST);

		updateButtonStates();
	}

	private JButton createEyeButton()
	{
		JButton button = new JButton();
		button.setBorder(null);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setPreferredSize(new Dimension(20, 20));
		button.setToolTipText("Toggle visibility");

		button.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				preferenceManager.toggleLocationHidden(locationName);
				updateButtonStates();
				if (onPreferencesChanged != null)
				{
					onPreferencesChanged.run();
				}
			}
		});

		return button;
	}

	private JButton createStarButton()
	{
		JButton button = new JButton();
		button.setBorder(null);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setPreferredSize(new Dimension(20, 20));
		button.setToolTipText("Toggle favorite");

		button.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				preferenceManager.toggleLocationFavorite(locationName);
				updateButtonStates();
				if (onPreferencesChanged != null)
				{
					onPreferencesChanged.run();
				}
			}
		});

		return button;
	}

	private void updateButtonStates()
	{
		boolean isHidden = preferenceManager.isLocationHidden(locationName);
		boolean isFavorite = preferenceManager.isLocationFavorite(locationName);

		// Eye button: visible = "👁", hidden = "👁‍🗨" (or similar placeholder)
		eyeButton.setText(isHidden ? "👁‍🗨" : "👁");
		eyeButton.setForeground(isHidden ? Color.GRAY : PluginColors.STAR_LIST_GROUP_LABEL);

		// Star button: favorite = "★", not favorite = "☆"
		starButton.setText(isFavorite ? "★" : "☆");
		starButton.setForeground(isFavorite ? Color.YELLOW : PluginColors.STAR_LIST_GROUP_LABEL);
	}

	public String getLocationName()
	{
		return locationName;
	}
}