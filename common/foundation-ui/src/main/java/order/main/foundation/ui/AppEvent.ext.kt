package order.main.foundation.ui

fun AppEventInstance.showSystemToast(
    message: String,
    onLabelPerformed: () -> Unit = {},
    onDismissed: () -> Unit = {},
): Boolean {
    return this.tryPostEvent(
        event = Event.Toast.SystemToast(
            showMessage = message,
            onFinished = { ret ->
                when (ret) {
                    ToastActionResult.ActionPerformed -> onLabelPerformed()
                    else -> onDismissed()
                }
            }
        )
    )
}

fun AppEventInstance.showSnackBarToast(
    message: String,
    actionLabel: String? = null,
    onLabelPerformed: () -> Unit = {},
    onDismissed: () -> Unit = {},
): Boolean {
    return this.tryPostEvent(
        event = Event.Toast.SnackBarToast(
            showMessage = message,
            actionLabel = actionLabel,
            onFinished = { ret ->
                when (ret) {
                    ToastActionResult.ActionPerformed -> onLabelPerformed()
                    else -> onDismissed()
                }
            }
        )
    )
}