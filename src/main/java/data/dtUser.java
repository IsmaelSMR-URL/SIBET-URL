package data;

import entities.tables.userTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class dtUser {

    connectionPool pc = connectionPool.getInstance();
    Connection c = null;
    private ResultSet rsUser = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

    public dtUser() {
    }

    public void fillRsUser(Connection c) {
        try {
            this.ps = c.prepareStatement("SELECT * FROM sibet.user;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            this.rsUser = ps.executeQuery();
        } catch (Exception e) {
            System.out.println("ERROR EN LISTAR USUARIOS: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<userTable> listUsers() {
        ArrayList<userTable> listUser = new ArrayList<userTable>();
        try {
            this.c = pc.getConnection();
            this.ps = this.c.prepareStatement("SELECT * FROM sibet.user;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.rsUser = this.ps.executeQuery();

            while (this.rsUser.next()) {
                userTable user = new userTable();
                user.setUserId(this.rsUser.getInt("userId"));
                user.setUsername(this.rsUser.getString("username"));
                user.setPassword(this.rsUser.getString("password"));
                user.setName(this.rsUser.getString("name"));
                user.setLastname(this.rsUser.getString("lastname"));
                user.setRoles(this.rsUser.getInt("roles"));
                listUser.add(user);
            }
        } catch (Exception e) {
            System.out.println("ERROR EN LISTAR USUARIOS: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (this.rsUser != null) {
                    this.rsUser.close();
                }
                if (this.c != null) {
                    this.ps.close();
                }
            } catch (Exception e) {
                System.out.println("ERROR EN LISTAR USUARIOS: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    if (this.rsUser != null) {
                        this.rsUser.close();
                    }
                    if (this.c != null) {
                        this.ps.close();
                    }
                } catch (Exception e) {
                    System.out.println("ERROR EN LISTAR USUARIOS: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return listUser;
    }

    public boolean addUser(userTable user) {
        boolean saved = false;
        try {
            c = connectionPool.getConnection();
            fillRsUser(c);
            rsUser.moveToInsertRow();
            rsUser.updateString("username", user.getUsername());
            rsUser.updateString("password", user.getPassword());
            rsUser.updateString("name", user.getName());
            rsUser.updateString("lastname", user.getLastname());
            rsUser.updateInt("roles", user.getRoles());

            rsUser.insertRow();
            rsUser.moveToCurrentRow();
            saved = true;
        } catch (Exception e) {
            System.out.println("ERROR EN AGREGAR USUARIO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rsUser != null) {
                    rsUser.close();
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

    public boolean modifyUser(userTable user) {
        boolean modified = false;
        try {
            c = connectionPool.getConnection();
            fillRsUser(c);
            rsUser.beforeFirst();
            while (rsUser.next()) {
                if (rsUser.getInt("userId") == user.getUserId()) {
                    rsUser.updateString("username", user.getUsername());
                    rsUser.updateString("password", user.getPassword());
                    rsUser.updateString("name", user.getName());
                    rsUser.updateString("lastname", user.getLastname());
                    rsUser.updateInt("roles", user.getRoles());
                    rsUser.updateRow();
                    modified = true;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR EN MODIFICAR USUARIO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rsUser != null) {
                    rsUser.close();
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

    public boolean deleteUser(int userId) {
        boolean deleted = false;
        try {
            c = connectionPool.getConnection();
            fillRsUser(c);
            rsUser.beforeFirst();
            while (rsUser.next()) {
                if (rsUser.getInt(1) == userId) {
                    rsUser.deleteRow();
                    deleted = true;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR EN ELIMINAR USUARIO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rsUser != null) {
                    rsUser.close();
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