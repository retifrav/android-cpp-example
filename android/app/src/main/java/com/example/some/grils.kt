package com.example.some

data class Gril(
    val name: String,
    val year: Int,
    val photoID: Int,
    val boobsRating: Int
)

object Grils {
    val grilsData = listOf(
        Gril("Alexandra Daddario", 1986, R.drawable.alexandra_daddario, 3),
        Gril("Blake Lively", 1987, R.drawable.blake_lively, 3),
        Gril("Cassandra Peterson", 1951, R.drawable.cassandra_peterson, 4),
        Gril("Christina Hendricks", 1975, R.drawable.christina_hendricks, 5),
        Gril("Elizabeth Olsen", 1989, R.drawable.elizabeth_olsen, 3),
        Gril("Gina Carano", 1982, R.drawable.gina_carano, 3),
        Gril("Scarlett Johansson", 1984, R.drawable.scarlett_johansson, 4),
        Gril("Sydney Sweeney", 1997, R.drawable.sydney_sweeney, 4)
    )
}
