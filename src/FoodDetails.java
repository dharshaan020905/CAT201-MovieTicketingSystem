
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class FoodDetails {

    JFrame FDetailsFr;
    String given_id;
//    JComboBox sType, sQuantity;
    JComboBox<String> sType, sQuantity;
     JLabel seat_type, seat_quantity;

    public FoodDetails() {
    	// if(dt.equals("1")) {
    		initFoodDetails();
    	// } else {
    	// 	initMovieDetails_2();
    	// }

    }


    public void initFoodDetails(){

        FDetailsFr = new JFrame("Details");


        FDetailsFr.setLayout(null);
        FDetailsFr.setBounds(100, 0, 700, 529);
        FDetailsFr.setResizable(false);
        FDetailsFr.setVisible(true);


    }

    // public void initMovieDetails_2(){

    //     MDetailsFr = new JFrame("Details");

    //     seat_type = new JLabel("Seat type");
    //     seat_type.setBounds(320, 300, 150, 40);
    //     sType = new JComboBox<String>();
    //     sType.addItem("Normal seat");
    //     sType.addItem("Twin seat");
    //     sType.addItem("Vip seat");
    //     sType.setBounds(320, 330, 200, 30);
    //     seat_quantity = new JLabel("Quantity");
    //     seat_quantity.setBounds(550, 300, 150, 40);
    //     sQuantity = new JComboBox<String>();
    //     sQuantity.addItem("1");
    //     sQuantity.addItem("2");
    //     sQuantity.addItem("3");
    //     sQuantity.addItem("4");
    //     sQuantity.addItem("5");
    //     sQuantity.addItem("6");
    //     sQuantity.addItem("7");
    //     sQuantity.setBounds(550, 330, 100, 30);

    //     MDetailsFr.add(seat_type);
    //     MDetailsFr.add(sType);
    //     MDetailsFr.add(seat_quantity);
    //     MDetailsFr.add(sQuantity);

    //     MDetailsFr.setLayout(null);
    //     MDetailsFr.setBounds(100, 0, 700, 529);
    //     MDetailsFr.setResizable(false);
    //     MDetailsFr.setVisible(true);


    // }

}
