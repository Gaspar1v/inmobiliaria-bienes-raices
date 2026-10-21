package pe.bienesraices.filtro;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Cabeceras HTTP de seguridad recomendadas por OWASP para todas las respuestas. */
public class CabecerasSeguridadFiltro extends HttpFilter {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        // Solo recursos del propio sitio: bloquea scripts inyectados (XSS)
        res.setHeader("Content-Security-Policy",
                "default-src 'self'; img-src 'self' data:; style-src 'self'; script-src 'self'; frame-ancestors 'none'");
        res.setHeader("X-Content-Type-Options", "nosniff");
        res.setHeader("X-Frame-Options", "DENY");
        res.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
        if (req.getRequestURI().contains("/app/")) {
            // Las paginas internas no deben quedar en la cache del navegador
            res.setHeader("Cache-Control", "no-store");
        }
        chain.doFilter(req, res);
    }
}
