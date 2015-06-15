package ru.max314.testvtm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.oscim.android.MapActivity;
import org.oscim.android.MapScaleBar;
import org.oscim.android.MapView;
import org.oscim.layers.tile.buildings.BuildingLayer;
import org.oscim.layers.tile.vector.VectorTileLayer;
import org.oscim.layers.tile.vector.labeling.LabelLayer;
import org.oscim.map.Map;
import org.oscim.theme.VtmThemes;
import org.oscim.tiling.source.mapfile.MapFileTileSource;


import java.util.logging.Logger;


public class MainActivity extends MapActivity {
//    public static final Logger log = LoggerFactory.getLogger(TestActivity.class);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapView mMapView = (MapView) this.findViewById(R.id.mapView);
        ImageView imageView = (ImageView) this.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    int y= 10;
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int y=19;
                return false;
            }
        });


        Map map = this.map();

        //VectorTileLayer baseLayer = map.setBaseMap(new OSciMap4TileSource());
        MapFileTileSource source = new MapFileTileSource();
        source.setMapFile("/mnt/sdcard/download/ros2.map");
        VectorTileLayer baseLayer = map.setBaseMap(source);
        map.layers().add(new BuildingLayer(map, baseLayer));
        map.layers().add(new LabelLayer(map, baseLayer));
        map.layers().add(new LayerScaleBar(mMapView));
        mMap.setTheme(VtmThemes.NEWTRON);
        //map.setTheme(VtmThemes.DEFAULT);

        //mMap.setMapPosition(49.417, 8.673, 1 << 17);
        map.setMapPosition(47.20031256, 38.91554832, 1 << 16);

        //	mMap.layers().add(new TileGridLayer(mMap));
    }
}