package Dibbidut.Classes;

import Dibbidut.Interfaces.*;

import java.util.ArrayList;

public class System {

    public ArrayList<Ship> shipsInRange;
    public IAISBuffer buffer;

    public Ship ownShip;

    public IDisplay display;
    public IVelocityObstacle MVO;

    private boolean running;


    public System() {

    }

    public void Main() {
        while(running) {
            UpdateOwnShip();
            UpdateShipList();
            UpdateVelocityObstacles();
            UpdateDisplay();
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
