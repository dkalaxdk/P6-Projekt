package Dibbidut.Classes.UI;

import Dibbidut.Classes.CASystem;
import Dibbidut.Classes.Geometry.Polygon;
import Dibbidut.Classes.Geometry.HPoint;
import Dibbidut.Classes.Ship;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;


public class Display extends JPanel {

    public double zoom;

    private final CASystem system;

    public Display(CASystem system) {

        this.system = system;
        zoom = 1;
    }

    public Display(Ship ownShip, ArrayList<Ship> ships, Hashtable<Ship, Polygon> MVO) {
        this.system = new CASystem();
        system.ownShip = ownShip;
        system.shipsInRange = ships;
        system.MVO = MVO;
    }

    public ArrayList<Ship> getShips() {
        return system.shipsInRange;
    }

    public Ship getOwnShip() {
        return system.ownShip;
    }

    private void clearDisplay() {
        repaint();
    }

    public Dimension getPreferredSize() {
        return new Dimension(1000,1000);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2));


        double displayWith = this.getWidth();
        double displayHeight = this.getHeight();

        // Mirror across the x-axis so that up is north. Rotation is dealt with in other places
        g2.scale(1, -1);

        g2.scale(1/ zoom,1 / zoom);

        // Translate so that own ship is in the center
        g2.translate((((displayWith / 2) * zoom)- system.ownShip.position.getX()),
                (((displayHeight / 2) * zoom)- system.ownShip.position.getY()) - (displayHeight * zoom)
        );


//        g2.translate((((displayWith / 2) )- system.ownShip.position.getX()),
//                (((displayHeight / 2) )- system.ownShip.position.getY()) - (displayHeight )
//        );



        drawGUIElements(g2, system.ownShip, system.shipsInRange);

        // Rotate so that north is at the top of the screen
//        g2.rotate(Math.PI, ownShip.position.getX(), ownShip.position.getY());

        // Rotate so that own ship is pointing at the top of the screen
//        g2.rotate(Math.PI - degreesToRadians(ownShip.heading), ownShip.position.getX(), ownShip.position.getY());

        system.listLock.lock();



        drawVelocityObstacles(g2, system.MVO);
        drawOwnShip(g2, system.ownShip);
        drawTargetShips(g2, system.shipsInRange);

        system.listLock.unlock();
    }

    public HPoint getZoomedPosition(HPoint ownShip, HPoint targetShip, double zoom) {
        HPoint point = targetShip.subtract(ownShip);
        point.scale(zoom);
        return ownShip.add(point);
    }

    private void drawGUIElements(Graphics2D g, Ship ownShip, ArrayList<Ship> ships) {
        g.setColor(Color.gray);

        // Horizontal
        g.draw(new Line2D.Double(ownShip.position.getX() - this.getWidth(),
                ownShip.position.getY(),
                ownShip.position.getX() + this.getWidth(),
                ownShip.position.getY()));

        // Vertical
        g.draw(new Line2D.Double(ownShip.position.getX(),
                ownShip.position.getY() - this.getHeight(),
                ownShip.position.getX(),
                ownShip.position.getY() + this.getHeight()));
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

    //TODO: Better name please
    private void drawShipVisualisation(Graphics2D g, Ship ship) {

        drawShip(ship, g);
        drawShipDomain(ship, g);
        drawHeading(ship, g);
        drawVelocity(ship, g);
    }

    private void drawShip(Ship ship, Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.rotate(Math.toRadians(360 - ship.heading), ship.position.getX(), ship.position.getY());

        HPoint p = getCoordinatesToDrawShipFrom(ship);

        Shape shape = new Rectangle2D.Double(p.getX(), p.getY(), ship.width, ship.length);

        g2.draw(shape);

        g2.dispose();
    }

    private void drawShipDomain(Ship ship, Graphics2D g) {

        Shape shape;
        if (ship.domain.getDomainType()) {
            // Pentagon

            shape = drawPentagonDomain(ship);
        }
        else {
            // Ellipse
            HPoint p = getCoordinatesToDrawDomainFrom(ship);

            ship.domain.Update(ship.sog,0, p.getY(), p.getX());

            shape = drawEllipseDomain(ship);
        }

        g.draw(shape);
    }

    private void drawHeading(Ship ship, Graphics2D g) {
        g.setColor(Color.blue);

        HPoint heading = new HPoint(0, (double) ship.length / 2);

        heading.rotate(ship.heading);

        HPoint point = ship.position.add(heading);

        Shape shape = new Line2D.Double(ship.position.getX(), ship.position.getY(), point.getX(), point.getY());

        g.draw(shape);
    }

    private void drawVelocity(Ship ship, Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(Color.green);
        g2.setStroke(new BasicStroke((float) zoom * 4));
        g2.setBackground(Color.BLACK);

        HPoint velocity = new HPoint(ship.scaledVelocity.getX(), ship.scaledVelocity.getY());

        velocity = ship.position.add(velocity);

        Shape shape = new Line2D.Double(ship.position.getX(), ship.position.getY(), velocity.getX(), velocity.getY());

        g2.draw(shape);

        g2.dispose();
    }

    private void drawVelocityObstacles(Graphics2D g, Hashtable<Ship, Polygon> mvo) {
        g.setColor(new Color(1f, 0f, 0f, 0.5f));

        Set<Ship> setOfShips = mvo.keySet();

        for (Ship ship : setOfShips) {
            drawVelocityObstacle(g, ship, mvo.get(ship));
        }
    }

    private void drawVelocityObstacle(Graphics2D g, Ship ship, Polygon vo) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));

        Area area = new Area(drawPolygon(vo));

        g2.fill(area);
        g2.draw(area);

        g2.dispose();
    }

    public Path2D drawPolygon(Polygon polygon) {
        Path2D outputShape = new Path2D.Double();

        outputShape.moveTo(polygon.coordinates.get(0).getX(), polygon.coordinates.get(0).getY());

        for (int i = 1; i < polygon.coordinates.size(); i++) {
            outputShape.lineTo(polygon.coordinates.get(i).getX(), polygon.coordinates.get(i).getY());
        }

        outputShape.closePath();

        return outputShape;
    }

    public HPoint getCoordinatesToDrawShipFrom(Ship ship) {

        double x = ship.position.getX() - (((double) ship.width) / 2);
        double y = ship.position.getY() - (((double) ship.length) / 2);

        return new HPoint(x, y);
    }

    public HPoint getCoordinatesToDrawDomainFrom(Ship ship) {

        double x = ship.position.getX() - (ship.domain.getWidth() / 2);
        double y = ship.position.getY() - (ship.domain.getHeight() / 2);

        return new HPoint(x,y);
    }

    private Path2D drawPentagonDomain(Ship ship) {
        Polygon domain = (Polygon) ship.domain.getDomain();
        domain.referencePoint = ship.position;

        return drawPolygon(domain);
    }

    // TODO: Implement me
    private Path2D drawEllipseDomain(Ship ship) {
        return new Path2D.Double();
    }
    public void Update() {
        clearDisplay();
    }
}
