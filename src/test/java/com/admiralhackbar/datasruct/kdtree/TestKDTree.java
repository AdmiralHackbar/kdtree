package com.admiralhackbar.datasruct.kdtree;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import junit.framework.Assert;
import org.junit.Test;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

/**
 */
public class TestKDTree {

    @Test
    public void testFind() {
        List<Point> points = Lists.newArrayList();
        points.add(new Point(1,1));
        points.add(new Point(2,2));
        points.add(new Point(3,3));
        points.add(new Point(4,4));
        points.add(new Point(5,5));
        List<Function<Point, Float>> functions = Lists.newArrayList();
        functions.add(new Function<Point, Float>() {
            @Nullable
            @Override
            public Float apply(@Nullable Point input) {
                return (float)input.getX();
            }
        });
        functions.add(new Function<Point, Float>() {
            @Nullable
            @Override
            public Float apply(@Nullable Point input) {
                return (float)input.getY();
            }
        });

        final KDTree<Point> kdTree = (new KDTreeBuilder<Point>(functions)).addAll(points).build();
        Assert.assertEquals(new Point(3,3), kdTree.find(new Point(3,3)));
        Assert.assertNull(kdTree.find(new Point(0,0)));

    }
}
