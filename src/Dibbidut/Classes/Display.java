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
        g2.translate(((displayWith / 2) - ownShip.position.x()),
                ((displayHeight / 2) - ownShip.position.y()) - displayHeight
        );

        drawGUIElements(g2, ownShip, ships);

        // Rotate so that north is at the top of the screen
//        g2.rotate(Math.PI, ownShip.position.x(), ownShip.position.y());

        // Rotate so that own ship is pointing at the top of the screen
//        g2.rotate(Math.PI - degreesToRadians(ownShip.heading), ownShip.position.x(), ownShip.position.y());

//        Shape shape = new Rectangle2D.Double(ownShip.position.x() - 100, ownShip.position.y() - 100, 50, 50);
//        Area area = new Area(shape);
//
//        g2.draw(area);

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
        g.draw(new Line2D.Double(ownShip.position.x() - this.getWidth(),
                ownShip.position.y(),
                ownShip.position.x() + this.getWidth(),
                ownShip.position.y()));

        // Vertical
        g.draw(new Line2D.Double(ownShip.position.x(),
                ownShip.position.y() - this.getHeight(),
                ownShip.position.x(),
                ownShip.position.y() + this.getHeight()));
    }

    //TODO: Better name please
    private void drawShipVisualisation(Graphics2D g, Ship ship) {
        drawShip(ship, g);
        drawShipDomain(ship, g);
        drawHeading(ship, g);
        drawVelocity(ship, g);
    }

    private void drawOwnShip(Graphics2D g, Ship ship) {
        g.setColor(Color.blue);

        drawShipVisualisation(g, ship);
    }

    private void drawTargetShips(Graphics2D g, ArrayList<Ship> targetShips) {

        for (Ship ship : targetShips) {
            g.setColor(Color.black);
            drawShipVisualisation(g, ship);
        }
    }

    private void drawShip(Ship ship, Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.rotate(degreesToRadians(360 - ship.heading), ship.position.x(), ship.position.y());

        Vector2D p = getCoordinatesToDrawShipFrom(ship);

        Shape shape = new Rectangle2D.Double(p.x(), p.y(), ship.width, ship.length);

//        g2.draw(new Ellipse2D.Double(p.x() - 2, p.y() - 2, 4, 4));

        g2.draw(shape);

        g2.dispose();
    }

    private void drawHeading(Ship ship, Graphics2D g) {
        g.setColor(Color.blue);

        Vector2D heading = new Vector2D(0, (double) ship.length / 2);

        heading = heading.rotate(degreesToRadians(360 - ship.heading));

        Vector2D point = ship.position.plus(heading);

        Shape shape = new Line2D.Double(ship.position.x(), ship.position.y(), point.x(), point.y());

        g.draw(shape);
    }

    private void drawVelocity(Ship ship, Graphics2D g) {
        g.setColor(Color.red);

        Vector2D velocity = new Vector2D(-ship.velocity.x(), ship.velocity.y());

        velocity = ship.position.plus(velocity);

        Shape shape = new Line2D.Double(ship.position.x(), ship.position.y(), velocity.x(), velocity.y());

        g.draw(shape);
    }

    private void drawShipDomain(Ship ship, Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.rotate(degreesToRadians(360 - ship.heading), ship.position.x(), ship.position.y());

        if (ship.domain.getDomainType()) {
            // Pentagon
            //TODO: Mirror pentagon domain?
            ship.domain.Update(ship.sog, 0, ship.position.y(), ship.position.x());
        }
        else {
            // Ellipse
            Vector2D p = getCoordinatesToDrawDomainFrom(ship);

            ship.domain.Update(ship.sog,0, p.y(), p.x());
        }

        Shape shape = ship.domain.getDomain();

        ship.domain.Update(ship.sog, ship.heading, ship.position.y(), ship.position.x());

        g2.draw(shape);

        g2.dispose();
    }

    private void drawVelocityObstacles(Graphics2D g, Area mvo) {
        g.draw(mvo);
    }

    public Vector2D getCoordinatesToDrawShipFrom(Ship ship) {

        Vector2D position = getZoomedPosition(this.ownShip.position, ship.position, this.zoom);

        double x = ship.position.x() - (((double) ship.width) / 2);
        double y = ship.position.y() - (((double) ship.length) / 2);

        return new Vector2D(x, y);
    }

    public Vector2D getCoordinatesToDrawDomainFrom(Ship ship) {

        double x = ship.position.x() - (ship.domain.getWidth() / 2);
        double y = ship.position.y() - (ship.domain.getHeight() / 2);

        return new Vector2D(x,y);
    }

    @Override
    public void Update() {
        clearDisplay();
    }
}
