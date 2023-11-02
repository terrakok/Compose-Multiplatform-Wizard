package wizard.files.app

import wizard.ProjectFile
import wizard.ProjectInfo

class WebPackConfig(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/webpack.config.d/config.js"
    override val content = """
        const TerserPlugin = require("terser-webpack-plugin");

        config.optimization = config.optimization || {};
        config.optimization.minimize = true;
        config.optimization.minimizer = [
            new TerserPlugin({
                terserOptions: {
                    mangle: true,    // Note: By default, mangle is set to true.
                    compress: false, // Disable the transformations that reduce the code size.
                    output: {
                        beautify: false,
                    },
                },
            }),
        ];
    """.trimIndent()
}

