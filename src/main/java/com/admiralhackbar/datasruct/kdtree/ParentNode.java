package com.admiralhackbar.datasruct.kdtree;

import javax.annotation.Nullable;

/**
 */
public class ParentNode<T> extends KDTreeNode<T> {

    @Nullable
    private final KDTreeNode<T> left;
    @Nullable
    private final KDTreeNode<T> right;

    public ParentNode(@Nullable final KDTreeNode<T> left, @Nullable final KDTreeNode<T> right, final int dimension, final int height, final float division) {
        super(dimension, height, division);
        this.left = left;
        this.right = right;
    }

    @Nullable
    public KDTreeNode<T> getLeft() {
        return left;
    }

    @Nullable
    public KDTreeNode<T> getRight() {
        return right;
    }

    @Override
    boolean isLeaf() {
        return false;
    }
}
