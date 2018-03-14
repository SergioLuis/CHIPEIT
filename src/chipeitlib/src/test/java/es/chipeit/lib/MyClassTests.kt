package es.chipeit.lib

import org.junit.Test
import kotlin.test.assertEquals

class MyClassTests {
    @Test
    fun sayHelloToSergioTest() {
        val myObject = MyClass()
        assertEquals(
                "Hello Sergio, pleased to meet you!",
                myObject.sayHelloTo("Sergio"))
    }

    @Test
    fun sayHelloToMikelTest() {
        val myObject = MyClass()
        assertEquals(
                "Hello Mikel, pleased to meet you!",
                myObject.sayHelloTo("Mikel"))
    }
}