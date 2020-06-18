import com.deflatedpickle.tosuto.ToastButton;
import com.deflatedpickle.tosuto.ToastItem;
import com.deflatedpickle.tosuto.ToastLevel;
import com.deflatedpickle.tosuto.ToastWindow;

import javax.swing.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        Set<ToastButton> buttonSet = new HashSet<>(
                Collections.singletonList(
                        ToastButton.CLOSE
                )
        );

        // Add the toast window
        ToastWindow toastWindow = new ToastWindow(frame, 140, 90);

        for (int i = 0; i < 3; i++) {
            toastWindow.add(new ToastItem(
                    ToastLevel.INFO,
                    ToastItem.Companion.getTransparentIcon(),
                    "Toast" + i,
                    "This is the content for it.",
                    buttonSet
            ));
        }

        toastWindow.setVisible(true);

        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
