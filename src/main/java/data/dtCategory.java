package main.java.data;

import entities.categoryTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class dtCategory {

    data.connectionPool pc = data.connectionPool.getInstance();
    Connection c = null;
    private ResultSet rsCategory = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

    public dtCategory() {
    }

    public void fillRsCategory(Connection c) {
        try{
            this.ps = c.prepareStatement("SELECT * FROM sibet.category;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            this.rsCategory = ps.executeQuery();
        }catch (Exception e){
            System.out.println("DATOS: ERROR EN LISTAR CATEGORIAS " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<categoryTable> listCategories(){
        ArrayList<categoryTable> listCat = new ArrayList<categoryTable>();
        try {
            this.c = pc.getConnection();
            this.ps = this.c.prepareStatement("SELECT * FROM sibet.category;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.rsCategory = this.ps.executeQuery();

            while (this.rsCategory.next()) {
                categoryTable cat = new categoryTable();
                cat.setCategoryId(this.rsCategory.getInt("categoryId"));
                cat.setName(this.rsCategory.getString("name"));
                cat.setDescription(this.rsCategory.getString("description"));
                listCat.add(cat);
            }
        } catch (Exception e) {
            System.out.println("DATOS: ERROR EN LISTAR CATEGORIAS " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (this.rsCategory != null) {
                    this.rsCategory.close();
                }
                if (this.ps != null) {
                    this.ps.close();
                }
                if (this.c != null) {
                    this.c.close();
                }
            } catch (Exception e) {
                System.out.println("DATOS: ERROR EN LISTAR CATEGORIAS " + e.getMessage());
                e.printStackTrace();
            }
        }
        return listCat;
    }

}
