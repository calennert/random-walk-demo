package com.github.calennert.rwdemo;

/**
 * Provides {@code Point}-related helper methods.
 *
 * @author calennert (Chris Lennert)
 */
public final class PointUtil {
    private static final float THIRD = 0.33333333333333333333333333f;

    /**
     * Selects a random neighbor of the point in the grid.
     *
     * @param point the point to use for neighbor selection
     * @param grid the grid in which to select a neighbor
     * @return a neighboring point
     */
    public static Point randomNeighbor(Point point, Grid grid) {
        int newX = point.getX() + randomOffset();
        int newY = point.getY() + randomOffset();

        if (newX < 0) {
            newX += grid.getWidth();
        }
        else if (newX >= grid.getWidth()) {
            newX -= grid.getWidth();
        }

        if (newY < 0) {
            newY += grid.getHeight();
        }
        else if (newY >= grid.getHeight()) {
            newY -= grid.getHeight();
        }

        return new Point(newX, newY);
    }

    /**
     * Selects a random offset from -1 to 1.
     *
     * @return random selection among -1, 0, or 1
     */
    private static int randomOffset() {
        float i = RandomUtil.randomFloat();

        int result = 0;
        if (0 <= i && i <= THIRD) {
            result = -1;
        }
        else if (THIRD < i && i <= (2f * THIRD)) {
            result = 0;
        }
        else {
            result = 1;
        }
        return result;
    }

    private PointUtil() {
        // not instantiable
    }
}
