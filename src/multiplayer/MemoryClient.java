package multiplayer;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

class Client extends Thread {
    private Socket socket;
    boolean polaczony = false;
    BufferedReader wejscie;
    String kolejnoscKart;
    public Client(){
    	
    }
    @Override
    public void run() {
        try {
            socket = new Socket("localhost", 2345);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(
            socket.getOutputStream());
            oos.flush();
            BoardPanel wiad = new BoardPanel("00112233445566778899");
            //System.out.println (wiad);
            oos.writeObject(wiad);
            oos.flush();
            BoardPanel odp = (BoardPanel) ois.readObject();
            System.out.println (odp);
            ois.close();
            oos.close();
            wejscie = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            polaczony = true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
            wejscie = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            kolejnoscKart = wejscie.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
            
            System.out.println(socket.isConnected());
    }
}

public class MemoryClient extends JFrame {
	private JPanel boardPanel, pointsPanel;
    private BoardPanel b = new BoardPanel("00112233445566778899");
    public MemoryClient(){
    	setPreferredSize(new Dimension(500, 500)); //need to use this instead of setSize
        setLocation(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setLayout(new BorderLayout());
        pointsPanel = new JPanel();
        add(b, BorderLayout.CENTER);
        add(pointsPanel, BorderLayout.SOUTH);
    }
	
    public static void main(String[] args) {
    	new MemoryClient();
        Client client = new Client();
        client.start();
    }
}