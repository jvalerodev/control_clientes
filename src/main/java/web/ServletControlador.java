package web;

import domain.Cliente;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import servicio.ClienteService;

@WebServlet(name = "ServletControlador", urlPatterns = {"/ServletControlador"})
public class ServletControlador extends HttpServlet {
    
    @Inject
    ClienteService clienteService;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        
        if(accion != null) {
            switch(accion) {
                case "editar":
                    this.editarCliente(request, response);
                    break;
                case "eliminar":
                    this.eliminarCliente(request, response);
                    break;
                default:
                    this.accionDefault(request, response);
            }
        }
        else {
            this.accionDefault(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        
        if(accion != null) {
            switch(accion) {
                case "insertar":
                    this.insertarCliente(request, response);
                    break;
                case "modificar":
                    this.modificarCliente(request, response);
                    break;
                default:
                    this.accionDefault(request, response);
            }
        }
        else {
            this.accionDefault(request, response);
        }
    }
    
    private void accionDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cliente> clientes = clienteService.listar();
        System.out.println("clientes = " + clientes);
        HttpSession sesion = request.getSession();
        sesion.setAttribute("clientes", clientes);
        sesion.setAttribute("totalClientes", clientes.size());
        sesion.setAttribute("saldoTotal", this.calcularSaldoTotal(clientes));
        // request.getRequestDispatcher("clientes.jsp").forward(request, response);
        response.sendRedirect("clientes.jsp");
    }
    
    private double calcularSaldoTotal(List<Cliente> clientes) {
        double saldoTotal = 0;
        for(Cliente cliente: clientes)
            saldoTotal += cliente.getSaldo();
            
        return saldoTotal;
    }
    
    private void insertarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Recuperamos los valores insertados en el formulario agregarCliente.jsp
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        double saldo = 0;
        String saldoString = request.getParameter("saldo");
        if(saldoString != null && !saldoString.equals("")) {
            saldo = Double.parseDouble(saldoString);
        }
        String genero = request.getParameter("genero");
        // 2. Creamos el objeto Cliente a insertar
        Cliente cliente = new Cliente(nombre, apellido, email, telefono, saldo, genero);
        clienteService.insertar(cliente);
        
        this.accionDefault(request, response);
    }
    
    private void modificarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recuperamos los valores insertados en el formulario editarCliente.jsp
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        double saldo = 0;
        String saldoString = request.getParameter("saldo");
        if(saldoString != null && !saldoString.equals("")) {
            saldo = Double.parseDouble(saldoString);
        }
        String genero = request.getParameter("genero");
        
        // 2. Creamos el objeto Cliente a modificar
        Cliente cliente = new Cliente(idCliente, nombre, apellido, email, telefono, saldo, genero);
        clienteService.actualizar(cliente);
        
        this.accionDefault(request, response);
    }
    
    private void editarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recuperamos el ID del cliente a modificar
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        Cliente cliente = clienteService.encontrar(new Cliente(idCliente));
        request.setAttribute("cliente", cliente);
        String jspEditar = "/WEB-INF/paginas/cliente/editarCliente.jsp";
        request.getRequestDispatcher(jspEditar).forward(request, response);
    }
    
    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recuperamos el ID del cliente a eliminar
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        Cliente cliente = new Cliente(idCliente);
        clienteService.eliminar(cliente);
        
        this.accionDefault(request, response);
    }
}