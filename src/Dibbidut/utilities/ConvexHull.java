package Dibbidut.utilities;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;

public interface ConvexHull<T> {
    Collection<T> Calculate(Collection<T> points);
}
