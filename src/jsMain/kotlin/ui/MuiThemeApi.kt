// kotlin-wrappers 2026.7.7 still generates deep imports (`@mui/material/styles/ThemeProvider`,
// `@mui/system/createTheme/createTheme`) that MUI 9's `exports` map no longer allows, so webpack
// cannot resolve them. Declare the two members we need against the supported `@mui/material/styles`
// entry point instead. Drop this file once the wrappers stop emitting deep paths.
@file:JsModule("@mui/material/styles")
@file:JsNonModule

package ui

import mui.material.styles.Theme
import mui.material.styles.ThemeOptions
import mui.material.styles.ThemeProviderProps
import react.FC

external fun createTheme(options: ThemeOptions): Theme

external val ThemeProvider: FC<ThemeProviderProps>
