package com.example.weatherstate.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherstate.data.db.converter.CurrentWeatherEntryConverters
import com.example.weatherstate.data.db.entity.CurrentWeatherEntry



// Room’un beyin kısmıdır.
// Verilerin kalıcı olarak tutulması için temel oluşturur.
// Veritabanını yönetir
// Veritabanına ana erişim noktasıdır. @Database annotation’ı ile kullanılır.
@Database(
        entities = [CurrentWeatherEntry::class],
        version = 2

)
//Database sınıfımız abstract olmalı ve RoomDatabase‘den türetilmelidir.
//Database ile alakalı tüm entity sınıfları burada belirtilmelidir.
//@Dao sınıflarımız abstract fun olarak Database içinde oluşturulmalı.
//Runtime’da, Room.databaseBuilder() ya da Room.inMemoryDatabaseBuilder() çağırarak da Database oluşturabiliriz
abstract class WeatherStateDatabase: RoomDatabase() {

    abstract fun getCurrentWeatherDao(): CurrentWeatherDao

    companion object{
        @Volatile private var instance: WeatherStateDatabase ?= null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }
        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        WeatherStateDatabase::class.java,
                        "weatherstate.db"
                ).build()

    }

}

// @Database annotation’ı içinde veritabanımızda bulunacak tüm entitiy sınıflarını ve veritabanı versiyonunu belirttik.
// Abstract bir class oluşturduk ve RoomDatabase’den türettik. Tüm Dao sınıflarımızı abstract fun olarak oluşturduk.
// WeatherStateDatabase instance oluşturduk ve @Volatile annotation’ı kullandık. Bu annotation sayesinde bir thread’de bu instance değiştiğinde diğer thread’ler hemen görebilecek.
// LOCK‘u synchronize ayarları için kullanacağız. Aynı anda Database’in yalnız bir instance’ı olduğundan emin olacağız.
// Database’den her bir instance oluşturulduğunda invoke fonksiyonu çağırılacak. Eğer instance null‘sa farklı thread’lerden aynı anda erişim olmaması için LOCK object ile synchronized bloğu oluşturuyoruz. Bu bloğun içerisinde yine bir null kontrolü yapıyoruz. Hala null ise buildDatabase fonksiyonunu ile instance‘a atama yapıyoruz.
// Room.databaseBuilder ile veritabanımızı oluşturuyoruz.