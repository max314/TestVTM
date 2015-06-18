package ru.max314.testvtm;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.oscim.android.MapActivity;
import org.oscim.android.MapScaleBar;
import org.oscim.android.MapView;
import org.oscim.backend.canvas.Bitmap;
import org.oscim.core.GeoPoint;
import org.oscim.layers.Layer;
import org.oscim.layers.marker.MarkerItem;
import org.oscim.layers.tile.buildings.BuildingLayer;
import org.oscim.layers.tile.vector.VectorTileLayer;
import org.oscim.layers.tile.vector.labeling.LabelLayer;
import org.oscim.map.Map;
import org.oscim.theme.VtmThemes;
import org.oscim.tiling.source.mapfile.MapFileTileSource;


import java.io.File;
import java.util.logging.Logger;

import static org.oscim.android.canvas.AndroidGraphics.drawableToBitmap;


public class MainActivity extends MapActivity implements LocationListener {
//    public static final Logger log = LoggerFactory.getLogger(TestActivity.class);

    MyLocationLayer layer;
    LocationManager locationManager;
    ImageView imageView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapView mMapView = (MapView) this.findViewById(R.id.mapView);

        imageView = (ImageView) this.findViewById(R.id.imageView);
        final Map map = this.map();

        imageView.setOnClickListener(new View.OnClickListener() {
            boolean flag = true;
            @Override
            public void onClick(View view) {
                if (flag)
                    mMap.setTheme(VtmThemes.DEFAULT);
                else
                    mMap.setTheme(VtmThemes.NEWTRON);
                flag = !flag;
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int y=19;
                return false;
            }
        });




        //VectorTileLayer baseLayer = map.setBaseMap(new OSciMap4TileSource());
        MapFileTileSource source = new MapFileTileSource();
        String mapfile = getMapFile().getAbsolutePath();
        source.setMapFile(mapfile);
        VectorTileLayer baseLayer = map.setBaseMap(source);
        map.layers().add(new BuildingLayer(map, baseLayer));
        map.layers().add(new LabelLayer(map, baseLayer));
        map.layers().add(new LayerScaleBar(mMapView));
        mMap.setTheme(VtmThemes.OSMARENDER);
        //map.setTheme(VtmThemes.DEFAULT);

        //mMap.setMapPosition(49.417, 8.673, 1 << 17);
        map.setMapPosition(47.20031256, 38.91554832, 1 << 16);

        Bitmap bitmap = drawableToBitmap(getResources(), R.drawable.jeep7);

        layer = MyLocationLayer.FactoryCreae(bitmap, map);
        layer.setEnabled(true);
        layer.addItem(new MarkerItem("Fooo", "", new GeoPoint(47.20031256, 38.91554832)));
        map.layers().add(layer);

        //	mMap.layers().add(new TileGridLayer(mMap));

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    public void onLocationChanged(Location location) {
//        layer.removeAllItems();
//        layer.addItem(new MarkerItem("Fooo", "", new GeoPoint(location.getLatitude(), location.getLongitude())));
        layer.setLocation(location);
        imageView.setRotation(-location.getBearing());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);

    }

    protected File getMapFileDirectory() {
        return Environment.getExternalStorageDirectory();
    }

    protected File getMapFile() {
        //File file = new File(getMapFileDirectory(), "/download/russia.map");
        File file = new File(getMapFileDirectory(), "ros2.map");
        //File file = new File(getMapFileDirectory(), "rostov+.map");
        return file;
    }


}