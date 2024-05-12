package data;

public class dtRol {

    connectionPool pc = connectionPool.getInstance();
    Connection c = null;
    private ResultSet rsRole = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

    public drRol() {
    }

    public void fillRsRole(Connection c) {
        try{
            this.ps = c.prepareStatement("SELECT * FROM sibet.role;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            this.rsRole = ps.executeQuery();
        }catch (Exception e){
            System.out.println("DATOS: ERROR EN LISTAR ROLES " + e.getMessage());
            e.printStackTrace();
        }

    }

    public ArrayList<roleTable> listarRoles() {
        ArrayList<roleTable> listRoles = new ArrayList<roleTable>();
        try {
            this.c = connectionPool.getInstance().getConnection();
            this.ps = this.c.prepareStatement("SELECT * FROM sibet.role;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READNOLY);
            this.rsRole = this.ps.executeQuery();

            while (this.rsRole.next()) {
                roleTable role = new roleTable();
                role.setRoleId(this.rsRole.getInt("roleId"));
                role.setName(this.rsRole.getString("name"));
                role.setDescription(this.rsRole.getString("description"));
                listRoles.add(role);
            }
        } catch (Exception e) {
            System.out.println("DATOS: ERROR EN LISTAR ROLES " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (this.rsRole != null) {
                    this.rsRole.close();
                }
                if (this.ps != null) {
                    this.ps.close();
                }
                if (this.c != null) {
                    this.c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listRoles;
    }

    public boolean addRole(roleTable role) {
        boolean saved = false;
        try {
            this.c = connectionPool.getInstance().getConnection();
            this.ps = this.c.prepareStatement("INSERT INTO sibet.role (name, description) VALUES (?, ?);");
            this.ps.setString(1, role.getName());
            this.ps.setString(2, role.getDescription());
            this.ps.executeUpdate();
            result = true;
        } catch (Exception e) {
            System.out.println("DATOS: ERROR EN AGREGAR ROL " + e.getMessage());
            e.printStackTrace();
}
