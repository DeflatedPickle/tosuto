import com.deflatedpickle.tosuto.api.*;
import com.deflatedpickle.tosuto.ToastItem;
import com.deflatedpickle.tosuto.ToastWindow;
import com.deflatedpickle.tosuto.command.ToastMultiCommand;
import com.deflatedpickle.tosuto.command.ToastSingleCommand;

import javax.swing.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JFrame frame = new JFrame();

        Set<ToastButtonType> buttonSet = new HashSet<>(
                Collections.singletonList(
                        ToastButtonType.CLOSE
                )
        );

        JMenuItem dumbAction = new JMenuItem("Dumb Action");
        dumbAction.addActionListener(e -> System.out.println("Dumb action invoked!"));

        Set<JMenuItem> actionsSet = new HashSet<>();
        Collections.addAll(actionsSet,
                dumbAction
        );

        Set<ToastCommand> commandSet = new HashSet<>();
        Collections.addAll(commandSet,
                new ToastSingleCommand("Fix...", () -> {
                    System.out.println("Fix invoked!");
                    return null;
                }),
                new ToastMultiCommand("Actions", actionsSet)
        );

        // Add the toast window
        ToastWindow toastWindow = new ToastWindow(frame, 140, ToastOrder.ITERATIVE, ToastItemAnchor.NORTH, ToastWindowAnchor.EAST);

        for (ToastLevel toastLevel: ToastLevel.values()) {
            toastWindow.add(new ToastItem(
                    toastLevel,
                    ToastItem.Companion.getTransparentIcon(),
                    toastLevel.name(),
                    String.join("", Collections.nCopies(toastLevel.ordinal() + 1, "This is the content for it.")),
                    buttonSet,
                    commandSet
            ));
        }

        toastWindow.setVisible(true);

        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
