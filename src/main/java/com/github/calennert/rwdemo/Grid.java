package com.github.calennert.rwdemo;

import static com.github.calennert.rwdemo.RandomUtil.randomBoolean;
import static com.github.calennert.rwdemo.RandomUtil.randomFloat;

import java.util.Set;
import java.util.TreeSet;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

/**
 * Defines a two-dimensional grid that maintains a set of collected points.
 *
 * @author calennert (Chris Lennert)
 */
public final class Grid {
    /**
     * The default height of the grid.
     */
    public static final int DEFAULT_HEIGHT = 11;

    /**
     * The default width of the grid.
     */
    public static final int DEFAULT_WIDTH = 11;

    private final int height;

    private final Set<Point> points = new TreeSet<>();

    private final int width;

    /**
     * Construct a new {@code Grid} instance. Width will be set to
     * {@linkplain Grid#DEFAULT_WIDTH} and height will be set to
     * {@linkplain Grid#DEFAULT_HEIGHT}.
     */
    public Grid() {
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
    }

    /**
     * Construct a new {@code Grid} instance.
     *
     * @param width the width of the grid
     * @param height the height of the grid
     */
    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Adds a point to the set of grid points.
     *
     * @param p the point to add
     */
    public void addPoint(Point p) {
        synchronized (this.points) {
            this.points.add(p);
        }
    }

    /**
     * Returns a center point for the specified {@code Grid}. For each dimension size z,
     * if z modulo 2 is zero, then a random index at the center of that dimension is
     * determined. Otherwise, the center index is returned.
     *
     * @return a center point
     */
    public Point centerPoint() {
        int x;
        int y;

        if (this.getWidth() % 2 == 0) {
            x = (this.getWidth() / 2) - 1;
            x += randomBoolean() ? 1 : 0;
        }
        else {
            x = this.getWidth() / 2;
        }

        if (this.getHeight() % 2 == 0) {
            y = (this.getHeight() / 2) - 1;
            y += randomBoolean() ? 1 : 0;
        }
        else {
            y = this.getWidth() / 2;
        }

        return new Point(x, y);
    }

    /**
     * Retrieves the grid's height.
     *
     * @return the height of the grid
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Retrieves an immutable set of the collected grid points.
     *
     * @return an immutable set of the collected grid points
     */
    public Set<Point> getPoints() {
        synchronized (this.points) {
            return ImmutableSet.copyOf(this.points);
        }
    }

    /**
     * Retrieves the grid's width.
     *
     * @return the width of the grid
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Determines if any of the collected points is adjacent to the specified point.
     *
     * @param point the point to test
     * @return true, if the grid's set of collected points contains a point adjacent to
     *         the specified point; false, otherwise
     */
    public boolean hasAdjacentTo(Point point) {
        boolean result = false;

        outer: for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                Point p = new Point(point.getX() + x, point.getY() + y);
                if (this.points.contains(p)) {
                    result = true;
                    break outer;
                }
            }
        }

        return result;
    }

    /**
     * Tests whether a specified point is on the grid's perimeter.
     *
     * @param point the point to test
     * @return true, if the specified point is on the grid's perimeter
     */
    public boolean isPerimeterPoint(Point point) {
        if (point == null) {
            return false;
        }

        int x = point.getX();
        int y = point.getY();

        if (x == 0 || x == this.width - 1 || y == 0 || y == this.height - 1) {
            return true;
        }

        return false;
    }

    /**
     * Selects a random point on the grid's perimeter.
     *
     * @return a random perimeter point
     */
    public Point randomPerimeterPoint() {
        Point result = null;
        float selection = randomFloat();

        if (0f <= selection && selection <= 0.25f) {
            result = new Point(randomX(), 0); // top
        }
        else if (0.25f < selection && selection <= 0.5f) {
            result = new Point(this.width - 1, randomY()); // left
        }
        else if (0.5f < selection && selection <= 0.75f) {
            result = new Point(randomX(), this.height - 1); // bottom
        }
        else {
            result = new Point(0, randomY()); // right
        }

        return result;
    }

    private int randomX() {
        float r = randomFloat();
        r *= this.width;
        return Math.round(r);
    }

    private int randomY() {
        float r = randomFloat();
        r *= this.height;
        return Math.round(r);
    }

    /**
     * Clears the grid's set of collected points.
     */
    public void reset() {
        this.points.clear();
    }

    /**
     * Returns the number of collected points.
     *
     * @return the number of collected points
     */
    public int size() {
        return this.points.size();
    }

    /**
     * Returns a caption-friendly string for the grid in "WxH" format.
     * 
     * @return a caption-friendly string
     */
    public String toCaptionString() {
        return new StringBuilder().append(this.width).append("x").append(this.height).toString();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("width", this.width).add("height", this.height)
                .add("points", this.points).toString();
    }
}