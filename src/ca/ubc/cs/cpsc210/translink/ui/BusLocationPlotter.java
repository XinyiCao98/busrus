package ca.ubc.cs.cpsc210.translink.ui;

import android.content.Context;
import ca.ubc.cs.cpsc210.translink.R;
import ca.ubc.cs.cpsc210.translink.model.Bus;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

// A plotter for bus locations
public class BusLocationPlotter extends MapViewOverlay {
    /**
     * overlay used to display bus locations
     */
    private ItemizedIconOverlay<OverlayItem> busLocationsOverlay;

    /**
     * Constructor
     *
     * @param context the application context
     * @param mapView the map view
     */
    public BusLocationPlotter(Context context, MapView mapView) {
        super(context, mapView);
        busLocationsOverlay = createBusLocnOverlay();
    }

    public ItemizedIconOverlay<OverlayItem> getBusLocationsOverlay() {
        return busLocationsOverlay;
    }

    /**
     * Plot buses serving selected stop
     */
    public void plotBuses() {
        busLocationsOverlay.removeAllItems();
        if (StopManager.getInstance().getSelected() != null) {
            List<Bus> buses = StopManager.getInstance().getSelected().getBuses();
            for (Bus next : buses) {
                GeoPoint point = new GeoPoint(next.getLatLon().getLatitude(), next.getLatLon().getLongitude());
                OverlayItem bus = new OverlayItem(next.getRoute().getName(), next.getDestination(), point);
                busLocationsOverlay.addItem(bus);
            }
        }
        mapView.invalidate();
    }

    /**
     * Create the overlay for bus markers.
     */
    private ItemizedIconOverlay<OverlayItem> createBusLocnOverlay() {
        ResourceProxy rp = new DefaultResourceProxyImpl(context);

        return new ItemizedIconOverlay<OverlayItem>(
                new ArrayList<OverlayItem>(),
                context.getResources().getDrawable(R.drawable.bus),
                null, rp);
    }
}
