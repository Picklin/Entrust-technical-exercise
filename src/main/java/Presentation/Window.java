package Presentation;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private JPanel vistaActual;
    public Window(Rectangle dimensiones) {
        setTitle("Paginator");
        setBounds(dimensiones);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        vistaActual = new StartView(this);
        add(vistaActual);

        setVisible(true);
    }
    public void cambiarVista(JPanel vista) {
        getContentPane().removeAll();
        vistaActual = vista;
        add(vistaActual);
        revalidate();
        repaint();
    }

}
