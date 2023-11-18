
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JComboBox;

public class MainGUI {

    JFrame fr;
    ImageIcon logo;
    SignInGUI sIn;
    SignUpGUI sUp;
    ButtonHandler hnd;
    DataConnector dCon;
    HomePage HP;
    Dashboard DB;
    Admin Ad;
    ChangePass changePassFrame;
    AddMovie addMovieFrame;
    AddFood addFoodFrame;
    MovieDetails MD;
    FoodDetails FD;
    FoodDetailsUser FDUSR;
    Scheduling SFr;
    String id;

    MainGUI() throws SQLException {
        initGUI();
    }

    public void initGUI() throws SQLException {
        fr = new JFrame("Book It");
        logo = new ImageIcon(getClass().getClassLoader().getResource(".\\res\\logo.jpg"));
        fr.setIconImage(logo.getImage());
        sUp = new SignUpGUI();
        sIn = new SignInGUI();
        hnd = new ButtonHandler();
        dCon = new DataConnector();
        HP = new HomePage(hnd);
        DB = new Dashboard(hnd);
        Ad = new Admin(hnd);

        sIn.signUpBtn.addActionListener(hnd);
        sIn.signInBtn.addActionListener(hnd);
        sUp.signInBtn.addActionListener(hnd);
        sUp.signUpBtn.addActionListener(hnd);

        sIn.signInPnl.setVisible(true);
        fr.add(sIn.signInPnl);
        fr.add(sUp.signUpPnl);
        fr.add(HP.mainPnl);
        fr.add(DB.dashboardPnl);
        fr.add(Ad.dashboardPnl);

        fr.setVisible(true);
        fr.setLayout(null);
        fr.setSize(1100, 680);
        fr.setLocationRelativeTo(null);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setResizable(false);

    }

