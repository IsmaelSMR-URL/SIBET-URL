package data;

import entities.tables.rolePermissionTable;
import entities.views.vwRolePermission;

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
        try {
            ps = c.prepareStatement("SELECT * FROM sibet.rolepermission;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rsRolePermission = ps.executeQuery();
        } catch (SQLException e) {
            System.out.println("DATOS: ERROR AL LISTAR PERMISOS DE ROL" + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<vwRolePermission> listRolePermissions() {
        ArrayList<vwRolePermission> listRolePermission = new ArrayList<vwRolePermission>();
        try {
            c = connectionPool.getConnection();
            ps = c.prepareStatement("SELECT * FROM sibet.vwRolePermission;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            while (rs.next()) {
                vwRolePermission rolePermission = new vwRolePermission();
                rolePermission.setRolePermissionId(rs.getInt("rolePermissionId"));
                rolePermission.setRoleName(rs.getString("roleName"));
                rolePermission.setPermissionName(rs.getString("permissionName"));
                rolePermission.setDescription(rs.getString("description"));
                listRolePermission.add(rolePermission);
            }
        } catch (SQLException e) {
            System.out.println("DATOS: ERROR AL LISTAR PERMISOS DE ROL" + e.getMessage());
            e.printStackTrace();
        } finally {
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

    public boolean assignPermission(rolePermissionTable rolePermission) {
        boolean saved = false;
        try {
            c = connectionPool.getConnection();
            this.fillRsRolePermission(c);
            this.rsRolePermission.moveToInsertRow();

            rsRolePermission.updateInt("roleId", rolePermission.getRoleId());
            rsRolePermission.updateInt("permissionId", rolePermission.getPermissionId());

            rsRolePermission.insertRow();
            rsRolePermission.moveToCurrentRow();
            saved = true;

        } catch (SQLException e) {
            System.out.println("DATOS: ERROR AL ASIGNAR PERMISO A ROL" + e.getMessage());
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

    public boolean deleteRolePermission(int id) {
        boolean deleted = false;
        try {
            c = connectionPool.getConnection();
            ps = c.prepareStatement("DELETE FROM sibet.rolepermission WHERE rolePermissionId = ?; ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                deleted = true;
            }
        } catch (Exception e) {
            System.out.println("DATOS: ERROR AL ELIMINAR PERMISO DE ROL" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rsRolePermission != null) {
                    rsRolePermission.close();
                }
                if (c != null) {
                    connectionPool.closeConnection(c);
                }
                if (c != null) {
                    connectionPool.closeConnection(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deleted;
    }
    
}

