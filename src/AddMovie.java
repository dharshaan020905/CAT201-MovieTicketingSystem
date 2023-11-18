
import java.awt.Color;
import java.io.FileInputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddMovie {

    JFrame miniFrame;
    JLabel titleLbl;
    JLabel genereLbl;
    JLabel durationLbl;
    JLabel cover_photo;
    JLabel dirLbl;
    JLabel descripLbl;
    JLabel summryLbl;
    JTextField preview;
    JTextField titleTextField;
    JTextField genereTextField;
    JTextField durationTextField;
    JTextField descTextField;
    JTextField dirTextField;
    JTextField summryTextField;
    JButton choosePhoto;
    JButton addRecordBtn;
    JButton cancel;
    FileInputStream imageStream;

    public AddMovie() {
        initGUI();
    }

    public void initGUI() {

        miniFrame = new JFrame("Add Movie");
        titleLbl = new JLabel("Movie Title:");
        genereLbl = new JLabel("Movie Genre:");
        durationLbl = new JLabel("Movie Duration:");
        dirLbl = new JLabel("Movie Director Name:");
        descripLbl = new JLabel("Movie Description:");
        summryLbl = new JLabel("Movie Summary:");
        titleTextField = new JTextField();
        genereTextField = new JTextField();
        durationTextField = new JTextField();
        descTextField = new JTextField();
        dirTextField = new JTextField();
        summryTextField = new JTextField();
        preview = new JTextField();
        choosePhoto = new JButton("Upload");
        addRecordBtn = new JButton("Add to Record");
        cancel = new JButton("Cancel");

        titleLbl.setBounds(30, 20, 150, 30);
        titleTextField.setBounds(30, 50, 350, 30);
        genereLbl.setBounds(30, 100, 150, 30);
        genereTextField.setBounds(30, 130, 350, 30);
        durationLbl.setBounds(30, 180, 150, 30);
        durationTextField.setBounds(30, 210, 350, 30);
        dirLbl.setBounds(30, 260, 150, 30);
        dirTextField.setBounds(30, 290, 350, 30);
        descripLbl.setBounds(30, 340, 150, 30);
        descTextField.setBounds(30, 370, 350, 30);
        summryLbl.setBounds(30, 420, 150, 30);
        summryTextField.setBounds(30, 450, 350, 30);



        preview.setBounds(500, 20, 200, 200);
        preview.setEditable(false);
        choosePhoto.setBounds(550, 220, 100, 30);
        choosePhoto.setBackground(Color.black);
        choosePhoto.setForeground(Color.WHITE);
        cancel.setBounds(420, 450, 100, 30);
        cancel.setBackground(Color.black);
        cancel.setForeground(Color.WHITE);
        addRecordBtn.setBounds(570, 450, 150, 30);
        addRecordBtn.setBackground(Color.black);
        addRecordBtn.setForeground(Color.WHITE);

        miniFrame.add(titleLbl);
        miniFrame.add(titleTextField);
        miniFrame.add(genereLbl);
        miniFrame.add(genereTextField);
        miniFrame.add(durationLbl);
        miniFrame.add(durationTextField);
        miniFrame.add(dirLbl);
        miniFrame.add(dirTextField);
        miniFrame.add(descripLbl);
        miniFrame.add(descTextField);
        miniFrame.add(summryLbl);
        miniFrame.add(summryTextField);
        miniFrame.add(preview);
        miniFrame.add(choosePhoto);
        miniFrame.add(cancel);
        miniFrame.add(addRecordBtn);

        miniFrame.setLayout(null);
        miniFrame.setResizable(false);
        miniFrame.setSize(800, 550);
        miniFrame.setVisible(true);
    }

}
