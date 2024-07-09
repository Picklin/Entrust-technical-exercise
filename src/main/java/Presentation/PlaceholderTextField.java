package Presentation;

import javax.swing.*;
import java.awt.*;
/**
 * Extension of the class JTextField to make a placeholder text to explain the fields to fill
 * @author Martin Viteri
 * @version 1.0, 9/07/24
 */
class PlaceholderTextField extends JTextField {
    public PlaceholderTextField(String placeholder) {
        setText(placeholder);
        setForeground(Color.GRAY);
        addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                    setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (getText().isEmpty()) {
                    setForeground(Color.GRAY);
                    setText(placeholder);
                }
            }
        });
    }
}