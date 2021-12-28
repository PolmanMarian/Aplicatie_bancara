import javax.swing.*;
import java.sql.*;

public class Main {
    /// urmeaza sa facem conexiunea
    private final static String URL = "jdbc:mysql://localhost:3306/";
    private final static String DB_NAME = "Aplicatie_bancara";
    private final static String USER = "root";
    private final static String PASSWORD = "fronemaneroot";

    public static JFrame currentFrame;
    public static void changeCurrentFrame(JFrame newFrame){
        currentFrame = newFrame;
        currentFrame.setVisible(true);
    }

    public static void main(String ... argc) {
//        Connection c;
//        try {
//            c = DriverManager.getConnection(URL + DB_NAME , USER , PASSWORD);
//            PreparedStatement s = c.prepareStatement("select * from user_rank");
//            ResultSet rez = s.executeQuery();
//            while(rez.next()) {
//                System.out.println("Rank " + rez.getString("rank"));
//            }
//        } catch ( Exception e ) {
//            System.out.println("Nu -i bine!");
//            System.out.println("SQLState: " + ((SQLException) e).getSQLState());
//            System.out.println("VendorError: " + ((SQLException) e).getErrorCode());
//        }
        changeCurrentFrame(new MainFrame("Aplicatie Bancara"));
    }
}
