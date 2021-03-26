package Dibbidut.Classes;

import Dibbidut.Interfaces.*;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class CASystem {

    public ArrayList<Ship> shipsInRange;
    public IAISBuffer buffer;

    public IOwnShip OS;
    public Ship ownShip;

    public IDisplay display;
    public IVelocityObstacle MVO;

    private boolean running;


    public CASystem() {
        running = true;
    }

    public void Start() {
        while(running) {
//            UpdateOwnShip();
//            UpdateShipList();
//            UpdateVelocityObstacles();
//            UpdateDisplay();
        }
    }

    public void UpdateOwnShip() {

    }

    public void UpdateShipList() {

    }

    public void UpdateVelocityObstacles() {

    }

    public void UpdateDisplay() {

    }
}
