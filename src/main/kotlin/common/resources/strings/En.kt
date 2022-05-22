package common.resources.strings

import common.resources.AppStrings
import common.resources.api.StringResource
import common.resources.api.stringResource

val AppStringsEn = object : AppStrings {
    override val name: StringResource = stringResource { "My App" }
    override val helpMessage: StringResource = stringResource { "Help" }
    override val foo: StringResource = stringResource { "Foo" }
}