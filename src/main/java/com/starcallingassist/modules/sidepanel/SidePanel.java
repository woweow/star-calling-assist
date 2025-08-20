package com.starcallingassist.modules.sidepanel;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.starcallingassist.StarCallingAssistConfig;
import com.starcallingassist.modules.sidepanel.LocationPreferenceManager;
import com.starcallingassist.constants.PluginColors;
import com.starcallingassist.events.ShowWorldPointOnWorldMapRequested;
import com.starcallingassist.events.WorldHopRequest;
import com.starcallingassist.modules.sidepanel.decorators.HeaderPanelDecorator;
import com.starcallingassist.modules.sidepanel.decorators.MasterPanelDecorator;
import com.starcallingassist.modules.sidepanel.decorators.StarListGroupEntryDecorator;
import com.starcallingassist.modules.sidepanel.enums.OrderBy;
import com.starcallingassist.modules.sidepanel.enums.TotalLevelType;
import com.starcallingassist.modules.sidepanel.panels.FilterManagementPanel;
import com.starcallingassist.modules.sidepanel.panels.HeaderPanel;
import com.starcallingassist.modules.sidepanel.panels.StarListPanel;
import com.starcallingassist.objects.Star;
import com.starcallingassist.objects.StarLocation;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.List;
import javax.annotation.Nonnull;
import javax.swing.JPanel;
import lombok.Setter;
import net.runelite.client.config.ConfigManager;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.Activatable;
import net.runelite.client.ui.PluginPanel;
import net.runelite.http.api.worlds.World;

@Slf4j
public class SidePanel extends PluginPanel implements Activatable
{
	@Setter
	protected Injector injector;

	@Inject
	private StarCallingAssistConfig config;

	@Inject
	private ConfigManager configManager;

	@Inject
	private LocationPreferenceManager locationPreferenceManager;

	private final MasterPanelDecorator decorator;

	@Setter
	private int currentWorld = 0;

	private final HeaderPanel headerPanel;

	private final StarListPanel starListPanel;

	private FilterManagementPanel filterManagementPanel;

	private final JPanel contentContainer;
	private final CardLayout contentCardLayout;
	private static final String STAR_LIST_VIEW = "STAR_LIST";
	private static final String FILTER_VIEW = "FILTER_MANAGEMENT";

