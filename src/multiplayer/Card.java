package multiplayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class Card extends JButton{
    private int id;
    private boolean matched = false;
    Image pikachu, bulbasaur, squirtle, charmander, pidgey, zubat, psyduck, abra, koffing, eevee;


    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setCardImage(int id){
        try {
            pikachu = ImageIO.read(getClass().getResource("/res/pikachu.jpg"));
            bulbasaur = ImageIO.read(getClass().getResource("/res/bulbasaur.png"));
            squirtle = ImageIO.read(getClass().getResource("/res/squirtle.png"));
            charmander = ImageIO.read(getClass().getResource("/res/charmander.png"));
            pidgey = ImageIO.read(getClass().getResource("/res/pidgey.png"));
            zubat = ImageIO.read(getClass().getResource("/res/zubat.png"));
            psyduck = ImageIO.read(getClass().getResource("/res/psyduck.png"));
            abra = ImageIO.read(getClass().getResource("/res/abra.jpg"));
            koffing = ImageIO.read(getClass().getResource("/res/koffing.png"));
            eevee = ImageIO.read(getClass().getResource("/res/eevee.png"));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        switch (id) {
            case 0:
                this.setIcon(new ImageIcon(pikachu));
                break;
            case 1:
                this.setIcon(new ImageIcon(bulbasaur));
                break;
            case 2:
                this.setIcon(new ImageIcon(squirtle));
                break;
            case 3:
                this.setIcon(new ImageIcon(charmander));
                break;
            case 4:
                this.setIcon(new ImageIcon(pidgey));
                break;
            case 5:
                this.setIcon(new ImageIcon(zubat));
                break;
            case 6:
                this.setIcon(new ImageIcon(psyduck));
                break;
            case 7:
                this.setIcon(new ImageIcon(abra));
                break;
            case 8:
                this.setIcon(new ImageIcon(koffing));
                break;
            case 9:
                this.setIcon(new ImageIcon(eevee));
                break;
        }
    }

    public void setMatched(boolean matched){
        this.matched = matched;
    }

    public boolean getMatched(){
        return this.matched;
    }
}