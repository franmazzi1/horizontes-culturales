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