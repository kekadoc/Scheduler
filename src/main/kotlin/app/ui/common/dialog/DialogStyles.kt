package app.ui.common.dialog

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogState

interface DialogStyle {
    val states: DialogStates

    companion object
}

private const val MIN_SIZE = 300

private const val START_NORMAL = 1.0
private const val START_BIG = 1.25
private const val START_EXTRA = 1.5

private const val STEP_SMALL = 0.15
private const val STEP_BIG = 0.25
private const val STEP_EXTRA = 0.5

/*
    minSize = 400
    widthStartModifier = 1.0
    heightStartModifier = 1.0
    widthStepModifier = 1.0
    heightStepModifier = 1.0

    small = h400 x w400
    medium = h800 x w800
    medium = h1200 x w1200

    minSize = 400
    widthStartModifier = 2.0
    heightStartModifier = 1.0
    widthStepModifier = 1.0
    heightStepModifier = 0.25

    small = h400 x w800
    medium = h500 x w1200
    medium = h600 x w1600

 */
@Suppress("SameParameterValue")
private fun buildStates(
    minSize: Int,
    widthStartModifier: Double,
    widthStepModifier: Double,
    heightStartModifier: Double,
    heightStepModifier: Double,
): DialogStates {
    val small = DialogState(
        width = (minSize * widthStartModifier).dp,
        height = (minSize * heightStartModifier).dp
    )
    val medium = DialogState(
        width = (minSize * (widthStartModifier + widthStepModifier)).dp,
        height = (minSize * (heightStartModifier + heightStepModifier)).dp
    )
    val large = DialogState(
        width = (minSize * (widthStartModifier + widthStepModifier * 2)).dp,
        height = (minSize * (heightStartModifier + heightStepModifier * 2)).dp
    )
    return DialogStates(
        small = small,
        medium = medium,
        large = large
    )
}

private object DialogStyleWide : DialogStyle {
    override val states: DialogStates = buildStates(
        minSize = MIN_SIZE,
        widthStartModifier = START_BIG,
        widthStepModifier = STEP_BIG,
        heightStartModifier = START_NORMAL,
        heightStepModifier = STEP_SMALL
    )
}
private object DialogStyleExtraWide : DialogStyle {
    override val states: DialogStates = buildStates(
        minSize = MIN_SIZE,
        widthStartModifier = START_EXTRA,
        widthStepModifier = STEP_EXTRA,
        heightStartModifier = START_NORMAL,
        heightStepModifier = STEP_SMALL
    )
}
private object DialogStyleSquire : DialogStyle {
    override val states: DialogStates = buildStates(
        minSize = MIN_SIZE,
        widthStartModifier = START_NORMAL,
        widthStepModifier = STEP_BIG,
        heightStartModifier = START_NORMAL,
        heightStepModifier = STEP_BIG
    )
}
private object DialogStyleLong : DialogStyle {
    override val states: DialogStates = buildStates(
        minSize = MIN_SIZE,
        widthStartModifier = START_NORMAL,
        widthStepModifier = STEP_SMALL,
        heightStartModifier = START_BIG,
        heightStepModifier = STEP_BIG
    )
}
private object DialogStyleExtraLong : DialogStyle {
    //2000
    override val states: DialogStates = buildStates(
        minSize = MIN_SIZE,
        widthStartModifier = START_NORMAL,
        widthStepModifier = STEP_SMALL,
        heightStartModifier = START_EXTRA,
        heightStepModifier = STEP_EXTRA
    )
}


val DialogStyle.Companion.Wide: DialogStyle
    get() = DialogStyleWide

val DialogStyle.Companion.ExtraWide: DialogStyle
    get() = DialogStyleExtraWide

val DialogStyle.Companion.Squire: DialogStyle
    get() = DialogStyleSquire

val DialogStyle.Companion.Long: DialogStyle
    get() = DialogStyleLong

val DialogStyle.Companion.ExtraLong: DialogStyle
    get() = DialogStyleExtraLong

data class DialogStates(
    val small: DialogState,
    val medium: DialogState,
    val large: DialogState,
)