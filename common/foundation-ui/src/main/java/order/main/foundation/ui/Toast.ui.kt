package order.main.foundation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import order.main.foundation.ui.theme.FoundationTheme

@Composable
fun ToastUi(
    data: Notification.Toast,
    isAnimRunning: () -> Boolean,
    controller: NotificationController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (data) {
            is Notification.Toast.ToastSnackBarMessage -> {
                SnackBarToast(
                    data = data,
                    controller = controller,
                    modifier = Modifier,
                    isAnimRunning = isAnimRunning,
                )
            }

            is Notification.Toast.SystemToast -> {
                SystemToast(
                    data = data,
                    modifier = Modifier.widthIn(max = 240.dp),
                )
            }
        }
    }
}


@Composable
fun SnackBarToast(
    data: Notification.Toast.ToastSnackBarMessage,
    isAnimRunning: () -> Boolean,
    controller: NotificationController,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.secondaryContainer,
        shadowElevation = 6.dp,
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 16.dp)
                    .weight(1f),
                text = data.showMessage,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            val actionLabel = data.actionLabel
            if (actionLabel != null) {
                TextButton(
                    onClick = {
                        if (!isAnimRunning()) {
                            controller.performAction()
                        }
                    }
                ) {
                    Text(
                        modifier = Modifier,
                        text = actionLabel,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun SystemToast(
    data: Notification.Toast.SystemToast,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.secondaryContainer,
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.1f))
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier,
                style = MaterialTheme.typography.bodyMedium,
                text = data.showMessage,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun SystemToastPreview() {
    FoundationTheme {
        SystemToast(
            data = Notification.Toast.SystemToast(
                showMessage = "Hello World"
            ),
            modifier = Modifier
        )
    }
}

@Preview
@Composable
fun SnackBarToastPreview() {
    FoundationTheme {
        SnackBarToast(
            data = Notification.Toast.ToastSnackBarMessage(
                showMessage = "Hello World",
                actionLabel = "确认"
            ),
            isAnimRunning = { false },
            controller = object : NotificationController {
                override fun performAction() {}
                override fun dismiss() {}
            },
            modifier = Modifier
        )
    }
}
