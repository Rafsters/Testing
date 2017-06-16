package multiplayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BoardPanel extends JPanel implements Serializable{

    private List<Card> cards;
    private Card selectedCard;
    private Card c1;
    private Card c2;
    private Timer t;
    private JPanel boardPanel;
    private JLabel player1, player2, player1Points, player2Points, round, roundPlayer;
    private int p1Points, p2Points;
    public StringBuilder cardsPlaces;
    char[] tablicaCharow = new char[20];
    int[] tablicaIntow = new int[20];

    public BoardPanel(String kolejnosc) {
        int pairs = 10;
        p1Points = 0;
        p2Points = 0;
        List<Card> cardsList = new ArrayList<Card>();
        List<Integer> cardVals = new ArrayList<Integer>();
        for(int i=0; i < kolejnosc.length(); i++){
            tablicaCharow[i] = kolejnosc.charAt(i);
        }
        for(int j =0; j < tablicaCharow.length; j++){
            tablicaIntow[j] = Integer.parseInt(String.valueOf(tablicaCharow[j]));
        }
        for (int i = 0; i < 20; i++) {
            cardVals.add(tablicaIntow[i]);
        }
        setLayout(new BorderLayout());
        boardPanel = new JPanel();
       
        
        add(boardPanel);
        boardPanel.setLayout(new GridLayout(4, 5));

        //Collections.shuffle(cardVals);

        cardsPlaces = new StringBuilder();
        for (int val : cardVals) {
            cardsPlaces.append(val);
        }

        for (int val : cardVals) {
            Card c = new Card();
            c.setId(val);
            c.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    selectedCard = c;
                    doTurn();
                }
            });
            cardsList.add(c);
        }
        this.cards = cardsList;
        //set up the timer
        t = new Timer(750, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                checkCards();
            }
        });

        t.setRepeats(false);

        //set up the board itself

        //Container pane = getContentPane();
        //pane.setLayout(new GridLayout(4, 5));
        for (Card c : cards) {
            c.setIcon(new ImageIcon(setBlank()));
            boardPanel.add(c);
        }
    }

    public StringBuilder getCardPlaces() {
        return cardsPlaces;
    }

    public Image setBlank() {
        Image card = null;
        try {
            card = ImageIO.read(getClass().getResource("/res/card.png"));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return card;
    }

    boolean player1IsMakingTurn = true;
    boolean player2IsMakingTurn = false;

    public void doTurn() {
        System.out.println("1: " + p1Points + ", 2: " + p2Points);
        if (c1 == null && c2 == null) {
            c1 = selectedCard;
            c1.setCardImage(c1.getId());
        }

        if (c1 != null && c1 != selectedCard && c2 == null) {
            c2 = selectedCard;
            c2.setCardImage(c2.getId());
            t.start();

        }

    }

    

   

    public void checkCards() {

        
        if (c1.getId() == c2.getId()) {//match condition
            c1.setEnabled(false); //disables the button
            c2.setEnabled(false);
            c1.setMatched(true); //flags the button as having been matched
            c2.setMatched(true);
            if (this.isGameWon()) {
                if (p1Points > p2Points) {
                    JOptionPane.showMessageDialog(this, "Gracz 1 wygrywa!");
                } else if (p1Points < p2Points) {
                    JOptionPane.showMessageDialog(this, "Gracz 2 wygrywa!");
                } else {
                    JOptionPane.showMessageDialog(this, "Remis!");
                }
                System.exit(0);
            }
        } else {
            c1.setIcon(new ImageIcon(setBlank()));
            c2.setIcon(new ImageIcon(setBlank()));
        }
        c1 = null; //reset c1 and c2
        c2 = null;
    }

    public boolean isGameWon() {
        for (Card c : this.cards) {
            if (c.getMatched() == false) {
                return false;
            }
        }
        return true;
    }
    
    public BoardPanel process(){
		return new BoardPanel("99887766554433221100");
    }

}
