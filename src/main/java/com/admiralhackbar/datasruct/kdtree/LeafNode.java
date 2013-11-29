package com.admiralhackbar.datasruct.kdtree;

import javax.annotation.Nullable;

/**
 */
public class LeafNode<T> extends KDTreeNode<T>{

    @Nullable
    private final T value;

    public LeafNode(@Nullable final T value, final int dimension, final int height, final float division) {
        super(dimension, height, division);
        this.value = value;
    }

    @Nullable
    public T getValue() {
        return value;
    }

    @Override
    boolean isLeaf() {
        return true;
    }
}
