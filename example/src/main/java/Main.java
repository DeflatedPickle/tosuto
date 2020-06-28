import com.deflatedpickle.tosuto.*;
import com.deflatedpickle.tosuto.api.*;
import com.deflatedpickle.tosuto.action.ToastAction;
import com.deflatedpickle.tosuto.action.ToastMultiAction;
import com.deflatedpickle.tosuto.action.ToastSingleAction;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JFrame frame = new JFrame();

        ArrayList<ToastButtonType> buttonSet = new ArrayList<>(
                Collections.singletonList(
                        ToastButtonType.CLOSE
                )
        );

        // Add the toast window
        ToastWindow toastWindow = new ToastWindow(frame, 160);

        for (ToastLevel toastLevel : ToastLevel.values()) {
            JMenuItem dumbAction = new JMenuItem("Dumb Action");
            dumbAction.addActionListener(e -> System.out.println("Dumb action invoked!"));

            ArrayList<JMenuItem> actionsSet = new ArrayList<>();
            Collections.addAll(actionsSet,
                    dumbAction
            );

            ArrayList<ToastAction> commandSet = new ArrayList<>();
            Collections.addAll(commandSet,
                    new ToastSingleAction("Fix...", null, (action, toastItem) -> {
                        System.out.println(String.format("Fix invoked with %s from %s!", action.getText(), toastItem.getTitle()));
                        toastItem.close();
                        return null;
                    }),
                    new ToastMultiAction("Actions", actionsSet)
            );

            toastWindow.add(new ToastItem(
                    toastLevel,
                    toastLevel.name().substring(0, 1).concat(toastLevel.name().substring(1).toLowerCase()),
                    String.join("", Collections.nCopies(toastLevel.ordinal() + 1, "This is the content for it. ")),
                    buttonSet,
                    commandSet
            ));
        }

        toastWindow.add(new TimedToastItem(
                ToastLevel.WARNING,
                "DESTRUCTION",
                "THIS TOAST WILL SELF-DESTRUCT"
        ));

        toastWindow.setVisible(true);

        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
