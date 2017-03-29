/**
 * Created by dapel on 7/2/2017.
 */

import com.sun.xml.internal.bind.v2.model.core.ID;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

public class Registration extends Frame implements ActionListener{
    TextField txtName, txtIC, txtContact;
    Choice cMembership;
    Button btnRegister, btnCancel;
    private int ID;

    public Registration(){
        setSize(450,300);
        setLocation(750,500);
        setTitle("Registration");
        setLayout(new BorderLayout());

        Panel input = new Panel();
        input.setLayout(new GridLayout(5,1));
        input.setBackground(Color.orange);

        Panel membership =  new Panel();
        membership.setLayout(new FlowLayout());
        Label lblMembership =  new Label("Type of Membership: ");
        cMembership = new Choice();
        cMembership.add("Deluxe");
        cMembership.add("Non-Deluxe");
        cMembership.add("Weekday");
        membership.add(lblMembership);
        membership.add(cMembership);


        Panel namePanel =  new Panel();
        namePanel.setLayout(new FlowLayout());
        Label lblName =  new Label("Name: ");
        txtName = new TextField("",20);
        namePanel.add(lblName);
        namePanel.add(txtName);

        Panel ICPanel =  new Panel();
        ICPanel.setLayout(new FlowLayout());
        Label lblIC =  new Label("IC: ");
        txtIC = new TextField("",20);
        ICPanel.add(lblIC);
        ICPanel.add(txtIC);

        Panel ContactPanel =  new Panel();
        ContactPanel.setLayout(new FlowLayout());
        Label lblContact =  new Label("Contact Number: ");
        txtContact = new TextField("",20);
        ContactPanel.add(lblContact);
        ContactPanel.add(txtContact);


        Panel buttonP =  new Panel();
        buttonP.setLayout(new FlowLayout());
        btnRegister = new Button("Register");
        btnCancel = new Button("Cancel");
        buttonP.add(btnRegister);
        buttonP.add(btnCancel);


        input.add(membership);
        input.add(namePanel);
        input.add(ICPanel);
        input.add(ContactPanel);
        input.add(buttonP);
        add(input);

        btnRegister.addActionListener(this);
        btnCancel.addActionListener(this);



        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);

    }


    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== btnRegister) {
            if (empty() == 0) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader("member.txt"));
                    if (br.readLine() == null) {
                        write();
                        txtName.setText("");
                        txtIC.setText("");
                        txtContact.setText("");
                    } else {
                        append();
                        txtName.setText("");
                        txtIC.setText("");
                        txtContact.setText("");
                    }

                    JOptionPane.showMessageDialog(null, "Successful Register. " + " Your ID is " + ID, "Success", JOptionPane.INFORMATION_MESSAGE);
                    br.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        else if (e.getSource()==btnCancel){
            new Main_Menu();
            setVisible(false);
        }
    }

    private void write() throws IOException{
        PrintWriter output = new PrintWriter("member.txt");
        ID=1;

        output.print(ID + ":" + txtName.getText() + ":" + cMembership.getSelectedItem() + ":" + txtIC.getText() + ":" + txtContact.getText());

        output.close();


    }

    private int read() throws IOException{
        FileReader Registration = new FileReader("member.txt");
        Scanner input = new Scanner(Registration);
        int count=1;
        while(input.hasNext()){
            String text = input.nextLine();

            String[]details= text.split(":");
             if (count==1){
                count=Integer.parseInt(details[0]);

            }
            else if(Integer.parseInt(details[0])>count){
                count= Integer.parseInt(details[0]);
            }

        }
        Registration.close();
        return(count);
    }

    private void append() throws IOException{
        int counter;
        counter =read();
        BufferedWriter out= new BufferedWriter(new FileWriter("member.txt",true));
        if (counter>0){
            out.newLine();
        }
        ID = counter + 1;
        out.write(ID + ":" + txtName.getText() + ":" + cMembership.getSelectedItem() + ":" + txtIC.getText() + ":" + txtContact.getText());
        out.close();
    }

    private int empty(){
        if (txtName.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Please enter name", "Warning",JOptionPane.WARNING_MESSAGE);
            return 1;
        }
        else if (txtIC.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Please enter IC number", "Warning",JOptionPane.WARNING_MESSAGE);
            return 1;
        }
        else if (txtContact.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Please enter contact number", "Warning",JOptionPane.WARNING_MESSAGE);
            return 1;
        }
        return 0;
    }

}
