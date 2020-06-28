import com.deflatedpickle.tosuto.TimedToastItem
import com.deflatedpickle.tosuto.ToastItem
import com.deflatedpickle.tosuto.ToastWindow
import com.deflatedpickle.tosuto.action.ToastMultiAction
import com.deflatedpickle.tosuto.action.ToastSingleAction
import com.deflatedpickle.tosuto.api.ToastButtonType
import com.deflatedpickle.tosuto.api.ToastLevel
import javax.swing.JFrame
import javax.swing.JMenuItem
import javax.swing.UIManager

fun main(args: Array<String>) {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

    val frame = JFrame()
    frame.title = "Kotlin Example"

    val buttonSet = listOf(
        ToastButtonType.CLOSE
    )

    // Add the toast window
    val toastWindow = ToastWindow(frame, 160)

    for (toastLevel in ToastLevel.values()) {
        toastWindow.add(
            ToastItem(
                toastLevel,
                toastLevel.name,
                "This is the content for it. ".repeat(toastLevel.ordinal + 1),
                buttonSet,
                listOf(
                    ToastSingleAction("Fix...", null) { action, toast ->
                        println("Fix invoked with ${action.text} from ${toast.title}!")
                        toast.close()
                    },
                    ToastMultiAction(
                        text = "Actions",
                        actions = listOf(JMenuItem("Dumb Action"))
                    )
                )
            )
        )
    }

    toastWindow.add(
        TimedToastItem(
            ToastLevel.WARNING,
            "DESTRUCTION",
            "THIS TOAST WILL SELF-DESTRUCT"
        )
    )

    toastWindow.isVisible = true

    frame.setSize(400, 500)
    frame.setLocationRelativeTo(null)
    frame.isVisible = true
}