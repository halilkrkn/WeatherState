package com.example.weatherstate.data.provider

import com.example.weatherstate.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}