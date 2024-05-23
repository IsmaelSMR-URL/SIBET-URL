package data;

import entities.tables.categoryTable;
import entities.views.vwTypeCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class dtCategory {

    connectionPool pc = connectionPool.getInstance();
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

    public ArrayList<vwTypeCategory> listCategories(){
        ArrayList<vwTypeCategory> listCat = new ArrayList<vwTypeCategory>();
        try {
            this.c = pc.getConnection();
            this.ps = this.c.prepareStatement("SELECT * FROM sibet.vwTypeCategory;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.rsCategory = this.ps.executeQuery();

            while (this.rsCategory.next()) {
                vwTypeCategory cat = new vwTypeCategory();
                cat.setTcId(this.rsCategory.getInt("tcId"));
                cat.setType(this.rsCategory.getString("type"));
                cat.setCat(this.rsCategory.getString("cat"));
                cat.setDesc(this.rsCategory.getString("desc"));

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

    public boolean addCategory(categoryTable cat){
        boolean saved = false;
        try {
            c = connectionPool.getConnection();
            this.fillRsCategory(c);
            rsCategory.moveToInsertRow();
            rsCategory.updateString("name", cat.getName());
            rsCategory.updateString("description", cat.getDescription());

            rsCategory.insertRow();
            rsCategory.moveToCurrentRow();
            saved = true;
        } catch (Exception e) {
            System.out.println("ERROR EN AGREGAR CATEGORIA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if(rsCategory != null){
                    rsCategory.close();
                }
                if(c != null){
                    connectionPool.closeConnection(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return saved;
    }

}
