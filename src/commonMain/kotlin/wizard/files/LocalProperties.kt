package wizard.files

import wizard.ProjectFile

class LocalProperties : ProjectFile {
    override val path = "local.properties"
    override val content = """
        ## This file must *NOT* be checked into Version Control Systems,
        # as it contains information specific to your local configuration.
        #
        # Location of the SDK. This is only used by Gradle.
        # For customization when using a Version Control System, please read the
        # header note.
        #Fri May 16 19:22:37 CEST 2025
        sdk.dir=
        
    """.trimIndent()
}