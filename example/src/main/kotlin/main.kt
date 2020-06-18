import com.deflatedpickle.tosuto.ToastButton
import com.deflatedpickle.tosuto.ToastItem
import com.deflatedpickle.tosuto.ToastItem.Companion.transparentIcon
import com.deflatedpickle.tosuto.ToastLevel
import com.deflatedpickle.tosuto.ToastWindow
import javax.swing.JFrame

fun main() {
    val frame = JFrame()

    val buttonSet = setOf(
        ToastButton.CLOSE
    )

    // Add the toast window
    val toastWindow = ToastWindow(frame, 140, 90)

    for (i in 0..2) {
        toastWindow.add(
            ToastItem(
                ToastLevel.INFO,
                transparentIcon,
                "Toast$i",
                "This is the content for it.",
                buttonSet
            )
        )
    }

    toastWindow.isVisible = true

    frame.setSize(400, 400)
    frame.setLocationRelativeTo(null)
    frame.isVisible = true
}