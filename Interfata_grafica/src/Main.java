import javax.swing.*;
import java.sql.*;

public class Main {
    private final static String URL = "jdbc:mysql://localhost:3306/";
    private final static String DB_NAME = "Aplicatie_bancara";
    private final static String USER = "root";
    private final static String PASSWORD = "fronemaneroot";
    public static Connection c;


    public static JFrame currentFrame;
    public static void changeCurrentFrame(JFrame newFrame){
        currentFrame = newFrame;
        currentFrame.setVisible(true);
        currentFrame.setLocationRelativeTo(null);
    }

    public static void main(String ... argc) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            c = DriverManager.getConnection(URL + DB_NAME , USER , PASSWORD);
        } catch ( Exception e ) {
            System.out.println("Nu -i bine!");
            System.out.println("SQLState: " + ((SQLException) e).getSQLState());
            System.out.println("VendorError: " + ((SQLException) e).getErrorCode());
        }
        changeCurrentFrame(new MainFrame("Aplicatie Bancara"));
    }
}
