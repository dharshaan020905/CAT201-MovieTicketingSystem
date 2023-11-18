
import java.awt.Color;
import java.io.FileInputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddFood {

    JFrame miniFrame;
    JLabel titleLbl, priceLbl, cover_photo;
    JTextField preview, titleTextField, priceTextField;
    JButton choosePhoto, addFoodBtn, cancel;
    FileInputStream imageStream;

    public AddFood() {
        initGUI();
    }

    public void initGUI() {

        miniFrame = new JFrame("Add Food");
        titleLbl = new JLabel("Name:");
        priceLbl = new JLabel("Price:");
        titleTextField = new JTextField();
        priceTextField = new JTextField();
        preview = new JTextField();
        choosePhoto = new JButton("Upload Image");
        addFoodBtn = new JButton("Save Food");
        cancel = new JButton("Close");

        titleLbl.setBounds(30, 40, 150, 30);
        titleTextField.setBounds(30, 70, 350, 30);
        priceLbl.setBounds(30, 120, 150, 30);
        priceTextField.setBounds(30, 150, 350, 30);



        preview.setBounds(500, 20, 200, 200);
        preview.setEditable(false);
        choosePhoto.setBounds(545, 220, 120, 30);
        choosePhoto.setBackground(Color.black);
        choosePhoto.setForeground(Color.WHITE);
        cancel.setBounds(250, 285, 100, 30);
        cancel.setBackground(Color.black);
        cancel.setForeground(Color.WHITE);
        addFoodBtn.setBounds(400, 285, 150, 30);
        addFoodBtn.setBackground(Color.black);
        addFoodBtn.setForeground(Color.WHITE);

        miniFrame.add(titleLbl);
        miniFrame.add(titleTextField);
        miniFrame.add(priceLbl);
        miniFrame.add(priceTextField);
        miniFrame.add(preview);
        miniFrame.add(choosePhoto);
        miniFrame.add(cancel);
        miniFrame.add(addFoodBtn);

        miniFrame.setLayout(null);
        miniFrame.setResizable(false);
        miniFrame.setSize(800, 375);
        miniFrame.setVisible(true);
    }

}
