package com.admiralhackbar.datasruct.kdtree;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 */
public abstract class KDTreeNode<T> {

    private final int dimension;
    private final int height;
    private final float division;

    public KDTreeNode(final int dimension,final int height, final float division) {
        this.dimension = dimension;
        this.height = height;
        this.division = division;
    }

    public int getDimension() {
        return dimension;
    }

    public int getHeight() {
        return height;
    }

    public float getDivision() {
        return division;
    }

    abstract boolean isLeaf();
}
