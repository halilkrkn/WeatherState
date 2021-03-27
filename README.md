# WeatherState
 
- Bu uygulama Android Architecture Components lerden olan MVVM mimarisini kullanarak yapılan bir Hava Durumu uygulamasıdır.
- MVVM mimarisi yani Model-View-ViewModel yapısını kullanarak katmanlı bir mimari içerisinde kodları daha sade, kaliteli, verimliliği yüksek, sürdürülebilir ve test edilebilir bir uygulama yapmamıza olanak sağlıyor. 

 # Bu uygulamada; 

- **UI için** Android Architecture Components içerisinde bulunan **Coroutines**, **LiveData**, **Lifecycle** - **ViewModel**, **Fragment**,**Navigation**, **Material Design** yapısını, 
- Android **Kotlin Dependency Injection** olan **Kodein** (Kotlin Dependency Injection) Library i,
- **Api üzerinden veri çekmek** için **Retrofit** ve **Gson** yapısını, 
- **Image dosyalarını apiden çekebilmek** için **Glide'ı**,
- **Uygulama içerisindeki verileri UI da listeleyebilmek** için **Groupie RecyclerView**,
- **Api den çektiğimiz verileri database eklemek için** sqlite tabanlı **Room database** ini kullanarak uygulama MVVM mimarisi içerisinde yapıldı.

 
# # Kullanılan Kütüphaneler ve Bileşenler;

- # Navigation Components
- Kullanıcıların uygulamadaki farklı içerikdeki sayfaları gezinmesine, içeri girmesine ve buradan çıkmasına olanak tanıyan etkileşimlerdir.
- Bknz: https://developer.android.com/guide/navigation
- Bknz: https://github.com/android/architecture-components-samples/tree/main/NavigationBasicSample

- # Lifecycle
- Yaşam döngüsüne duyarlı bileşenler, etkinlikler ve parçalar gibi başka bir bileşenin yaşam döngüsü durumundaki bir değişikliğe yanıt olarak eylemler gerçekleştirir. Bu bileşenler, bakımı daha kolay olan daha iyi organize edilmiş ve genellikle daha hafif kodlar üretmenize yardımcı olur.
- Uygulamamızın etkinlik ve parça yaşam döngülerini yönetir, yapılandırma değişikliklerinden kurtulur, bellek sızıntılarını önler ve verileri kullanıcı arayüzümüze kolayca yükler.
- Bknz: https://developer.android.com/jetpack/androidx/releases/lifecycle
- Bknz: https://blog.mindorks.com/what-are-android-architecture-components

- # Fragment & Collection
- Fragment, bir Activity'de activity veya kullanıcı arabiriminin bir bölümünü temsil eder.
- Bknz: https://medium.com/@muhammetbct/android-fragment-kullan%C4%B1m%C4%B1-318c3eeb379

- # Room Database
- Sqlite’ta olduğu gibi verileri android cihaza kaydetmeye yarayan ve sqlite yapısını daha kolay şekilde kullanmamızı sağlayan bir kütüphanedir. Sqlite’a göre kod daha sade ve yazımı daha basittir.
- Bknz: https://speednet.pl/blog/room-persistence-library-introduction-part-1/ 
- Bknz: https://speednet.pl/blog/room-persistence-library-introduction-part-2/

- # Retrofit
- Android ve Java için tür güvenli bir HTTP istemcisi.
- Bknz: https://github.com/square/retrofit

- # Gson
- Java Nesnelerini JSON temsillerine dönüştürmek için kullanılabilecek bir Java kitaplığıdır.
- Bknz: https://github.com/google/gson

- # Kodein (Kotlin Dependency Injection)
- Nesneleri kullanacağımız yerde oluşturmaktansa dışarıda oluşturup gerekli yerlere aktarılması olayıdır.
- Kodein ile katmanlar arasındakı bağlantıları kurabilir ve ayrıca kullanılan kaynaklar üzerinde kolayca kontrol sağlayabiliriz.
- Bknz: https://github.com/Kodein-Framework/Kodein-DI

- # Glide
-  Video fotoğraflarını, görüntüleri ve animasyonlu GIF'leri getirmeyi, kod çözmeyi ve görüntülemeyi destekler.
- Bknz: https://github.com/bumptech/glide

- # Groupie RecyclerView
- Karmaşık RecyclerView düzenleri için basit ve esnek bir kitaplıktır. Groupie, içeriğinizi mantıksal gruplar olarak ele almanıza olanak tanır ve sizin için değişiklik bildirimlerini yönetir
- Bknz: https://github.com/lisawray/groupie

- # Material Design
- Daha kullanışlı UI bileşenleri oluşturmamıza sağlayan bir tasarım dilidir.
- Bknz: https://material.io/develop/android


# Kaynaklar:

- Bu uygulamayı yapmak için ResoCoder Kanalının yapmış olduğu  Forecast App - Android Kotlin MVVM Tutorial Course dan faydalanarak bu yapıları ve MVVM mimarisini, Room DataBase ve Navigation Components İşlemlerini, KODEIN yapısını, Retrofit ve Gson kullanımı daha derinden öğrenme fırsatına eriştim ve kendimi bu alan da artık daha etkili bir şekilde geliştirdiğimi düşünüyorum. 
- Bknz: https://www.youtube.com/playlist?list=PLB6lc7nQ1n4jTLDyU2muTBo8xk0dg0D_w
- Bknz: https://resocoder.com/2018/10/21/android-kotlin-forecast-app-01-navigation-app-foundation-mvvm-tutorial-course/
