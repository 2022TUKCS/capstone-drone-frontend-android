package com.example.firedron.dto

data class Drone(
    var id: String,
    var surveillance_area: String,
    var drone_alias: String,
    var is_active: Boolean,
    var admin: String,
    var flight: String
)