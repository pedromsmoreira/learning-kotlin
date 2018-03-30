package toggle.it.api.domain

data class Toggle(val name: String, val active: Boolean, val tags: List<String> = listOf(), val id: Int){
    fun switch() = Toggle(name, !active, tags, id)
}