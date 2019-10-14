package cs3500.animator.controller;


/**
 * Interface that represents the Controller of Easy Animator. Has run method that will be used to
 * run main program.
 */
public interface EasyAnimatorController {

  /**
   * Runs the program sending information to the view from the model.
   * @param framesPerSecond how fast the animation will run.
   * @throws IllegalArgumentException framesPerSecond is not greater than 0.
   */
  void run(int framesPerSecond) throws IllegalArgumentException;
}
