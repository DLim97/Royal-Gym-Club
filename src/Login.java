import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;


/**
 * Created by dapel on 3/2/2017.
 */


public class Login extends Frame implements ActionListener{
    private JPasswordField txtPassword;
    private JTextField txtUsername;
    private Button btnLogin,btnCancel;
    public static String user;
    private int pass;



    public Login(){
        setSize(450,300);
        setLocation(750,500);
        setTitle("Royal Gym Club");
        setLayout(new BorderLayout());


        Panel titlePanel = new Panel();
        titlePanel.setBackground(Color.gray);
        titlePanel.setLayout(new FlowLayout());
        Label lbltitle = new Label("Login");
        titlePanel.add(lbltitle);
        add(titlePanel, "North");

        Panel Login = new Panel();
        Login.setLayout(new GridLayout(3,1,0,0));
        Login.setBackground(Color.orange);

        Panel username =  new Panel();
        username.setLayout(new FlowLayout());
        Label lblUsername = new Label("Username");
         txtUsername = new JTextField("",20);
        username.add(lblUsername);
        username.add(txtUsername);


        Panel password =  new Panel();
        password.setLayout(new FlowLayout());
        Label lblPassword =  new Label("Password");
        txtPassword = new JPasswordField("",20);
        password.add(lblPassword);
        password.add(txtPassword);


        Panel buttonPanel =  new Panel();
        buttonPanel.setLayout(new FlowLayout());
        btnLogin = new Button("Login");
        btnCancel = new Button("Cancel");
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnCancel);


        Login.add(username);
        Login.add(password);
        Login.add(buttonPanel);
        add(Login);


        btnLogin.addActionListener(this);
        btnCancel.addActionListener(this);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }


    public void actionPerformed (ActionEvent e){
        int ans;
        if (e.getSource()==btnLogin) {

            try {
                ans=fileRead();
                if (ans==0) {
                    Staff s = new Staff(txtUsername.getText());
                    user = s.getStaff();
                    new Main_Menu();
                    setVisible(false);

                }
                else{
                    JOptionPane.showMessageDialog(null,"Incorrect username or password!", "Warning",JOptionPane.ERROR_MESSAGE);
                    txtUsername.setText("");
                    txtPassword.setText("");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }




        }
        else if (e.getSource() == btnCancel) {
            dispose();

        }
    }


    private int fileRead() throws IOException{
        File login = new File("Login.txt");
        Scanner input = new Scanner(login);

        while(input.hasNext()){
            String text = input.nextLine();

            String[] details = text.split(":");
            if (details[0].equals(txtUsername.getText()) && details[1].equals(txtPassword.getText())){
                return 0;

            }


        }
        input.close();
        return 1;
    }


}
