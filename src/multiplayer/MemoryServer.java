package multiplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public class MemoryServer extends JFrame {
    private JButton uruchom, zatrzymaj;
    private JPanel panel;
    private JTextField port;
    private JTextArea log;
    private int numerPortu = 2345;
    private boolean uruchomiony = false;
    private Vector<Polaczenie> klienci = new Vector<Polaczenie>();
    public static String kolejnoscKart;

    public MemoryServer() {
        super("Memory Server");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        panel = new JPanel(new FlowLayout());
        log = new JTextArea();
        log.setLineWrap(true);
        log.setEditable(false);
        port = new JTextField((new Integer(numerPortu)).toString(), 8);
        uruchom = new JButton("Uruchom");
        zatrzymaj = new JButton("Zatrzymaj");
        zatrzymaj.setEnabled(false);
        ObslugaZdarzen obsluga = new ObslugaZdarzen();
        uruchom.addActionListener(obsluga);
        zatrzymaj.addActionListener(obsluga);
        panel.add(new JLabel("Port: "));
        panel.add(port);
        panel.add(uruchom);
        panel.add(zatrzymaj);
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(log), BorderLayout.CENTER);
        setVisible(true);
    }

    private class ObslugaZdarzen implements ActionListener {
        private Serwer srw;
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Uruchom")) {
                srw = new Serwer();
                srw.start();
                uruchomiony = true;
                uruchom.setEnabled(false);
                zatrzymaj.setEnabled(true);
                port.setEnabled(false);
                repaint();
            }
            if (e.getActionCommand().equals("Zatrzymaj")){
                srw.kill();
                uruchomiony = false;
                zatrzymaj.setEnabled(false);
                uruchom.setEnabled(true);
                port.setEnabled(true);
                repaint();
            }
        }
    }

    private class Serwer extends Thread {
        private ServerSocket server;
        public void kill(){
            try {
                server.close();
                for(Polaczenie klient: klienci){
                    try {
                        klient.socket.close();
                    }
                    catch (IOException e) { }
                }
                wyswietlKomunikat("Wszystkie Połączenia zostały zakończone.\n");
            }
            catch (IOException e) { }
        }
        public void run(){
            try {
                server = new ServerSocket(new Integer(port.getText()));
                wyswietlKomunikat("Serwer uruchomiony na porcie: " +
                        port.getText() + "\n");
                java.util.List<Integer> cardVals = new ArrayList<Integer>();
                for(int i=0; i<10; i++){
                    cardVals.add(i);
                    cardVals.add(i);
                }
                Collections.shuffle(cardVals);
                StringBuilder cardsPlaces = new StringBuilder();
                for (int val : cardVals) {
                    cardsPlaces.append(val);
                }
                kolejnoscKart = cardsPlaces.toString();
                while (uruchomiony) {
                    Socket socket = server.accept();
                    wyswietlKomunikat("Nowe połączenie.\n");
                    new Polaczenie(socket).start();
                }
            } catch (SocketException e){
            } catch (Exception e){
                wyswietlKomunikat(e.toString());
            } finally {
                try {
                    if(server != null) server.close();
                }catch (IOException e){
                    wyswietlKomunikat(e.toString());
                }
            }
            wyswietlKomunikat("Serwer zatrzymany.\n");
        }
    }

    private class Polaczenie extends Thread {
        private Socket socket;
        private boolean polaczony;
        private PrintWriter wyjscie;

        public Polaczenie (Socket socket) {
            polaczony = true;
            this.socket = socket;
            synchronized(klienci) {
                klienci.add(this);
            }
        }

        public void run() {
            try {
                wyjscie = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                wyswietlKomunikat("Gracz dołączył do gry.\n");
                for (Polaczenie klient : klienci) {
                    klient.wyjscie.println(kolejnoscKart);
                }
                wyswietlKomunikat("Połączenie zostało zakończone.\n");
            } catch (Exception e) {
            } finally {
                try {
                    socket.close();
                } catch(IOException e) {}
            }
        }
    }
    private void wyswietlKomunikat(String tekst){
        log.append(tekst);
        log.setCaretPosition(log.getDocument().getLength());
    }

    public static void main(String[] args ) {
        new MemoryServer();
    }
}