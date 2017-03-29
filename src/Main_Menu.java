import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by dapel on 7/2/2017.
 */
public class Main_Menu extends Frame implements ActionListener{
Button btnInsert,btnModify,btnPayment,btnBack, btnSearch;

    public Main_Menu(){
        setSize(450,300);
        setLocation(750,500);
        setTitle("Main Menu");
        setLayout(new GridLayout(5,1));

        btnInsert = new Button("Insert New Details");
        btnModify = new Button("Modify Current Details");
        btnPayment = new Button("Recording Payment");
        btnSearch = new Button("Search Payment Details");
        btnBack = new Button("Back");

        add(btnInsert);
        add(btnModify);
        add(btnPayment);
        add(btnSearch);
        add(btnBack);

        btnInsert.addActionListener(this);
        btnModify.addActionListener(this);
        btnPayment.addActionListener(this);
        btnSearch.addActionListener(this);
        btnBack.addActionListener(this);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== btnInsert){
            new Registration();
            setVisible(false);
        }
        else if (e.getSource()==btnModify){
            new Modification();
            setVisible(false);
        }
        else if (e.getSource()==btnPayment){
            new Payment();
            setVisible(false);
        }
        else if (e.getSource()==btnSearch){
            new SearchPayment();
            setVisible(false);
        }
        else if (e.getSource()==btnBack){
            new Login();
            setVisible(false);
        }
    }
}
