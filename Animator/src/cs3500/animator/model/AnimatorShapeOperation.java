package cs3500.animator.model;

/**
 * An interface for all animation operations performed on EasyAnimatorShapes.
 */
public interface AnimatorShapeOperation {


  /**
   * Getter for the start time of the command.
   * @return the start time of the command
   */
  int getStartTime();

  /**
   * Getter for the end time of the command.
   * @return the completion time of the command
   */
  int getEndTime();

  /**
   * Sets the shape of the command.
   * @param shape the EasyAnimator shape to set the shape that the command will act upon
   */
  void setShape(EasyAnimatorShape shape);


  /**
   * Prints the description of the action performed on the shape.
   * @return the command description
   */
  String formatCommand();

  /**
   * Prints the description of the action performed on the shape.
   * This method takes in frames per second argument, and changes the start times and end times
   * of the command to correspond to actual seconds.
   * @return the command description
   */
  String formatCommand(int framesPerSecond);

  /**
   * A getter for the EasyAnimatorShape involved in the command.
   * @return the EasyAnimatorShape the command is acting upon.
   */
  EasyAnimatorShape getAnimateShape();

  /**
   * Mutates the shape to its state at the given time unit.
   * This method will act upon the shape such that it represents the
   * desired shape at the given time.
   * @param time the time unit
   * @throws IllegalArgumentException if the given time is before or after the action is committed
   */
  void increment(int time) throws IllegalArgumentException;


  /**
   * Formats the corresponding command (Move, Scale, ColorChange) in SVG code style as a String.
   * @param framesPerSecond how many frames occur per second based on the given
   *                        (speed of animation).
   * @param loop a boolean representing whether or not the animation will be looped
   * @return the command in SVG format as a String.
   */
  String formatAsSVG(int framesPerSecond, boolean loop);

}
