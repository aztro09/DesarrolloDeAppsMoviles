package com.example.navegacionedifico1.utils

fun isLab(salonNumber: Int): Boolean{
    return salonNumber in listOf(
        1003, 1007, 1012, // PB
        1104, 1108, 1113, // Piso 1
        1202, 1206, 1210  // Piso 2
    )
}