package ru.itmo.domain


class Action(
    val state: String? = null,
    val place: String? = null,
    val name: String? = null,
    val description: String? = null,
    val isLast: Boolean = false
) {
    override fun toString(): String {
        var ret = ""
        state?.let { ret = ret.plus(", $state,") }
        place?.let { ret = ret.plus(" $place") }
        name?.let { ret = ret.plus(" $name") }
        description?.let { ret = ret.plus(" $description") }
        ret = if (isLast) ret.plus(". ") else ret.plus(" и ")

        return ret
    }
}