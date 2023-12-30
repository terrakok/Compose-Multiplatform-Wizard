package wizard.files.app

import wizard.ProjectFile
import wizard.ProjectInfo

class GraphQLQuery(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/commonMain/graphql/HelloQuery.graphql"
    override val content = """
        query HelloQuery {
          hello
        }
    """.trimIndent()
}

class GraphQLSchema(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/commonMain/graphql/schema.graphqls"
    override val content = """
        type Query {
          hello: String!
        }
    """.trimIndent()
}
