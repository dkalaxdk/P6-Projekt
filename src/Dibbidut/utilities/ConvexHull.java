package Dibbidut.utilities;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface ConvexHull<T> {
    List<T> Calculate(List<T> points);
}
