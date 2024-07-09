package Presentation;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Martin Viteri
 * @version 1.0, 9/07/24
 */
public class StartView extends JPanel {
    public StartView(Window mainWindow) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Welcome to the Paginator!");
        title.setFont(new Font("Arial Bold", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.CENTER);

        JButton startButton = new JButton("Start");
        add(startButton, BorderLayout.SOUTH);

        startButton.addActionListener(event -> mainWindow.cambiarVista(new FileSelectionView()));
    }
}
