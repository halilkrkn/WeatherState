package com.example.weatherstate.internal

import kotlinx.coroutines.*

// Burada corutines işlemlerinden suspend i blocklayıp lazyDeferred ile gerekli yerlerde suspend fonksiyonunu blocklayıp ertelemek için bu fonksiyonu yaptık..
// Mesela CurrentWeatherViewModel da suspend hatası aldığımız için bu şekilde o hatayı erteliyip blocklamış olduk.
fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }

}