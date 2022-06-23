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
    leftContent: (@Composable RowScope.() -> Unit)? = null,
    rightContent: (@Composable RowScope.() -> Unit)? = null,
    title: String? = null,
    titleStyle: TextStyle = MaterialTheme.typography.h6,
    titleAlignment: Alignment.Horizontal = Alignment.Start,
    subtitle: String? = null,
    subtitleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    subtitleAlignment: Alignment.Horizontal = Alignment.Start,
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
            modifier = modifier.fillMaxWidth().wrapContentHeight().padding(8.dp)
        ) {
            leftContent?.invoke(this)
            Column(
                modifier = Modifier.wrapContentHeight().weight(1f).padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start,
            ) {
                if (title != null) {
                    Text(
                        modifier = Modifier.align(titleAlignment),
                        text = title,
                        style = titleStyle
                    )
                }
                if (subtitle != null) {
                    Text(
                        modifier = Modifier.align(subtitleAlignment),
                        text = subtitle,
                        style = subtitleStyle
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
            rightContent?.invoke(this)
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
    title: String? = null,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    titleAlignment: Alignment.Horizontal = Alignment.Start,
    subtitle: String? = null,
    subtitleStyle: TextStyle = MaterialTheme.typography.caption,
    subtitleAlignment: Alignment.Horizontal = Alignment.Start,
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
                        modifier = Modifier.align(Alignment.CenterVertically),
                        elevation = 0.dp,
                        onClick = onLeftImageClick
                    ) {
                        ImageThemed(
                            modifier = Modifier.size(44.dp).align(Alignment.CenterVertically),
                            imageVector = leftImage,
                            contentDescription = null,
                            alignment = Alignment.Center
                        )
                    }
                } else {
                    ImageThemed(
                        modifier = Modifier.size(44.dp).align(Alignment.CenterVertically),
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
                            modifier = Modifier.size(44.dp).align(Alignment.CenterVertically),
                            imageVector = rightImage,
                            contentDescription = null,
                            alignment = Alignment.Center
                        )
                    }
                } else {
                    ImageThemed(
                        modifier = Modifier.size(44.dp).align(Alignment.CenterVertically),
                        imageVector = rightImage,
                        contentDescription = null,
                        alignment = Alignment.Center
                    )
                }
            }
        },
        title = title,
        titleStyle = titleStyle,
        titleAlignment = titleAlignment,
        subtitle = subtitle,
        subtitleStyle = subtitleStyle,
        subtitleAlignment = subtitleAlignment,
        caption = caption,
        captionStyle = captionStyle,
        captionAlignment = captionAlignment,
        onClick = onClick
    )
}