package cs3500.animator.view;

import java.awt.event.ActionListener;

import javax.swing.event.ChangeListener;

import cs3500.animator.controller.ButtonListener;
import cs3500.animator.model.EasyAnimatorShape;

/**
 * An interface that extends EasyAnimatorView to guarantee necessary functionality for
 * views involving an interactive user interface. This includes a scrubber and buttons.
 */
public interface EasyAnimatorInteractiveView extends EasyAnimatorView {


  /**
   * Adds an ActionListener to the view.
   * This method allows the controller to maintain a connection with the ActionEvents
   * happening in the model.
   * @param l the ActionListener
   */
  void addActionListener(ActionListener l);

  /**
   * Recreates and exports the animation as an svg file.
   * @param loop whether or not the svg file should be looped
   */
  void exportToSVG(boolean loop);


  /**
   * A getter for the speed of the animation.
   * @return the speed of the animation
   */
  int getSpeed();

  /**
   * A getter for the name of the file to export the svg code to.
   * @return the name of the svg file to export to.
   */
  String getOutputName();

  /**
   * Sets the output for the exportation of the svg file.
   * @param output the output for the exportation of the svg file.
   */
  void setOutput(Appendable output);

  /**
   * Displays the given shapes visually in the user interface.
   * @param gameState the list of shapes to display.
   */
  void draw(EasyAnimatorShape[] gameState);

  /**
   * Returns the ButtonListener of the view.
   * @return the ButtonListener
   */
  ButtonListener getButtonListener();


  /**
   * Sets the value of the scrubber. This allows the controller to set the number of the frame
   * that it is displaying to the scrubber.
   * @param i the frame number
   */
  void setScrubber(int i);

  /**
   * Adds a change listener to the scrubber.
   * @param changeListener the ChangeListener of the scrubber
   */
  void addScrubListener(ChangeListener changeListener);
}
