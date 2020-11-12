package ui;

import javax.swing.*;

public class SaveFileFrame extends JFrame {

    private JLabel saveFileLabel;
    private JTextField fileNameInput;
    private JButton BtnSave;

    public SaveFileFrame(){
//        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//        setLayout();
        setSize(200, 200);
        setLocation(0, 0);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        saveFileLabel = new JLabel("Please enter the name of the file");
        add(saveFileLabel);
        fileNameInput = new JTextField();
        add(fileNameInput);
        BtnSave = new JButton("Save");


        add(BtnSave);

        BtnSave.addActionListener( e -> {
            String name = fileNameInput.getText();
            System.out.println(name);
        });
    }


}
