package data;

import entities.interfaces.CRUD;
import entities.tables.typeTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class dtType implements CRUD {

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
    @Override
    public List listar() {
        return null;
    }

    @Override
    public typeTable list(int id) {
        return null;
    }

    @Override
    public boolean add(typeTable t) {
        String sql= "INSERT INTO sibet.type (typeId, name) VALUES ('"+t.getTypeId()+"','"+t.getName()+"')";
        try {
            this.c = connectionPool.getConnection();
            this.ps = this.c.prepareStatement(sql);
            this.ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR EN INSERTAR TIPO: " + e.getMessage());
            e.printStackTrace();
    }
        return false;
    }

    @Override
    public boolean edit(typeTable t) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
