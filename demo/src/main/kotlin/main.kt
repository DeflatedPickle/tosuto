import com.deflatedpickle.tosuto.ToastItem
import com.deflatedpickle.tosuto.ToastLayout
import com.deflatedpickle.tosuto.ToastWindow
import com.deflatedpickle.tosuto.api.ToastItemAnchor
import com.deflatedpickle.tosuto.api.ToastLevel
import com.deflatedpickle.tosuto.api.ToastOrder
import com.deflatedpickle.tosuto.api.ToastWindowAnchor
import com.deflatedpickle.undulation.constraints.FillBothFinishLine
import com.deflatedpickle.undulation.constraints.FillHorizontalFinishLine
import java.awt.GridBagLayout
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JSeparator
import javax.swing.JSlider
import javax.swing.JTextArea
import javax.swing.JTextField
import javax.swing.SwingConstants
import javax.swing.UIManager

fun main(args: Array<String>) {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

    val frame = JFrame()
    frame.title = "Tosuto Demo"
    frame.layout = GridBagLayout()

    val toastWindow = ToastWindow(frame)
    toastWindow.isVisible = true

    // Changing current toasts

    frame.add(JLabel("Toast Width:"))
    val widthSlider = JSlider(1, 400).apply {
        this.value = toastWindow.toastWidth
        addChangeListener {
            toastWindow.toastWidth = this.value
        }
    }
    frame.add(widthSlider, FillHorizontalFinishLine)

    frame.add(JLabel("Window Anchor:"))
    val windowAnchorSelection = JComboBox(ToastWindowAnchor.values()).apply {
        this.selectedItem = toastWindow.windowAnchor

        addActionListener {
            toastWindow.windowAnchor = this.selectedItem as ToastWindowAnchor
        }
    }
    frame.add(windowAnchorSelection, FillHorizontalFinishLine)

    // Toast layout
    frame.add(JSeparator(SwingConstants.HORIZONTAL), FillHorizontalFinishLine)

    frame.add(JLabel("Toast Order:"))
    val orderSelection = JComboBox(ToastOrder.values()).apply {
        this.selectedItem = toastWindow.toastOrder

        addActionListener {
            toastWindow.toastOrder = this.selectedItem as ToastOrder
        }
    }
    frame.add(orderSelection, FillHorizontalFinishLine)

    frame.add(JLabel("Toast Anchor:"))
    val anchorSelection = JComboBox(ToastItemAnchor.values()).apply {
        this.selectedItem = toastWindow.toastAnchor

        addActionListener {
            toastWindow.toastAnchor = this.selectedItem as ToastItemAnchor
        }
    }
    frame.add(anchorSelection, FillHorizontalFinishLine)

    frame.add(JLabel("Spacing:"))
    val spacingSlider = JSlider(1, 20).apply {
        this.value = (toastWindow.contentPane.layout as ToastLayout).spacing
        addChangeListener {
            (toastWindow.contentPane.layout as ToastLayout).spacing = this.value
            toastWindow.refresh()
        }
    }
    frame.add(spacingSlider, FillHorizontalFinishLine)

    frame.add(JPanel().apply {
        this.border = BorderFactory.createTitledBorder("Margins")
        this.layout = GridBagLayout()

        this.add(JLabel("North:"))
        val northSlider = JSlider(1, 20).apply {
            this.value = (toastWindow.contentPane.layout as ToastLayout).northMargin
            addChangeListener {
                (toastWindow.contentPane.layout as ToastLayout).northMargin = this.value
                toastWindow.refresh()
            }
        }
        this.add(northSlider, FillHorizontalFinishLine)

        this.add(JLabel("East:"))
        val eastSlider = JSlider(1, 20).apply {
            this.value = (toastWindow.contentPane.layout as ToastLayout).eastMargin
            addChangeListener {
                (toastWindow.contentPane.layout as ToastLayout).eastMargin = this.value
                toastWindow.refresh()
            }
        }
        this.add(eastSlider, FillHorizontalFinishLine)

        this.add(JLabel("South:"))
        val southSlider = JSlider(1, 20).apply {
            this.value = (toastWindow.contentPane.layout as ToastLayout).southMargin
            addChangeListener {
                (toastWindow.contentPane.layout as ToastLayout).southMargin = this.value
                toastWindow.refresh()
            }
        }
        this.add(southSlider, FillHorizontalFinishLine)

        this.add(JLabel("West:"))
        val westSlider = JSlider(1, 20).apply {
            this.value = (toastWindow.contentPane.layout as ToastLayout).westMargin
            addChangeListener {
                (toastWindow.contentPane.layout as ToastLayout).westMargin = this.value
                toastWindow.refresh()
            }
        }
        this.add(westSlider, FillHorizontalFinishLine)
    }, FillBothFinishLine)

    // Adding new toasts
    frame.add(JSeparator(SwingConstants.HORIZONTAL), FillHorizontalFinishLine)

    val levelSelection = JComboBox(ToastLevel.values())
    val nameField = JTextField("Name")
    val contentTextArea = JTextArea("Content").apply {
        lineWrap = true
    }
    val addButton = JButton("Add").apply {
        addActionListener {
            toastWindow.addToast(
                ToastItem(
                    level = levelSelection.selectedItem as ToastLevel,
                    title = nameField.text,
                    content = contentTextArea.text
                )
            )
        }
    }

    frame.add(levelSelection)
    frame.add(nameField, FillHorizontalFinishLine)
    frame.add(contentTextArea, FillBothFinishLine)
    frame.add(addButton)

    frame.setSize(400, 500)
    frame.setLocationRelativeTo(null)
    frame.isVisible = true
}