package Dibbidut.Classes;

import math.geom2d.Shape2D;
import math.geom2d.Vector2D;
import math.geom2d.polygon.Rectangle2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.InvalidClassException;
import java.util.ArrayList;

public abstract class Obstacle {
    public Vector2D position;
    public Vector2D velocity;
    public ArrayList<Vector2D> trajectory;

    public Obstacle(Vector2D position, Vector2D velocity) {
        this.position = position;
        this.velocity = velocity;
    }
}
