@file:OptIn(ExperimentalMaterialApi::class)

package app.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun BaseItemComponent(
    modifier: Modifier = Modifier,
    leftContent: (@Composable BoxScope.() -> Unit)? = null,
    rightContent: (@Composable BoxScope.() -> Unit)? = null,
    label: String? = null,
    labelStyle: TextStyle = MaterialTheme.typography.caption,
    labelAlignment: Alignment.Horizontal = Alignment.Start,
    title: String? = null,
    titleStyle: TextStyle = MaterialTheme.typography.h6,
    titleAlignment: Alignment.Horizontal = Alignment.Start,
    caption: String? = null,
    captionStyle: TextStyle = MaterialTheme.typography.caption,
    captionAlignment: Alignment.Horizontal = Alignment.Start,
    onClick: (() -> Unit)? = null,
) {
    Card(
        modifier = Modifier.wrapContentSize(),
        onClick = onClick ?: {},
        enabled = onClick != null
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
        ) {
            if (leftContent != null) {
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .align(Alignment.CenterVertically),
                    content = leftContent
                )
            }
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start,
            ) {
                if (label != null) {
                    Text(
                        modifier = Modifier.align(labelAlignment),
                        text = label,
                        style = labelStyle
                    )
                }
                if (title != null) {
                    Text(
                        modifier = Modifier.align(titleAlignment),
                        text = title,
                        style = titleStyle
                    )
                }
                if (caption != null) {
                    Text(
                        modifier = Modifier.align(captionAlignment),
                        text = caption,
                        style = captionStyle
                    )
                }
            }
            if (rightContent != null) {
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .align(Alignment.CenterVertically),
                    content = rightContent
                )
            }
        }
    }
}

@Composable
fun SimpleItemComponent(
    modifier: Modifier = Modifier,
    leftImage: ImageVector? = null,
    onLeftImageClick: (() -> Unit)? = null,
    rightImage: ImageVector? = null,
    onRightImageClick: (() -> Unit)? = null,
    label: String? = null,
    labelStyle: TextStyle = MaterialTheme.typography.caption,
    labelAlignment: Alignment.Horizontal = Alignment.Start,
    title: String? = null,
    titleStyle: TextStyle = MaterialTheme.typography.h6,
    titleAlignment: Alignment.Horizontal = Alignment.Start,
    caption: String? = null,
    captionStyle: TextStyle = MaterialTheme.typography.caption,
    captionAlignment: Alignment.Horizontal = Alignment.Start,
    onClick: (() -> Unit)? = null,
) {
    BaseItemComponent(
        modifier = modifier,
        leftContent = {
            if (leftImage != null) {
                if (onLeftImageClick != null) {
                    Card(
                        modifier = Modifier.align(Alignment.Center),
                        elevation = 0.dp,
                        onClick = onLeftImageClick
                    ) {
                        ImageThemed(
                            modifier = Modifier.size(44.dp).align(Alignment.Center),
                            imageVector = leftImage,
                            contentDescription = null,
                            alignment = Alignment.Center
                        )
                    }
                } else {
                    ImageThemed(
                        modifier = Modifier.size(44.dp).align(Alignment.Center),
                        imageVector = leftImage,
                        contentDescription = null,
                        alignment = Alignment.Center
                    )
                }
            }
        },
        rightContent = {
            if (rightImage != null) {
                if (onRightImageClick != null) {
                    Card(
                        onClick = onRightImageClick,
                        elevation = 0.dp
                    ) {
                        ImageThemed(
                            modifier = Modifier.size(44.dp).align(Alignment.Center),
                            imageVector = rightImage,
                            contentDescription = null,
                            alignment = Alignment.Center
                        )
                    }
                } else {
                    ImageThemed(
                        modifier = Modifier.size(44.dp).align(Alignment.Center),
                        imageVector = rightImage,
                        contentDescription = null,
                        alignment = Alignment.Center
                    )
                }
            }
        },
        label = label,
        labelStyle = labelStyle,
        labelAlignment = labelAlignment,
        title = title,
        titleStyle = titleStyle,
        titleAlignment = titleAlignment,
        caption = caption,
        captionStyle = captionStyle,
        captionAlignment = captionAlignment,
        onClick = onClick
    )
}