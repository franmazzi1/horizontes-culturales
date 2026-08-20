# Decisiones de Arquitectura

## ADR 001: Información de contacto y redes sociales como configuración, no como entidades de BD

**Contexto:** el proyecto busca que todo el contenido cultural (eventos, talleres, novedades) 
sea editable desde un panel admin sin tocar código.

**Decisión:** el email/WhatsApp de contacto y los links de redes sociales NO se modelan como 
tablas en la base de datos. Viven como variables de entorno / configuración de Spring Boot.

**Razones:**
- Cambian con muy poca frecuencia.
- Los administra únicamente el desarrollador del proyecto, no personal no técnico.
- Exponer un endpoint editable agrega superficie de ataque innecesaria para un dato de bajo 
  valor de "frescura" y alto costo de exposición si el sistema de autenticación fallara.

**Alternativa descartada:** tabla de una sola fila en BD, editable vía API — descartada por 
el motivo de seguridad/exposición innecesaria arriba mencionado.

## Backlog de funcionalidades futuras (no diseñadas todavía)

### Catálogo de material de lectura (libros)
Depende del sistema de alumnos (Fase B) — requiere `Usuario` para asociar material 
a grupos/alumnos con acceso diferenciado. Se diseña junto con `Usuario`, `Inscripcion`.

### Catálogo de merchandising
Vidriera de productos (sin pago online). Flujo esperado: el visitante ve productos, 
completa un formulario con sus datos e interés, y se lo redirige a contactar por WhatsApp. 
Implicaría dos entidades nuevas aproximadas: `Producto` (nombre, descripción, precio, 
imagen, disponibilidad) y `SolicitudConsulta` (datos del formulario: nombre, contacto, 
producto de interés, fecha). No forma parte de la Fase A actual — se evalúa como 
actualización posterior, una vez estabilizado el core (Talleres/Grupos/Eventos/Novedades).

## ADR 002: Taller sin campo de categoría

**Contexto:** se evaluó agregar un campo `categoria` (o una entidad `CategoriaTaller`) 
para clasificar talleres.

**Decisión:** no se agrega. El nombre del Taller ya comunica su temática (hoy: "Filosofía"). 
Con un solo Taller, no hay nada que agrupar o filtrar.

**Razón (principio YAGNI):** agregar una entidad de categorización sin un caso de uso 
concreto es complejidad prematura. Se revisita si en el futuro existen múltiples Talleres 
que necesiten agruparse bajo un tema más amplio.
### Notificaciones push de nuevos Grupos
El visitante puede optar por recibir una notificación push del navegador (sin necesidad 
de email ni cuenta) cuando se cree un nuevo Grupo — distinta de "todas las novedades".
Requiere: entidad `SuscripcionPush` (guarda el endpoint que genera el navegador), 
librería de envío (ej. web-push), claves VAPID, Service Worker en el frontend, y HTTPS 
en producción. No forma parte de la Fase A — se evalúa una vez estabilizado el core.