package Dibbidut.Classes;

import Dibbidut.Interfaces.IDisplay;
import math.geom2d.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;


public class Display extends JPanel implements IDisplay {

    private final ArrayList<Ship> ships;
    private final Ship ownShip;
    private final Area MVO;
    private double zoom;

    public Display(Ship ownShip, ArrayList<Ship> shipsInRange, Area mvo) {

        ships = shipsInRange;
        this.ownShip = ownShip;
        this.MVO = mvo;
        zoom = 2;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public Ship getOwnShip() {
        return ownShip;
    }

    private void clearDisplay() {
        repaint(0,0,this.getWidth(), this.getHeight());
    }

    public Dimension getPreferredSize() {
        return new Dimension(1000,1000);
    }

    private double degreesToRadians(int degrees) {
        return degrees * (Math.PI / 180);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2));

        double displayWith = this.getWidth();
        double displayHeight = this.getHeight();

        // Mirror across the x-axis so that up is north. Rotation is dealt with in other places
        g2.scale(1, -1);

        // Translate so that own ship is in the center
        g2.translate(((displayWith / 2) - ownShip.centeredPosition.x()),
                ((displayHeight / 2) - ownShip.centeredPosition.y()) - displayHeight
        );

        drawGUIElements(g2, ownShip, ships);

        // Rotate so that north is at the top of the screen
//        g2.rotate(Math.PI, ownShip.position.x(), ownShip.position.y());

        // Rotate so that own ship is pointing at the top of the screen
//        g2.rotate(Math.PI - degreesToRadians(ownShip.heading), ownShip.position.x(), ownShip.position.y());

        drawOwnShip(g2, ownShip);
        drawTargetShips(g2, ships);
        drawVelocityObstacles(g2, MVO);
    }

    //TODO: Use me
    public Vector2D getZoomedPosition(Vector2D ownShip, Vector2D targetShip, double zoom) {
        return ownShip.plus(targetShip.minus(ownShip).times(zoom));
    }

    private void drawGUIElements(Graphics2D g, Ship ownShip, ArrayList<Ship> ships) {
        g.setColor(Color.gray);

        // Horizontal
        g.draw(new Line2D.Double(ownShip.centeredPosition.x() - this.getWidth(),
                ownShip.centeredPosition.y(),
                ownShip.centeredPosition.x() + this.getWidth(),
                ownShip.centeredPosition.y()));

        // Vertical
        g.draw(new Line2D.Double(ownShip.centeredPosition.x(),
                ownShip.centeredPosition.y() - this.getHeight(),
                ownShip.centeredPosition.x(),
                ownShip.centeredPosition.y() + this.getHeight()));
    }

    private void drawOwnShip(Graphics2D g, Ship ship) {
        g.setColor(Color.blue);

        g.draw(drawShip(ship));
        g.draw(drawShipDomain(ship));
        g.draw(drawHeading(ship));
    }

    private void drawTargetShips(Graphics2D g, ArrayList<Ship> targetShips) {
        g.setColor(Color.black);

        for (Ship ship : targetShips) {

            g.draw(drawShip(ship));
            g.draw(drawShipDomain(ship));
            g.draw(drawHeading(ship));
        }
    }

    private Shape drawShip(Ship ship) {
        Vector2D p = getCoordinatesToDrawShipFrom(ship);

        Shape shape = new Rectangle2D.Double(p.x(), p.y(), ship.width, ship.length);

        return AffineTransform.getRotateInstance(
                degreesToRadians(360 - ship.heading),
                ship.centeredPosition.x(),
                ship.centeredPosition.y())
                .createTransformedShape(shape);
    }

    private Shape drawHeading(Ship ship) {
        Vector2D heading = new Vector2D(0, (double) ship.length / 2);
        heading = heading.rotate(degreesToRadians(360 - ship.heading));
        Vector2D point = ship.centeredPosition.plus(heading);

        return new Line2D.Double(ship.centeredPosition.x(), ship.centeredPosition.y(), point.x(), point.y());
    }

    private Shape drawShipDomain(Ship ship) {
        Vector2D p = getCoordinatesToDrawDomainFrom(ship);

        ship.domain.Update(ship.sog, ship.heading, p.y(), p.x());

        Shape shape = ship.domain.getDomain();

        // TODO: We might need to rotate the domain again

        ship.domain.Update(ship.sog, ship.heading, ship.position.y(), ship.position.x());

        return shape;
    }

    private void drawVelocityObstacles(Graphics2D g, Area mvo) {
        g.draw(mvo);
    }

    public Vector2D getCoordinatesToDrawShipFrom(Ship ship) {

        Vector2D position = getZoomedPosition(this.ownShip.centeredPosition, ship.centeredPosition, this.zoom);

        double x = ship.centeredPosition.x() - (((double) ship.width) / 2);
        double y = ship.centeredPosition.y() - (((double) ship.length) / 2);

        return new Vector2D(x, y);
    }

    public Vector2D getCoordinatesToDrawDomainFrom(Ship ship) {

        double x = ship.centeredPosition.x() - (ship.domain.getWidth() / 2);
        double y = ship.centeredPosition.y() - (ship.domain.getHeight() / 2);

        return new Vector2D(x,y);
    }

    @Override
    public void Update() {
        clearDisplay();
    }
}
