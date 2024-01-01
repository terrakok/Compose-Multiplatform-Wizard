package wizard.dependencies

import wizard.Dependency

val ComposeIconsFontAwesome = Dependency(
    title = "Font Awesome Icons",
    description = "Compose Multiplatform icons is a pack of libraries that provide well known Icon Packs.",
    url = "https://github.com/DevSrSouza/compose-icons/blob/master/font-awesome/DOCUMENTATION.md",
    group = "br.com.devsrsouza.compose.icons",
    id = "font-awesome",
    version = "1.1.0",
    catalogVersionName = "composeIcons",
    catalogName = "composeIcons-fontAwesome",
    platforms = emptySet()
)

val ComposeIconsSimple = ComposeIconsFontAwesome.copy(
    title = "Simple Icons",
    url = "https://github.com/DevSrSouza/compose-icons/blob/master/simple-icons/DOCUMENTATION.md",
    id = "simple-icons",
    catalogName = "composeIcons-simpleIcons",
)

val ComposeIconsFeather = ComposeIconsFontAwesome.copy(
    title = "Feather Icons",
    url = "https://github.com/DevSrSouza/compose-icons/blob/master/feather/DOCUMENTATION.md",
    id = "feather",
    catalogName = "composeIcons-featherIcons",
)

val ComposeIconsTabler = ComposeIconsFontAwesome.copy(
    title = "Tabler Icons",
    url = "https://github.com/DevSrSouza/compose-icons/blob/master/tabler-icons/DOCUMENTATION.md",
    id = "tabler-icons",
    catalogName = "composeIcons-tablerIcons",
)

val ComposeIconsEva = ComposeIconsFontAwesome.copy(
    title = "Eva Icons",
    url = "https://github.com/DevSrSouza/compose-icons/blob/master/eva-icons/DOCUMENTATION.md",
    id = "eva-icons",
    catalogName = "composeIcons-evaIcons",
)

val ComposeIconsOcticons = ComposeIconsFontAwesome.copy(
    title = "Octicons Icons",
    url = "https://github.com/DevSrSouza/compose-icons/blob/master/octicons/DOCUMENTATION.md",
    id = "octicons",
    catalogName = "composeIcons-octiconsIcons",
)

val ComposeIconsLinea = ComposeIconsFontAwesome.copy(
    title = "Linea Icons",
    url = "https://github.com/DevSrSouza/compose-icons/blob/master/linea/DOCUMENTATION.md",
    id = "linea",
    catalogName = "composeIcons-lineaIcons",
)

val ComposeIconsLineAwesome = ComposeIconsFontAwesome.copy(
    title = "Line Awesome Icons",
    url = "https://github.com/DevSrSouza/compose-icons/blob/master/line-awesome/DOCUMENTATION.md",
    id = "line-awesome",
    catalogName = "composeIcons-lineAwesomeIcons",
)

val ComposeIconsWeather = ComposeIconsFontAwesome.copy(
    title = "Weather Icons",
    url = "https://github.com/DevSrSouza/compose-icons/blob/master/erikflowers-weather-icons/DOCUMENTATION.md",
    id = "erikflowers-weather-icons",
    catalogName = "composeIcons-weatherIcons",
)

val ComposeIconsCSSGG = ComposeIconsFontAwesome.copy(
    title = "CSS.GG Icons",
    url = "https://github.com/DevSrSouza/compose-icons/blob/master/css-gg/DOCUMENTATION.md",
    id = "css-gg",
    catalogName = "composeIcons-cssggIcons",
)