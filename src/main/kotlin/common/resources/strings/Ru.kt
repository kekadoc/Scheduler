package common.resources.strings

import common.resources.AppStrings
import common.resources.api.StringResource
import common.resources.api.stringResource

val AppStringsRu = object : AppStrings {
    override val name: StringResource = stringResource { "Мое приложение" }
    override val helpMessage: StringResource = stringResource { "Памагите" }
    override val foo: StringResource = stringResource { "Фуу" }
}