
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.ScrollPane;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Admin {

    JPanel dashboardPnl;
    JPanel innerPanel;
    JLabel userNameLbl;
    JButton lOutBtn;
    JButton pswdBtn;
    JButton addBtn;
    JButton addFoodBtn;
    DataConnector dCon;
    ImageIcon icon;
    ResultSet rs;
    ImageIcon ic;
    Blob bl;
    ScrollPane scrollPane;
    String uName;
    MainGUI.ButtonHandler hnd;

    Admin(MainGUI.ButtonHandler h) throws SQLException {
        hnd = h;
        initGUI();
    }

    private void initGUI() throws SQLException {

        dashboardPnl = new JPanel();
        dashboardPnl.setBackground((new Color(222, 243, 253)));
        dCon = new DataConnector();

        innerPanel = new JPanel();
        innerPanel.setLayout(null);
        innerPanel.setBounds(100, 100, 1000, 500);
        innerPanel.setVisible(true);

        lOutBtn = new JButton("LOG OUT");
        lOutBtn.setFocusPainted(false);
        lOutBtn.setBorderPainted(false);
        lOutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        pswdBtn = new JButton("Change Password");
        pswdBtn.setFocusPainted(false);
        pswdBtn.setBorderPainted(false);
        pswdBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addBtn = new JButton("Add Movie");
        addBtn.setFocusPainted(false);
        addBtn.setBorderPainted(false);
        addBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addFoodBtn = new JButton("Add Food");
        addFoodBtn.setFocusPainted(false);
        addFoodBtn.setBorderPainted(false);
        addFoodBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        scrollPane = new ScrollPane();

        scrollPane.setBounds(0, 100, 1095, 500);
        userNameLbl = new JLabel("");
        userNameLbl.setFont(new Font("Times new roman", Font.BOLD, 18));
        userNameLbl.setOpaque(true);
        userNameLbl.setBackground(new Color(222, 243, 253));
        userNameLbl.setForeground(Color.BLACK);

        userNameLbl.setText("       You're signed in as " + this.uName);

        dashboardPnl.setLayout(null);

        dashboardPnl.setSize(1100, 680);

        userNameLbl.setBounds(0, 30, 500, 30);

        addBtn.setBounds(500, 30, 110, 30);
        addBtn.setBackground((new Color(222, 243, 253)));
        addBtn.setForeground(Color.BLACK);

        addFoodBtn.setBounds(620, 30, 90, 30);
        addFoodBtn.setBackground((new Color(222, 243, 253)));
        addFoodBtn.setForeground(Color.BLACK);

        pswdBtn.setBounds(710, 30, 150, 30);
        pswdBtn.setBackground((new Color(222, 243, 253)));
        pswdBtn.setForeground(Color.BLACK);

        lOutBtn.setBounds(980, 30, 100, 30);
        lOutBtn.setBackground((new Color(222, 243, 253)));
        lOutBtn.setForeground(Color.BLACK);

        addBtn.addActionListener(hnd);
        addFoodBtn.addActionListener(hnd);
        pswdBtn.addActionListener(hnd);
        lOutBtn.addActionListener(hnd);

        dashboardPnl.add(userNameLbl);
        dashboardPnl.add(addBtn);
        dashboardPnl.add(addFoodBtn);
        dashboardPnl.add(pswdBtn);
        dashboardPnl.add(lOutBtn);
        dashboardPnl.setVisible(false);

    }

    public void updateDashboard() throws SQLException, IOException {

        innerPanel.removeAll();
        int posX = 100, posY = 10;
        int n = dCon.getNumberOfMoviesTobePlayed();

        rs = dCon.getScheduledMoviesRecords();
        rs.next();
        JLabel schedual_Lbl = new JLabel("Scheduled Movies: ");
        schedual_Lbl.setBounds(posX, posY, 500, 40);
        schedual_Lbl.setFont(new Font("Times new roman", Font.BOLD, 25));
        innerPanel.add(schedual_Lbl);
        posY = posY + 45;
        JLabel[] Schedual_id = new JLabel[n];
        JLabel[] Food_id = new JLabel[n];
        JLabel[] food_picture = new JLabel[n];
        JLabel[] food_name = new JLabel[n];
        JLabel[] movie_picture = new JLabel[n];
        JLabel[] movie_name = new JLabel[n];
        JLabel[] movie_hall = new JLabel[n];
        JPanel[] movie_pnl = new JPanel[n];
        JLabel[] flag = new JLabel[n];

        for (int i = 0; i < n; i++) {
            flag[i] = new JLabel("sm");
            Blob b = rs.getBlob(3);
            Image img;
            try {
                img = ImageIO.read(b.getBinaryStream()).getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                icon = new ImageIcon(img);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Schedual_id[i] = new JLabel(rs.getString(1));
            movie_picture[i] = new JLabel(icon);
            movie_picture[i].setBounds(0, 0, 200, 200);

            movie_name[i] = new JLabel("  " + rs.getString(2));
            movie_name[i].setBounds(0, 0 + 200, 200, 35);
            movie_name[i].setFont(new Font("Times new roman", Font.BOLD, 18));

            movie_hall[i] = new JLabel("  " + rs.getString(4));
            movie_hall[i].setBounds(0, 15 + 200, 200, 35);
            movie_hall[i].setFont(new Font("Times new roman", Font.ITALIC, 15));

            Schedual_id[i].setVisible(false);
            movie_pnl[i] = new JPanel();
            movie_pnl[i].setLayout(null);
            movie_pnl[i].add(movie_picture[i]);
            movie_pnl[i].add(movie_name[i]);

            movie_pnl[i].add(Schedual_id[i]);
            movie_pnl[i].add(flag[i]);
            movie_pnl[i].add(movie_hall[i]);
            movie_pnl[i].setBackground(Color.WHITE);
            movie_pnl[i].setBounds(posX, posY, 200, 245);
            movie_pnl[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            movie_pnl[i].addMouseListener(hnd);
            posX = (posX + 225) % 900;

            if (posX == 100 && i != n - 1) {
                posY = posY + 250;
            }

            innerPanel.add(movie_pnl[i]);
            rs.next();
        }
        posX = 100;
        posY = posY + 250;
        n = dCon.getCountOfAllMovies();

        rs = dCon.getAllMoviesRecord();
        rs.next();
        JLabel allMoviesLbl = new JLabel("All Available Movies:");
        allMoviesLbl.setBounds(posX, posY + 20, 500, 40);
        allMoviesLbl.setFont(new Font("Times new roman", Font.BOLD, 25));
        innerPanel.add(allMoviesLbl);
        posY = posY + 65;

        Schedual_id = new JLabel[n];
        movie_picture = new JLabel[n];
        movie_name = new JLabel[n];
        movie_pnl = new JPanel[n];
        flag = new JLabel[n];

        for (int i = 0; i < n; i++) {
            flag[i] = new JLabel("am");
            Blob b = rs.getBlob(3);
            Image img;
            try {
                img = ImageIO.read(b.getBinaryStream()).getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                icon = new ImageIcon(img);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Schedual_id[i] = new JLabel(rs.getString(1));
            movie_picture[i] = new JLabel(icon);
            movie_picture[i].setBounds(0, 0, 200, 200);

            movie_name[i] = new JLabel("  " + rs.getString(2));
            movie_name[i].setBounds(0, 0 + 200, 200, 35);
            movie_name[i].setFont(new Font("Times new roman", Font.BOLD, 18));

            Schedual_id[i].setVisible(false);
            movie_pnl[i] = new JPanel();
            movie_pnl[i].setLayout(null);
            movie_pnl[i].add(movie_picture[i]);
            movie_pnl[i].add(movie_name[i]);
            movie_pnl[i].add(Schedual_id[i]);
            movie_pnl[i].add(flag[i]);
            movie_pnl[i].setBackground(Color.WHITE);
            movie_pnl[i].setBounds(posX, posY, 200, 235);
            movie_pnl[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            movie_pnl[i].addMouseListener(hnd);
            posX = (posX + 225) % 900;

            if (posX == 100 && i != n - 1) {
                posY = posY + 250;
            }

            innerPanel.add(movie_pnl[i]);
            rs.next();
        }

        innerPanel.setPreferredSize(new Dimension(950, posY + 250));

        posX = 100;
        posY = posY + 250;
        n = dCon.getCountOfAllFood();

        rs = dCon.getAllFoodRecord();
        rs.next();
        JLabel allFoodLbl = new JLabel("Available Foods:");
        allFoodLbl.setBounds(posX, posY + 20, 500, 40);
        allFoodLbl.setFont(new Font("Times new roman", Font.BOLD, 25));
        innerPanel.add(allFoodLbl);
        posY = posY + 65;

        Food_id = new JLabel[n];
        food_picture = new JLabel[n];
        food_name = new JLabel[n];
        movie_pnl = new JPanel[n];
        flag = new JLabel[n];

        for (int i = 0; i < n; i++) {
            flag[i] = new JLabel("fds");
            Blob b = rs.getBlob(3);
            Image img;
            try {
                img = ImageIO.read(b.getBinaryStream()).getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                icon = new ImageIcon(img);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Food_id[i] = new JLabel(rs.getString(1));
            food_picture[i] = new JLabel(icon);
            food_picture[i].setBounds(0, 0, 200, 200);

            food_name[i] = new JLabel("  " + rs.getString(2));
            food_name[i].setBounds(0, 0 + 200, 200, 35);
            food_name[i].setFont(new Font("Times new roman", Font.BOLD, 18));

            Food_id[i].setVisible(false);
            movie_pnl[i] = new JPanel();
            movie_pnl[i].setLayout(null);
            movie_pnl[i].add(food_picture[i]);
            movie_pnl[i].add(food_name[i]);
            movie_pnl[i].add(Food_id[i]);
            movie_pnl[i].add(flag[i]);
            movie_pnl[i].setBackground(Color.WHITE);
            movie_pnl[i].setBounds(posX, posY, 200, 235);
            movie_pnl[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            movie_pnl[i].addMouseListener(hnd);
            posX = (posX + 225) % 900;

            if (posX == 100 && i != n - 1) {
                posY = posY + 250;
            }

            innerPanel.add(movie_pnl[i]);
            rs.next();
        }

        innerPanel.setPreferredSize(new Dimension(950, posY + 250));

        scrollPane.add(innerPanel);
        dashboardPnl.add(scrollPane);

    }

    public void setData(String uName) {
        this.uName = uName;
        userNameLbl.setText("       You're signed in as " + this.uName + " (ADMIN)");
    }

}
