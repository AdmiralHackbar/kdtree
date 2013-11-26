package com.admiralhackbar.kdtree;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 */
public class KDTreeNode<T> {

    private final int dimension;
    private final float division;

    @Nullable
    private final T value;
    @Nullable
    private KDTreeNode left;
    @Nullable
    private KDTreeNode right;

    public KDTreeNode(final int dimension, final float division, final KDTreeNode left, final KDTreeNode right) {
        this.dimension = dimension;
        this.division = division;
        this.value = null;
        this.left = left;
        this.right = right;
    }

    public KDTreeNode(final int dimension, @Nonnull final T value) {
        this.dimension = dimension;
        this.division = 0.0f;
        this.value = value;
    }

    public int getDimension() {
        return dimension;
    }

    public float getDivision() {
        return division;
    }

    @Nullable
    public T getValue() {
        return value;
    }

    @Nullable
    public KDTreeNode getLeft() {
        return left;
    }

    @Nullable
    public KDTreeNode getRight() {
        return right;
    }

    public void setLeft(@Nullable final KDTreeNode left) {
        this.left = left;
    }

    public void setRight(@Nullable final KDTreeNode right) {
        this.right = right;
    }

    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }
}
