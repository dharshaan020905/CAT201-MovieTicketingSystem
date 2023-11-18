
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DataConnector {

    Connection con;
    Statement stat;
    ResultSet rs;

    DataConnector() {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinematicketbooking", "root", "");
            stat = con.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addUserRecord(String name, String pNo, String CNIC, String password) {
        try {
            stat.executeUpdate("insert into user (User_Name,Password,PhoneNo,CNIC) VALUES('"
                    + name + "','" + password + "','"
                    + pNo + "','" + CNIC + "')");
            JOptionPane.showMessageDialog(null, "SignUP Successful", "SignUp", JOptionPane.INFORMATION_MESSAGE);

            //System.out.println(password);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignUP Not Successful", "SignUp Faild", JOptionPane.INFORMATION_MESSAGE);
            ex.printStackTrace();
        }
    }

    public String getUserID(String NIC) {
        try {
            rs = stat.executeQuery("SELECT LOGIN_ID FROM user WHERE CNIC='" + NIC + "'");

            if (rs.next()) {
                return rs.getString(1);
            }
//           else
//           {
//               JOptionPane.showMessageDialog(null, "Incorrect UserName or Password", "SignIn" , JOptionPane.INFORMATION_MESSAGE);
//           }

            return null;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }

    public String getUserName(String NIC, String pass) {
        try {
            rs = stat.executeQuery("SELECT USER_NAME FROM user WHERE CNIC='" + NIC + "' AND Password='" + pass + "'");

            if (rs.next()) {
                return rs.getString(1);
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect CNIC or Password", "SignIn Faild", JOptionPane.INFORMATION_MESSAGE);
            }

            return null;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }

    public String getUserName(int ID) {
        try {
            rs = stat.executeQuery("SELECT USER_NAME FROM user WHERE LOGIN_ID='" + ID + "'");

            if (rs.next()) {
                return rs.getString(1);
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect UserName or Password", "SignIn", JOptionPane.INFORMATION_MESSAGE);
            }

            return null;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }

    public ResultSet getAllMoviesRecord() {
        try {
            rs = stat.executeQuery("SELECT Movie_ID, Movie_Title, Movie_Cover_Photo FROM movie");

            return rs;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }

    public ResultSet getScheduledMoviesRecords() {
        try {
            rs = stat.executeQuery("SELECT SCHEDULE.SCHEDULE_ID, movie.Movie_Title, movie.Movie_Cover_Photo, SCHEDULE.Hall_Name FROM movie,SCHEDULE where SCHEDULE.MOVIE_ID = movie.Movie_ID AND schedule.s_date >= sysdate()");

            return rs;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ResultSet getdMoviesDetailsBySchedual_ID(String MId) {
        try {
            rs = stat.executeQuery("SELECT Movie_Title, Movie_Cover_Photo, SCHEDULED_MOVIE_PRICE, S_Date, Starting_time, Scheduled_Movie_Price_Twin, Scheduled_Movie_Price_Vip FROM movie,SCHEDULE where SCHEDULE_ID ='" + MId + "' And SCHEDULE.Movie_ID=Movie.Movie_ID");
            return rs;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ResultSet getMoviesDetailsByMovie_ID(String MId) {
        try {
            rs = stat.executeQuery("SELECT Movie_Title, Movie_Cover_Photo FROM movie where MOVIE_ID ='" + MId + "'");
            return rs;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ResultSet getBookedMoviesRecords(String uID) {
        try {
            rs = stat.executeQuery("SELECT SCHEDULE.SCHEDULE_ID, movie.Movie_Title, movie.Movie_Cover_Photo, SCHEDULE.Hall_Name, ticket.Seat_type, ticket.Quantity, SCHEDULE.S_Date, SCHEDULE.Starting_Time FROM movie,SCHEDULE join ticket where '" + uID + "'= USER_LOGIN_ID AND ticket.schedule_id = schedule.schedule_id AND schedule.Movie_ID = movie.Movie_ID");

            return rs;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }

    public ResultSet getSearchedMovie(String searchText) {
        searchText = searchText.trim();
        searchText = "%" + searchText + "%";
        try {
            rs = stat.executeQuery("SELECT SCHEDULE.SCHEDULE_ID, movie.Movie_Title, movie.Movie_Cover_Photo, SCHEDULE.Hall_Name, SCHEDULE.S_Date, SCHEDULE.Starting_Time FROM movie,SCHEDULE where SCHEDULE.MOVIE_ID = movie.Movie_ID AND schedule.s_date >= sysdate() AND UPPER(movie_title) LIKE UPPER('" + searchText + "')");

            return rs;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }

    public int getCountOfAllMovies() {
        int n = 0;
        ResultSet r;
        try {
            r = stat.executeQuery(" Select count(movie_id) from movie");
            if (r.next()) {
                n = r.getInt(1);
            }
            r.close();
            return n;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int getNumberOfMoviesTobePlayed() {
        try {
            int n = 0;
            ResultSet r = stat.executeQuery(" Select count(movie_id) from schedule where s_date >= sysdate()");
            if (r.next()) {
                n = r.getInt(1);
            }
            r.close();
            return n;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }

        return 0;
    }

    public int getNumberOfSearchResults(String searchText) {
        searchText = searchText.trim();
        searchText = "%" + searchText + "%";
        try {
            int n = 0;
            ResultSet r = stat.executeQuery(" Select count(movie_id) from movie Join SCHEDULE using(MOVIE_ID) where schedule.s_date >= sysdate() AND UPPER(movie_title) LIKE UPPER('" + searchText + "')");
            if (r.next()) {
                n = r.getInt(1);
            }
            r.close();
            return n;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Try without punctuation marks", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        return 0;
    }

    public int getMovieID(String searchText) {
        searchText = searchText.trim();
        searchText = "%" + searchText + "%";
        try {
            int n = 0;
            ResultSet r = stat.executeQuery(" Select movie_id from movie where UPPER(movie_title) LIKE UPPER('" + searchText + "')");
            if (r.next()) {
                n = r.getInt(1);
            }
            r.close();
            return n;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int getNumberOfTicketsForUser(String user) {
        try {
            int n = 0;

            ResultSet r = stat.executeQuery(" Select count(ticket_id) from ticket where USER_LOGIN_ID='" + user + "'");
            if (r.next()) {
                n = r.getInt(1);
            }
            r.close();
            return n;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Dashboard loading Not Successful", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }

        return 0;
    }

    public int updatePassword(String NIC, String oldPass, String newPass) {
        try {
            int n = stat.executeUpdate("UPDATE user SET Password='" + newPass + "' WHERE CNIC='" + NIC + "' AND Password='" + oldPass + "'");
            return n;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid Password", "Password Update fail", JOptionPane.INFORMATION_MESSAGE);
        }
        return 0;
    }

    public boolean isAdmin(String id) {
        try {
            boolean r = false;
            ResultSet n = stat.executeQuery("SELECT isAdmin FROM user WHERE Login_ID='" + id + "'");
            if (n.next()) {
                r = n.getBoolean(1);
            }
            n.close();
            return r;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Admin Side Error", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        return false;
    }

    public int addMovieRecord(String title, String genere, String duration_time, String description, String director, String summary, FileInputStream image) {
        try {

            String sql = "Insert INTO movie (Movie_Title,Movie_Genere,Movie_Duration,Movie_Description,Movie_Dir_Name,Movie_Summary,Movie_Cover_Photo) values (?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, title);
            pst.setString(2, genere);
            pst.setString(3, duration_time);
            pst.setString(4, description);
            pst.setString(5, director);
            pst.setString(6, summary);
            pst.setBinaryStream(7, image);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Movie added to record", "Completed", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Insertion Not Successful", "Insertion Failed", JOptionPane.INFORMATION_MESSAGE);
        }
        return 0;
    }

    public void confirmBooking(String uid, String given_id, String seat_type, String seat_quantity) {
        try {
            String sql = "insert into Ticket (User_Login_ID,Schedule_ID,Seat_type,Quantity) VALUES(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, uid);
            pst.setString(2, given_id);
            pst.setString(3, seat_type);
            pst.setString(4, seat_quantity);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Your seats are reserved :)", "Booking Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "No more seats available :(", "Booking Failed", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void cancelBooking(String uid, String given_id) {
        try {
            stat.executeUpdate("Delete From ticket where Schedule_ID='" + given_id + "'");
            stat.executeUpdate("Delete From schedule where Schedule_ID='" + given_id + "'");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "You can't cancel this booking.", "Failed", JOptionPane.INFORMATION_MESSAGE);
        }
        // try {
        //     stat.executeUpdate("Delete From schedule where Schedule_ID='" + given_id + "'");
        // } catch (SQLException ex) {
        //     JOptionPane.showMessageDialog(null, "You can't cancel this booking.", "Failed", JOptionPane.INFORMATION_MESSAGE);
        // }
    }

    public void deleteMovie(String given_id) {
        try {
            stat.executeUpdate("Delete From Movie where Movie_ID='" + given_id + "'");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Try Again", "Opretion Failed", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public ResultSet getHallsName() {
        try {
            rs = stat.executeQuery("SELECT Hall_Name From Hall");
            return rs;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void addToSchedual(String mId, String price_txt, String stime, String etime, String datePicker, String sHall, String price_twin_txt, String price_vip_txt) {
        String sql = "insert into Schedule (Movie_ID,Hall_Name,Starting_time,Ending_time,S_Date,Scheduled_Movie_Price,Scheduled_Movie_Price_Twin,Scheduled_Movie_Price_Vip) values (?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, mId);
            pst.setString(2, sHall);
            pst.setString(3, stime);
            pst.setString(4, etime);
            pst.setString(5, datePicker);
            pst.setString(6, price_txt);
            pst.setString(7, price_twin_txt);
            pst.setString(8, price_vip_txt);
            pst.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Try Again", "Opretion Failed", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public int addFoodRecord(String title, String price, FileInputStream image) {
        try {
            String sql = "Insert INTO food (Food_Title,Food_Price,Food_Cover_Photo) values (?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, title);
            pst.setString(2, price);
            pst.setBinaryStream(3, image);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Food added to record", "Completed", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Insertion Not Successful", "Insertion Failed", JOptionPane.INFORMATION_MESSAGE);
        }
        return 0;
    }

    public int getCountOfAllFood() {
        int n = 0;
        ResultSet r;
        try {
            r = stat.executeQuery(" Select count(Food_ID) from food");
            if (r.next()) {
                n = r.getInt(1);
            }
            r.close();
            return n;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public ResultSet getAllFoodRecord() {
        try {
            rs = stat.executeQuery("SELECT Food_ID, Food_Title, Food_Cover_Photo FROM food");

            return rs;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }

    public ResultSet getFoodDetailsByFood_ID(String FId) {
        try {
            rs = stat.executeQuery("SELECT Food_Title, Food_Price, Food_Cover_Photo FROM food where Food_ID ='" + FId + "'");
            return rs;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void deleteFood(String given_id) {
        try {
            stat.executeUpdate("Delete From food where Food_ID='" + given_id + "'");
            JOptionPane.showMessageDialog(null, "Successful", "Opretion Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Try Again", "Opretion Failed", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Hall points
    // A - 3d
    // B - Imax
    // C - Dbox
    // D - Commersion
    // E - small
    // getting scheduled movies by group
    public ResultSet getScheduledMoviesRecordsByHallA() {
        try {
            rs = stat.executeQuery("SELECT SCHEDULE.SCHEDULE_ID, movie.Movie_Title, movie.Movie_Cover_Photo, SCHEDULE.Hall_Name, SCHEDULE.Starting_Time, SCHEDULE.S_Date FROM movie,SCHEDULE where SCHEDULE.MOVIE_ID = movie.Movie_ID AND schedule.s_date >= sysdate() AND SCHEDULE.Hall_Name = '3d_hall'");

            return rs;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getNumberOfMoviesTobePlayedByHallA() {
        try {
            int n = 0;
            ResultSet r = stat.executeQuery(" Select count(movie_id) from schedule where s_date >= sysdate() AND SCHEDULE.Hall_Name = '3d_hall'");
            if (r.next()) {
                n = r.getInt(1);
            }
            r.close();
            return n;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }

        return 0;
    }

    public ResultSet getScheduledMoviesRecordsByHallB() {
        try {
            rs = stat.executeQuery("SELECT SCHEDULE.SCHEDULE_ID, movie.Movie_Title, movie.Movie_Cover_Photo, SCHEDULE.Hall_Name, SCHEDULE.Starting_Time, SCHEDULE.S_Date FROM movie,SCHEDULE where SCHEDULE.MOVIE_ID = movie.Movie_ID AND schedule.s_date >= sysdate() AND SCHEDULE.Hall_Name = 'Imax_hall'");

            return rs;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getNumberOfMoviesTobePlayedByHallB() {
        try {
            int n = 0;
            ResultSet r = stat.executeQuery(" Select count(movie_id) from schedule where s_date >= sysdate() AND SCHEDULE.Hall_Name = 'Imax_hall'");
            if (r.next()) {
                n = r.getInt(1);
            }
            r.close();
            return n;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }

        return 0;
    }

    public ResultSet getScheduledMoviesRecordsByHallC() {
        try {
            rs = stat.executeQuery("SELECT SCHEDULE.SCHEDULE_ID, movie.Movie_Title, movie.Movie_Cover_Photo, SCHEDULE.Hall_Name, SCHEDULE.Starting_Time, SCHEDULE.S_Date FROM movie,SCHEDULE where SCHEDULE.MOVIE_ID = movie.Movie_ID AND schedule.s_date >= sysdate() AND (SCHEDULE.Hall_Name = 'D-box_hall_1' OR SCHEDULE.Hall_Name = 'D-box_hall_2')");

            return rs;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getNumberOfMoviesTobePlayedByHallC() {
        try {
            int n = 0;
            ResultSet r = stat.executeQuery(" Select count(movie_id) from schedule where s_date >= sysdate() AND (SCHEDULE.Hall_Name = 'D-box_hall_1' OR SCHEDULE.Hall_Name = 'D-box_hall_2')");
            if (r.next()) {
                n = r.getInt(1);
            }
            r.close();
            return n;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }

        return 0;
    }

    public ResultSet getScheduledMoviesRecordsByHallD() {
        try {
            rs = stat.executeQuery("SELECT SCHEDULE.SCHEDULE_ID, movie.Movie_Title, movie.Movie_Cover_Photo, SCHEDULE.Hall_Name, SCHEDULE.Starting_Time, SCHEDULE.S_Date FROM movie,SCHEDULE where SCHEDULE.MOVIE_ID = movie.Movie_ID AND schedule.s_date >= sysdate() AND (SCHEDULE.Hall_Name = 'commercial_huge_1_hall' OR SCHEDULE.Hall_Name = 'commercial_huge_2_hall')");

            return rs;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getNumberOfMoviesTobePlayedByHallD() {
        try {
            int n = 0;
            ResultSet r = stat.executeQuery(" Select count(movie_id) from schedule where s_date >= sysdate() AND (SCHEDULE.Hall_Name = 'commercial_huge_1_hall' OR SCHEDULE.Hall_Name = 'commercial_huge_2_hall')");
            if (r.next()) {
                n = r.getInt(1);
            }
            r.close();
            return n;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }

        return 0;
    }

    public ResultSet getScheduledMoviesRecordsByHallE() {
        try {
            rs = stat.executeQuery("SELECT SCHEDULE.SCHEDULE_ID, movie.Movie_Title, movie.Movie_Cover_Photo, SCHEDULE.Hall_Name, SCHEDULE.Starting_Time, SCHEDULE.S_Date FROM movie,SCHEDULE where SCHEDULE.MOVIE_ID = movie.Movie_ID AND schedule.s_date >= sysdate() AND (SCHEDULE.Hall_Name = 'small_hall_1' OR SCHEDULE.Hall_Name = 'small_hall_2')");

            return rs;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getNumberOfMoviesTobePlayedByHallE() {
        try {
            int n = 0;
            ResultSet r = stat.executeQuery(" Select count(movie_id) from schedule where s_date >= sysdate() AND (SCHEDULE.Hall_Name = 'small_hall_1' OR SCHEDULE.Hall_Name = 'small_hall_2')");
            if (r.next()) {
                n = r.getInt(1);
            }
            r.close();
            return n;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }

        return 0;
    }

    public void confirmFood(String uid, String date, String food_id, String quantity) {
        try {
            String sql = "insert into foodbooking (User_Login_ID,S_Date,Food_ID,Quantity) VALUES(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, uid);
            pst.setString(2, date);
            pst.setString(3, food_id);
            pst.setString(4, quantity);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Food orders confirmed:)", "Order Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Food orders failed :(", "BookiOrderng Failed", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public int getNumberOfFoodOrdered(String uid) {
        try {
            int n = 0;
            ResultSet r = stat.executeQuery(" Select count(FoodBook_ID) from foodbooking where User_Login_ID='" + uid + "'");
            if (r.next()) {
                n = r.getInt(1);
            }
            r.close();
            return n;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }

        return 0;
    }

    public ResultSet getFoodOrderedByUser(String uid) {
        try {
            ResultSet rs = stat.executeQuery(" Select foodbooking.S_Date, foodbooking.Quantity, food.Food_Title, food.Food_Price, food.Food_Cover_Photo from foodbooking,food where foodbooking.Food_ID=food.Food_ID AND foodbooking.User_Login_ID='" + uid + "' AND foodbooking.S_Date >= sysdate()");
            return rs;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }

}
