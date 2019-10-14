package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cs3500.animator.model.EasyAnimatorModel;
import cs3500.animator.view.EasyAnimatorInteractiveView;



/**
 * Controller designed for use with an EasyAnimatorInteractiveView. Includes all necessary listeners
 * and runs the animation with a timer. Allows for the animation to be exported as an svg file.
 */
public class EasyAnimatorInteractiveController implements  EasyAnimatorController {
  private final EasyAnimatorInteractiveView view;
  private final EasyAnimatorModel model;
  private Timer timer;
  private int curFrame;
  private boolean restart;
  private boolean rewind;
  private boolean pause;
  private boolean loop;
  private boolean export;
  private boolean adjustingScrub;
  private int speed;

  /**
   * Initializes the controller with the given view and the given model.
   * Interacts with the model and the view such that they will never have to directly interact
   * with each other.
   * @param view the view.
   * @param model the model.
   */
  public EasyAnimatorInteractiveController(EasyAnimatorInteractiveView view,
                                           EasyAnimatorModel model) {
    this.view = view;
    this.model = model;
    restart = false;
    rewind = false;
    pause = true;
    loop = false;
    export = false;
    speed = 1;
    adjustingScrub = false;
    this.configureButtonListener();
    timer = initTimer();
    configureScrubChangeListener();
  }

  /**
   * Sends all instances of every shape in the model to the view so the view can get all initial
   * states in order to print a correct svg file.
   * Then initializes a timer to display the animation and receive user input to control the
   * animation in the views user interface.
   * @param framesPerSecond how fast the animation will run.
   * @throws IllegalArgumentException framesPerSecond is not greater than 0.
   */
  @Override
  public void run(int framesPerSecond) throws IllegalArgumentException {
    if (framesPerSecond < 0) {
      throw new IllegalArgumentException("Frames per second must be greater than 0");
    }
    for (int i = 0 ; i <= model.getTotalTime(); i ++ ) {
      view.addToView(model.getGameState(i), framesPerSecond);
    }
    view.declareInputFinished();
    timer.setDelay(1000 / framesPerSecond);
    timer.start();
  }


  /**
   * Initializes a timer to run the animation in the UI of the interactive view.
   * Also calls the export to svg functionality when this controller recieves an ExportButton event.
   * @return
   */
  private Timer initTimer() {
    Timer timer = new Timer(1000 / speed, null);
    timer.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        if (export) {
          view.exportToSVG(loop);
          export = false;
        }
        if (!pause && curFrame >= 0 && curFrame < model.getTotalTime() + 1 && !adjustingScrub ) {
          view.draw(model.getGameState(curFrame));
          if (!rewind) {
            view.setScrubber(curFrame);
            curFrame++;
          } else {
            view.setScrubber(curFrame);
            curFrame--;
          }
        }
        if (restart) {
          curFrame = 0;
          restart = false;
          rewind = false;
        }
        if (!rewind && loop) {
          if (curFrame >= model.getTotalTime()) {
            curFrame = 0;
          }
        }
        else if (loop) {
          if (curFrame <= 0) {
            curFrame = model.getTotalTime() - 1;
          }
        }
      }
    });
    return timer;
  }

  /**
   * Adds a changeListener to the view. This ChangeListener is meant to be used by the view's
   * slider for scrubbing. It allows the scrubber to set the current frame of this controller.
   */
  private void configureScrubChangeListener() {
    ChangeListener changeListener = new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {

        JSlider source = (JSlider) e.getSource();
        if (source.getValueIsAdjusting()) {
          adjustingScrub = true;
          view.draw(model.getGameState(source.getValue()));
          curFrame = source.getValue();
        }
        else {
          adjustingScrub = false;
        }
      }
    };
    this.view.addScrubListener(changeListener);
  }



  /**
   * Creates a ButtonListener with all relevant events. Every event that can affect the animation
   * in the user interface is given a command as it's key in the HashMap, and that key corresponds
   * to a Runnable that will execute the corresponding command.
   * Adds the ButtonListener as the action listener of the view.
   */
  private void configureButtonListener() {
    Map<String,Runnable> buttonClickedMap = new HashMap<>();
    ButtonListener buttonListener = new ButtonListener();

    buttonClickedMap.put("Speed Button", new SpeedButtonAction());
    buttonClickedMap.put("Play/Pause Button", new PlayPauseButtonAction());
    buttonClickedMap.put("Loop Button", new LoopButtonAction());
    buttonClickedMap.put("Restart Button", new RestartButtonAction());
    buttonClickedMap.put("Export Button", new ExportButtonAction());
    buttonClickedMap.put("Rewind Button", new RewindButtonAction());
    buttonClickedMap.put("Scrub Slider", new ScrubSliderAction());



    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.view.addActionListener(buttonListener);
  }


  /**
   * When run is called, inverts the controller's pause boolean.
   */
  class PlayPauseButtonAction implements Runnable {
    @Override
    public void run() {
      pause = !pause;
    }
  }

  /**
   * When run is called, inverts the controller's loop boolean.
   */
  class LoopButtonAction implements Runnable {
    @Override
    public void run() {
      loop = !loop;
    }
  }

  /**
   * When run is called, sets the restart boolean to true.
   */
  class RestartButtonAction implements Runnable {
    @Override
    public void run() {
      restart = true;
    }
  }

  /**
   * When run is called, sets the controller's export boolean to true.
   * Also sets the output of the view to a BufferedWriter with the file name to export to as
   * the view's outputName.
   */
  class ExportButtonAction implements Runnable {
    @Override
    public void run() {
      export = true;
      FileWriter fileWriter = null;
      if (view.getOutputName() != null) {
        try {
          fileWriter = new FileWriter(view.getOutputName());
        } catch (IOException e) {
          e.printStackTrace();
        }
        Appendable output = new BufferedWriter(fileWriter);
        view.setOutput(output);
      }
    }
  }

  /**
   * When run is called, inverts the controller's rewind boolean.
   */
  class RewindButtonAction implements Runnable {
    @Override
    public void run() {
      rewind = !rewind;
    }
  }

  /**
   * When run is called, changes the controller's speed value as well as the timer's delay value.
   */
  class SpeedButtonAction implements Runnable {
    @Override
    public void run() {
      speed = view.getSpeed();
      timer.setDelay(1000 / view.getSpeed());
    }
  }


  class ScrubSliderAction implements  Runnable {

    @Override
    public void run() {

    }
  }
}
