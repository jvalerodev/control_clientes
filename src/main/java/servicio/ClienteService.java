package servicio;

import domain.Cliente;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ClienteService {
    
    public List<Cliente> listar();
    
    public Cliente encontrar(Cliente cliente);
    
    public void insertar(Cliente cliente);
    
    public void actualizar(Cliente cliente);
    
    public void eliminar(Cliente cliente);
}