package com.example.weatherstate.internal

import java.io.IOException

//Burada IOException dan kalıtım alarak hata durumlarını ve çözümlerini çektik.
class NoConnectivityException : IOException()
class LocationPermissionNotGrantedExpetion: Exception()