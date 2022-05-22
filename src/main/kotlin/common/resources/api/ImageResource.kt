package common.resources.api

interface ImageResourceGroup : ResourceGroup {

    companion object : ImageResourceGroup
}

fun interface ImageResource : Resource<String>

