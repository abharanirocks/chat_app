import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
class Server extends JFrame{

    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;
    private JLabel clientside=new JLabel("Server Area");
    private JTextArea textArea=new JTextArea();
    private JTextField textField=new JTextField();
    private Font font=new Font("Roboto",Font.PLAIN,20);

    public Server(){

        try{
            server = new ServerSocket(7777);
            System.out.println("Server has started and ready to take connection");
            System.out.println("Waiting ....");
            socket = server.accept();

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());

        createGUI2();
        handleEvents();
            startReading();
            // startWriting();

        }
        catch(Exception e){
            e.printStackTrace();
        }

        
    }
    private void handleEvents() {

        textField.addKeyListener((new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
              
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
               
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==10){
                    String contentToSend= textField.getText();
                    textArea.append("Me : "+contentToSend+"\n");
                    out.println(contentToSend);
                    out.flush();
                    textField.setText("");
                    textField.requestFocus();
                }
                
            }
            
        }));

    }


    private void createGUI2() {

        setTitle("Server Messager[END]");
        setSize(585,780);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        
        Container c=this.getContentPane();
        clientside.setFont(font);
        textField.setFont(font);
        
        clientside.setBounds(240,20,150,50);
        c.add(clientside);
        
        textField.setBounds(10,680,420,50);
        c.add(textField);
        
        
        textArea=new ImageJTextArea(new File("C:\\Users\\ASUS\\Desktop\\Coderspacket\\ChatApplication\\background.png"));
        
        textArea.setBounds(10,70,550,600);
        textArea.setFont(font);
        textArea.setForeground(Color.white);
        textArea.setEditable(false);
        c.add(textArea);
        //  c.add(textArea);
        

        
        


    setVisible(true);
    }
    private void startReading() {

        Runnable r1=()->{

            System.out.println("Reader start . . .");
            try{
            while(true){
                
                    String msg=br.readLine();
                    if(msg.equalsIgnoreCase("exit") || msg==null){
                        System.out.println("Client has terminated the meeting . . . ");
                        JOptionPane.showMessageDialog(this,"Client has Terminated the chat");
                        textField.setEnabled(false);
                        socket.close();
                        break;
                    }
                    System.out.println("Client : "+msg);
                    textArea.append("Client : "+msg+"\n");

                
            }
        }
        catch(Exception e){
            
        }

        };

        new Thread(r1).start();
    }
    private void startWriting() {

        System.out.println("Writer start . . .");

        Runnable r2=()->{

            try {
            while(!socket.isClosed()){
                
                    BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
                    String msg2=in.readLine();
                    out.println(msg2);
                    out.flush();
                    if(msg2.equalsIgnoreCase("exit")){
                       
                        socket.close();
                        break;
                    }
                    
                    
                    
                
            }
        } catch (Exception e) {        
        }
        System.out.println("Connection is Closed ");

        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        
        new Server();
    }
}

    


