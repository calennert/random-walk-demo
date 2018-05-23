package com.github.calennert.rwdemo;

import com.google.common.base.MoreObjects;

/**
 * Defines parameters for rendering a {@linkplain Grid} to a canvas.
 *
 * @author calennert (Chris Lennert)
 */
public final class CanvasParams {

    /**
     * The default border padding.
     */
    public static final int BORDER_PADDING = 10;

    /**
     * The default element arc size.
     */
    public static final int ELEMENT_ARC_SIZE = 8;

    /**
     * The default element gap.
     */
    public static final int ELEMENT_GAP = 6;

    /**
     * The default element size.
     */
    public static final int ELEMENT_SIZE = 50;

    private final int borderPadding;

    private final int elementArcSize;

    private final int elementGap;

    private final int elementSize;

    /**
     * Construct a new {@code CanvasParams} instance.
     */
    public CanvasParams() {
        this.borderPadding = BORDER_PADDING;
        this.elementSize = ELEMENT_SIZE;
        this.elementGap = ELEMENT_GAP;
        this.elementArcSize = ELEMENT_ARC_SIZE;
    }

    /**
     * Construct a new {@code CanvasParams} instance.
     *
     * @param borderPadding the border padding
     * @param elementSize the size of each grid element on the canvas
     * @param elementGap distance between elements
     * @param elementArcSize corner arc size of elements
     */
    public CanvasParams(int borderPadding, int elementSize, int elementGap, int elementArcSize) {
        this.borderPadding = borderPadding;
        this.elementSize = elementSize;
        this.elementGap = elementGap;
        this.elementArcSize = elementArcSize;
    }

    /**
     * Retrieves the border padding.
     *
     * @return the border padding
     */
    public int getBorderPadding() {
        return this.borderPadding;
    }

    /**
     * Determines the size of the canvas from the grid.
     *
     * @param grid the grid
     * @return a {@link Point} object where x is the calculated width and y is the
     *         calculated height
     */
    public Point getCanvasSize(Grid grid) {
        int w1 = (2 * this.borderPadding) + (grid.getWidth() * this.elementSize)
                + ((grid.getWidth() - 1) * this.elementGap);
        int h1 = (2 * this.borderPadding) + (grid.getHeight() * this.elementSize)
                + ((grid.getHeight() - 1) * this.elementGap);
        return new Point(w1, h1);
    }

    /**
     * Retrieves the arc size of each element's corners
     *
     * @return the arc size
     */
    public int getElementArcSize() {
        return this.elementArcSize;
    }

    /**
     * Retrieves the distance between each element.
     *
     * @return the element gap distance
     */
    public int getElementGap() {
        return this.elementGap;
    }

    /**
     * Retrieves the size of each canvas element.
     *
     * @return the element size
     */
    public int getElementSize() {
        return this.elementSize;
    }

    /**
     * Translates a {@link Grid} coordinate to the upper-left coordinate of the
     * corresponding canvas element.
     *
     * @param gridPoint the grid coordinate
     * @return the upper-left coordinate of the element on the canvas
     */
    public Point gridPointToCanvasElementUL(Point gridPoint) {
        int x1 = this.borderPadding + (gridPoint.getX() * this.elementSize) + (gridPoint.getX() * this.elementGap);
        int y1 = this.borderPadding + (gridPoint.getY() * this.elementSize) + (gridPoint.getY() * this.elementGap);
        return new Point(x1, y1);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("borderPadding", this.borderPadding)
                .add("elementGap", this.elementGap).add("elementSize", this.elementSize)
                .add("elementArcSize", this.elementArcSize).toString();
    }
}
