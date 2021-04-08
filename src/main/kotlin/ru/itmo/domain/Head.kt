package ru.itmo.domain

class Head (
    val name: String,
    val eyes: List<Eye>,
    val jaw: Jaw = Jaw(),
    val state: String = "",
    private val maxAmountOfUnrealisticThings: Int
    )
{
    override fun toString(): String = "$name$state"

    fun checkJawState() = "$jaw"

    fun see(environment: Environment): String {
        if (eyes.isEmpty()) {
            return ("$name ничего не видел, потому что у него не было глаз")
        }

        if (environment.humans.filter { it.head != this }.isNotEmpty()){
            jaw.restore()
            return ("$name обрадовался, увидев человеческое лицо")
        }


        if (environment.things.isNotEmpty()) {
            val thingsPerEye = environment.things.size / eyes.size
            val maxAmountUnrealisticThingsPerEye = maxAmountOfUnrealisticThings / eyes.size
            val thingsPerEyes = environment.things.withIndex().groupBy { it.index / thingsPerEye }.map { it.value.map { it.value } }

            for ((i, eye) in eyes.withIndex())
                eye.see(thingsPerEyes[i], maxAmountUnrealisticThingsPerEye)

            var sure = ""
            if (eyes.all { !it.isTrustable }) {
                sure = "не "
                jaw.drop()
            }


            return """Количество вещей, 
            |видя которые, 
            |$name ${sure}верил 
            |${if (eyes.size > 1) "своим глазам" else "своему глазу"}
            |, всё ${if (environment.things.size > 1) "росло" else "убывало"}.""".trimMargin().replace("\n", "")
        }

        if (environment.aliens.isNotEmpty())
            return "был ошеломлен, увидев ".plus(environment.aliens.joinToString(", ", transform = (Alien::description)))
        else
            return "был спокоен, увидев, что вокруг нет инопланетян"
    }
}

class Jaw (
    var isDropped : Boolean = false
    )
{
    fun drop(){
        this.isDropped = true
    }

    fun restore(){
        this.isDropped = false
    }

    override fun toString(): String = if (isDropped) "Его челюсть отвисла." else "Его челюсть в полном порядке."
}


class Eye (
    var isTrustable: Boolean = true,
    )
{
    fun see(things: List<Thing>, maxUnrealisticThings: Int){
        this.isTrustable = (things.count { !it.isRealistic } < maxUnrealisticThings)
    }
}