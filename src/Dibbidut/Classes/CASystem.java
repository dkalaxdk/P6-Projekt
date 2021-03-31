package Dibbidut.Classes;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Interfaces.*;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CASystem {

    public ArrayList<Ship> shipsInRange;
    public BlockingQueue<AISData> buffer;

    public IOwnShip OS;
    public Ship ownShip;

    public IDisplay display;
    public IVelocityObstacle MVO;

    private boolean running;


    public CASystem() {
        running = true;
        buffer = new LinkedBlockingQueue<>();

        
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
