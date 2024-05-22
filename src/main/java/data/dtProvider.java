package data;

import entities.tables.providerTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class dtProvider {

    connectionPool pc = connectionPool.getInstance();
    Connection c = null;
    private ResultSet rsProvider = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

    public dtProvider() {
    }

    public void fillRsProvider(Connection c) {
        try {
            this.ps = c.prepareStatement("SELECT * FROM sibet.provider;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            this.rsProvider= ps.executeQuery();
        } catch (Exception e) {
            System.out.println("ERROR EN LISTAR PROVEEDORES: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<providerTable> listProviders(){
        ArrayList<providerTable> listProvider = new ArrayList<providerTable>();
        try{
            this.c = pc.getConnection();
            this.ps = this.c.prepareStatement("SELECT * FROM sibet.provider;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.rsProvider = this.ps.executeQuery();

            while (this.rsProvider.next()) {
                providerTable provider = new providerTable();
                provider.setProviderId(this.rsProvider.getInt("providerId"));
                provider.setCompanyName(this.rsProvider.getString("companyName"));
                provider.setContactName(this.rsProvider.getString("contactName"));
                provider.setContactEmail(this.rsProvider.getString("contactEmail"));
                provider.setPhoneNumber(this.rsProvider.getString("phoneNumber"));
                provider.setCountry(this.rsProvider.getString("country"));
                provider.setCity(this.rsProvider.getString("city"));
                provider.setAddress(this.rsProvider.getString("address"));
                provider.setPostalCode(this.rsProvider.getString("postalCode"));
                listProvider.add(provider);
            }
        } catch (Exception e) {
            System.out.println("ERROR EN LISTAR PROVEEDORES: " + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (this.rsProvider != null) {
                    this.rsProvider.close();
                }
                if (this.ps != null) {
                    this.ps.close();
                }
            } catch (Exception e) {
                System.out.println("ERROR EN LISTAR PROVEEDORES: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return listProvider;
    }

    public boolean addProvider(providerTable provider) {
        boolean saved = false;
        try {
            c = connectionPool.getConnection();
            this.fillRsProvider(c);
            this.rsProvider.moveToInsertRow();
            this.rsProvider.updateString("companyName", provider.getCompanyName());
            this.rsProvider.updateString("contactName", provider.getContactName());
            this.rsProvider.updateString("contactEmail", provider.getContactEmail());
            this.rsProvider.updateString("phoneNumber", provider.getPhoneNumber());
            this.rsProvider.updateString("country", provider.getCountry());
            this.rsProvider.updateString("city", provider.getCity());
            this.rsProvider.updateString("address", provider.getAddress());
            this.rsProvider.updateString("postalCode", provider.getPostalCode());

            this.rsProvider.insertRow();
            rsProvider.moveToCurrentRow();
            saved = true;
        } catch (Exception e) {
            System.out.println("ERROR EN AGREGAR PROVEEDOR: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (this.rsProvider != null) {
                    this.rsProvider.close();
                }
                if (this.c != null) {
                    connectionPool.closeConnection(this.c);
                }
            } catch (Exception e) {
                System.out.println("ERROR EN AGREGAR PROVEEDOR: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return saved;
    }
}
