package ru.itmo.domain

class Alien (
    val name: String? = null,
    val heads: List<Head>,
    val description: String = "неизвестного"
)
{
    fun checkHeadState() = heads.joinToString(", но зато ", postfix = ".")

    fun doAction(action: Action): String{
        return if (name != null) "$name$action" else "$action"
    }
}