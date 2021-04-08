package ru.itmo

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.itmo.domain.*
import org.junit.jupiter.api.function.Executable

class DomainTest {
    @Test
    fun `artur both eyes became untrusted after seeing too much unrealistic things`(){
        val artur = Human("Артур" )

        val things = listOf<Thing>(Thing("first thing", false), Thing("second thing", false))
        val env = Environment(things = things)

        assertAll("artur",
            Executable { assertTrue(artur.head.eyes[0].isTrustable) },
            Executable { assertTrue(artur.head.eyes[1].isTrustable) }
        )

        artur.see(env)

        assertAll("artur",
            Executable { assertFalse(artur.head.eyes[0].isTrustable) },
            Executable { assertFalse(artur.head.eyes[1].isTrustable) }
            )
    }


    @Test
    fun `artur jaw dropped after seeing too much unrealistic things`(){
        val artur = Human("Артур" )
        assertFalse(artur.head.jaw.isDropped)

        val things = listOf<Thing>(Thing("first thing", false), Thing("second thing", false))
        val env = Environment(things = things)

        artur.see(env)

        assertTrue(artur.head.jaw.isDropped)
    }

    @Test
    fun `main plot`(){

        val artur = Human("Артур" )
        val leftHead = Head("левая", eyes= listOf(), maxAmountOfUnrealisticThings = 10000, state=" улыбалась широко и непринужденно")
        var rightHead = Head("Правая голова", state=", казалось, была всецело занята этим", eyes= listOf(), maxAmountOfUnrealisticThings = 10000)
        var alien = Alien(heads= listOf(rightHead, leftHead), description = "развалившегося в кресле человека")
        val things = listOf<Thing>(Thing("пульт управления", false), Thing("другая странная вещь", false))
        val goAction = Action("нервничая", null, "вошел", "следом")
        val layLegAction = Action("положившего ноги", "на пульт управления", "и ковыряющего", "левой рукой в зубах правой головы", isLast = true)
        val env = Environment(humans = listOf(artur), aliens = listOf(alien))


        print(artur.doAction(goAction))
        print(artur.see(env))
        print(alien.doAction(layLegAction))
        print(alien.checkHeadState())

        assertFalse(artur.head.jaw.isDropped)
        env.things = things
        assertTrue(artur.head.eyes[0].isTrustable)
        assertTrue(artur.head.eyes[1].isTrustable)
        print(artur.see(env))
        assertFalse(artur.head.eyes[0].isTrustable)
        assertFalse(artur.head.eyes[1].isTrustable)

        print(artur.checkJawState())
        assertTrue(artur.head.jaw.isDropped)
    }

}