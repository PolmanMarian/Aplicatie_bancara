package UserServices;

import Gui.Main;


import javax.swing.table.DefaultTableModel;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminService {
    public static DefaultTableModel dataModelTransferuriBancare(String sqlSelect) {
        String SQL;
        if (sqlSelect.equals(""))
            SQL = "select * from transferuri_bancare";
        else
            SQL = sqlSelect;
        ResultSet rs;
        List<String> ret = new ArrayList<>();
        String cols[] = {"iban_cont_plecare" , "iban_cont_intrare" , "nume_titular" , "id" , "status"};

        try {
            CallableStatement collect = Main.c.prepareCall(SQL);
            rs = collect.executeQuery();
            while(rs.next()) {
                ret.add(rs.getString(1));
                ret.add(rs.getString(2));
                ret.add(rs.getString(3));
                ret.add(rs.getString(4));
                ret.add(rs.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final int size = ret.size() / cols.length;
        String[][] data = new String[size][5];
        for (int i = 0 ; i < ret.size() ; i += 5) {
            data[i/5][0] = ret.get(i);
            data[i/5][1] = ret.get(i + 1);
            data[i/5][2] = ret.get(i + 2);
            data[i/5][3] = ret.get(i + 3);
            data[i/5][4] = ret.get(i + 4);
        }
        DefaultTableModel model = new DefaultTableModel(data , cols);
        return model;
    }
}
