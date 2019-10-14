package cs3500.animator.view;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.event.ChangeListener;

import cs3500.animator.controller.ButtonListener;
import cs3500.animator.model.EasyAnimatorShape;


/**
 * THIS CLASS IS USED FOR TESTING ONLY. Represents a an empty shell view of the
 * EasyAnimatorInteractiveView interface. All methods do not accomplish their purpose declared in
 * the interface. Instead, they append indicators to an output.
 */
public class EmptyShellView extends JFrame implements EasyAnimatorInteractiveView {
  ArrayList<EasyAnimatorShape[]> frames;
  ArrayList<EasyAnimatorShape> accuShapes;
  boolean inputFinished;
  Appendable output;
  JButton dummyButton;
  boolean printShapesAdded;

  /**
   * Creates a new empty shell view. Creates a dummy button to store an action listener. Requires
   * an output that string indicators be appended to.
   * @param output the output
   * @throws IllegalArgumentException if the output is null.
   */
  public EmptyShellView(Appendable output) throws IllegalArgumentException {
    super();
    if (output == null) {
      throw new IllegalArgumentException("Output cannot be null");
    }
    accuShapes = new ArrayList<>();
    frames = new ArrayList<>();
    inputFinished = false;
    dummyButton = new JButton();
    this.output = output;
    this.printShapesAdded = true;


  }

  /**
   * Adds an action listener to the dummy button. Also appends an indicator that this method
   * has been reached to output.
   * @param l the ActionListener
   */
  @Override
  public void addActionListener(ActionListener l) {
    this.dummyButton.addActionListener(l);
    try {
      output.append("Action Listener added\n");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


  /**
   * Appends an indicator that this method has been reached to output.
   * @param loop whether or not the svg file should be looped
   */
  @Override
  public void exportToSVG(boolean loop) {
    try {
      output.append("Export to SVG called\n");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Appends an indicator that this method has been reached to output.
   * @param shapes a list of shapes
   */
  @Override
  public void draw(EasyAnimatorShape[] shapes) {
    try {
      output.append("Draw Called\n");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Appends an indicator that this method has been reached to output.
   * @return 1.
   */
  @Override
  public int getSpeed() {
    try {
      output.append("Get speed called\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 1;
  }

  /**
   * Appends an indicator that this method has been reached to output.
   * @return the ButtonListener
   */
  @Override
  public ButtonListener getButtonListener() {
    return (ButtonListener) dummyButton.getActionListeners()[0];
  }

  @Override
  public void setScrubber(int i) {

  }

  @Override
  public void addScrubListener(ChangeListener changeListener) {

  }

  /**
   * Appends an indicator that this method has been reached to output.
   * @return the outputName.
   */
  @Override
  public String getOutputName() {
    try {
      output.append("getOutputName called\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "le.svg";
  }


  /**
   * Appends an indicator that this method has been reached to output.
   * @param output the output for the exportation of the svg file.
   */
  @Override
  public void setOutput(Appendable output) {
    try {
      output.append("Output set\n");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Appends an indicator that this method has been reached to output.
   * @param shapes          list of given shapes read from the controller with their commands.
   * @param framesPerSecond how many frames occur per second based on the given
   */
  @Override
  public void addToView(EasyAnimatorShape[] shapes, int framesPerSecond) {
    if (printShapesAdded) {
      try {
        output.append("Shapes added to view\n");
      } catch (IOException e) {
        e.printStackTrace();
      }
      printShapesAdded = false;
    }

  }



  @Override
  public void declareInputFinished() {
    inputFinished = true;
  }
}

