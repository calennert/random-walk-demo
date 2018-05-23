package com.github.calennert.rwdemo;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class PointUtilTest {

    @Test
    public void randomPointTest() {
        Grid grid = new Grid(100, 100);
        Point point = grid.randomPerimeterPoint();

        for (int i = 0; i < 10000; i++) {
            point = PointUtil.randomNeighbor(point, grid);

            assertTrue(point.getX() >= 0);
            assertTrue(point.getX() < grid.getWidth());
            assertTrue(point.getY() >= 0);
            assertTrue(point.getY() < grid.getHeight());
        }
    }
}
