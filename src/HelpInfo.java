import javax.swing.*;

class HelpInfo extends JFrame {

        HelpInfo(){
            setSize(600,600);
            setVisible(true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLocation(400,50);
            JPanel pane = new JPanel();
            add(pane);
            String s = "<html><font size='4' >WELCOME USER. <br><br>  " +
                    "Important Info: <br> " +
                    "1. Select on radiobutton and perform action <br> " +
                    "2. Move vertex occurrs with two clicks instead of dragging Node <br>  " +
                    "3. ShowComponents paints every component with a different color<br>  " +
                    "3. CutVertices paints the Cut vertices with a different color</font></html>";
            JLabel label = new JLabel(s);
            pane.add(label);

        }
    }