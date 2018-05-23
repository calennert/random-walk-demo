package com.github.calennert.rwdemo;

/**
 * Provides predefined grids and related canvas parameters.
 *
 * @author calennert (Chris Lennert)
 */
public enum GridSize {
    /**
     * The default grid size.
     */
    Default(new Grid(), new CanvasParams()),
    /**
     * The large grid size.
     */
    Large(new Grid(91, 91), new CanvasParams(10, 7, 1, 1)),
    /**
     * The massive grid size.
     */
    Massive(new Grid(350, 350), new CanvasParams(10, 2, 0, 0)),
    /**
     * The medium grid size.
     */
    Medium(new Grid(31, 31), new CanvasParams(10, 20, 3, 4)),
    /**
     * The small grid size.
     */
    Small(new Grid(7, 7), new CanvasParams(10, 80, 7, 11));

    /**
     * Retrieves an array of enumeration items in a specific order.
     * 
     * @return an order array of enumeration items
     */
    public static GridSize[] orderedValues() {
        return new GridSize[] { GridSize.Small, GridSize.Default, GridSize.Medium, GridSize.Large, GridSize.Massive };
    }

    private final CanvasParams canvasParams;

    private final Grid grid;

    /**
     * Construct a new {@code GridSizes} instance.
     *
     * @param grid the associated grid
     * @param canvasParams the associated canvas parameters
     */
    private GridSize(Grid grid, CanvasParams canvasParams) {
        this.grid = grid;
        this.canvasParams = canvasParams;
    }

    /**
     * Retrieves the canvas parameters associated with the enumeration item.
     *
     * @return the associated canvas parameters
     */
    public CanvasParams getCanvasParams() {
        return this.canvasParams;
    }

    /**
     * Retrieves the grid associated with the enumeration item.
     *
     * @return the associated grid
     */
    public Grid getGrid() {
        return this.grid;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", super.toString(), this.grid.toCaptionString());
    }
}
