package com.poketeammaker.model

import com.poketeammaker.entity.Movement


data class PokemonMovement (
    val name: String,
    val type: String,
    val movementType: String,
    val points: Long? = null,
    val accuracy: Long? = null,
    val priority: Long = 0,
) {
    data class Builder(
        private var name: String? = null,
        private var type: String? = null,
        private var movementType: String? = null,
        private var points: Long? = null,
        private var accuracy: Long? = null,
        private var priority: Long? = null
    ) {
        fun from(movement: Movement): Builder {
            apply { this.name = movement.name }
            apply { this.type = movement.type }
            apply { this.movementType = movement.movementType }
            apply { this.points = movement.points }
            apply { this.accuracy = movement.accuracy }
            apply { this.priority = movement.priority }

            return this
        }
        fun build() = PokemonMovement(this.name!!, this.type!!, this.movementType!!, this.points, this.accuracy, this.priority!!)
    }
}