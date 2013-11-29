# KDTree

Java implementation of the k-d-tree data structure.

## Example usage

```
// Create guava Functions to denote the attributes that you are organizing your tree with.
final List<Function<Point, Float>> functions = Lists.newArrayList(new Function<Point, Float>() {
    @Nullable
    @Override
    public Float apply(@Nullable Point input) {
        return (float)input.getX();
    }
}, new Function<Point, Float>(){
    @Nullable
    @Override
    public Float apply(@Nullable Point input) {
        return (float)input.getY();
    }
});

// Create a builder
final KDTreeBuilder<Point> builder = new KDTreeBuilder<Point>(functions);

// Add items to the builder
builder.add(new Point(0,0));
builder.add(new Point(1,1));
builder.add(new Point(2,2));
builder.add(new Point(3,3));
builder.add(new Point(4,4));
builder.add(new Point(5,5));
builder.add(new Point(6,6));
builder.add(new Point(7,7));

// Build the tree
final KDTree<Point> kdTree = builder.build();

// Find K nearest neighbors
List<Point> nearestPoints = kdTree.findKNearestValues(new Point(3,3), 2);
'''
