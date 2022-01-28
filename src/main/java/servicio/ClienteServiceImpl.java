package servicio;

import datos.ClienteDAO;
import domain.Cliente;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ClienteServiceImpl implements ClienteService {

    @Inject
    ClienteDAO clienteDao;
    
    @Override
    public List<Cliente> listar() {
        return clienteDao.listar();
    }

    @Override
    public Cliente encontrar(Cliente cliente) {
        return clienteDao.encontrar(cliente);
    }

    @Override
    public void insertar(Cliente cliente) {
        clienteDao.insertar(cliente);
    }

    @Override
    public void actualizar(Cliente cliente) {
        clienteDao.actualizar(cliente);
    }

    @Override
    public void eliminar(Cliente cliente) {
        clienteDao.eliminar(cliente);
    }
}