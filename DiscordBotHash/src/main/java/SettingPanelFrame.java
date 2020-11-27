import java.awt.*;
import javax.swing.*;

public class SettingPanelFrame extends JFrame {

    public SettingPanelFrame(String title, int x, int y, int width, int height) {

        setTitle(title);
        setBounds(x, y, width, height);


        JPanel frameContent = new SettingPanelPanel();

        Container visibleArea = getContentPane();
        visibleArea.add(frameContent);
        frameContent.setPreferredSize(new Dimension(width, height));
        pack();
        frameContent.requestFocusInWindow();
        setVisible(true);
    }
}
//Change Nothing Here