import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import java.util.*;
import java.io.*;


/**
 * Created by dapel on 19/2/2017.
 */
public class SearchPayment extends Frame implements ActionListener {
    private TextField txtSearch, txtStaff, txtName, txtType;
    private JTextArea txtDate;
    private Button btnBack, btnSearch;
    private String Fees;
    private int cusID;


    public SearchPayment() {
        setSize(500, 450);
        setLocation(750, 500);
        setTitle("Search Payment Details");
        setLayout(new BorderLayout());

        Panel input = new Panel();
        input.setLayout(new GridLayout(6, 1));
        input.setBackground(Color.orange);

        Panel search = new Panel();
        search.setLayout(new FlowLayout());
        search.setBackground(Color.lightGray);
        Label lblMembership = new Label("Enter ID To Search: ");
        txtSearch = new TextField("", 20);
        btnSearch = new Button("Search");
        search.add(lblMembership);
        search.add(txtSearch);
        search.add(btnSearch);

        Panel namePanel = new Panel();
        namePanel.setLayout(new FlowLayout());
        Label lblName = new Label("Name: ");
        txtName = new TextField("", 20);
        namePanel.add(lblName);
        namePanel.add(txtName);

        Panel typemember = new Panel();
        typemember.setLayout(new FlowLayout());
        Label lblType = new Label("Type of Membership: ");
        txtType = new TextField("", 20);
        typemember.add(lblType);
        typemember.add(txtType);

        Panel dateP = new Panel();
        dateP.setLayout(new FlowLayout());
        Label lblDate = new Label("Date of Payment and Total Fees: ");
        txtDate = new JTextArea(2,20);
        dateP.add(lblDate);
        dateP.add(txtDate);


        Panel Entries = new Panel();
        Entries.setLayout(new FlowLayout());
        Label lblStaff = new Label("Entries made by: ");
        txtStaff = new TextField("", 20);
        Entries.add(lblStaff);
        Entries.add(txtStaff);



        Panel buttonP = new Panel();
        buttonP.setLayout(new FlowLayout());
        btnBack = new Button("Back");
        buttonP.add(btnBack);

        input.add(search);
        input.add(namePanel);
        input.add(typemember);
        input.add(dateP);
        input.add(Entries);
        input.add(buttonP);
        add(input);

        btnBack.addActionListener(this);
        btnSearch.addActionListener(this);


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });


        setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            new Main_Menu();
            setVisible(false);
        } else if (e.getSource() == btnSearch) {

            try {
                txtName.setText("");
                txtType.setText("");
                txtStaff.setText("");
                txtDate.setText("");
                readPayment();
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }

    public void readPayment() throws IOException {
        int found=1;
        FileReader sp = new FileReader("payment.txt");
        Scanner s = new Scanner(sp);
        while (s.hasNext()) {
            String text = s.nextLine();

            String[] details = text.split(":");
            if (txtSearch.getText().equals(details[0])) {
                Fees = details[2];
                txtDate.append(details[1] + "-"+ Fees + "\n");
                txtStaff.setText(details[3]);
                readMember();
                found=0;
            }
        }
        sp.close();
        if(found==1){

            JOptionPane.showMessageDialog(null, "Payment not found", "Warning", JOptionPane.ERROR_MESSAGE);
            txtName.setText("");
            txtType.setText("");
            txtStaff.setText("");
            txtDate.setText("");

        }
        return;

    }

    public void readMember() throws IOException {
        FileReader rm = new FileReader("member.txt");
        Scanner r = new Scanner(rm);

        while (r.hasNext()) {
            String text = r.nextLine();

            String[] details = text.split(":");
            if (txtSearch.getText().equals(details[0])) {
                txtName.setText(details[1]);
                txtType.setText(details[2]);

            }
        }

        rm.close();
    }

}

