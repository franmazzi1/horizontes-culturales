## Decisiones de Arquitectura — Horizontes Culturales

> Documento vivo. Describe el estado **actual** del proyecto y el porqué de cada
> decisión. Las decisiones que reemplazan a otras anteriores lo indican.
> Última revisión: octubre de 2026.

## 1. Visión general

Sitio web real para un espacio cultural de Santa Fe (talleres de filosofía, eventos,
novedades). Proyecto de aprendizaje guiado, con foco en buenas prácticas y en que sea
**entregable**: quien lo administre no sabe programar, y el desarrollador no debería
tener que intervenir después de entregarlo.

**Stack:** Java 25, Spring Boot 4.1, Spring Web MVC, Spring Data JPA, Hibernate,
Bean Validation, PostgreSQL 18, Maven. Frontend en HTML/CSS/JavaScript puro (Fetch API).

**Arquitectura:** backend como API REST pura, y clientes que la consumen (sitio público
y panel de administración). Capas del backend:

```
model/       entidades JPA (qué datos existen y cómo se relacionan)
repository/  acceso a datos (Spring Data JPA)
service/     reglas de negocio
controller/  entrada HTTP (recibe y devuelve DTOs)
dto/         objetos de entrada (Request) y de salida (Response)
exception/   excepciones propias y su traducción a respuestas HTTP
util/        utilidades sin estado (textos, enlaces)
```

El controller habla con el service, nunca con el repository. El service no conoce
HTTP.

## 2. Cómo funciona la página

### Página principal
Tres secciones: Novedades (destacadas, o las últimas por fecha si no hay ninguna),
Talleres (tarjetas; si hay un único Taller se navega directo a su página) y Eventos
(sección visual con los destacados, o los últimos por fecha).

### Novedades
Noticias de un "diario digital": título y contenido. Se muestran primero las
destacadas y, si no hay ninguna, las últimas por fecha. Una novedad puede estar
vinculada a un Evento (el click navega a él) o ser una noticia suelta.

- Se crean automáticamente al crear un Evento, o a mano para cualquier otro anuncio.
- La fecha la pone el servidor al crearlas.

### Talleres
Descripción, uno o más profesores y un desplegable con sus Grupos (día, horario y
modalidad).

### Eventos
Título, descripción, fecha, horario (texto libre), lugar, categoría, expositores
(cero o más), galería multimedia y crónica.

- **Próximos:** fecha de hoy o posterior. **Realizados:** fecha anterior a hoy.
- La **crónica** se carga después del evento, con un endpoint propio, junto con fotos
  y videos.

### Categorías de evento
Nombre, descripción e imagen. Contenido editorial, no solo una etiqueta.

### Personas
Nombre y una referencia opcional (link a CV o redes). Puede representar a una persona
o a un grupo. Se reutiliza entre profesores de Taller y expositores de Evento.

### Multimedia
Imágenes y videos de un Evento (y, a futuro, de Novedades, Talleres y Personas).

## 3. Modelo de entidades

| Entidad | Campos principales | Relaciones |
|---|---|---|
| `Persona` | nombre, referencia (opcional) | — |
| `Taller` | nombre, descripción | muchos a muchos con `Persona` (profesores, al menos uno) |
| `Grupo` | día, horario, modalidad | `Taller` (obligatoria) |
| `CategoriaEvento` | nombre, descripción, imagenUrl | — |
| `Evento` | título, descripción, crónica, fecha, horario, lugar, destacado | `CategoriaEvento` (obligatoria); muchos a muchos con `Persona` (expositores, cero o más); `Multimedia` |
| `Novedad` | título, contenido, fecha, destacado | `Evento` (opcional) |
| `Multimedia` | url, tipo (IMAGEN o VIDEO) | un único dueño: `Evento`, `Novedad`, `Taller` o `Persona` |

## 4. Reglas de borrado

| Entidad | Cómo se borra | Detalle |
|---|---|---|
| `Persona` | Manual, condicional | Se rechaza con **409** si es profesor de algún Taller o expositor de algún Evento |
| `CategoriaEvento` | Manual | Borra en cascada sus Eventos |
| `Taller` | Manual | Borra en cascada sus Grupos |
| `Grupo` | Manual, o en cascada | Se borra con su Taller |
| `Evento` | Manual, o en cascada | Se borra con su Categoría; borra su Novedad y su Multimedia |
| `Novedad` | Manual, o en cascada | Se borra con su Evento |
| `Multimedia` | Manual, o en cascada | Se borra con su dueño |

