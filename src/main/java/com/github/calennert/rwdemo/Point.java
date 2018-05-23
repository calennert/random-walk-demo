package com.github.calennert.rwdemo;

import java.util.Objects;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ComparisonChain;

/**
 * Defines a two-dimensional coordinate.
 *
 * @author calennert (Chris Lennert)
 */
public final class Point implements Comparable<Point> {
    private final int x;

    private final int y;

    /**
     * Construct a new {@code Point} instance.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point p) {
        return ComparisonChain.start().compare(this.x, p.x).compare(this.y, p.y).result();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Point)) {
            return false;
        }
        Point p = (Point) obj;
        return Objects.equals(this.x, p.x) && Objects.equals(this.y, p.y);
    }

    /**
     * Retrieves the x coordinate.
     *
     * @return the x coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Retrieves the y coordinate.
     *
     * @return the y coordinate
     */
    public int getY() {
        return this.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("x", this.x).add("y", this.y).toString();
    }
}