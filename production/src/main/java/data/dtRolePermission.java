package data;

import entities.rolePermissionTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class dtRolePermission {

    connectionPool pc = connectionPool.getInstance();
    Connection c = null;
    private ResultSet rsRolePermission = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

    public void fillRsRolePermission(Connection c) {
        try{
            ps = c.prepareStatement("SELECT * FROM sibet.rolepermission;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rsRolePermission = ps.executeQuery();
        } catch (SQLException e) {
            System.out.println("DATOS: ERROR AL LISTAR PERMISOS DE ROL" + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<vwRolePermissions> listRolePermissions(){
        ArrayList<vwRolePermissions> listRolePermission = new ArrayList<vwRolePermissions>();
        try{
            c = connectionPool.getConnection();
            ps = c.prepareStatement("SELECT * FROM sibet.vwRolePermissions;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            while(rs.next()) {
                vwRolePermissions rolePermission = new vwRolePermissions();
                rolePermission.setRoleId(rs.getInt("roleId"));
                rolePermission.setRoleName(rs.getString("roleName"));
                rolePermission.setPermissionId(rs.getInt("permissionId"));
                rolePermission.setPermissionName(rs.getString("permissionName"));
                listRolePermission.add(rolePermission);
            }
        }catch (SQLException e) {
            System.out.println("DATOS: ERROR AL LISTAR PERMISOS DE ROL" + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listRolePermission;
    }

    public boolean assignPermission(rolePermissionTable rolePermission){
        boolean saved = false;
        try{
            c = connectionPool.getConnection();
            this.fillRsRolePermission(c);
            this.rsRolePermission.moveToInsertRow();

            rsRolePermission.updateInt("roleId", rolePermission.getRoleId());
            rsRolePermission.updateInt("permissionId", rolePermission.getPermissionId());

            rsRolePermission.insertRow();
            rsRolePermission.moveToCurrentRow();
            saved = true;

        }catch (SQLException e) {
            System.out.println("DATOS: ERROR AL ASIGNAR PERMISO A ROL" + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (rsRolePermission != null) {
                    rsRolePermission.close();
                }
                if (c != null) {
                    connectionPool.closeConnection(c);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return saved;
    }

    public boolean modifyRolePermission(rolePermissionTable rolePermission) {
        boolean modified = false;
        try {
            c = connectionPool.getConnection();
            fillRsRolePermission(c);
            rsRolePermission.beforeFirst();
            while (rsRolePermission.next()) {
                if (rsRolePermission.getInt(1) == rolePermission.getRoleId()) {
                    rsRolePermission.updateInt("roleId", rolePermission.getRoleId());
                    rsRolePermission.updateInt("permissionId", rolePermission.getPermissionId());
                    rsRolePermission.updateRow();
                    modified = true;
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("DATOS: ERROR AL MODIFICAR PERMISO DE ROL" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rsRolePermission != null) {
                    rsRolePermission.close();
                }
                if (c != null) {
                    connectionPool.closeConnection(c);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return modified;
    }
}
