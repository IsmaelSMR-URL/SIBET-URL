package data;

import entities.tables.permissionTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class dtPermission {

    connectionPool pc = connectionPool.getInstance();
    Connection c = null;
    private ResultSet rsPermission = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

    public dtPermission() {
    }

    public void fillRsPermission(Connection c) {
        try {
            this.ps = c.prepareStatement("SELECT * FROM sibet.permission;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            this.rsPermission = ps.executeQuery();
        } catch (Exception e) {
            System.out.println("ERROR EN LISTAR PERMISOS: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<permissionTable> listPermissions() {
        ArrayList<permissionTable> listPermission = new ArrayList<permissionTable>();
        try {
            this.c = pc.getConnection();
            this.ps = this.c.prepareStatement("SELECT * FROM sibet.permission;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.rsPermission = this.ps.executeQuery();

            while (this.rsPermission.next()) {
                permissionTable permission = new permissionTable();
                permission.setPermissionId(this.rsPermission.getInt("permissionId"));
                permission.setName(this.rsPermission.getString("name"));
                permission.setDescription(this.rsPermission.getString("description"));
                listPermission.add(permission);
            }
        } catch (Exception e) {
            System.out.println("ERROR EN LISTAR PERMISOS: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (this.rsPermission != null) {
                    this.rsPermission.close();
                }
                if (this.ps != null) {
                    this.ps.close();
                }
                if (this.c != null) {
                    connectionPool.closeConnection(this.c);
                }
            } catch (Exception e) {
                System.out.println("ERROR EN LISTAR PERMISOS: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return listPermission;
    }

    public boolean addPermissions(permissionTable permission) {
        boolean saved = false;
        try {
            c = connectionPool.getConnection();
            fillRsPermission(c);
            rsPermission.moveToInsertRow();

            rsPermission.updateString("name", permission.getName());
            rsPermission.updateString("description", permission.getDescription());

            rsPermission.insertRow();
            rsPermission.moveToCurrentRow();
            saved = true;
        } catch (Exception e) {
            System.err.println("ERROR AL GUARDAR PERMISO: " + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (rsPermission != null) {
                    rsPermission.close();
                }
                if (ps != null) {
                    ps.close();
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

    public boolean modifyPermission(permissionTable permission) {
        boolean modified = false;
        try {
            c = connectionPool.getConnection();
            fillRsPermission(c);
            rsPermission.beforeFirst();
            while (rsPermission.next()) {
                if (rsPermission.getInt(1) == permission.getPermissionId()) {
                    rsPermission.updateString("name", permission.getName());
                    rsPermission.updateString("description", permission.getDescription());
                    rsPermission.updateRow();
                    modified = true;
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR AL MODIFICAR PERMISO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rsPermission != null) {
                    rsPermission.close();
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

    public boolean deletePermission(int permissionId) {
        boolean deleted = false;
        try {
            c = connectionPool.getConnection();
            fillRsPermission(c);
            rsPermission.beforeFirst();
            while (rsPermission.next()) {
                if (rsPermission.getInt(1) == permissionId) {
                    rsPermission.deleteRow();
                    deleted = true;
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR PERMISO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rsPermission != null) {
                    rsPermission.close();
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
