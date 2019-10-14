package cs3500.animator.view;

import java.io.IOException;
import java.util.ArrayList;

import cs3500.animator.model.EasyAnimatorShape;

/**
 * An abstract class for views outputting textual information about animations.
 */
public abstract class AbstractTextView implements EasyAnimatorView {
  Appendable output;
  ArrayList<EasyAnimatorShape> accuShapes;
  boolean inputFinished;

  /**
   * This abstract class receives an Appendable representing the output to display the textual
   * view.
   *
   * @param output the output.
   * @throws IllegalArgumentException if the appendable is not in a state to receive input
   */
  AbstractTextView(Appendable output) throws IllegalArgumentException {
    if (output == null) {
      throw new IllegalArgumentException("Output cannot be null");
    }
    this.output = output;
    this.accuShapes = new ArrayList<>();
    inputFinished = false;
  }


  /**
   * If input is not complete, adds all new shapes that are not in accuShapes to accuShapes.
   * Once input is finished, add all necessary text description to the output.
   *
   * @param shapes          list of given shapes read from the controller with their commands.
   * @param framesPerSecond how many frames occur per second based on the given
   *                        (speed of animation).
   */
  @Override
  public void addToView(EasyAnimatorShape[] shapes, int framesPerSecond) {
    if (this.inputFinished) {
      this.addToOutPut(framesPerSecond);
    } else {
      for (EasyAnimatorShape shape : shapes) {
        int i = 0;
        if (shape != null) {
          for (EasyAnimatorShape containedShape : accuShapes) {
            if (shape.getID() != containedShape.getID()) {
              i++;
            }
          }
          if (i == accuShapes.size()) {
            accuShapes.add(shape.copy());
          }
        }
      }
    }
  }

  /**
   * An abstract method that adds appends all relevant text to the output.
   *
   * @param framesPerSecond the frames per second of the animation.
   */
  protected abstract void addToOutPut(int framesPerSecond);

  @Override
  public void declareInputFinished() {
    this.inputFinished = true;
  }


  /**
   * Appends str to output.
   *
   * @param str String that represents input as textual description
   */
  protected void appendAbstraction(String str) {
    try {
      output.append(str);
    } catch (IOException e) {
      throw new IllegalStateException("Appendable not in state to receive output");
    }
  }

}
