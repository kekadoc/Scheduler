package common.resources.api

interface ColorResourceGroup : ResourceGroup {

    companion object : ColorResourceGroup
}

fun interface ColorResource : Resource<String>
