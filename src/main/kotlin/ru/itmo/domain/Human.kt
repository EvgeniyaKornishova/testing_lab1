package ru.itmo.domain

class Human (
    val name : String,
    val head: Head = Head(name=name, eyes= listOf<Eye>(Eye(), Eye()), jaw=Jaw(), maxAmountOfUnrealisticThings = 1),
)
{
    fun see(environment: Environment): String{
        return head.see(environment)
    }

    fun checkJawState(): String{
        return head.checkJawState()
    }

    fun doAction(action: Action): String{
        return "$name$action"
    }
}