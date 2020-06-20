import com.deflatedpickle.tosuto.ToastItem
import com.deflatedpickle.tosuto.action.ToastMultiAction
import com.deflatedpickle.tosuto.action.ToastSingleAction
import com.deflatedpickle.tosuto.ToastWindow
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
    val toastWindow = ToastWindow(frame)

    for (toastLevel in ToastLevel.values()) {
        toastWindow.add(
            ToastItem(
                toastLevel,
                toastLevel.name,
                "This is the content for it.".repeat(toastLevel.ordinal + 1),
                buttonSet,
                listOf(
                    ToastSingleAction("Fix...", null) {
                        println("Fix invoked!")
                    },
                    ToastMultiAction(
                        "Actions",
                        listOf(JMenuItem("Dumb Action"))
                    )
                )
            )
        )
    }

    toastWindow.isVisible = true

    frame.setSize(400, 500)
    frame.setLocationRelativeTo(null)
    frame.isVisible = true
}