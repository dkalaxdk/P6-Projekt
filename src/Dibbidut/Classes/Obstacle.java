package Dibbidut.Classes;

import Dibbidut.Classes.Geometry.HPoint;
import math.geom2d.Shape2D;
import math.geom2d.Vector2D;
import math.geom2d.polygon.Rectangle2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.InvalidClassException;
import java.util.ArrayList;

public abstract class Obstacle {
    public HPoint position;
    public HPoint translatedPosition;
    public HPoint velocity;
    public HPoint scaledVelocity;
    public ArrayList<HPoint> trajectory;

    public Obstacle(HPoint position, HPoint velocity) {
        this.position = position;
        this.velocity = velocity;
        this.scaledVelocity = velocity;
    }

    public void setScaledVelocity(double scale) {
        scaledVelocity = velocity.copy();
        scaledVelocity.scale(scale);
    }

    public HPoint getScaledVelocity() {
        return scaledVelocity;
    }

    public void setTranslatedPosition(HPoint point) {
        translatedPosition = position.copy();
        translatedPosition.translate(-point.getX(), -point.getY());
    }

    public HPoint getTranslatedPosition() {
        return translatedPosition;
    }
}
