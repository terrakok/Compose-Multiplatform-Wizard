package wizard.files.app

import wizard.ProjectFile

class GraphQLQuery : ProjectFile {
    override val path = "composeApp/src/commonMain/graphql/HelloQuery.graphql"
    override val content = """
        query HelloQuery {
          hello
        }
    """.trimIndent()
}

class GraphQLSchema : ProjectFile {
    override val path = "composeApp/src/commonMain/graphql/schema.graphqls"
    override val content = """
        type Query {
          hello: String!
        }
    """.trimIndent()
}