Las cascadas se resuelven en la base de datos (`@OnDelete(CASCADE)`), no en el código.

## 5. Decisiones (ADR)

### ADR 001 — Contacto y redes sociales como configuración
**Decisión:** email, WhatsApp y redes no se modelan en la base: viven como variables de
entorno, editables solo por el desarrollador.
**Razones:** cambian poco y exponer un endpoint editable agrega superficie de ataque.
**Costo conocido:** cada cambio de teléfono o de red requiere intervención del
desarrollador, lo que choca con el objetivo de no tener que mantenerlo. Se revisa al
armar el panel.

### ADR 002 — Taller sin categoría
**Decisión:** no existe `CategoriaTaller`.
**Razón (YAGNI):** con pocos talleres no hay nada que agrupar. Se revisa si crecen.

### ADR 003 — Persona compartida, con relación muchos a muchos
**Decisión:** `Persona` se reutiliza entre profesores de Taller y expositores de Evento,
y tanto un Taller como un Evento pueden tener **varias** personas (tablas intermedias
`taller_profesores` y `evento_expositores`).
**Razón:** una charla en panel o un taller dictado por dos personas es un caso normal.
Una Persona puede ser también un grupo, porque `nombre` es texto libre.
**Reemplaza** a la versión original, donde cada uno tenía una sola persona.
**Reglas:** un Taller exige al menos un profesor; un Evento admite cero o más
expositores. La relación se modela con `Set` para evitar repetidos.

### ADR 004 — Ciclo de vida: todo se puede borrar, con cascadas
**Decisión:** Evento, Taller, Categoría y Novedad se eliminan manualmente desde el
panel. Persona se elimina de forma condicional (ver sección 4).
**Razón:** se prioriza la limpieza de datos sobre conservar historial completo.
**Reemplaza** a la decisión original de que Evento, Taller y Persona nunca se borran.
**Pendiente:** la limpieza automática de una Persona cuando se borra la última entidad
que la usa **no está implementada**; hoy se borra a mano, y solo si no tiene
referencias.

### ADR 005 — Novedad solo se relaciona con Evento
**Decisión:** `Novedad` tiene una relación opcional con `Evento`, y nada más.
**Reglas:**
- Al crear un Evento se crea automáticamente su Novedad (con el título y la descripción
  del evento).
- Las demás novedades son noticias sueltas, escritas a mano.
- Una novedad vinculada a un evento **hereda su destacado**; no tiene uno propio.

**Reemplaza** a la versión original, donde una novedad podía vincularse a Taller, Evento
o Grupo.

### ADR 006 — El destacado es manual
**Decisión:** quien administra marca y desmarca el destacado de un evento a mano. No hay
destacado automático por cercanía de la fecha.
**Razón:** es lo más simple de entender y de mantener; no requiere tareas programadas.
**Consecuencia conocida:** los destacados **no vencen solos**; un evento viejo sigue
primero hasta que se lo desmarque. Si se quiere evitar, alcanza con que las portadas
ignoren los eventos cuya fecha ya pasó.
**Portadas:** si hay destacados, se devuelven esos; si no, los últimos por fecha.

### ADR 007 — Horario como texto libre
**Decisión:** el horario de un Evento (y de un Grupo) es un texto de hasta 50
caracteres, obligatorio pero sin formato fijo ("19:00 hs", "a la tarde").
**Razón:** no hace falta una hora exacta, y un campo libre es más fácil para quien carga
los datos. La fecha sigue siendo una fecha (`LocalDate`).

### ADR 008 — Título propio en Evento y Novedad
**Decisión:** ambos tienen un `titulo` obligatorio (máx. 150), separado de su texto.
**Razón:** una portada y un "diario digital" necesitan un titular corto; copiar la
descripción larga como si fuera la noticia no sirve.

### ADR 009 — DTOs con validación en la entrada
**Decisión:** los controllers no reciben ni devuelven entidades: usan DTOs
(`records`) de entrada (`...Request`) y de salida (`...Response`).
**Razones:**
- La respuesta de un POST y la de un GET son siempre iguales.
- El cliente envía ids simples (`tallerId`) y no objetos anidados.
- Se evita que el cliente envíe campos que no debe (como un `id`).
- Los errores se detectan antes de llegar a la base, con mensajes en castellano.

