package ru.max314.testvtm;

import android.location.Location;

import org.oscim.backend.canvas.Bitmap;
import org.oscim.core.GeoPoint;
import org.oscim.layers.marker.ItemizedLayer;
import org.oscim.layers.marker.MarkerItem;
import org.oscim.layers.marker.MarkerSymbol;
import org.oscim.map.Map;

import static org.oscim.android.canvas.AndroidGraphics.drawableToBitmap;

/**
 * Created by max on 17.06.2015.
 */
public class MyLocationLayer extends ItemizedLayer<MarkerItem>
{

    public MyLocationLayer(Map map, MarkerSymbol defaulMarker) {
         super(map, defaulMarker);
    }

    public static MyLocationLayer FactoryCreae(Bitmap bitmap,Map map) {
        MarkerSymbol symbol = new MarkerSymbol(bitmap, MarkerItem.HotspotPlace.BOTTOM_CENTER);
        MyLocationLayer layer = new MyLocationLayer(map, symbol);
        return layer;
    }

    public void setLocation(Location location){
        this.removeAllItems();
        addItem(new MarkerItem("Fooo", "", new GeoPoint(location.getLatitude(), location.getLongitude())));
        this.map().render();
        //map().getMapPosition().setPosition();
        map().viewport().setRotation(-location.getBearing());
        map().animator().animateTo(new GeoPoint(location.getLatitude(), location.getLongitude()));
        //this.map().setMapPosition(location.getLatitude(),location.getLongitude(),scale);

    }
}
