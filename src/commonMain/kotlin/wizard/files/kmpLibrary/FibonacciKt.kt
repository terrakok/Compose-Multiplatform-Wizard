package wizard.files.kmpLibrary

import wizard.*

class FibonacciKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/commonMain/kotlin/${info.packagePath}/Fibonacci.kt"
    override val content = """
        package ${info.packageId}
        
        /**
         * Returns a list of Fibonacci numbers up to the specified count.
         *
         * @param count The number of Fibonacci numbers to generate.
         * @return A list of Fibonacci numbers.
         * @throws IllegalArgumentException if count is less than zero.
         */
        fun getFibonacciNumbers(count: Int): List<Int> {
            require(count >= 0)
            val result = mutableListOf<Int>()
        
            if (count == 0) return result
        
            var t1 = 0
            var t2 = 1
            for (i in 1..count) {
                result.add(t1)
                val sum = t1 + t2
                t1 = t2
                t2 = sum
            }
        
            return result
        }
    """.trimIndent()
}

class FibonacciTestKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/commonTest/kotlin/${info.packagePath}/FibonacciTest.kt"
    override val content = """
        package ${info.packageId}
        
        import kotlin.test.*
        
        class FibonacciTest {
        
            @Test
            fun testFibonacciNumbers() {
                assertFails {
                    getFibonacciNumbers(-1)
                }
                assertEquals(
                    listOf(0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377),
                    getFibonacciNumbers(15)
                )
            }
        
        }
    """.trimIndent()
}