package com.admiralhackbar.datasruct.kdtree;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

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
        return find(getAttributes(value));
    }

    @Nullable
    public T find(@Nonnull final float[] attributes) {
        Preconditions.checkArgument(attributes.length == getNumDimensions(), "The number of attributes and the number of dimensions do not match.");
        int currentDimension = 1;
        KDTreeNode<T> currentNode = root;
        while(currentNode != null) {
            if (currentNode instanceof LeafNode) {
                final LeafNode<T> leafNode = (LeafNode<T>)currentNode;
                // Either this node contains the value or it doesn't.
                if (leafNode.getValue() != null && Arrays.equals(attributes, getAttributes(leafNode.getValue()))) {
                    return leafNode.getValue();
                } else {
                    return null;
                }
            } else {
                final ParentNode<T> parentNode = (ParentNode<T>)currentNode;
                if (currentNode.getDivision() >= attributes[currentDimension -1]) {
                    // If the nodes division >= the attribute value, the value may be stored in the left tree.
                    currentNode = parentNode.getLeft();
                } else {
                    currentNode = parentNode.getRight();
                }
                currentDimension = getNumDimensions() == currentDimension ? 1 : currentDimension + 1;
            }
        }
        return null;
    }

    private float[] getAttributes(@Nonnull final T value) {
        float[] attributes = new float[getNumDimensions()];
        for (int i = 0; i < getNumDimensions(); i++) {
            attributes[i] = divisors.get(i).apply(value);
        }
        return attributes;
    }

    @Nullable
    public List<T> findKNearestValues(@Nonnull final T value, final int k) {
        return findKNearestValues(getAttributes(value), k);
    }

    @Nullable
    public List<T> findKNearestValues(@Nonnull final float[] attributes, final int k) {
        final PriorityQueue<DistanceNode<T>> nodeQueue = new PriorityQueue<DistanceNode<T>>(k * k, new Comparator<DistanceNode<T>>() {
            @Override
            public int compare(DistanceNode<T> o1, DistanceNode<T> o2) {
                return o1.distance <= o2.distance ? -1 : 1;
            }
        });

        final Comparator<T> sortedComparator = new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return computeDistance(attributes, o1) <= computeDistance(attributes, o2) ? -1 : 1;
            }
        };
        final List<T> valuesQueue = Lists.newArrayList();
        nodeQueue.add(new DistanceNode<T>(root, computeDistance(attributes, root)));
        while(!foundNearestKNodes(nodeQueue, valuesQueue, k, attributes)) {
            final DistanceNode<T> node = nodeQueue.remove();
            if (node.node instanceof ParentNode) {
                final ParentNode<T> parentNode = (ParentNode<T>)node.node;
                if (parentNode.getRight() != null) {
                    nodeQueue.add(new DistanceNode<T>(parentNode.getRight(), computeDistance(attributes, parentNode.getRight())));
                }
                if (parentNode.getLeft() != null) {
                    nodeQueue.add(new DistanceNode<T>(parentNode.getLeft(), computeDistance(attributes, parentNode.getLeft())));
                }
            } else {
                final LeafNode<T> leafNode = (LeafNode<T>)node.node;
                valuesQueue.add(leafNode.getValue());
                // TODO: Implement a more efficient solution that sorting on each add.
                Collections.sort(valuesQueue, sortedComparator);
            }
        }

        final List<T> outputList = Lists.newArrayListWithCapacity(k);
        for (int i = 0; i < k && valuesQueue.size() > 0; i++) {
            outputList.add(valuesQueue.get(i));
        }
        return outputList;
    }

    protected boolean foundNearestKNodes(@Nonnull final PriorityQueue<DistanceNode<T>> nodeQueue, @Nonnull final List<T> valuesQueue, final int k, final float[] attiributes) {
        if (nodeQueue.size() == 0) {
            return true;
        }

        if (valuesQueue.size() > k && nodeQueue.peek().node instanceof ParentNode) {
            return nodeQueue.peek().distance > nodeQueue.peek().distance;
        }
        return false;
    }

    protected double computeDistance(@Nonnull final float[] attributes, @Nonnull final KDTreeNode<T> node) {
        if (node instanceof LeafNode) {
            return computeDistance(attributes, ((LeafNode<T>)node).getValue());
        }
        return Math.pow(attributes[node.getDimension() -1] - node.getDivision(), 2);
    }

    protected double computeDistance(@Nonnull final float[] attr1, @Nonnull final T value2) {
        double distance = 0;
        final float[] attr2 = getAttributes(value2);

        for (int i = 0; i < attr1.length; i++) {
            distance += Math.pow(attr1[i] - attr2[i], 2);
        }
        return distance;
    }

    private final class DistanceNode<T> {
        private final KDTreeNode<T> node;
        private final double distance;

        private DistanceNode(@Nonnull final KDTreeNode<T> node, final double distance) {
            this.node = node;
            this.distance = distance;
        }
    }
}
