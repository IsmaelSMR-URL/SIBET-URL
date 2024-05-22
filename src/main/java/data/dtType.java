package data;

import entities.tables.typeTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            this.ps = c.prepareStatement("SELECT * FROM sibet.type", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            this.rsType = ps.executeQuery();
        } catch (Exception e) {
            System.out.println("ERROR EN LISTAR TIPOS: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<typeTable> listTypes(){
        ArrayList<typeTable> listType = new ArrayList<typeTable>();
        try{
            this.c = connectionPool.getConnection();
            this.ps = this.c.prepareStatement("SELECT * FROM sibet.type", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.rs = this.ps.executeQuery();

            while (this.rs.next()) {
                typeTable type = new typeTable();
                type.setTypeId(rs.getInt("typeId"));
                type.setName(rs.getString("name"));
                listType.add(type);
            }
        } catch (Exception e) {
            System.out.println("ERROR EN LISTAR TIPOS: " + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (this.rs != null) {
                    this.rs.close();
                }
                if (this.ps != null) {
                    this.ps.close();
                }
                if (this.c != null) {
                    connectionPool.closeConnection(this.c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listType;
    }

    public boolean addType(typeTable type){
        boolean saved = false;
        try{
            c = connectionPool.getConnection();
            this.fillRsType(c);
            this.rsType.moveToInsertRow();

            rsType.updateInt("typeId", type.getTypeId());
            rsType.updateString("name", type.getName());
            rsType.insertRow();

            rsType.insertRow();
            rsType.moveToCurrentRow();
            saved = true;

        } catch (Exception e) {
            System.out.println("ERROR EN INSERTAR TIPO: " + e.getMessage());
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
                e.printStackTrace();
            }
        }
        return saved;
    }

    public boolean updateType(typeTable type) {
        boolean modified = false;
        try {
            c = connectionPool.getConnection();
            this.fillRsType(c);
            while (rsType.next()) {
                if (rsType.getInt("typeId") == type.getTypeId()) {
                    rsType.updateString("name", type.getName());
                    rsType.updateRow();
                    modified = true;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR EN MODIFICAR TIPO: " + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (rsType != null) {
                    rsType.close();
                }
                if (c != null) {
                    connectionPool.closeConnection(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return modified;
    }

    public boolean deleteType(int typeId) {
        boolean deleted = false;
        try {
            c = connectionPool.getConnection();
            this.fillRsType(c);
            while (rsType.next()) {
                if (rsType.getInt("typeId") == typeId) {
                    rsType.deleteRow();
                    deleted = true;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR EN ELIMINAR TIPO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rsType != null) {
                    rsType.close();
                }
                if (c != null) {
                    connectionPool.closeConnection(c);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return deleted;
    }
}
