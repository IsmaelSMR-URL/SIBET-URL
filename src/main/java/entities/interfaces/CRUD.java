package entities.interfaces;

import entities.tables.typeTable;
import java.util.List;
public interface CRUD {
    public List listar();
    public typeTable list(int id);
    public boolean add(typeTable t);
    public boolean edit(typeTable t);
    public boolean delete(int id);

}
