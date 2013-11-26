package com.admiralhackbar.kdtree;

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

    public KDTreeBuilder<T> add(@Nonnull final T value) {
        this.values.add(value);
        return this;
    }

    public KDTreeBuilder<T> addAll(@Nonnull final Collection<T> values) {
        this.values.addAll(values);
        return this;
    }

    public KDTree<T> build() {
        return new KDTree<T>(buildRoot(values, 1), divisors);
    }

    private KDTreeNode buildRoot(@Nonnull final List<T> values, final int dimension) {
        if (values.size() == 1) {
            return new KDTreeNode(dimension, values.get(0));
        } else {
            final Function<T, Float> divisor = divisors.get(dimension -1);
            final SplitList<T> splitList = new SplitList<T>(values, divisor);
            int nextDimension = dimension == divisors.size() ? 1 : dimension + 1;

            return new KDTreeNode(nextDimension,
                    divisor.apply(splitList.getLeftList().get(splitList.getLeftList().size() -1)),
                    buildRoot(splitList.getLeftList(), nextDimension),
                    buildRoot(splitList.getRightList(), nextDimension)
            );
        }
    }

    private class SplitList<T> {

        private List<T> leftList;
        private List<T> rightList;

        public SplitList(final List<T> values, final Function<T, Float> divisor) {
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

        private List<T> getLeftList() {
            return leftList;
        }

        private List<T> getRightList() {
            return rightList;
        }
    }
}
