package pe.bienesraices.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bitacora de auditoria (RNF007). Escribe en el logger "AUDITORIA", que Logback
 * envia a un archivo separado (logs/auditoria.log). El usuario y la IP se
 * agregan automaticamente desde el MDC (ver MdcFiltro).
 */
public final class Auditoria {

    private static final Logger LOG = LoggerFactory.getLogger("AUDITORIA");

    private Auditoria() {
    }

    public static void registrar(String accion, String detalle) {
        LOG.info("accion={} detalle=\"{}\"", accion, limpiar(detalle));
    }

    /**
     * Evita la inyeccion de lineas en el log (CWE-117): se eliminan saltos de
     * linea para que un usuario no pueda falsificar entradas de auditoria.
     */
    public static String limpiar(String texto) {
        if (texto == null) {
            return "";
        }
        return StringUtils.replaceChars(texto, "\r\n\t", "   ");
    }

    /** Enmascara datos personales en los logs: 45678912 -> ****8912 */
    public static String enmascarar(String dato) {
        if (StringUtils.length(dato) <= 4) {
            return "****";
        }
        return StringUtils.leftPad(StringUtils.right(dato, 4), dato.length(), '*');
    }
}
