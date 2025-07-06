package order.main.foundation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import order.main.foundation.ui.theme.FoundationTheme

@Composable
fun TopNotificationUi(
    data: Notification.TopNotification,
    isAnimRunning: () -> Boolean,
    controller: NotificationController,
    modifier: Modifier = Modifier
) {
    when (data) {
        is Notification.TopNotification.Simple -> {
            SimpleNotificationContent(
                modifier = modifier,
                simpleNotification = data,
                onClick = {
                    if (!isAnimRunning()) {
                        controller.performAction()
                    }
                }
            )
        }

        else -> {}
    }
}

@Composable
fun SimpleNotificationContent(
    simpleNotification: Notification.TopNotification.Simple,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.secondaryContainer,
        shadowElevation = 6.dp,
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier,
                    text = simpleNotification.title,
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                val subTitle = simpleNotification.subTitle
                if (!subTitle.isNullOrEmpty()) {
                    Text(
                        modifier = Modifier,
                        text = subTitle,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SimpleNotificationPreview() {
    FoundationTheme {
        SimpleNotificationContent(
            modifier = Modifier
                .fillMaxWidth(),
            simpleNotification = Notification.TopNotification.Simple(
                title = "Hello World",
                subTitle = "哈哈哈哈哈哈哈"
            ),
            onClick = {}
        )
    }
}