package  cs3500.animator.model;

/**
 * A model interface for an animation application. This model is capable of producing a String of
 * all actions within its total animation, as well as producing a 2D array of EasyAnimatorShapes
 * representing the status of animated shapes at any given point in time.
 */
public interface EasyAnimatorModel {

  /**
   * Prints the description of the model's animation from beginning to end. Includes
   * descriptions of the shapes involved in the animation.
   * @return the complete description as a String
   */
  String printDescription();

  /**
   * Prints the description of the model's animation from beginning to end. Includes
   * descriptions of the shapes involved in the animation.
   * ALL times involved are returned as seconds, and they correspond to the given
   * number of frames per second
   * @param framesPerSecond the frames per second
   * @return the complete description
   */
  String printDescription(int framesPerSecond);

  /**
   * Returns the game state at an instance in time. The game state is represented by a list
   * of EasyAnimatorShapes, with their exact state corresponding to the specific time argument.
   * @param t the desired time
   * @return the list of shapes corresponding to the state of the animation at time t
   * @throws IllegalArgumentException if t is out of the model's time bounds.
   */
  EasyAnimatorShape[] getGameState(int t) throws IllegalArgumentException;

  /**
   * Returns the greatest disappear time of all the shapes. This represents the necessary
   * total time of the entire animation
   * @return the total time for the entire animation
   */
  int getTotalTime();

}
