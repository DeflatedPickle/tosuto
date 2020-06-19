import com.deflatedpickle.tosuto.api.ToastButtonType
import com.deflatedpickle.tosuto.ToastItem
import com.deflatedpickle.tosuto.ToastItem.Companion.transparentIcon
import com.deflatedpickle.tosuto.api.ToastLevel
import com.deflatedpickle.tosuto.ToastWindow
import com.deflatedpickle.tosuto.command.ToastMultiCommand
import com.deflatedpickle.tosuto.command.ToastSingleCommand
import javax.swing.JFrame
import javax.swing.JMenuItem
import javax.swing.UIManager

fun main() {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

    val frame = JFrame()

    val buttonSet = setOf(
        ToastButtonType.CLOSE
    )

    val actionsSet = setOf(JMenuItem("Dumb Action"))

    val commandSet = setOf(
        ToastSingleCommand("Fix...") {
            println("Fix invoked!")
        },
        ToastMultiCommand("Actions", actionsSet)
    )

    // Add the toast window
    val toastWindow = ToastWindow(frame)

    for (toastLevel in ToastLevel.values()) {
        toastWindow.add(
            ToastItem(
                toastLevel,
                transparentIcon,
                toastLevel.name,
                "This is the content for it.".repeat(toastLevel.ordinal + 1),
                buttonSet,
                commandSet
            )
        )
    }

    toastWindow.isVisible = true

    frame.setSize(400, 500)
    frame.setLocationRelativeTo(null)
    frame.isVisible = true
}