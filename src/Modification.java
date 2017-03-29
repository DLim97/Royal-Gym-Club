/**
 * Created by dapel on 7/2/2017.
 */

import jdk.jfr.events.FileWriteEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;


public class Modification extends Frame implements ActionListener {
    private TextField txtName, txtIC, txtContact, txtSearch;
    private Button btnModify, btnCancel, btnSearch;
    private Choice cMembership;
    private String name, member, ic, contact;
    private int id;


    public Modification() {
        setSize(450, 300);
        setLocation(750, 500);
        setTitle("Modification");
        setLayout(new BorderLayout());

        Panel input = new Panel();
        input.setLayout(new GridLayout(6, 1));
        input.setBackground(Color.orange);

        Panel search = new Panel();
        search.setLayout(new FlowLayout());
        search.setBackground(Color.gray);
        Label lblsearch = new Label("Enter ID To Search: ");
        txtSearch = new TextField("", 20);
        btnSearch = new Button("Search");
        search.add(lblsearch);
        search.add(txtSearch);
        search.add(btnSearch);


        Panel namePanel = new Panel();
        namePanel.setLayout(new FlowLayout());
        Label lblName = new Label("Name: ");
        txtName = new TextField("", 20);
        namePanel.add(lblName);
        namePanel.add(txtName);

        Panel membertype = new Panel();
        membertype.setLayout(new FlowLayout());
        Label lblMember = new Label("Type of Member: ");
        cMembership = new Choice();
        cMembership.add("Deluxe");
        cMembership.add("Non-Deluxe");
        cMembership.add("Weekday");
        membertype.add(lblMember);
        membertype.add(cMembership);

        Panel ICPanel = new Panel();
        ICPanel.setLayout(new FlowLayout());
        Label lblIC = new Label("IC: ");
        txtIC = new TextField("", 20);
        ICPanel.add(lblIC);
        ICPanel.add(txtIC);

        Panel ContactPanel = new Panel();
        ContactPanel.setLayout(new FlowLayout());
        Label lblContact = new Label("Contact Number: ");
        txtContact = new TextField("", 20);
        ContactPanel.add(lblContact);
        ContactPanel.add(txtContact);

        Panel buttonP = new Panel();
        buttonP.setLayout(new FlowLayout());
        btnModify = new Button("Modify");
        btnCancel = new Button("Cancel");
        buttonP.add(btnModify);
        buttonP.add(btnCancel);


        input.add(search);
        input.add(namePanel);
        input.add(membertype);
        input.add(ICPanel);
        input.add(ContactPanel);
        input.add(buttonP);
        add(input);

        btnModify.addActionListener(this);
        btnCancel.addActionListener(this);
        btnSearch.addActionListener(this);


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);

        txtName.setEnabled(false);
        cMembership.setEnabled(false);
        txtContact.setEnabled(false);
        txtIC.setEnabled(false);
        btnModify.setEnabled(false);


    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnModify) {
            if (empty()==0) {
                try {
                    modify();
                    JOptionPane.showMessageDialog(null, "Modification Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clear();
                    cMembership.select("Deluxe");

                } catch (IOException mod) {
                    mod.printStackTrace();
                }
            }
        } else if (e.getSource() == btnCancel) {
            new Main_Menu();
            setVisible(false);
        } else if (e.getSource() == btnSearch) {
            try {

                if (txtSearch.getText().isEmpty()==false) {
                    readFile();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Enter ID!", "Warning", JOptionPane.ERROR_MESSAGE);

                }

            } catch (IOException io) {
                JOptionPane.showMessageDialog(null, "Error Occured!", "Warning", JOptionPane.ERROR_MESSAGE);
            }


        }
    }


    private void readFile() throws IOException {
        int notfound = 1;
        File modify = new File("member.txt");
        Scanner input = new Scanner(modify);

        while (input.hasNext()) {
            String text = input.nextLine();

            String[] details = text.split(":");
            if (Integer.parseInt(details[0]) == Integer.parseInt(txtSearch.getText())) {
                id = Integer.parseInt(details[0]);
                name = details[1];
                member = details[2];
                ic = details[3];
                contact = details[4];

                txtIC.setEnabled(true);
                cMembership.setEnabled(true);
                txtContact.setEnabled(true);
                btnModify.setEnabled(true);

                txtName.setText(name);
                txtIC.setText(ic);
                txtContact.setText(contact);
                cMembership.select(member);
                notfound = 0;
            }

        }
        if (notfound == 1) {
            JOptionPane.showMessageDialog(null, "ID not found!", "Warning", JOptionPane.ERROR_MESSAGE);
            clear();
            cMembership.select("Deluxe");
            txtName.setEnabled(false);
            cMembership.setEnabled(false);
            txtContact.setEnabled(false);
            txtIC.setEnabled(false);
            btnModify.setEnabled(false);
        }
        input.close();
    }

    private void modify() throws IOException {
        String newdetails = new String();
        String olddetails = new String();
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader("member.txt"));
            bw = new BufferedWriter(new FileWriter("tempMember.txt"));//temporary file name
            String row;
            while ((row = br.readLine()) != null) {
                olddetails = row;

                if (row.contains(id + ":" + name + ":" + member + ":" + ic + ":" + contact)) {

                    member = cMembership.getSelectedItem();
                    ic = txtIC.getText();
                    contact = txtContact.getText();
                    newdetails = id + ":" + name + ":" + member + ":" + ic + ":" + contact;

                    row = row.replace(olddetails, newdetails);
                }
                bw.write(row + "\n");
            }
        } catch (Exception e) {
            return;
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {

            }
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {

            }
        }
        // Deleting old file
        File oldFile = new File("member.txt");
        oldFile.delete();

        // Renaming new file
        File newFile = new File("tempMember.txt");
        newFile.renameTo(oldFile);

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

    private void clear(){
        txtName.setText("");
        txtIC.setText("");
        txtContact.setText("");
    }

}




