package pe.bienesraices.seguridad;

import static pe.bienesraices.modelo.Rol.ADMINISTRADOR;
import static pe.bienesraices.modelo.Rol.AGENTE;
import static pe.bienesraices.modelo.Rol.ASISTENTE;
import static pe.bienesraices.modelo.Rol.GERENTE;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Sets;

import pe.bienesraices.modelo.ModuloSistema;
import pe.bienesraices.modelo.Rol;

/**
 * Matriz de autorizacion por rol (RNF003). Se aplica en el filtro de
 * autenticacion a todas las rutas /app/*.
 *
 * <p>Las colecciones son inmutables (Google Guava): una vez cargada la matriz
 * ningun codigo puede agregar permisos en tiempo de ejecucion.</p>
 */
public final class Permisos {

    /** Ruta protegida -&gt; roles que pueden ingresar. En esta entrega solo existe el panel. */
    private static final ImmutableSetMultimap<String, Rol> RUTAS = ImmutableSetMultimap.<String, Rol>builder()
            .putAll("/app/panel", AGENTE, GERENTE, ASISTENTE, ADMINISTRADOR)
            .build();

    /** Modulos del portal segun el caso de estudio; se iran activando en cada avance. */
    private static final ImmutableList<ModuloSistema> MODULOS = ImmutableList.of(
            new ModuloSistema("inmuebles", "Inmuebles", "Registrar, modificar, consultar y dar de baja inmuebles; historial de estados.",
                    "RF002, RF007, RF009", Sets.immutableEnumSet(AGENTE, GERENTE, ASISTENTE, ADMINISTRADOR), false),
            new ModuloSistema("busqueda", "Búsqueda y pedidos", "Búsqueda por características y registro de pedidos de compra o alquiler.",
                    "RF003, RF004", Sets.immutableEnumSet(AGENTE, GERENTE, ADMINISTRADOR), false),
            new ModuloSistema("captacion", "Captación", "Solicitudes de propietarios, verificación de documentos e incorporación a la cartera.",
                    "RF005", Sets.immutableEnumSet(AGENTE, ASISTENTE, ADMINISTRADOR), false),
            new ModuloSistema("operaciones", "Operaciones", "Ventas y alquileres con verificación de disponibilidad y cálculo de comisiones.",
                    "RF006", Sets.immutableEnumSet(AGENTE, ASISTENTE, ADMINISTRADOR), false),
            new ModuloSistema("reportes", "Contratos y reportes", "Contratos con formato de la empresa y reportes de ventas, alquileres y comisiones.",
                    "RF008", Sets.immutableEnumSet(GERENTE, ADMINISTRADOR), false),
            new ModuloSistema("usuarios", "Usuarios y agentes", "Gestión de usuarios, roles, permisos y agentes inmobiliarios.",
                    "RF001, RF010", Sets.immutableEnumSet(ADMINISTRADOR), false));

    private Permisos() {
    }

    /**
     * Denegar por defecto: una ruta /app/* no registrada no es accesible.
     *
     * @param rol  rol del usuario autenticado
     * @param ruta ruta del servlet solicitado, por ejemplo {@code /app/panel}
     * @return {@code true} si el rol tiene acceso a la ruta
     */
    public static boolean puedeAcceder(Rol rol, String ruta) {
        return RUTAS.containsEntry(ruta, rol);
    }

    /** @return modulos planificados del portal, en el orden de las pestanas del dashboard */
    public static List<ModuloSistema> modulos() {
        return MODULOS;
    }
}
