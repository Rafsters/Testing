package multiplayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board extends JFrame {


 
    private JPanel boardPanel, pointsPanel;
    private BoardPanel b = new BoardPanel("00112233445566778899");
    

    public Board() {
        
        setLayout(new BorderLayout());
        pointsPanel = new JPanel();
        add(b, BorderLayout.CENTER);
        add(pointsPanel, BorderLayout.SOUTH);
           //Collections.shuffle(cardVals);

    }

}
