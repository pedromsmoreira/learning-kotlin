package toggle.it.api.domain

data class Toggle(val name: String, val active: Boolean, val id: Int){
    fun switch() : Toggle = Toggle(name, !active, id)
}