package datos;

import domain.Cliente;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class ClienteDAOimpl implements ClienteDAO {

    @PersistenceContext(unitName = "ClientePU")
    EntityManager em;
    
    @Override
    public List<Cliente> listar() {
        return em.createNamedQuery("Cliente.findAll").getResultList();
    }

    @Override
    public Cliente encontrar(Cliente cliente) {
        return em.find(Cliente.class, cliente.getIdCliente());
    }

    @Override
    public void insertar(Cliente cliente) {
        em.persist(cliente);
    }

    @Override
    public void actualizar(Cliente cliente) {
        em.merge(cliente);
    }

    @Override
    public void eliminar(Cliente cliente) {
        em.remove(em.merge(cliente));
    }
}