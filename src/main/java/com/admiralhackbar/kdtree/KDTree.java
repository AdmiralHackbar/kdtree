package com.admiralhackbar.kdtree;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

/**
 */
public class KDTree<T> {

    private final KDTreeNode<T> root;
    private final ImmutableList<Function<T, Float>> divisors;

    public KDTree(@Nonnull final KDTreeNode<T> root, @Nonnull final List<Function<T, Float>> divisors) {
        this.root = root;
        this.divisors = new ImmutableList.Builder<Function<T, Float>>().addAll(divisors).build();
    }

    @Nonnull
    public KDTreeNode<T> getRoot() {
        return root;
    }

    public int getNumDimensions() {
        return divisors.size();
    }

    @Nonnull
    public ImmutableList<Function<T, Float>> getDivisors() {
        return divisors;
    }

    @Nullable
    public T find (@Nonnull final T value) {
        float[] attributes = new float[getNumDimensions()];
        for (int i = 0; i < getNumDimensions(); i++) {
            attributes[i] = divisors.get(i).apply(value);
        }
        return find(attributes);
    }

    @Nullable
    public T find(@Nonnull final float[] attributes) {
        Preconditions.checkArgument(attributes.length == getNumDimensions(), "The number of attributes and the number of dimensions do not match.");
        int currentDimension = 1;
        KDTreeNode<T> currentNode = root;
        while(currentNode != null) {
            if (currentNode.isLeaf()) {
                // Either this node contains the value or it doesn't.
                final Function<T, Float> divisor = divisors.get(currentDimension -1);
                final float valueAttr = divisor.apply(currentNode.getValue());
                if (valueAttr == attributes[currentDimension -1]) {
                    return currentNode.getValue();
                } else {
                    return null;
                }
            } else {
                if (currentNode.getDivision() >= attributes[currentDimension -1]) {
                    // If the nodes division >= the attribute value, the value may be stored in the left tree.
                    currentNode = currentNode.getLeft();
                } else {
                    currentNode = currentNode.getRight();
                }
                currentDimension = getNumDimensions() == currentDimension ? 1 : currentDimension + 1;
            }
        }
        // Will never be executed
        return null;
    }
}
