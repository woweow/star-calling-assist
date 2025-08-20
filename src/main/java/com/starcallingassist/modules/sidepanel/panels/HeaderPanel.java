package com.starcallingassist.modules.sidepanel.panels;

import com.starcallingassist.constants.PluginColors;
import com.starcallingassist.modules.sidepanel.decorators.HeaderPanelDecorator;
import com.starcallingassist.modules.sidepanel.elements.Link;
import com.starcallingassist.modules.sidepanel.enums.OrderBy;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.components.TitleCaseListCellRenderer;

@Slf4j
public class HeaderPanel extends JPanel
{
	private final HeaderPanelDecorator decorator;

	private String errorMessage = "";

	private final JPanel sortingPanel = new JPanel(new BorderLayout());
	private final JComboBox<String> dropdown;
	private final JButton filterToggleButton;
	private boolean isFilterViewActive = false;

	public HeaderPanel(HeaderPanelDecorator decorator)
	{
		this.decorator = decorator;

		setLayout(new BorderLayout());
		setBackground(PluginColors.PRIMARY_BACKGROUND);
		setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createMatteBorder(0, 0, 2, 0, PluginColors.HEADER_BOTTOM_BORDER),
			BorderFactory.createEmptyBorder(3, 5, 1, 5)
		));

		dropdown = createSortingDropdown();
		filterToggleButton = createFilterToggleButton();

		sortingPanel.setOpaque(false);
		sortingPanel.add(dropdown, BorderLayout.CENTER);
		sortingPanel.setBorder(new EmptyBorder(0, 0, 3, 0));
	}

	private JComboBox<String> createSortingDropdown()
	{
		JComboBox<String> dropdown = new JComboBox();
		dropdown.setBackground(PluginColors.DROPDOWN_BACKGROUND);
		dropdown.setForeground(PluginColors.DROPDOWN_ARROW);
		dropdown.setFocusable(false);
		dropdown.setRenderer(new TitleCaseListCellRenderer());
		dropdown.setToolTipText("Change the sorting order of the star list");
		dropdown.addItemListener(event ->
		{
			if (event.getStateChange() == ItemEvent.SELECTED)
			{
				decorator.onSortingChanged(OrderBy.fromString((String) event.getItem()));
			}
		});

		for (OrderBy orderBy : OrderBy.values())
		{
			dropdown.addItem(orderBy.getName());
		}

		return dropdown;
	}

	private JButton createFilterToggleButton()
	{
		JButton button = new JButton();
		button.setToolTipText("Toggle location filters");
		
		// Set button size to ensure it's clickable
		button.setPreferredSize(new Dimension(25, 25));
		button.setMinimumSize(new Dimension(25, 25));
		button.setMaximumSize(new Dimension(25, 25));
		
		// Set initial icon
		button.setText(isFilterViewActive ? "☰" : "⚙");
		
		// Make the button look clickable but minimal
		button.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
		button.setFocusPainted(false);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		
		// Enable the button and make it respond to clicks
		button.setEnabled(true);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		// Use ActionListener instead of MouseListener for better button behavior
		button.addActionListener(e -> {
			// Let the decorator handle the state change
			decorator.onFilterToggleClicked();
		});

		return button;
	}

	private void updateFilterButtonIcon()
	{
		// Using placeholder text icons - can be replaced with actual icons later
		if (filterToggleButton != null)
		{
			filterToggleButton.setText(isFilterViewActive ? "☰" : "⚙");
		}
	}

	public void startUp()
	{
		dropdown.setSelectedItem(decorator.getOrderBy().getName());

		rebuild();
	}

	public void rebuild()
	{
		removeAll();
		add(createMainContentPanel(), BorderLayout.NORTH);

		// Only show sorting dropdown in main star list view, not in filter view
		if (decorator.hasAuthorization() && !isFilterViewActive)
		{
			add(sortingPanel, BorderLayout.CENTER);
		}
	}

	private JPanel createMainContentPanel()
	{
		JPanel mainContent = new JPanel(new BorderLayout());
		mainContent.setOpaque(false);

		mainContent.add(createDiscordAdPanel(), BorderLayout.NORTH);
		mainContent.add(createInfoPanel(), BorderLayout.CENTER);

		return mainContent;
	}

	private JPanel createDiscordAdPanel()
	{
		JPanel adPanel = new JPanel(new BorderLayout());
		adPanel.setOpaque(false);
		adPanel.setBorder(new EmptyBorder(0, 0, 5, 0));

		adPanel.add(new Link("https://discord.gg/starminers", "Join the Star Miners Discord!").center(), BorderLayout.CENTER);
		
		if (decorator.hasAuthorization())
		{
			// Create a container for the button with proper padding
			JPanel buttonContainer = new JPanel(new BorderLayout());
			buttonContainer.setOpaque(false);
			buttonContainer.setBorder(new EmptyBorder(0, 5, 0, 0)); // Add left padding
			buttonContainer.add(filterToggleButton, BorderLayout.CENTER);
			
			adPanel.add(buttonContainer, BorderLayout.EAST);
		}

		return adPanel;
	}

	private JPanel createInfoPanel()
	{
		JPanel infoPanel = new JPanel(new BorderLayout());
		infoPanel.setOpaque(false);

		if (decorator.hasAuthorization() && !errorMessage.isEmpty())
		{
			JLabel errorInfo = new JLabel("<html>Error when fetching list of stars: <br><br>" + errorMessage + "</html>");
			errorInfo.setForeground(PluginColors.DANGEROUS_AREA);
			errorInfo.setBorder(new EmptyBorder(0, 0, 5, 0));

			infoPanel.add(errorInfo, BorderLayout.CENTER);
		}

		return infoPanel;
	}


	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
		rebuild();
	}

	public void setFilterViewActive(boolean active)
	{
		this.isFilterViewActive = active;
		updateFilterButtonIcon();
		rebuild(); // Rebuild to show/hide sorting dropdown
	}

	public boolean isFilterViewActive()
	{
		return isFilterViewActive;
	}
}
