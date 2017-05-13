package com.code4piter.blueskythinking.utils;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class GeoTools {

    public static int distination(double latitude1, double longitude1, double latitude2, double longitude2) {
        GeometryFactory gf = new GeometryFactory();
        Point p = gf.createPoint(new Coordinate(latitude1, longitude1));
        Point p2 = gf.createPoint(new Coordinate(latitude2, longitude2));
        return (int)(p.distance(p2)*100000);
    }
}
