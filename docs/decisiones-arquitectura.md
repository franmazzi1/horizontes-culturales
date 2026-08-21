# Decisiones de Arquitectura — Horizontes Culturales

## Cómo funciona la página (resumen funcional)

### Página principal
Tres secciones: Novedades (últimas destacadas, o últimas por fecha si no hay destacadas), 
Talleres (tarjetas con imagen, título, descripción breve — si hay un único Taller, se 
navega directo a su página sin listado intermedio), Eventos (sección visual/publicitaria 
con los últimos eventos).

### Novedades
Funcionan como noticias de un "diario digital": texto + galería de imágenes propia. 
Se muestran ordenadas por destacadas primero, luego por fecha. Tienen buscador. Cada una 
puede estar vinculada opcionalmente a un Taller, Evento o Grupo — el click navega directo 
a esa entidad si existe el vínculo.

**Cómo se crean:**
- Automáticamente al crear un Evento nuevo.
- Manualmente por el admin para cualquier otro anuncio (cambio de sede, vacaciones, nuevo 
  Taller/Grupo, feedback curado), con vínculo opcional a la entidad correspondiente.

**Ciclo de vida:** nunca se borran por el paso del tiempo, solo manualmente por el admin. 
Excepción: si el Grupo vinculado se elimina, la Novedad se elimina en cascada con él.

### Talleres
Página de detalle: descripción extensa, modalidad/sede, profesor a cargo (nombre + 
referencia opcional + foto), multimedia del lugar, y un desplegable con sus Grupos 
(día y horario de cada uno).

### Grupos
Pertenecen a un Taller. Si un Grupo deja de funcionar, se elimina físicamente (no se 
conserva como historial, a diferencia de Evento).

### Eventos
Nunca se borran (se conservan como historial permanente). Antes de ocurrir: descripción, 
fecha, lugar, categoría, expositor opcional. Después de ocurrir: se les puede agregar una 
crónica y fotos, sin perder la publicación original. Página de Eventos: anuncio del 
próximo, filtro por categoría, opción de ver últimos realizados.

---

## Modelo de entidades (Fase A)

- **Taller**: nombre, descripción, profesor (relación a Persona)
- **Grupo**: día, horario, modalidad, relación a Taller (obligatoria)
- **CategoriaEvento**: nombre, descripción, imagen — clasifica tipos de actividad 
  (Charla, Noche de Cine, Banquete...)
- **Evento**: descripción, crónica (opcional, posterior), fecha, lugar, destacado, 
  relación a CategoriaEvento (obligatoria) y a Persona como expositor (opcional)
- **Novedad**: contenido, fecha, destacado, relaciones opcionales a Taller, Evento y Grupo
- **Persona**: nombre, referencia (link a CV/redes) — reutilizada entre profesor de 
  Taller y expositor de Evento
- **ImagenGaleria**: url, relación opcional a Evento, Novedad, Taller o Persona 
  (una imagen pertenece como máximo a una de las cuatro)

## Configuración fija (no en base de datos)
Email de contacto, WhatsApp/teléfono, y links de redes sociales — viven en variables de 
entorno, editables solo por el desarrollador vía código.

---

## ADRs (Architecture Decision Records)

### ADR 001: Información de contacto y redes sociales como configuración
**Decisión:** no se modelan como tablas de BD. Viven como variables de entorno.
**Razones:** cambian con poca frecuencia, las administra solo el desarrollador, exponer 
un endpoint editable agrega superficie de ataque innecesaria.

### ADR 002: Taller sin campo de categoría
**Decisión:** no existe CategoriaTaller. El nombre del Taller ya comunica su temática.
**Razón (YAGNI):** con un solo Taller, no hay nada que agrupar o filtrar. Se revisita si 
en el futuro hay múltiples Talleres que lo justifiquen.

### ADR 003: Ciclo de vida de Novedad
**Decisión:** las Novedades nunca se eliminan automáticamente por tiempo. Solo se borran 
manualmente desde el panel admin.
**Excepción:** Novedad-Grupo usa borrado en cascada, ya que un Grupo eliminado no puede 
dejar una Novedad "colgando" de una referencia inexistente.
**Sobre eventos finalizados:** no se borra ni modifica la Novedad asociada. La prioridad 
de visualización (destacados vigentes, luego últimos) se resuelve en tiempo de consulta 
en la capa service, no en el modelo.

### ADR 004: Reglas de creación de Novedad
**Decisión:** Evento→Novedad es automático al crear un Evento. Taller/Grupo→Novedad es 
siempre manual, con vínculo opcional para permitir la navegación por click.

### ADR 005: Persona como entidad compartida
**Decisión:** se crea una entidad Persona (nombre, referencia) reutilizada entre 
Taller.profesor y Evento.expositor, en vez de duplicar campos de texto en cada entidad.
**Razón:** ambos representan el mismo concepto (una persona referente con nombre y 
posible link de referencia/foto), a diferencia de CategoriaTaller vs CategoriaEvento, 
que resultaron ser conceptos distintos y no se unificaron.

---

## Backlog de funcionalidades futuras (no diseñadas todavía)

### Catálogo de material de lectura (libros)
Depende del sistema de alumnos (Fase B) — requiere Usuario para asociar material a 
grupos/alumnos con acceso diferenciado.

### Catálogo de merchandising
Vidriera de productos sin pago online. El visitante completa un formulario y se lo 
redirige a WhatsApp. Entidades futuras aproximadas: Producto, SolicitudConsulta.

### Notificaciones push de nuevos Grupos
El visitante puede optar por recibir una notificación push del navegador cuando se cree 
un nuevo Grupo. Requiere entidad SuscripcionPush, librería de envío, claves VAPID, 
Service Worker en frontend, y HTTPS en producción.

### Feedback directo persona↔admin
Depende de que exista Usuario (Fase B) — con persistencia y moderación antes de publicar.

---

## Backlog de diseño de frontend (pendiente para etapa de frontend)

- Página principal con 3 secciones descritas arriba.
- Vista de detalle de Taller con acordeón de Grupos.
- Vista de Eventos con filtro por categoría.
- Buscador de Novedades.
- Navegación condicional: si hay un único Taller, se accede directo sin listado intermedio.
- Lógica de "últimas N destacadas, o últimas por fecha si no hay destacadas" (capa service).