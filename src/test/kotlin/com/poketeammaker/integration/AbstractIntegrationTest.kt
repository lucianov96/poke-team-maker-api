package com.poketeammaker.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.poketeammaker.dao.PokemonDAO
import com.poketeammaker.utils.createPokemon
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AbstractIntegrationTest {

    @MockkBean
    protected lateinit var pokemonDAO: PokemonDAO

    @Autowired
    protected lateinit var restTemplate: TestRestTemplate

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    protected val pokemon = createPokemon(
        hp = 10,
        attack = 20,
        defense = 30,
        spAttack = 40,
        spDefense = 50,
        speed = 60
    )

    private val pokemonList = mutableListOf(
        pokemon,
        createPokemon(2, "mockmon2")
    )
}
