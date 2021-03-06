package com.admiralhackbar.datasruct.kdtree;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KDTreeBuilder<T> {

    private List<Function<T, Float>> divisors;
    private List<T> values = Lists.newArrayList();

    public KDTreeBuilder(@Nonnull final List<Function<T, Float>> divisors) {
        this.divisors = divisors;
    }

    @Nonnull
    public KDTreeBuilder<T> add(@Nonnull final T value) {
        this.values.add(value);
        return this;
    }

    @Nonnull
    public KDTreeBuilder<T> addAll(@Nonnull final Collection<T> values) {
        this.values.addAll(values);
        return this;
    }

    @Nonnull
    public KDTree<T> build() {
        return new KDTree<T>(buildRoot(values, 1, 0), divisors);
    }

    @Nonnull
    private KDTreeNode buildRoot(@Nonnull final List<T> values, final int dimension, final int height) {
        final Function<T, Float> divisor = divisors.get(dimension -1);
        if (values.size() == 1) {
            return new LeafNode(values.get(0), dimension, height, divisor.apply(values.get(0)));
        } else {
            final SplitList<T> splitList = new SplitList<T>(values, divisor);
            int nextDimension = dimension == divisors.size() ? 1 : dimension + 1;

            return new ParentNode(buildRoot(splitList.getLeftList(), nextDimension, height +1),
                    buildRoot(splitList.getRightList(), nextDimension, height +1),
                    nextDimension,
                    height,
                    divisor.apply(splitList.getLeftList().get(splitList.getLeftList().size() -1))
            );
        }
    }

    private class SplitList<T> {

        private List<T> leftList;
        private List<T> rightList;

        public SplitList(@Nonnull final List<T> values, @Nonnull final Function<T, Float> divisor) {
            Collections.sort(values, new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    final Float f1 = divisor.apply(o1);
                    final Float f2 = divisor.apply(o2);
                    return f1.compareTo(f2);
                }
            });
            leftList = values.subList(0, values.size() / 2);
            rightList = values.subList(values.size() / 2, values.size());
        }

        @Nonnull
        private List<T> getLeftList() {
            return leftList;
        }

        @Nonnull
        private List<T> getRightList() {
            return rightList;
        }
    }
}
