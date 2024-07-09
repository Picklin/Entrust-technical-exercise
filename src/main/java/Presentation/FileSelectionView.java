package Presentation;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ItemEvent;
/**
 * Class consisting in the view when we are selecting the document to paginate
 * @author Martin Viteri
 * @version 1.0, 9/07/24
 */
public class FileSelectionView extends JPanel {
    public FileSelectionView() {
        //to have the elements better organized
        setLayout(new BorderLayout());

        //a subpanel where we can put elements freely
        JPanel panel = new JPanel(null);

        //fields to fill
        JTextField pathInput = new PlaceholderTextField("input txt file path *");
        JTextField pathOutput = new PlaceholderTextField("directory where you want to save your document *");
        JTextField documentName = new PlaceholderTextField("document name");
        pathInput.setBounds(60,80,350,20);
        pathOutput.setBounds(60,120,350,20);
        documentName.setBounds(60,160,350,20);
        panel.add(pathInput);
        panel.add(pathOutput);
        panel.add(documentName);

        //buttons to help fill the fields above by opening a file/directory chooser
        JButton pIButton = new JButton("Browse File");
        JButton pOButton = new JButton("Browse Dir");
        pIButton.setBounds(420, 80, 120, 20);
        pOButton.setBounds(420, 120, 120, 20);
        panel.add(pIButton);
        panel.add(pOButton);

        //extension the output file will have
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

        //message shown when the file is created or when an error occurs
        //in case an error happens the message will be changed below
        JLabel resultMsg = new JLabel("Document successfully created!");
        resultMsg.setBounds(60, 250, 600, 30);
        resultMsg.setForeground(Color.GREEN);
        resultMsg.setVisible(false);
        panel.add(resultMsg);

        add(panel);

        JButton paginateButton = new JButton("Paginate!");
        add(paginateButton, BorderLayout.SOUTH);

        //file chooser to choose the input document
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
        //directory chooser to choose the destination directory
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
                //make sure every field except the name of the document is filled
                //the document has a default name "output"
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
