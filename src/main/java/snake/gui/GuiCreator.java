package snake.gui;

import static snake.gui.StyleUtility.getSkin;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class GuiCreator {
    private transient LauncherClass launcher;

    public GuiCreator(LauncherClass launcher) {
        this.launcher = launcher;
    }

    /**
     * Creates a new label with the given text and y-coordinate.
     *
     * @param text text of the label
     * @param y    y-coordinate of the label
     * @return new label with these attributes
     */
    public Label createLabel(String text, Float x, float y) {
        Label label = new Label(text,
                getSkin().get("optional", Label.LabelStyle.class));
        label.setAlignment(Align.center);
        label.setFontScale(1.5f);

        float positionX = x == null ? (launcher.getWidth() - label.getWidth()) / 2f : x;
        label.setPosition(positionX, y);

        label.setBounds(x == null
                        ? (launcher.getWidth() - label.getWidth() * 1.5f) / 2f : x,
                y, label.getWidth() * 1.5f, label.getHeight() * 1.5f);
        return label;
    }

    /**
     * Creates label with an on-click listener.
     *
     * @param text     text of the label
     * @param y        y-coordinate of the label
     * @param listener listener of the label
     * @return new label with these attributes + listener
     */
    public Label createLabelWithOnClick(String text, float y, ClickListener listener) {
        Label label = createLabel(text, null, y);
        label.addListener(listener);
        return label;
    }

    /**
     * Creates button with the given input.
     *
     * @param text     text of the button
     * @param x        x-coordinate of the button,
     *                 can be null to indicate that the button needs to be in the center
     * @param y        y-coordinate of the button
     * @param listener listener of the button
     * @return button with the given attributes
     */
    public TextButton createButton(String text, Float x, float y, ClickListener listener) {
        TextButton button = new TextButton(text, getSkin());

        //default x, places it in the middle
        if (x == null) {
            x = (launcher.getWidth() - button.getWidth()) / 2f;
        }

        button.setPosition(x, y);
        button.addListener(listener);
        return button;
    }

    /**
     * Creates a new dialog with the given error message.
     * @param text text of the error message
     */
    public void createDialog(String text, String title, Stage stage) {
        Dialog dialog = new Dialog(title, getSkin(), "dialog");
        dialog.padTop(35);

        Label label = new Label("\n" + text + "\n",
                StyleUtility.getSkin().get("optional", Label.LabelStyle.class));
        dialog.text(label);
        dialog.button("Confirm", true);

        dialog.show(stage);
    }
}