**Reglas de entrada:**
- Las anotaciones de Bean Validation (`@NotBlank`, `@Size`, `@NotEmpty`, `@NotNull`)
  llevan mensajes en castellano.
- Los textos opcionales en blanco se guardan como `null` (constructor compacto del
  `record` más `util/Textos`).

### ADR 010 — Manejo de errores centralizado
**Decisión:** el service lanza excepciones que dicen **qué** pasó, y un único
`GlobalExceptionHandler` decide **cómo** se comunica en HTTP.

| Excepción | Respuesta |
|---|---|
| `RecursoNoEncontradoException` | 404 |
| `ReglaDeNegocioException` | 409 |
| Fallo de validación / `EntradaInvalidaException` | 400, con `errores` por campo |

**Formato:** `ProblemDetail`; los 400 incluyen `errores` (campo → mensaje) para que el
panel muestre cada mensaje junto a su campo.
**Razón:** el service no debe conocer HTTP.

### ADR 011 — Multimedia: imágenes y videos
**Decisión:** `ImagenGaleria` pasa a llamarse `Multimedia` y tiene un `tipo` (IMAGEN o
VIDEO). Cada elemento tiene un único dueño. Por ahora solo se gestionan los de Evento
(`POST /eventos/{id}/multimedia`, `DELETE /multimedia/{id}`).
**Videos:** se cargan como **enlace** de YouTube, Vimeo o Instagram, no como archivo.
Alojar video propio implica mucho costo y mantenimiento.
**Validación:** el enlace se analiza como URL (no como texto), se exige `https` y se
compara el **host** contra una lista permitida, para rechazar dominios que imitan a
uno permitido o que lo esconden en la ruta o en el usuario.
**Instagram:** funciona con cuentas y publicaciones públicas; si el reel se borra o la
cuenta pasa a privada, deja de verse en el sitio.
**Imágenes:** por ahora se aceptan por dirección `https`; cuando exista la subida de
archivos pasarán a apuntar al propio servidor y la regla se endurece.
**Galería:** se muestra debajo del texto de la crónica, no mezclada en el texto.

### ADR 012 — La crónica tiene endpoint propio
**Decisión:** la crónica se carga con `PUT /eventos/{id}/cronica`. Editar un evento
(`PUT /eventos/{id}`) **no la toca**.
**Razón:** se escribe después del evento y no debe pisarse por accidente al corregir
sus datos.

### ADR 013 — Transacciones en operaciones de varios pasos
**Decisión:** `EventoService.crear` es `@Transactional`: guardar el evento y su novedad
automática se confirma junto o no se confirma.
**Razón:** sin esto, un fallo en el segundo paso dejaba un evento sin novedad.

## 6. Notas técnicas

- **Esquema de base:** en desarrollo se usa `ddl-auto=update`. Para producción hay que
  pasar a migraciones controladas.
- **Credenciales:** la contraseña de la base va en la variable de entorno
  `DB_PASSWORD`, nunca en el código.
- **`open-in-view`:** está activo (valor por defecto de Spring), y las relaciones
  perezosas se cargan al armar las respuestas. Se revisa si hay que optimizar.
- **Pruebas de API:** `docs/pruebas-api.http` (extensión REST Client) recorre todos los
  endpoints, de arriba hacia abajo, con los ids encadenados.

## 7. Pendiente

**Antes de entregar (bloqueantes):**
1. **Login del panel de administración** (un usuario administrador). Hoy cualquiera que
   conozca la dirección puede crear o borrar contenido.
2. **Subida de imágenes** desde la computadora, para que quien administra no pegue URLs.
3. **Panel de administración** (`admin/`) y **sitio público** (`frontend/`).
4. **Despliegue y copias de seguridad.**

**Mejoras identificadas:**
- Buscador de novedades.
- Que los destacados dejen de serlo al pasar la fecha del evento.
- Limpieza automática de personas sin referencias.
- Editar datos de contacto desde el panel (ver ADR 001).
- Orden alfabético de personas para los desplegables.

**Backlog (Fase B, no diseñado):** sistema de alumnos (usuarios, roles, inscripciones,
material de lectura, feedback), catálogo de merchandising, notificaciones push de nuevos
Grupos.