	public SidePanel(MasterPanelDecorator decorator)
	{
		super(false);
		this.decorator = decorator;
		setLayout(new BorderLayout());
		setBackground(PluginColors.SCROLLBOX_BACKGROUND);

		starListPanel = new StarListPanel(new StarListGroupEntryDecorator()
		{

			@Override
			public boolean hasAuthorization()
			{
				return SidePanel.this.hasAuthorization();
			}

			@Override
			public boolean shouldEstimateTier()
			{
				return config != null ? config.estimateTier() : true;
			}

			@Override
			public boolean showFreeToPlayWorlds()
			{
				return config != null ? config.showF2P() : true;
			}

			@Override
			public boolean showMembersWorlds()
			{
				return config != null ? config.showMembers() : true;
			}

			@Override
			public boolean showPvPWorlds()
			{
				return config != null ? config.showPvp() : false;
			}

			@Override
			public boolean showHighRiskWorlds()
			{
				return config != null ? config.showHighRisk() : false;
			}

			@Override
			public TotalLevelType maxTotalLevel()
			{
				return config != null ? config.totalLevelType() : TotalLevelType.TOTAL_2200;
			}

			@Override
			public int minTier()
			{
				return config != null ? config.minTier() : 1;
			}

			@Override
			public int maxTier()
			{
				return config != null ? config.maxTier() : 9;
			}

			@Override
			public int minDeadTime()
			{
				return config != null ? config.minDeadTime() : -5;
			}

			@Override
			public boolean isLocationHidden(String locationName)
			{
				return locationPreferenceManager != null ? locationPreferenceManager.isLocationHidden(locationName) : false;
			}

			@Override
			public boolean isLocationFavorite(String locationName)
			{
				return locationPreferenceManager != null ? locationPreferenceManager.isLocationFavorite(locationName) : false;
			}

			@Override
			public Boolean showWorldTypeColumn()
			{
				return config != null ? config.showWorldType() : true;
			}

			@Override
			public Boolean showTierColumn()
			{
				return config != null ? config.showTier() : true;
			}

			@Override
			public Boolean showDeadTimeColumn()
			{
				return config != null ? config.showDeadTime() : true;
			}

			@Override
			public Boolean showFoundByColumn()
			{
				return config != null ? config.showFoundBy() : true;
			}

			@Override
			public List<StarLocation> getCurrentPlayerLocations()
			{
				return decorator.getCurrentPlayerRegions();
			}

			@Override
			public int getCurrentWorldId()
			{
				return currentWorld;
			}

			@Override
			public void onWorldHopRequest(WorldHopRequest request)
			{
				decorator.onWorldHopRequest(request);
			}

			@Override
			public void onShowWorldPointOnWorldMapRequested(ShowWorldPointOnWorldMapRequested showWorldPointOnWorldMapRequested)
			{
				decorator.onShowWorldPointOnWorldMapRequested(showWorldPointOnWorldMapRequested);
			}
		});

		// Create CardLayout container for switching views (filter panel will be added later)
		contentCardLayout = new CardLayout();
		contentContainer = new JPanel(contentCardLayout);
		contentContainer.add(starListPanel, STAR_LIST_VIEW);

		headerPanel = new HeaderPanel(new HeaderPanelDecorator()
		{
			@Override
			public boolean hasAuthorization()
			{
				return SidePanel.this.hasAuthorization();
			}

			@Override
			public OrderBy getOrderBy()
			{
				return config != null ? config.orderBy() : OrderBy.LOCATION;
			}

			@Override
			public void onSortingChanged(OrderBy orderBy)
			{
				starListPanel.setOrderByColumn(orderBy);
				starListPanel.rebuild();
			}

			@Override
			public void onFilterToggleClicked()
			{
				toggleView();
			}
		});

		add(headerPanel, BorderLayout.NORTH);
		add(contentContainer, BorderLayout.CENTER);
	}

	private boolean hasAuthorization()
	{
		return config != null && !config.getAuthorization().isEmpty();
	}

	private void toggleView()
	{
		// Ensure filter panel is created before toggling
		if (filterManagementPanel == null)
		{
			return;
		}

		// Get current view state from header (before any changes)
		boolean isCurrentlyStarList = isShowingStarListView();
		
		if (isCurrentlyStarList)
		{
			// Switch to filter view
			headerPanel.setFilterViewActive(true);
			contentCardLayout.show(contentContainer, FILTER_VIEW);
			filterManagementPanel.refresh();
		}
		else
		{
			// Switch to star list view  
			headerPanel.setFilterViewActive(false);
			contentCardLayout.show(contentContainer, STAR_LIST_VIEW);
			starListPanel.rebuild();
		}
	}

	private boolean isShowingStarListView()
	{
		// Simple way to check current view - we can track this with a field if needed
		// For now, we'll rely on the header panel state
		return !headerPanel.isFilterViewActive();
	}

	public void startUp()
	{
		// Create filter management panel now that dependency injection is complete
		if (filterManagementPanel == null)
		{
			filterManagementPanel = new FilterManagementPanel(locationPreferenceManager, () -> {
				// Callback when preferences change - rebuild star list
				starListPanel.rebuild();
			});
			contentContainer.add(filterManagementPanel, FILTER_VIEW);
		}

		headerPanel.startUp();
		starListPanel.startUp();
	}

	public void shutDown()
	{
		//
	}

	public void onStarUpdate(@Nonnull Star star, @Nonnull World world, long updatedAt)
	{
		starListPanel.onStarUpdate(star, world, updatedAt);
	}

	public void setErrorMessage(String errorMessage)
	{
		headerPanel.setErrorMessage(errorMessage);
		rebuild();
	}

	public void rebuild()
	{
		headerPanel.rebuild();
		starListPanel.rebuild();

		revalidate();
		repaint();
	}

	@Override
	public void onActivate()
	{
		decorator.onSidePanelVisibilityChanged(true);
	}

	@Override
	public void onDeactivate()
	{
		decorator.onSidePanelVisibilityChanged(false);
	}
}