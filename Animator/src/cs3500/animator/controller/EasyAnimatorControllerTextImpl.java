package cs3500.animator.controller;

import cs3500.animator.model.EasyAnimatorModel;

import cs3500.animator.model.EasyAnimatorShape;

import cs3500.animator.view.EasyAnimatorView;

/**
 * A Controller designed for text output. This class sends information to the view without a timer.
 * It is not useful for visual views, but will work much more efficiently for text views.
 */
public class EasyAnimatorControllerTextImpl implements EasyAnimatorController {
  private EasyAnimatorModel model;
  private EasyAnimatorView view;

  /**
   * Creates an EasyAnimatorControllerTextImpl with frameCount set equal to 0.
   * @param model given EasyAnimatorModel that is read from the input file.
   * @param view  the given type of view that will be appended to the output (Visual, Text, SVG).
   */
  public EasyAnimatorControllerTextImpl(EasyAnimatorModel model, EasyAnimatorView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * For each increment the current states of the EasyAnimatorShapes of the model are added to
   * the givenview. Timer starts when first called and ends when frameCount equals the total
   * time of thegiven model.
   * @param framesPerSecond given amount of frames wanted per second.
   * @throws IllegalArgumentException framesPerSecond is not greater than 0.
   */
  @Override
  public void run(int framesPerSecond) throws IllegalArgumentException {
    if (framesPerSecond <= 0) {
      throw new IllegalArgumentException("Frames per second must be greater than 0");
    } else {
      for (int i = 0; i < model.getTotalTime() + 1; i++) {
        view.addToView(model.getGameState(i), framesPerSecond);
      }
      view.declareInputFinished();
      view.addToView(new EasyAnimatorShape[1], framesPerSecond);
    }
  }
}