    public class ButtonHandler implements ActionListener, MouseListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(sIn.signUpBtn)) {
                sIn.signInPnl.setVisible(false);
                sUp.signUpPnl.setVisible(true);
            } else if (e.getSource().equals(sUp.signInBtn)) {
                sUp.signUpPnl.setVisible(false);
                sIn.signInPnl.setVisible(true);
            } else if (e.getSource().equals(sUp.signUpBtn)) {

                if (sUp.userName.getText().trim().equals("") || sUp.cnic.getText().trim().equals("") || sUp.pass.getText().trim().equals("") || sUp.phone.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please Fill Every Field", "SignUp", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    dCon.addUserRecord(sUp.userName.getText(), sUp.phone.getText(), sUp.cnic.getText(), sUp.pass.getText());
                    sUp.userName.setText("");
                    sUp.cnic.setText("");
                    sUp.pass.setText("");
                    sUp.phone.setText("");
                    sIn.signInPnl.setVisible(true);
                    sUp.signUpPnl.setVisible(false);
                }

            } else if (e.getSource().equals(sIn.signInBtn)) {

                if (sIn.CNIC.getText().trim().equals("") || sIn.pass.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please Fill Every Field", "Sign In Failed", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    String uName = dCon.getUserName(sIn.CNIC.getText(), sIn.pass.getText());
                    String uID = dCon.getUserID(sIn.CNIC.getText());
                    id = uID;
                    sIn.pass.setText("");

                    if (uName != null) {
                        HP.setName(uName);
                        DB.setData(uName, uID);
                        Ad.setData(uName);
                        try {
                            HP.updateHomePnl();
                        } catch (SQLException ex) {
                            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        if (dCon.isAdmin(id)) {
                            try {

                                Ad.updateDashboard();
                                sIn.signInPnl.setVisible(false);
                                Ad.dashboardPnl.setVisible(true);
                            } catch (SQLException ex) {
                                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            sIn.signInPnl.setVisible(false);

                            HP.mainPnl.setVisible(true);
                        }

                    }
                }
            } else if (e.getActionCommand().equals("LOG OUT")) {
                sIn.signInPnl.setVisible(true);
                HP.search.setText("");
                HP.mainPnl.setVisible(false);
                DB.dashboardPnl.setVisible(false);
                Ad.dashboardPnl.setVisible(false);
            } else if (e.getActionCommand().equals("Dashboard")) {
                try {
                    if (dCon.isAdmin(id)) {
                        Ad.updateDashboard();
                        Ad.dashboardPnl.setVisible(true);
                    } else {
                        DB.updateDashboard();
                        DB.dashboardPnl.setVisible(true);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                HP.mainPnl.setVisible(false);
            } else if (e.getActionCommand().equals("Go")) {
                try {
                    if (!HP.search.getText().trim().equals("")) {
                        HP.updateOnSearch();
                        HP.mainPnl.setVisible(false);
                        HP.mainPnl.setVisible(true);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else if (e.getActionCommand().equals("Home")) {
                HP.mainPnl.setVisible(true);
                Ad.dashboardPnl.setVisible(false);
                DB.dashboardPnl.setVisible(false);

            } else if (e.getActionCommand().equals("Change Password")) {
                changePassFrame = new ChangePass();
                changePassFrame.miniFrame.setLocationRelativeTo(fr);
                changePassFrame.miniFrame.setIconImage(logo.getImage());
                changePassFrame.cancel.addActionListener(hnd);
                changePassFrame.confirm.addActionListener(hnd);
            } else if (e.getActionCommand().equals("Add Movie")) {
                addMovieFrame = new AddMovie();
                addMovieFrame.miniFrame.setLocationRelativeTo(fr);
                addMovieFrame.miniFrame.setIconImage(logo.getImage());
                addMovieFrame.cancel.addActionListener(hnd);
                addMovieFrame.choosePhoto.addActionListener(hnd);
                addMovieFrame.addRecordBtn.addActionListener(hnd);
            } else if (e.getActionCommand().equals("Add Food")) {
                addFoodFrame = new AddFood();
                addFoodFrame.miniFrame.setLocationRelativeTo(fr);
                addFoodFrame.miniFrame.setIconImage(logo.getImage());
                addFoodFrame.cancel.addActionListener(hnd);
                addFoodFrame.choosePhoto.addActionListener(hnd);
                addFoodFrame.addFoodBtn.addActionListener(hnd);
            } else if (e.getActionCommand().equals("Cancel")) {
                if (addMovieFrame != null) {
                    addMovieFrame.miniFrame.dispose();
                } else if (changePassFrame != null) {
                    changePassFrame.miniFrame.dispose();
                } else if (addFoodFrame != null) {
                    addFoodFrame.miniFrame.dispose();
                }
            }  else if (e.getActionCommand().equals("Close")) {
                if (addFoodFrame != null) {
                    addFoodFrame.miniFrame.dispose();
                    try {
						Ad.updateDashboard();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                } else if (changePassFrame != null) {
                    changePassFrame.miniFrame.dispose();
                } else if (addMovieFrame != null) {
                    addMovieFrame.miniFrame.dispose();
                    try {
						Ad.updateDashboard();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            } else if (e.getActionCommand().equals("Confirm")) {
                int n = dCon.updatePassword(sIn.CNIC.getText(), changePassFrame.oldPass.getText(), changePassFrame.newPass.getText());
                if (n < 1) {
                    JOptionPane.showMessageDialog(null, "Invalid Password", "Password Update fail", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    changePassFrame.miniFrame.dispose();
                    JOptionPane.showMessageDialog(null, "Password Updated Successfully", "Password Update", JOptionPane.INFORMATION_MESSAGE);
                }

            } else if (e.getActionCommand().equals("\u2770 Home")) {
                try {
                    HP.updateHomePnl();
                    HP.mainPnl.setVisible(false);
                    HP.mainPnl.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (e.getActionCommand().equals("Upload")) {
                JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.home")));
                chooser.showOpenDialog(fr);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "png");
                chooser.addChoosableFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);
                File pic = chooser.getSelectedFile();

                try {
                    if (pic != null) {
                        String path = pic.getAbsolutePath();
                        File image = new File(path);

                        addMovieFrame.preview.setText(pic.getName());
                        addMovieFrame.imageStream = new FileInputStream(image);
                    }

                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Select a .jpg file", "Selection failed", JOptionPane.INFORMATION_MESSAGE);
                }
            }  else if (e.getActionCommand().equals("Upload Image")) {
                JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.home")));
                chooser.showOpenDialog(fr);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "png");
                chooser.addChoosableFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);
                File pic = chooser.getSelectedFile();

                try {
                    if (pic != null) {
                        String path = pic.getAbsolutePath();
                        File image = new File(path);

                        addFoodFrame.preview.setText(pic.getName());
                        addFoodFrame.imageStream = new FileInputStream(image);
                    }

                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Select a .jpg file", "Selection failed", JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (e.getActionCommand().equals("Add to Record")) {
                if (addMovieFrame.titleTextField.getText().trim().equals("") || addMovieFrame.genereTextField.getText().trim().equals("") || addMovieFrame.durationTextField.getText().trim().equals("") || addMovieFrame.descTextField.getText().trim().equals("") || addMovieFrame.dirTextField.getText().trim().equals("") || addMovieFrame.summryTextField.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please Fill Every Field", "Insertion", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    dCon.addMovieRecord(addMovieFrame.titleTextField.getText(), addMovieFrame.genereTextField.getText(), addMovieFrame.durationTextField.getText(), addMovieFrame.descTextField.getText(), addMovieFrame.dirTextField.getText(), addMovieFrame.summryTextField.getText(), addMovieFrame.imageStream);
                    addMovieFrame.titleTextField.setText("");
                    addMovieFrame.genereTextField.setText("");
                    addMovieFrame.durationTextField.setText("");
                    addMovieFrame.descTextField.setText("");
                    addMovieFrame.dirTextField.setText("");
                    addMovieFrame.summryTextField.setText("");
                    try {
						Ad.updateDashboard();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            }  else if (e.getActionCommand().equals("Save Food")) {
                if (addFoodFrame.titleTextField.getText().trim().equals("") || addFoodFrame.priceTextField.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please Fill Every Field", "Insertion", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    dCon.addFoodRecord(addFoodFrame.titleTextField.getText(), addFoodFrame.priceTextField.getText(), addFoodFrame.imageStream);
                    addFoodFrame.titleTextField.setText("");
                    addFoodFrame.priceTextField.setText("");
                    try {
						Ad.updateDashboard();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            } else if (e.getActionCommand().equals("Confirm Booking")) {
            	dCon.confirmBooking(id, MD.given_id, MD.sType.getSelectedItem().toString(), MD.sQuantity.getSelectedItem().toString());
                MD.MDetailsFr.dispose();
            } else if (e.getActionCommand().equals("Cancel Booking")) {
                dCon.cancelBooking(id, MD.given_id);
                try {
                    Ad.updateDashboard();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                MD.MDetailsFr.dispose();
            } else if (e.getActionCommand().equals("Delete Movie")) {

                dCon.deleteMovie(MD.given_id);
                try {
                    Ad.updateDashboard();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                MD.MDetailsFr.dispose();
            } else if (e.getActionCommand().equals("Delete Food")) {
                dCon.deleteFood(FD.given_id);
                try {
                    Ad.updateDashboard();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                FD.FDetailsFr.dispose();
            } else if (e.getActionCommand().equals("Order Food")) {
                dCon.confirmFood(id, FDUSR.datePicker.getJFormattedTextField().getText(), FDUSR.given_id, FDUSR.sQuantity.getSelectedItem().toString());
                try {
                    Ad.updateDashboard();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                FDUSR.FDetailsFr.dispose();
            } else if (e.getActionCommand().equals("Add to Schedule")) {
                schedule_a_movie(MD.given_id);
                MD.MDetailsFr.dispose();
            } else if (e.getActionCommand().equals("Confirm Schedule")) {
                dCon.addToSchedual(SFr.given_id,SFr.price_txt.getText(),SFr.stime_txt.getText(),SFr.etime_txt.getText(),SFr.datePicker.getJFormattedTextField().getText(),SFr.sHall.getSelectedItem().toString(),SFr.twin_price_txt.getText(),SFr.vip_price_txt.getText());
               JOptionPane.showMessageDialog(null, "Schedule added", "Completed",  JOptionPane.INFORMATION_MESSAGE);
                try {
                    Ad.updateDashboard();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                SFr.schedulingFr.dispose();
            }

        }

        public void dashboard_movie_Details_function(String sid) {
            MD = new MovieDetails("1");
            MD.MDetailsFr.setLocationRelativeTo(fr);
            MD.MDetailsFr.setIconImage(logo.getImage());
            MD.given_id = sid;
            JLabel movie_name, movie_picture, rate, date, time, rate_twin, rate_vip;
            JButton cancleBookBtn;

            try {

                ResultSet rs = dCon.getdMoviesDetailsBySchedual_ID(sid);
                rs.next();

                Blob b = rs.getBlob(2);
                Image img = ImageIO.read(b.getBinaryStream()).getScaledInstance(300, 500, Image.SCALE_SMOOTH);

                movie_picture = new JLabel(new ImageIcon(img));
                movie_picture.setBounds(0, 0, 300, 500);
                movie_name = new JLabel(rs.getString(1));
                movie_name.setBounds(320, 20, 250, 40);
                movie_name.setFont(new Font("Times new roman", Font.BOLD, 28));
                rate = new JLabel("Price: RM " + rs.getString(3));
                rate.setBounds(320, 65, 200, 20);
                rate_twin = new JLabel("Price Twin: RM " + rs.getString(6));
                rate_twin.setBounds(320, 85, 200, 20);
                rate_vip = new JLabel("Price Vip: RM " + rs.getString(7));
                rate_vip.setBounds(320, 105, 200, 20);
                date = new JLabel("Date: " + rs.getString(4));
                date.setBounds(320, 125, 200, 20);
                time = new JLabel("Time: " + rs.getString(5));
                time.setBounds(320, 145, 200, 20);

                cancleBookBtn = new JButton("Cancel Booking");
                cancleBookBtn.addActionListener(hnd);
                cancleBookBtn.setBounds(350, 400, 300, 50);
                cancleBookBtn.setBackground(Color.black);
                cancleBookBtn.setForeground(Color.WHITE);

                MD.MDetailsFr.add(movie_name);
                MD.MDetailsFr.add(movie_picture);
                MD.MDetailsFr.add(rate);
                MD.MDetailsFr.add(rate_twin);
                MD.MDetailsFr.add(rate_vip);
                MD.MDetailsFr.add(date);
                MD.MDetailsFr.add(time);
                MD.MDetailsFr.add(cancleBookBtn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void schedule_a_movie(String sid) {
            SFr = new Scheduling();
            SFr.schedulingFr.setLocationRelativeTo(fr);
            SFr.schedulingFr.setIconImage(logo.getImage());
            SFr.given_id = sid;
            JLabel movie_name, movie_picture;
            JButton scheduleBtn;

            try {

                ResultSet rs = dCon.getMoviesDetailsByMovie_ID(sid);
                rs.next();

                Blob b = rs.getBlob(2);
                Image img = ImageIO.read(b.getBinaryStream()).getScaledInstance(300, 500, Image.SCALE_SMOOTH);

                movie_picture = new JLabel(new ImageIcon(img));
                movie_picture.setBounds(0, 0, 300, 500);
                movie_name = new JLabel(rs.getString(1));
                movie_name.setBounds(320, 20, 250, 40);
                movie_name.setFont(new Font("Times new roman", Font.BOLD, 28));

                rs = dCon.getHallsName();
                while (rs.next()) {
                    SFr.sHall.addItem(rs.getString(1));
                }

                scheduleBtn = new JButton("Confirm Schedule");
                scheduleBtn.addActionListener(hnd);
                scheduleBtn.setBounds(350, 490, 300, 50);
                scheduleBtn.setBackground(Color.black);
                scheduleBtn.setForeground(Color.WHITE);
                scheduleBtn.addActionListener(hnd);

                SFr.schedulingFr.add(movie_name);
                SFr.schedulingFr.add(movie_picture);
                SFr.schedulingFr.add(scheduleBtn);

            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public void movie_basic_Details_function(String sid) {
            MD = new MovieDetails("1");
            MD.MDetailsFr.setLocationRelativeTo(fr);
            MD.MDetailsFr.setIconImage(logo.getImage());
            MD.given_id = sid;
            JLabel movie_name, movie_picture;
            JButton scheduleBtn, deleteMovieBtn;

            try {

                ResultSet rs = dCon.getMoviesDetailsByMovie_ID(sid);
                rs.next();

                Blob b = rs.getBlob(2);
                Image img = ImageIO.read(b.getBinaryStream()).getScaledInstance(300, 500, Image.SCALE_SMOOTH);

                movie_picture = new JLabel(new ImageIcon(img));
                movie_picture.setBounds(0, 0, 300, 500);
                movie_name = new JLabel(rs.getString(1));
                movie_name.setBounds(320, 20, 250, 40);
                movie_name.setFont(new Font("Times new roman", Font.BOLD, 28));

                scheduleBtn = new JButton("Add to Schedule");
                scheduleBtn.addActionListener(hnd);
                scheduleBtn.setBounds(350, 340, 300, 50);
                scheduleBtn.setBackground(Color.black);
                scheduleBtn.setForeground(Color.WHITE);



                deleteMovieBtn = new JButton("Delete Movie");
                deleteMovieBtn.addActionListener(hnd);
                deleteMovieBtn.setBounds(350, 400, 300, 50);
                deleteMovieBtn.setBackground(Color.black);
                deleteMovieBtn.setForeground(Color.WHITE);

                MD.MDetailsFr.add(movie_name);
                MD.MDetailsFr.add(movie_picture);
                MD.MDetailsFr.add(scheduleBtn);

                MD.MDetailsFr.add(deleteMovieBtn);

            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void homePage_movie_Details_function(String sid) {

            MD = new MovieDetails("2");
            MD.MDetailsFr.setLocationRelativeTo(fr);
            MD.MDetailsFr.setIconImage(logo.getImage());
            MD.given_id = sid;
            JLabel movie_name, movie_picture, rate, date, time, twin_rate, vip_rate, seat_type, seat_quantity;
            JButton bookBtn;
            // JComboBox sType, sQuantity;

            try {

                ResultSet rs = dCon.getdMoviesDetailsBySchedual_ID(sid);
                rs.next();

                Blob b = rs.getBlob(2);
                Image img = ImageIO.read(b.getBinaryStream()).getScaledInstance(300, 500, Image.SCALE_SMOOTH);

                movie_picture = new JLabel(new ImageIcon(img));
                movie_picture.setBounds(0, 0, 300, 500);
                movie_name = new JLabel(rs.getString(1));
                movie_name.setBounds(320, 20, 250, 40);
                movie_name.setFont(new Font("Times new roman", Font.BOLD, 28));
                rate = new JLabel("Price: RM " + rs.getString(3));
                rate.setBounds(320, 65, 200, 20);
                twin_rate = new JLabel("Twin Price: RM " + rs.getString(6));
                twin_rate.setBounds(320, 85, 200, 20);
                vip_rate = new JLabel("VIP Price: RM " + rs.getString(7));
                vip_rate.setBounds(320, 105, 200, 20);
                date = new JLabel("Date: " + rs.getString(4));
                date.setBounds(320, 125, 200, 20);
                time = new JLabel("Time: " + rs.getString(5));
                time.setBounds(320, 145, 200, 20);

                // seat_type = new JLabel("Seat type");
                // seat_type.setBounds(320, 300, 150, 40);
                // sType = new JComboBox();
                // sType.addItem("Normal seat");
                // sType.addItem("Twin seat");
                // sType.addItem("Vip seat");
                // sType.setBounds(320, 330, 200, 30);
                // seat_quantity = new JLabel("Quantity");
                // seat_quantity.setBounds(550, 300, 150, 40);
                // sQuantity = new JComboBox();
                // sQuantity.addItem("1");
                // sQuantity.addItem("2");
                // sQuantity.addItem("3");
                // sQuantity.addItem("4");
                // sQuantity.addItem("5");
                // sQuantity.addItem("6");
                // sQuantity.addItem("7");
                // sQuantity.setBounds(550, 330, 100, 30);

                bookBtn = new JButton("Confirm Booking");
                bookBtn.addActionListener(hnd);
                bookBtn.setBounds(320, 400, 330, 50);
                bookBtn.setBackground(Color.black);
                bookBtn.setForeground(Color.WHITE);

                MD.MDetailsFr.add(movie_name);
                MD.MDetailsFr.add(movie_picture);
                MD.MDetailsFr.add(rate);
                MD.MDetailsFr.add(twin_rate);
                MD.MDetailsFr.add(vip_rate);
                MD.MDetailsFr.add(date);
                MD.MDetailsFr.add(time);
                // MD.MDetailsFr.add(seat_type);
                // MD.MDetailsFr.add(sType);
                // MD.MDetailsFr.add(seat_quantity);
                // MD.MDetailsFr.add(sQuantity);
                MD.MDetailsFr.add(bookBtn);

            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void dashboard_food_Details_function(String fid) {
            FD = new FoodDetails();
            FD.FDetailsFr.setLocationRelativeTo(fr);
            FD.FDetailsFr.setIconImage(logo.getImage());
            FD.given_id = fid;
            JLabel movie_name, movie_picture, rate, date, time;
            JButton cancleBookBtn;

            try {

                ResultSet rs = dCon.getFoodDetailsByFood_ID(fid);
                rs.next();

                Blob b = rs.getBlob(3);
                Image img = ImageIO.read(b.getBinaryStream()).getScaledInstance(300, 500, Image.SCALE_SMOOTH);

                movie_picture = new JLabel(new ImageIcon(img));
                movie_picture.setBounds(0, 0, 300, 500);
                movie_name = new JLabel(rs.getString(1));
                movie_name.setBounds(320, 20, 250, 40);
                movie_name.setFont(new Font("Times new roman", Font.BOLD, 28));
                rate = new JLabel("Price: " + rs.getString(2) + "/-");
                rate.setBounds(320, 65, 200, 20);

                cancleBookBtn = new JButton("Delete Food");
                cancleBookBtn.addActionListener(hnd);
                cancleBookBtn.setBounds(350, 400, 300, 50);
                cancleBookBtn.setBackground(Color.black);
                cancleBookBtn.setForeground(Color.WHITE);

                FD.FDetailsFr.add(movie_name);
                FD.FDetailsFr.add(movie_picture);
                FD.FDetailsFr.add(rate);
                FD.FDetailsFr.add(cancleBookBtn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void dashboard_food_Details_user_function(String fid) {
            FDUSR = new FoodDetailsUser();
            FDUSR.FDetailsFr.setLocationRelativeTo(fr);
            FDUSR.FDetailsFr.setIconImage(logo.getImage());
            FDUSR.given_id = fid;
            JLabel movie_name, movie_picture, rate, date, time;
            JButton cancleBookBtn;

            try {

                ResultSet rs = dCon.getFoodDetailsByFood_ID(fid);
                rs.next();

                Blob b = rs.getBlob(3);
                Image img = ImageIO.read(b.getBinaryStream()).getScaledInstance(300, 500, Image.SCALE_SMOOTH);

                movie_picture = new JLabel(new ImageIcon(img));
                movie_picture.setBounds(0, 0, 300, 500);
                movie_name = new JLabel(rs.getString(1));
                movie_name.setBounds(320, 20, 250, 40);
                movie_name.setFont(new Font("Times new roman", Font.BOLD, 28));
                rate = new JLabel("Price: " + rs.getString(2) + "/-");
                rate.setBounds(320, 65, 200, 20);

                cancleBookBtn = new JButton("Order Food");
                cancleBookBtn.addActionListener(hnd);
                cancleBookBtn.setBounds(350, 400, 300, 50);
                cancleBookBtn.setBackground(Color.black);
                cancleBookBtn.setForeground(Color.WHITE);

                FDUSR.FDetailsFr.add(movie_name);
                FDUSR.FDetailsFr.add(movie_picture);
                FDUSR.FDetailsFr.add(rate);
                FDUSR.FDetailsFr.add(cancleBookBtn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            JPanel temp = (JPanel) e.getSource();
            JLabel flag = (JLabel) temp.getComponent(3);
            if (flag.getText().equals("h")) {
                JLabel selected_id = (JLabel) temp.getComponent(2);
                homePage_movie_Details_function(selected_id.getText());
            } else if (flag.getText().equals("d")) {
                JLabel selected_id = (JLabel) temp.getComponent(2);
                dashboard_movie_Details_function(selected_id.getText());
            } else if (flag.getText().equals("am")) {
                JLabel selected_id = (JLabel) temp.getComponent(2);
                movie_basic_Details_function(selected_id.getText());
            } else if (flag.getText().equals("sm")) {
                JLabel selected_id = (JLabel) temp.getComponent(2);
                dashboard_movie_Details_function(selected_id.getText());
            } else if (flag.getText().equals("fds")) {
                JLabel selected_id = (JLabel) temp.getComponent(2);
                dashboard_food_Details_function(selected_id.getText());
            } else if (flag.getText().equals("fdsusr")) {
                JLabel selected_id = (JLabel) temp.getComponent(2);
                dashboard_food_Details_user_function(selected_id.getText());
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
//            JPanel temp = (JPanel) e.getSource();
//            JLabel selected_id = (JLabel) temp.getComponent(2);
//            homePage_movie_Details_function(selected_id.getText());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // System.out.println(e.getSource());
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            Border blackline = BorderFactory.createBevelBorder(0);
            JPanel temp = (JPanel) e.getSource();
            temp.setBorder(blackline);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JPanel temp = (JPanel) e.getSource();
            temp.setBorder(null);
        }

    }

}
