package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import cs3500.animator.model.EasyAnimatorModel;
import cs3500.animator.model.EasyAnimatorShape;
import cs3500.animator.view.EasyAnimatorView;

/**
 * Class that implements run method from EasyAnimatorController. Represents an
 * EasyAnimatorController that creates timer to incrementally run the run method for a visual view.
 * This controller is used for a controller between a model and a visual view
 */
public class EasyAnimatorControllerViewImpl implements EasyAnimatorController {
  private EasyAnimatorModel model;
  private EasyAnimatorView view;
  private int frameCount;
  private int framesPerSecond;

  /**
   * Creates an EasyAnimatorController with frameCount set equal to 0.
   *
   * @param model given EasyAnimatorModel that is read from the input file.
   * @param view  the given type of view that will be appended to the output (Visual, Text, SVG).
   */
  public EasyAnimatorControllerViewImpl(EasyAnimatorModel model, EasyAnimatorView view) {
    this.model = model;
    this.view = view;
    this.frameCount = 0;
  }

  /**
   * Creates a timer that has a delay based on the frames per second for its increment. For each
   * increment the current states of the EasyAnimatorShapes of the model are added to the given
   * view. Timer starts when first called and ends when framecount equals the total time of the
   * given model.
   *
   * @param framesPerSecond1 given amount of frames wanted per second.
   * @throws IllegalArgumentException framesPerSecond is not greater than 0.
   */
  @Override
  public void run(int framesPerSecond1) throws IllegalArgumentException {
    this.framesPerSecond = framesPerSecond1;
    if (framesPerSecond <= 0) {
      throw new IllegalArgumentException("Frames per second must be greater than 0");
    } else {
      Timer timer = new Timer(1000 / framesPerSecond, null);
      timer.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          view.addToView(model.getGameState(frameCount), framesPerSecond);
          frameCount++;
          if (frameCount == model.getTotalTime()) {
            timer.stop();
            view.declareInputFinished();
            view.addToView(new EasyAnimatorShape[1], framesPerSecond);
          }
        }
      });
      if (frameCount == 0) {
        timer.start();
        try {
          Thread.sleep(model.getTotalTime() * 1000 / framesPerSecond);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
