package data;

import entities.typeTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class dtType {

    connectionPool pc = connectionPool.getInstance();
    Connection c = null;
    private ResultSet rsType = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

    public dtType() {
    }

    public void fillRsType(Connection c){
        try {
            ps = c.prepareStatement("SELECT * FROM type", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rsType = ps.executeQuery();
        } catch (Exception e) {
            System.out.println("ERROR EN LISTAR TIPOS: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<typeTable> listTypes(){
        ArrayList<typeTable> listType = new ArrayList<typeTable>();
        try{
            c = pc.getConnection();
            ps = c.prepareStatement("SELECT * FROM type", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rsType = ps.executeQuery();

            while (rsType.next()) {
                typeTable type = new typeTable();
                type.setTypeId(rsType.getInt("typeId"));
                type.setName(rsType.getString("name"));
                listType.add(type);
            }
        } catch (Exception e) {
            System.out.println("ERROR EN LISTAR TIPOS: " + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (rsType != null) {
                    rsType.close();
                }
                if (c != null) {
                    ps.close();
                }
            } catch (Exception e) {
                System.out.println("ERROR EN LISTAR TIPOS: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return listType;
    }

}
