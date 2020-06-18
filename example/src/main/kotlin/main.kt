import com.deflatedpickle.tosuto.ToastButtonType
import com.deflatedpickle.tosuto.ToastItem
import com.deflatedpickle.tosuto.ToastItem.Companion.transparentIcon
import com.deflatedpickle.tosuto.ToastLevel
import com.deflatedpickle.tosuto.ToastWindow
import com.deflatedpickle.tosuto.command.ToastMultiCommand
import com.deflatedpickle.tosuto.command.ToastSingleCommand
import java.util.*
import javax.swing.JFrame
import javax.swing.JMenuItem
import kotlin.collections.HashSet

fun main() {
    val frame = JFrame()

    val buttonSet = setOf(
        ToastButtonType.CLOSE
    )

    val actionsSet = setOf(JMenuItem("Dumb Action"))

    val actionSet = setOf(
        ToastSingleCommand("Fix...") {
            println("Fix invoked!")
        },
        ToastMultiCommand("Actions", actionsSet)
    )

    // Add the toast window
    val toastWindow = ToastWindow(frame, 140, 90)

    for (toastLevel in ToastLevel.values()) {
        toastWindow.add(
            ToastItem(
                toastLevel,
                transparentIcon,
                toastLevel.name,
                "This is the content for it.",
                buttonSet,
                actionSet
            )
        )
    }

    toastWindow.isVisible = true

    frame.setSize(400, 400)
    frame.setLocationRelativeTo(null)
    frame.isVisible = true
}