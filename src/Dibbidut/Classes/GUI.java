package Dibbidut.Classes;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GUI extends JPanel implements ActionListener, WindowListener, ChangeListener {

    private final CASystem system;

    public GUI(CASystem system) {
        this.system = system;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentY(Component.TOP_ALIGNMENT);


        JSlider slider = new JSlider();

        add(slider);

        JSlider slider2 = new JSlider();
        add(slider2);

//        setBorder(BorderFactory.createBevelBorder(1));
        setBorder(BorderFactory.createEtchedBorder(1));
    }

    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void stateChanged(ChangeEvent e) {

    }
}
