package datos;

import domain.Cliente;
import java.util.List;

public interface ClienteDAO {
    
    public List<Cliente> listar();
    
    public Cliente encontrar(Cliente cliente);
    
    public void insertar(Cliente cliente);
    
    public void actualizar(Cliente cliente);
    
    public void eliminar(Cliente cliente);
}