package cs3500.animator.view;

import java.awt.Dimension;

import javax.swing.JFrame;

import cs3500.animator.model.EasyAnimatorShape;

/**
 * Class for Visual View that implements methods from EasyAnimatorView and is a JFrame. Reads input
 * and converts it to a JFrame that uses JPanel ViewPanel to hold instances of shapes as animation
 * runs.
 */
public class EasyAnimatorVisualViewImpl extends JFrame implements EasyAnimatorView {
  private boolean inputFinished;
  private ViewPanel ourPanel;

  /**
   * Creates one EasyAnimatorView with the Visual View. Sets inputFinished to false, sets the size
   * off the JFrame to the given width and height, sets ourPanel to a new ViewPanel, sets the layout
   * of the frame to a new Layout that is a scrollbar, and close operations to exit when the
   * visual is closed.
   *
   * @param panelWidth  given width of entire screen.
   * @param panelHeight given height of entire screen.
   * @throws IllegalArgumentException given width or height are not greater than 0.
   */
  public EasyAnimatorVisualViewImpl(int panelWidth, int panelHeight) throws
          IllegalArgumentException {
    if (panelWidth <= 0 && panelHeight <= 0) {
      throw new IllegalArgumentException("Width and height have to be greater than 0.");
    }
    this.inputFinished = false;
    setSize(new Dimension(panelWidth, panelHeight));
    this.ourPanel = new ViewPanel(); // represents panel that holds shapes and is added to

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
   * Calls draw shapes that takes in the array of given AbstractShapes.
   *
   * @param shapes          list of given shapes read from the controller with their commands.
   * @param framesPerSecond how many frames occur per second based on the given
   *                        (speed of animation).
   */
  @Override
  public void addToView(EasyAnimatorShape[] shapes, int framesPerSecond) {
    this.draw(shapes);
  }

  @Override
  public void declareInputFinished() {
    inputFinished = true;
  }

  /**
   * Draws shapes that are added to ourPanel. Each time method is called, ourPanel is cleared and
   * then added to JScrollPane scrollPane which is added to EasyAnimatorVisualViewImpl. OurPanel
   * is then manipulated with all the graphical representations of the shapes. Finally,
   * EasyAnimatorVisualViewImpl is made visible.
   *
   * @param shapes list of given shapes read from the controller with their commands.
   */
  private void draw(EasyAnimatorShape[] shapes) {
    ourPanel = new ViewPanel();
    ourPanel.setAccuShapes(shapes);
    add(ourPanel);
    this.setVisible(true);
  }


}


