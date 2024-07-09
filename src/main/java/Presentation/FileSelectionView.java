package Presentation;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ItemEvent;

public class FileSelectionView extends JPanel {
    public FileSelectionView() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(null);

        JTextField pathInput = new PlaceholderTextField("input txt file path *");
        JTextField pathOutput = new PlaceholderTextField("directory where you want to save your document *");
        JTextField documentName = new PlaceholderTextField("document name");
        pathInput.setBounds(60,80,350,20);
        pathOutput.setBounds(60,120,350,20);
        documentName.setBounds(60,160,350,20);
        panel.add(pathInput);
        panel.add(pathOutput);
        panel.add(documentName);

        JButton pIButton = new JButton("Browse File");
        JButton pOButton = new JButton("Browse Dir");
        pIButton.setBounds(420, 80, 120, 20);
        pOButton.setBounds(420, 120, 120, 20);
        panel.add(pIButton);
        panel.add(pOButton);

        final String[] extension = {".txt"};
        String[] extensions = {".txt", ".pdf"};
        JComboBox<String> extensionChooser = new JComboBox<>(extensions);
        extensionChooser.setBounds(420, 160, 120, 20);
        panel.add(extensionChooser);
        extensionChooser.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                int pos = extensionChooser.getSelectedIndex();
                extension[0] = extensions[pos];
            }
        });

        JLabel resultMsg = new JLabel("Document successfully created!");
        resultMsg.setBounds(60, 250, 600, 30);
        resultMsg.setForeground(Color.GREEN);
        resultMsg.setVisible(false);
        panel.add(resultMsg);

        add(panel);

        JButton paginateButton = new JButton("Paginate!");
        add(paginateButton, BorderLayout.SOUTH);

        pIButton.addActionListener(event -> {
            JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            chooser.setDialogTitle("Select the txt file");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int ret = chooser.showOpenDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getPath();
                pathInput.setForeground(Color.BLACK);
                pathInput.setText(path);
            }
        });
        pOButton.addActionListener(event -> {
            JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            chooser.setDialogTitle("Select the directory to save the document");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int ret = chooser.showOpenDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getPath();
                pathOutput.setForeground(Color.BLACK);
                pathOutput.setText(path);
            }
        });
        paginateButton.addActionListener(event -> {
            try {
                if (pathInput.getForeground().equals(Color.BLACK) && pathOutput.getForeground().equals(Color.BLACK)) {
                    if (documentName.getForeground().equals(Color.BLACK))
                        PresentationCtrl.getInstance().paginate(pathInput.getText(), pathOutput.getText(), documentName.getText(), extension[0]);
                    else {
                        PresentationCtrl.getInstance().paginate(pathInput.getText(), pathOutput.getText(), "output", extension[0]);
                    }
                    resultMsg.setText("Document successfully created!");
                    resultMsg.setForeground(Color.GREEN);
                }
                else {
                    resultMsg.setText("Empty field");
                    resultMsg.setForeground(Color.RED);
                }
            } catch (Exception e) {
                resultMsg.setText(e.getMessage());
                resultMsg.setForeground(Color.RED);
            }
            resultMsg.setVisible(true);
        });
    }
}
