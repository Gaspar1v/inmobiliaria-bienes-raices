# Portal Web Inmobiliaria Bienes Raíces S.A.C.

Proyecto del **Curso Integrador I: Sistemas – Software** (UTP Arequipa, 2026-2, clase 47314).
Portal web para gestionar la venta, el alquiler y la captación de inmuebles.

**Equipo:** Cáceres Quispe Kasandra · Torres Ccari Jhare Jhoao · Vera Quispe Gaspar Yamir · Dolorier Arias Alisson Valeria
**Docente:** MSc. Ing. María Vilma Escobar Castillo

## Estado de esta entrega

| Módulo | Estado |
|---|---|
| Inicio de sesión del personal (RF001) | ✅ Disponible |
| Dashboard con pestañas por módulo | ✅ Disponible (pestaña Inicio) |
| Inmuebles, Búsqueda y pedidos, Captación, Operaciones, Contratos y reportes, Usuarios y agentes | 🔜 Próximamente |

## Tecnologías

- Java 17+ (probado con JDK 24) y Jakarta EE 10 (Servlets 6, JSP, JSTL 3)
- Arquitectura **MVC** + patrón **DAO** con **JDBC** y **MySQL 8**
- Maven · Google Guava · Apache Commons (Lang, Codec) · Logback

```
src/main/java/pe/bienesraices/
├── config/       Configuración, arranque y contenedor de servicios
├── modelo/       Usuario, Rol, UsuarioSesion, ModuloSistema
├── dao/          UsuarioDAO + FabricaConexion (impl/: JDBC)
├── servicio/     AuthService (inicio de sesión con bloqueo por intentos)
├── controlador/  LoginServlet, LogoutServlet, PanelServlet (dashboard)
├── filtro/       Cabeceras de seguridad, MDC de Logback, CSRF, autenticación
├── seguridad/    PasswordHasher (PBKDF2), CsrfUtil, Permisos por rol
└── util/         Auditoria (bitácora con Logback)
src/main/webapp/WEB-INF/vistas/   login.jsp, panel.jsp, error.jsp
```

## Cómo ejecutarlo

### Desde Apache NetBeans
1. *File → Open Project* y elegir esta carpeta (NetBeans reconoce el `pom.xml`).
2. *Run* con GlassFish 7 o Tomcat 10.1, o ejecutar el objetivo Maven `jetty:run`.

### Desde la consola (sin instalar servidor ni MySQL)

```bash
mvn jetty:run
```

Abrir http://localhost:8080/inmobiliaria. Por defecto se usa una base H2 en memoria con los usuarios de prueba.

### Con MySQL 8

```bash
mysql -u root -p < sql/01_crear_bd_mysql.sql
mysql -u root -p bienes_raices < src/main/resources/db/schema.sql
mysql -u root -p bienes_raices < src/main/resources/db/datos.sql
```

Definir antes de iniciar el servidor (las credenciales **no** se guardan en el repositorio):

```bash
setx BR_DB_URL "jdbc:mysql://localhost:3306/bienes_raices?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Lima"
setx BR_DB_USER "br_app"
setx BR_DB_PASSWORD "la-clave-que-definieron"
setx BR_DB_INICIALIZAR "false"
```

## Usuarios de prueba (solo demostración)

| Usuario | Contraseña | Rol |
|---|---|---|
| admin | Admin#2026 | Administrador del sistema |
| gerente | Gerente#2026 | Gerente de agentes |
| agente | Agente#2026 | Agente inmobiliario |
| asistente | Asistente#2026 | Asistente administrativo |

## Seguridad del inicio de sesión

- Contraseñas con PBKDF2-HMAC-SHA256 (210 000 iteraciones y sal aleatoria).
- Bloqueo de 15 minutos tras 5 intentos fallidos y mensaje genérico de error.
- Cambio del identificador de sesión al ingresar; cookie `HttpOnly` y `SameSite=Strict`; sesión de 30 minutos.
- Token anti-CSRF en los formularios y cabeceras CSP, `X-Frame-Options` y `nosniff`.
- Rutas internas protegidas por rol con denegación por defecto.
- Bitácora de auditoría (`logs/auditoria.log`) con usuario e IP de cada inicio y cierre de sesión.

## Pruebas y documentación

```bash
mvn test
```

```bash
mvn javadoc:javadoc
```

El Javadoc queda en `target/reports/apidocs/index.html`. Más detalle en `docs/ARQUITECTURA.md` y `docs/MANUAL_USUARIO.md`.
