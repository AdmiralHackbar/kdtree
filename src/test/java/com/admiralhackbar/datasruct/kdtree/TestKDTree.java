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

    @Test
    public void testFindKNearestValues() {
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

        List<Point> foundPoints = kdTree.findKNearestValues(new Point(3,3), 3);
        Assert.assertEquals(3, foundPoints.size());
        Assert.assertEquals(new Point(3,3), foundPoints.get(0));
        Assert.assertEquals(new Point(4,4), foundPoints.get(1));
        Assert.assertEquals(new Point(2,2), foundPoints.get(2));
    }

    @Test
    public void testFindKNearestValues2() {
        List<Point> points = Lists.newArrayList();
        points.add(new Point(1,1));
        points.add(new Point(0,0));
        points.add(new Point(1,0));
        points.add(new Point(0,1));
        points.add(new Point(2,2));
        points.add(new Point(3,3));
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

        List<Point> foundPoints = kdTree.findKNearestValues(new Point(1,1), 3);
        Assert.assertEquals(3, foundPoints.size());
        Assert.assertEquals(new Point(1,1), foundPoints.get(0));
        Assert.assertEquals(new Point(0,1), foundPoints.get(1));
        Assert.assertEquals(new Point(1,0), foundPoints.get(2));
    }
}
