/**
 * Created by dapel on 7/2/2017.
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import javax.swing.*;
import java.util.*;

public class Payment extends Frame implements ActionListener{
    private TextField txtSearch, txtDate, txtFees, txtStaff, txtName, txtType;
    private Choice chcTop;
    private Button btnRecord, btnCancel, btnSearch;
    private String cusName,cusMember;
    private int cusID;



    public Payment(){
        setSize(450,300);
        setLocation(750,500);
        setTitle("Recording Payment");
        setLayout(new BorderLayout());

        Panel input = new Panel();
        input.setLayout(new GridLayout(8,1));
        input.setBackground(Color.orange);

        Panel search =  new Panel();
        search.setLayout(new FlowLayout());
        search.setBackground(Color.lightGray);
        Label lblMembership =  new Label("Enter ID To Search: ");
        txtSearch = new TextField("",20);
        btnSearch = new Button("Search");
        search.add(lblMembership);
        search.add(txtSearch);
        search.add(btnSearch);

        Panel namePanel =  new Panel();
        namePanel.setLayout(new FlowLayout());
        Label lblName =  new Label("Name: ");
        txtName = new TextField("",20);
        namePanel.add(lblName);
        namePanel.add(txtName);

        Panel typemember =  new Panel();
        typemember.setLayout(new FlowLayout());
        Label lblType =  new Label("Type of Membership: ");
        txtType = new TextField("",20);
        typemember.add(lblType);
        typemember.add(txtType);

        Panel dateP =  new Panel();
        dateP.setLayout(new FlowLayout());
        Label lblDate =  new Label("Date of Payment: ");
        txtDate = new TextField("",20);
        dateP.add(lblDate);
        dateP.add(txtDate);

        Panel top =  new Panel();
        top.setLayout(new FlowLayout());
        Label lbltop =  new Label("Type of Fees: ");
        chcTop = new Choice();
        chcTop.add("Registration");
        chcTop.add("Monthly");


        top.add(lbltop);
        top.add(chcTop);



        Panel Entries =  new Panel();
        Entries.setLayout(new FlowLayout());
        Label lblStaff =  new Label("Entries made by: ");
        txtStaff = new TextField("",20);
        Entries.add(lblStaff);
        Entries.add(txtStaff);

        Panel Fees =  new Panel();
        Fees.setLayout(new FlowLayout());
        Label lblFees =  new Label("Total Fees: ");
        txtFees = new TextField("",20);
        Fees.add(lblFees);
        Fees.add(txtFees);

        Panel buttonP =  new Panel();
        buttonP.setLayout(new FlowLayout());
        btnRecord = new Button("Calculate");
        btnCancel = new Button("Cancel");
        buttonP.add(btnRecord);
        buttonP.add(btnCancel);



        input.add(search);
        input.add(namePanel);
        input.add(typemember);
        input.add(dateP);
        input.add(top);
        input.add(Entries);
        input.add(Fees);
        input.add(buttonP);
        add(input);

        btnRecord.addActionListener(this);
        btnCancel.addActionListener(this);
        btnSearch.addActionListener(this);


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        txtName.setEnabled(false);
        txtType.setEnabled(false);
        txtFees.setEnabled(false);
        txtStaff.setEnabled(false);
        txtDate.setEnabled(false);
        chcTop.setEnabled(false);
        chcTop.addItemListener(this::itemStateChange);
        btnRecord.setEnabled(false);

        setVisible(true);

    }

    public void itemStateChange(ItemEvent i){
        if(i.getStateChange()==ItemEvent.SELECTED){
            btnRecord.setLabel("Calculate");
        }
    }

    public void actionPerformed(ActionEvent e) {
        int i = 0;
        String choice, type;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();




        if (e.getSource() == btnRecord) {
            if(btnRecord.getLabel()=="Calculate") {
                btnRecord.setLabel("Record");
                choice = chcTop.getSelectedItem();
                Fees totalFees = new Fees();
                type = txtType.getText();

                i = totalFees.Fees(type, choice);

                txtFees.setText(Integer.toString(i));
                txtDate.setText(df.format(date));



                txtStaff.setText(Login.user);

            }

            else{
                try{
                write();
                JOptionPane.showMessageDialog(null,"Successfully Recorded!", "Success",JOptionPane.INFORMATION_MESSAGE);
                    txtName.setText("");
                    txtType.setText("");
                    txtStaff.setText("");
                    txtDate.setText("");
                    txtFees.setText("");
                }
                catch (IOException e2){
                    e2.printStackTrace();
                }
            }

        } else if (e.getSource() == btnCancel) {
            new Main_Menu();
            setVisible(false);
        } else if (e.getSource() == btnSearch) {
            try{
                if(txtSearch.getText().isEmpty()==false){
                readFile();
                txtName.setText(cusName);
                txtType.setText(cusMember);

                }
                else {
                    JOptionPane.showMessageDialog(null, "Enter ID!", "Warning", JOptionPane.ERROR_MESSAGE);

                }

            }
            catch(IOException io){
                io.printStackTrace();
            }


        }
    }

    private void readFile() throws IOException{
        int found=0;
        File spayment = new File("member.txt");
        Scanner input = new Scanner(spayment);

        while(input.hasNext()){
            String text = input.nextLine();

            String[] details = text.split(":");
            if (Integer.parseInt(details[0])==Integer.parseInt(txtSearch.getText())){
                cusID = Integer.parseInt(details[0]);
                cusName = details[1];
                cusMember = details[2];
                txtFees.setEnabled(true);
                txtStaff.setEnabled(true);
                txtDate.setEnabled(true);
                chcTop.setEnabled(true);
                btnRecord.setEnabled(true);
                found=1;

            }


        }
        if(found==0){
            JOptionPane.showMessageDialog(null,"ID not found!", "Warning",JOptionPane.ERROR_MESSAGE);
            txtName.setText("");
            txtType.setText("");
        }
        input.close();
    }

    private void write() throws IOException{

        FileWriter fwrite = new FileWriter("payment.txt", true);
        PrintWriter pwrite = new PrintWriter(fwrite);

        pwrite.println(cusID + ":" + txtDate.getText() + ":" + txtFees.getText() + ":" + txtStaff.getText());

        pwrite.close();

    }







}
