package es.chipeit.android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import es.chipeit.lib.MyClass

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myObject = MyClass()
        myObject.sayHelloTo("Sergio")
    }
}
