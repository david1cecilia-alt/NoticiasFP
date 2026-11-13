# Noticias FP · Material Design — Entrega

Proyecto Android (Java + XML) con paquete `com.ejemplo.noticias` y API mínima 24.

## Capturas de las pruebas (carpeta `capturas/`)

| Prueba | Captura | Resultado |
|---|---|---|
| La app arranca | `01_arranque.png` | Barra «Portada» con ☰ y tarjetas con chip de categoría ✔ |
| Marcar dos estrellas | `02_dos_favoritas.png` | Estrella llena y tarjeta Filled ✔ |
| Pulsar ☰ | `03_panel_abierto.png` | Panel con cabecera, contador de favoritas y secciones ✔ |
| Atrás con el panel abierto | `04_atras_cierra_panel.png` | Se cierra el panel y la app sigue abierta ✔ |
| Pulsar «Nueva» | `05_nueva_snackbar.png` | «Última hora 1» arriba y Snackbar con «Deshacer» encima del FAB ✔ |
| Desplazar hacia abajo | `06_fab_encogido.png` | El FAB se encoge y solo muestra el icono ✔ |
| Elegir «Tecnología» | `07_seccion_tecnologia.png` | Solo noticias de tecnología, título cambiado y opción marcada ✔ |
| «Mis favoritas» | `08_mis_favoritas.png` | Solo aparecen las dos marcadas ✔ |
| Desmarcar una en «Mis favoritas» | `09_desmarcar_favorita.png` | La tarjeta desaparece ✔ |
| Modo oscuro | `10_modo_oscuro.png`, `11_modo_oscuro_panel.png` | Colores de `values-night` ✔ |

## Ejercicios de ampliación realizados

### Ejercicio 3 · Contador en el panel
La cabecera (`nav_header.xml`) tiene un `TextView` llamado `tvContadorFavoritas`. `MainActivity` lo obtiene
con `binding.navView.getHeaderView(0).findViewById(...)`. El texto se actualiza con
`actualizarContadorFavoritas()` cada vez que se aplica el filtro o se marca o desmarca una favorita. Usa
`plurals` para escribir «1 noticia favorita» o «N noticias favoritas».

### Ejercicio 4 · Tipos de tarjeta
`item_noticia.xml` sigue usando `Widget.Material3.CardView.Elevated`. En `NoticiaAdapter.mostrar()` las
noticias favoritas se pintan como una tarjeta **Filled**: elevación 0 y fondo
`?attr/colorSurfaceContainerHighest`, que es el mismo rol que usa el estilo Filled. Las demás recuperan
la elevación y el fondo del estilo Elevated.

**¿Qué transmite cada estilo?** La tarjeta **Elevated** tiene sombra y parece «flotar» sobre el fondo.
Por eso destaca como contenido independiente y se puede pulsar. La tarjeta **Filled** no tiene sombra
y tiene un fondo tonal más intenso. Se ve más integrada y «guardada», así que marca bien un estado
distinto: noticias que el usuario ya ha separado como suyas. El contraste entre las dos permite ver de
un vistazo cuáles son favoritas.

### Mejora adicional
Al cambiar a modo oscuro o al girar la pantalla, la Activity se recrea. Con el código original se
perdían las favoritas, y el panel marcaba una sección distinta de la que se mostraba. Ahora la sección
y las favoritas se guardan en `onSaveInstanceState` y se recuperan en `onCreate`.

## 6. Preguntas de comprobación

**1. ¿Qué ventaja tiene definir los colores en el tema en lugar de en cada vista?**
Los colores se definen una sola vez y todos los componentes los toman del tema. Si se cambia una línea
del tema, cambia toda la app. Además, la app es coherente y es fácil tener una versión clara y otra
oscura (`values` / `values-night`) sin tocar los layouts. Si hubiera colores sueltos en cada vista,
habría que cambiarlos uno a uno y no se adaptarían al modo oscuro.

**2. ¿Qué diferencia hay entre `@color/marca_primario` y `?attr/colorPrimary`?**
`@color/marca_primario` apunta a un **color concreto y fijo**: siempre es #00695C. `?attr/colorPrimary`
apunta a un **rol del tema activo**: significa «el color que diga el tema para el rol primario». Con
el tema claro vale `marca_primario` y con el oscuro vale `marca_primario_oscuro`, sin cambiar la vista.

**3. ¿Cuándo usarías un Navigation Drawer y cuándo una Bottom Navigation?**
El **Navigation Drawer** sirve cuando hay 5 o más secciones, o secciones que se usan poco. Queda oculto
y se abre con ☰ o deslizando desde el borde. La **Bottom Navigation** sirve cuando hay de 3 a 5
secciones principales que se usan mucho y conviene tener siempre visibles. No se deben usar los dos
para las mismas secciones: el usuario tendría dos caminos para lo mismo, y eso duplica y confunde.

**4. ¿Por qué solo debe haber un FAB por pantalla?**
Porque el FAB representa **la acción principal** de la pantalla y llama la atención por su tamaño,
color y elevación. Si hubiera varios, competirían entre sí. El usuario ya no sabría cuál es la acción
importante y la jerarquía visual se perdería.

**5. ¿Qué dos hijos tiene un DrawerLayout y qué atributo necesita el segundo?**
El primer hijo es el **contenido principal**, que siempre se ve (aquí, el `CoordinatorLayout` con la
barra, la lista y el FAB). El segundo es el **panel lateral** (`NavigationView`) y necesita
`android:layout_gravity="start"`. Así el DrawerLayout sabe que es el panel y que debe ocultarlo a la
izquierda.

**6. ¿Para qué sirve `setAnchorView` en un Snackbar?**
Ancla el Snackbar encima de una vista, aquí el FAB, para que aparezca justo por encima en lugar de
taparlo. Así el botón sigue visible y se puede pulsar mientras se muestra el aviso.
