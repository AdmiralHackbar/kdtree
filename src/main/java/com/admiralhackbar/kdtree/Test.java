package com.admiralhackbar.kdtree;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.List;

/**
 */
public class Test {

    public static void main(String[] args) {

        final Function<Float, Float> function = new Function<Float, Float>() {
            @Nullable
            @Override
            public Float apply(@Nullable Float input) {
                return input;
            }
        };

        List<Function<Float, Float>> functions = Lists.newArrayList();
        functions.add(function);
        functions.add(function);
        functions.add(function);
        functions.add(function);
        final KDTreeBuilder<Float> builder = new KDTreeBuilder<Float>(functions);
        builder.add(1.0f);
        builder.add(20.0f);
        builder.add(-123.0f);
        builder.add(123.0f);
        builder.add(-121.0f);
        builder.add(29.0f);
        builder.add(-13.0f);
        builder.add(23.0f);
        final KDTree<Float> kdTree = builder.build();
        final float[] attrs = {23.0f, 23.0f, 23.0f, 23.0f};
        System.out.println(kdTree.find(attrs));
    }
}
