
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;

public class FoodDetailsUser {

    JFrame FDetailsFr;
    String given_id;
//    JComboBox sType, sQuantity;
    JComboBox<String> sType, sQuantity;
    JLabel seat_type, seat_quantity;
    JDatePickerImpl datePicker;

    public FoodDetailsUser() {
    	// if(dt.equals("1")) {
    		// initFoodDetails();
    	// } else {
    		initMovieDetails_2();
    	// }

    }


    // public void initFoodDetails(){

    //     FDetailsFr = new JFrame("Details");


    //     FDetailsFr.setLayout(null);
    //     FDetailsFr.setBounds(100, 0, 700, 529);
    //     FDetailsFr.setResizable(false);
    //     FDetailsFr.setVisible(true);


    // }

    public void initMovieDetails_2(){

        FDetailsFr = new JFrame("Food Details");

        seat_type = new JLabel("Date");
        seat_type.setBounds(320, 300, 150, 40);


        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setBounds(320, 330, 200, 30);


        //
        // sType = new JComboBox<String>();
        // sType.addItem("Normal seat"); FDUSR
        // sType.addItem("Twin seat");
        // sType.addItem("Vip seat");
        // sType.setBounds(320, 330, 200, 30);

        //
        seat_quantity = new JLabel("Quantity");
        seat_quantity.setBounds(550, 300, 150, 40);
        sQuantity = new JComboBox<String>();
        sQuantity.addItem("1");
        sQuantity.addItem("2");
        sQuantity.addItem("3");
        sQuantity.addItem("4");
        sQuantity.addItem("5");
        sQuantity.addItem("6");
        sQuantity.addItem("7");
        sQuantity.setBounds(550, 330, 100, 30);

        FDetailsFr.add(seat_type);
        FDetailsFr.add(datePicker);
        FDetailsFr.add(seat_quantity);
        FDetailsFr.add(sQuantity);

        FDetailsFr.setLayout(null);
        FDetailsFr.setBounds(100, 0, 700, 529);
        FDetailsFr.setResizable(false);
        FDetailsFr.setVisible(true);


    }

    public class DateLabelFormatter extends AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }
}
