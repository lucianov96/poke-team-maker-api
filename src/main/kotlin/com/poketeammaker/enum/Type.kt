package com.poketeammaker.enum

enum class Type {
    BUG, DARK, DRAGON,
    ELECTRIC, FAIRY, FIGHTING,
    FIRE, FLYING, GHOST,
    GRASS, GROUND, ICE,
    NORMAL, POISON, PSYCHIC,
    ROCK, STEEL, WATER;

    companion object {
        fun all(): String {
            return "${BUG.name.toLowerCase()}, " +
                "${DARK.name.toLowerCase()}, " +
                "${DRAGON.name.toLowerCase()}, " +
                "${ELECTRIC.name.toLowerCase()}, " +
                "${FAIRY.name.toLowerCase()}, " +
                "${FIGHTING.name.toLowerCase()}, " +
                "${FIRE.name.toLowerCase()}, " +
                "${FLYING.name.toLowerCase()}, " +
                "${GHOST.name.toLowerCase()}, " +
                "${GRASS.name.toLowerCase()}, " +
                "${GROUND.name.toLowerCase()}, " +
                "${ICE.name.toLowerCase()}, " +
                "${NORMAL.name.toLowerCase()}, " +
                "${POISON.name.toLowerCase()}, " +
                "${PSYCHIC.name.toLowerCase()}, " +
                "${ROCK.name.toLowerCase()}, " +
                "${STEEL.name.toLowerCase()}, " +
                "${WATER.name.toLowerCase()}"
        }
    }
}
