package com.example.weatherstate.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherstate.data.db.entity.CurrentWeatherEntry

@Database(
        entities = [CurrentWeatherEntry::class],
        version = 1
)
abstract class WeatherStateDatabase: RoomDatabase() {

    abstract fun currentWeatherDao() : CurrentWeatherDao

    companion object {
        @Volatile private var instance: WeatherStateDatabase ?= null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }

        }

        private fun buildDatabase(context: Context)=
                Room.databaseBuilder(context.applicationContext,WeatherStateDatabase::class.java,"weather.db")
                        .build()

    }

}


// @Volatile = volatile, java'da bir anahtar kelimedir. Java'da uçucu, bu değişkenin değerini önbelleğe almayan ve her zaman ana bellekten okuyan Java derleyicisi ve Thread için bir gösterge olarak kullanılır. Java volatile anahtar sözcüğü, yöntem veya sınıf ile kullanılamaz ve yalnızca bir değişkenle kullanılabilir.
// Ek açıklamalı özelliğin JVM destek alanını geçici olarak işaretler, yani bu alana yazılanlar diğer iş parçacıkları tarafından hemen görünür hale getirilir.

//Ne zaman kullanılmalı
// Bir değişken birden çok iş parçacığı arasında paylaşılmıyorsa, bu değişkenle geçici anahtar sözcük kullanmanıza gerek yoktur.
//Bknz: https://steemit.com/volatile/@sharpchain/how-to-use-volatile-in-kotlin
//Bknz: https://medium.com/@theazat/kotlinde-singleton-object-expression-ve-companion-objects-602a2b9cee0a