package Presentation;

import javax.swing.*;
import java.awt.*;
/**
 * Main window of the application
 * Here we can change the views without having to create new windows
 * @author Martin Viteri
 * @version 1.0, 9/07/24
 */
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
