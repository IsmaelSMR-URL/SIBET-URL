package data;

import entities.tables.itemTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class dtItem {

    connectionPool pc = connectionPool.getInstance();
    Connection c = null;
    private ResultSet rsItem = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

    public dtItem() {
    }

    public void fillRsItem(Connection c) {
        try{
            this.ps = c.prepareStatement("SELECT * FROM sibet.item;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            this.rsItem = ps.executeQuery();
        }catch (Exception e){
            System.out.println("DATOS: ERROR EN LISTAR ITEMS " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<itemTable> listItems(){
        ArrayList<itemTable> listItem = new ArrayList<itemTable>();
        try {
            this.c = pc.getConnection();
            this.ps = this.c.prepareStatement("SELECT * FROM sibet.item;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.rsItem = this.ps.executeQuery();

            while (this.rsItem.next()) {
                itemTable item = new itemTable();
                item.setItemId(this.rsItem.getInt("itemId"));
                item.setName(this.rsItem.getString("name"));
                item.setDescription(this.rsItem.getString("description"));
                item.setQuantity(this.rsItem.getInt("quantity"));
                item.setPrice(this.rsItem.getDouble("price"));
                item.setTypeId(this.rsItem.getInt("typeId"));
                item.setCategoryId(this.rsItem.getInt("categoryId"));
                listItem.add(item);
            }
        } catch (Exception e) {
            System.out.println("DATOS: ERROR EN LISTAR ITEMS " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (this.rsItem != null) {
                    this.rsItem.close();
                }
                if (this.ps != null) {
                    this.ps.close();
                }
                if (this.c != null) {
                    this.c.close();
                }
            } catch (Exception e) {
                System.out.println("DATOS: ERROR EN LISTAR ITEMS " + e.getMessage());
            }
        }
        return listItem;
    }

    public boolean addItem(itemTable Item) {
        boolean saved = false;
        try {
            c = connectionPool.getConnection();
            fillRsItem(c);
            rsItem.moveToInsertRow();
            rsItem.updateString("name", Item.getName());
            rsItem.updateString("description", Item.getDescription());
            rsItem.updateInt("quantity", Item.getQuantity());
            rsItem.updateDouble("price", Item.getPrice());
            rsItem.updateInt("typeId", Item.getTypeId());
            rsItem.updateInt("categoryId", Item.getCategoryId());

            rsItem.insertRow();
            rsItem.moveToCurrentRow();
            saved = true;
        } catch (Exception e) {
            System.err.println("ERROR EN GUARDAR ITEM: " + e.getMessage());
            e.printStackTrace();
    }finally{
            try {
                if (rsItem != null) {
                    rsItem.close();
                }
                if (c != null) {
                    connectionPool.closeConnection(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return saved;
    }

}
