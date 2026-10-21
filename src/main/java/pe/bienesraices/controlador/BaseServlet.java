package pe.bienesraices.controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.bienesraices.config.Servicios;
import pe.bienesraices.modelo.UsuarioSesion;

/** Utilidades comunes de los controladores (patron MVC). */
abstract class BaseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected Servicios servicios() {
        return Servicios.de(getServletContext());
    }

    protected UsuarioSesion usuario(HttpServletRequest req) {
        return (UsuarioSesion) req.getSession().getAttribute("usuario");
    }

    /** Las vistas estan en WEB-INF: el navegador no puede abrirlas directamente. */
    protected void vista(String nombre, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/vistas/" + nombre + ".jsp").forward(req, res);
    }

    /** Patron Post-Redirect-Get con mensaje flash en la sesion. */
    protected void redirigir(String ruta, String mensaje, HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        if (mensaje != null) {
            req.getSession().setAttribute("flash", mensaje);
        }
        res.sendRedirect(req.getContextPath() + ruta);
    }
}
