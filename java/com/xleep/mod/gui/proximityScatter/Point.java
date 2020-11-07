package com.xleep.mod.gui.proximityScatter;

import java.util.Date;

//this class stores an individual point
public class Point {
    private long x;
    private long y;
    private Date time;

    public Point(long x, long y, Date time) {
        this.x = x;
        this.y = y;
        this.time = time;
    }
}
