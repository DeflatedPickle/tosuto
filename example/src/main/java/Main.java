import com.deflatedpickle.tosuto.ToastButtonType;
import com.deflatedpickle.tosuto.ToastItem;
import com.deflatedpickle.tosuto.ToastLevel;
import com.deflatedpickle.tosuto.ToastWindow;
import com.deflatedpickle.tosuto.api.ToastCommand;
import com.deflatedpickle.tosuto.command.ToastMultiCommand;
import com.deflatedpickle.tosuto.command.ToastSingleCommand;

import javax.swing.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
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
                new ToastMultiCommand("Actions", actionsSet),
                new ToastSingleCommand("Fix...", () -> {
                    System.out.println("Fix invoked!");
                    return null;
                })
        );

        // Add the toast window
        ToastWindow toastWindow = new ToastWindow(frame, 140, 90);

        for (ToastLevel toastLevel: ToastLevel.values()) {
            toastWindow.add(new ToastItem(
                    toastLevel,
                    ToastItem.Companion.getTransparentIcon(),
                    toastLevel.name(),
                    "This is the content for it.",
                    buttonSet,
                    commandSet
            ));
        }

        toastWindow.setVisible(true);

        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        MainKt.main();
    }
}
