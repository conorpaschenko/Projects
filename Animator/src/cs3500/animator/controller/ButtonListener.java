package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * A class representing one listener that can run multiple possible actions upon an actionPerformed
 * event.
 */
public class ButtonListener implements ActionListener {
  private Map<String,Runnable> buttonClickedActions;

  /**
   * Empty default constructor.
   */
  public ButtonListener() {
    // Does not require any initialization inside the  constructor because the buttonClickedActions
    // can be set using its setter.
  }

  /**
   * Set the map for key typed events.
   */

  public void setButtonClickedActionMap(Map<String,Runnable> map) {
    buttonClickedActions = map;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (buttonClickedActions.containsKey(e.getActionCommand())) {
      buttonClickedActions.get(e.getActionCommand()).run();
    }
  }
}