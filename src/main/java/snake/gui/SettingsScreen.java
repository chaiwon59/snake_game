package snake.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingsScreen extends InputScreen {
    public SettingsScreen(Game game) {
        super(game);
    }

    @Override
    public List<Actor> createActors() {
        CheckBox noWalls = createCheckBoxNoWalls();
        CheckBox noGrid = createCheckBoxNoGrid();
        TextButton save = createSaveButton(noWalls, noGrid);

        return new ArrayList<>(Arrays.asList(noWalls, noGrid, save));
    }

    /**
     * Creates a checkbox with the given values.
     * @param text Text next to the checkbox
     * @param y y-coordinate of the checkbox
     * @param checked boolean indicating whether the checkbox should be checked
     * @return checkbox with given values
     */
    private CheckBox createCheckBox(String text, float y, boolean checked) {
        CheckBox checkBox = new CheckBox(text, getSkin());
        checkBox.setPosition((getGame().getWidth() - checkBox.getWidth()) / 2,
                y);
        if (checked) {
            checkBox.setChecked(true);
        }
        return checkBox;
    }

    /**
     * Creates checkbox for the no walls setting.
     * @return checkbox
     */
    private CheckBox createCheckBoxNoWalls() {
        return createCheckBox(" No Walls", getGame().getHeight() / 1.75f,
                getGame().getUser().isNoWalls());
    }

    /**
     * Creates checkbox for the no grid setting.
     * @return checkbox
     */
    private CheckBox createCheckBoxNoGrid() {
        return createCheckBox(" No Grid", getGame().getHeight() / 1.9f,
                getGame().getUser().isNoGrid());
    }

    /**
     * Creates textbutton which saves the indicated settings in the user class.
     * @param noWalls checkbox for the no walls setting
     * @param noGrid checkbox for the no grid setting
     * @return save button with the given functionality
     */
    private TextButton createSaveButton(CheckBox noWalls, CheckBox noGrid) {
        return createButton("Save", null, getGame().getHeight() / 2.25f, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (noWalls.isChecked()) {
                    getGame().getUser().setNoWalls(true);
                } else {
                    getGame().getUser().setNoWalls(false);
                }

                if (noGrid.isChecked()) {
                    getGame().getUser().setNoGrid(true);
                } else {
                    getGame().getUser().setNoGrid(false);
                }
                getGame().setScreen(new MainMenu(getGame()));
            }
        });
    }
}
