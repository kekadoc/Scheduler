package common.resources.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable


fun interface StringResource : Resource<String>

interface StringResourceGroup : ResourceGroup {

    companion object : StringResourceGroup
}

fun StringResourceGroup.stringResource(definition: ResourceDefinition<String>): StringResource {
    return StringResource { qualifier -> definition(qualifier) }
}

@Composable
@ReadOnlyComposable
fun stringResource(res: StringResource): String {
    val qualifier = LocalResourceQualifier.current
    return res.get(qualifier)
}