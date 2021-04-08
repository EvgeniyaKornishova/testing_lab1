package ru.itmo.domain

class Environment(
    val humans: List<Human> = listOf(),
    val aliens: List<Alien> = listOf(),
    var things: List<Thing> = listOf()
)
{}