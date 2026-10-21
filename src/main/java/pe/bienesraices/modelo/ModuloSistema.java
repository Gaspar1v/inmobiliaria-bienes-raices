package pe.bienesraices.modelo;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * Modulos planificados del portal. En esta entrega solo el panel principal
 * esta activo; el resto se muestra en el dashboard como «Proximamente».
 *
 * @param clave       identificador de la pestana
 * @param nombre      nombre visible
 * @param descripcion que hara el modulo
 * @param requisitos  requisitos funcionales que cubre
 * @param roles       roles que tendran acceso
 * @param activo      si ya esta disponible
 */
public record ModuloSistema(String clave, String nombre, String descripcion, String requisitos,
                            ImmutableSet<Rol> roles, boolean activo) {

    /** Indica si el rol podra usar el modulo cuando este disponible. */
    public boolean permitidoPara(Rol rol) {
        return roles.contains(rol);
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    // Getters con prefijo get para usarlos desde las vistas JSP (EL)
    public String getClave() { return clave; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getRequisitos() { return requisitos; }
    public boolean isActivo() { return activo; }
}
