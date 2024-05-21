package data;

import entities.tables.roleTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class dtRole {

    connectionPool pc = connectionPool.getInstance();
    Connection c = null;
    private ResultSet rsRole = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

    public dtRole() {
    }

    public void fillRsRole(Connection c) {
        try {
            this.ps = c.prepareStatement("SELECT * FROM sibet.role;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            this.rsRole = ps.executeQuery();
        } catch (Exception e) {
            System.out.println("ERROR EN LISTAR ROLES: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<roleTable> listRoles(){
        ArrayList<roleTable> listRole = new ArrayList<roleTable>();
        try{
            this.c = pc.getConnection();
            this.ps = this.c.prepareStatement("SELECT * FROM sibet.role;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.rsRole = this.ps.executeQuery();

            while (this.rsRole.next()) {
                roleTable role = new roleTable();
                role.setRoleId(this.rsRole.getInt("roleId"));
                role.setName(this.rsRole.getString("name"));
                role.setDescription(this.rsRole.getString("description"));
                listRole.add(role);
            }
        } catch (Exception e) {
            System.out.println("ERROR EN LISTAR ROLES: " + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (this.rsRole != null) {
                    this.rsRole.close();
                }
                if (this.c != null) {
                    this.ps.close();
                }
            } catch (Exception e) {
                System.out.println("ERROR EN LISTAR ROLES: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return listRole;
    }

    public roleTable getRoleById(int roleId) {
        roleTable role = new roleTable();
        try {
            this.c = pc.getConnection();
            this.ps = this.c.prepareStatement("SELECT * FROM sibet.role WHERE roleId = ?;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.ps.setInt(1, roleId);
            this.rsRole = this.ps.executeQuery();

            if (this.rsRole.next()) {
                role.setRoleId(this.rsRole.getInt("roleId"));
                role.setName(this.rsRole.getString("name"));
                role.setDescription(this.rsRole.getString("description"));
            }
        } catch (Exception e) {
            System.out.println("ERROR EN LISTAR ROLES: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (this.rsRole != null) {
                    this.rsRole.close();
                }
                if (this.c != null) {
                    this.ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return role;
    }

    public boolean modifyRole(roleTable role) {
        boolean modified = false;
        try {
            this.c = pc.getConnection();
            this.ps = this.c.prepareStatement("UPDATE sibet.role SET name = ?, description = ? WHERE roleId = ?;");
            this.ps.setString(1, role.getName());
            this.ps.setString(2, role.getDescription());
            this.ps.setInt(3, role.getRoleId());
            int result = ps.executeUpdate();
            modified = (result > 0) ? true : false;
        } catch (Exception e) {
            System.out.println("ERROR EN MODIFICAR ROL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (this.c != null) {
                    connectionPool.closeConnection(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return modified;
    }
}
