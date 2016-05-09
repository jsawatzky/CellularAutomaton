package jacoblibrary.utils.font;

import javax.swing.*;
import java.awt.*;

public class FontUtils {

    public static Font scaleFontToBounds(Font font, String text, Dimension bounds, JComponent comp, boolean multiLine) {

        if (multiLine) {

            JFrame frame = new JFrame("Test");
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            JTextArea textArea = new JTextArea(text);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setColumns(0);
            panel.setMaximumSize(new Dimension(bounds.width, 90000));
            panel.setPreferredSize(bounds);
            panel.setMinimumSize(new Dimension(bounds.width, 0));
            panel.add(textArea);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(panel);
            frame.pack();

            Font savedFont = font;

            for (int i = 1; i < 500; i++) {

                textArea.setFont(font.deriveFont((float) i));

                if (textArea.getPreferredSize().height > bounds.height) {
                    frame.dispose();
                    return savedFont;
                }
                savedFont = textArea.getFont();
            }
            frame.dispose();
            return savedFont;

        } else {

            Font savedFont = font;
            for (int i = 1; i < 500; i++) {
                Font newFont = font.deriveFont((float) i);
                FontMetrics fm = comp.getFontMetrics(newFont);
                int widthText = fm.stringWidth(text);
                int heightText = fm.getHeight();
                if (heightText > bounds.height || widthText > bounds.width) {
                    return savedFont;
                }
                savedFont = newFont;
            }
            return savedFont;

        }

    }

}
