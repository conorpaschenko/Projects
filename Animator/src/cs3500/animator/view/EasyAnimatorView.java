package cs3500.animator.view;


import cs3500.animator.model.EasyAnimatorShape;

/**
 * Interface for View of EasyAnimatorModel. Represents one EasyAnimatorView whether the view is
 * Text, Visual, or SVG. Has method that adds to the output based on type of view and whether
 * the input is finished.
 */
public interface EasyAnimatorView {

  /**
   * Add all relevant information to the view given the list of shapes. Varies for type of view
   * whether it is the shapes and their commands described as text, a visual animation, or SVG code.
   *
   * @param shapes          list of given shapes read from the controller with their commands.
   * @param framesPerSecond how many frames occur per second based on the given
   *                        (speed of animation).
   */
  void addToView(EasyAnimatorShape[] shapes, int framesPerSecond);

  /**
   * Declares when Input when is finished.
   */
  void declareInputFinished();

}
