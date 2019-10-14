import org.junit.Test;

import cs3500.animator.controller.EasyAnimatorController;
import cs3500.animator.controller.EasyAnimatorInteractiveController;
import cs3500.animator.model.EasyAnimatorModel;

import cs3500.animator.model.EasyAnimatorModelImpl;

import cs3500.animator.model.EasyAnimatorShape;

import cs3500.animator.model.AnimatorShapeOperation;

import cs3500.animator.model.ColorChangeCommand;

import cs3500.animator.model.Posn;

import cs3500.animator.model.Shape;

import cs3500.animator.model.Rectangle;

import cs3500.animator.model.MyColor;
import cs3500.animator.view.EasyAnimatorInteractiveView;
import cs3500.animator.view.EmptyShellView;

import java.awt.event.ActionEvent;
import java.io.StringWriter;
import java.util.ArrayList;


import static org.junit.Assert.assertEquals;

public class EasyAnimatorInteractiveViewImplTest {

  // Tests Export Button
  @Test
  public void ExportButton() {

    float[] blueArray = {0, 0, 1};
    MyColor blue = new MyColor(blueArray);
    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    float[] deltaColor = {-1, 0, 1};
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new ColorChangeCommand(animateRectangle1, 30, 60, deltaColor);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorInteractiveView interactiveView = new EmptyShellView(output);

    EasyAnimatorController controller = new EasyAnimatorInteractiveController(interactiveView,
            myModel);

    controller.run(1);


    ActionEvent one = new ActionEvent(interactiveView, ActionEvent.ACTION_FIRST,
            "Play/Pause Button");

    ActionEvent two = new ActionEvent(interactiveView, ActionEvent.ACTION_LAST,
            "Export Button");
    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(one);
    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(two);

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals("Action Listener added\n" +
            "Shapes added to view\n" +
            "getOutputName called\n" +
            "getOutputName called\n" +
            "Export to SVG called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n", output.toString());

  }


  // Tests Rewind Button
  @Test
  public void RewindButton() {

    float[] blueArray = {0, 0, 1};
    MyColor blue = new MyColor(blueArray);
    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    float[] deltaColor = {-1, 0, 1};
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new ColorChangeCommand(animateRectangle1, 30, 60, deltaColor);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorInteractiveView interactiveView = new EmptyShellView(output);

    EasyAnimatorController controller = new EasyAnimatorInteractiveController(interactiveView,
            myModel);


    controller.run(1);

    ActionEvent one = new ActionEvent(interactiveView, ActionEvent.ACTION_FIRST,
            "Play/Pause Button");

    ActionEvent two = new ActionEvent(interactiveView, ActionEvent.ACTION_LAST,
            "Rewind Button");
    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(one);
    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(two);

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals("Action Listener added\n" +
            "Shapes added to view\n" +
            "Draw Called\n", output.toString());

  }

  // Tests Loop Button
  @Test
  public void LoopButton() {

    float[] blueArray = {0, 0, 1};
    MyColor blue = new MyColor(blueArray);
    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    float[] deltaColor = {-1, 0, 1};
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new ColorChangeCommand(animateRectangle1, 30, 60, deltaColor);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorInteractiveView interactiveView = new EmptyShellView(output);

    EasyAnimatorController controller = new EasyAnimatorInteractiveController(interactiveView,
            myModel);

    controller.run(1);

    ActionEvent one = new ActionEvent(interactiveView, ActionEvent.ACTION_FIRST,
            "Play/Pause Button");

    ActionEvent two = new ActionEvent(interactiveView, ActionEvent.ACTION_LAST,
            "Loop Button");
    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(one);
    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(two);

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals("Action Listener added\n" +
            "Shapes added to view\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n", output.toString());

  }

  // Tests Restart Button
  @Test
  public void RestartButton() {

    float[] blueArray = {0, 0, 1};
    MyColor blue = new MyColor(blueArray);
    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    float[] deltaColor = {-1, 0, 1};
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new ColorChangeCommand(animateRectangle1, 30, 60, deltaColor);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorInteractiveView interactiveView = new EmptyShellView(output);

    EasyAnimatorController controller = new EasyAnimatorInteractiveController(interactiveView,
            myModel);

    controller.run(1);

    ActionEvent one = new ActionEvent(interactiveView, ActionEvent.ACTION_FIRST,
            "Play/Pause Button");

    ActionEvent two = new ActionEvent(interactiveView, ActionEvent.ACTION_LAST,
            "Restart Button");
    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(one);
    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(two);

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals("Action Listener added\n" +
            "Shapes added to view\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n", output.toString());

  }

  // Tests Play Pause Button
  @Test
  public void PlayPauseButton() {

    float[] blueArray = {0, 0, 1};
    MyColor blue = new MyColor(blueArray);
    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    float[] deltaColor = {-1, 0, 1};
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new ColorChangeCommand(animateRectangle1, 30, 60, deltaColor);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorInteractiveView interactiveView = new EmptyShellView(output);

    EasyAnimatorController controller = new EasyAnimatorInteractiveController(interactiveView,
            myModel);


    controller.run(1);
    ActionEvent one = new ActionEvent(interactiveView, ActionEvent.ACTION_FIRST,
            "Play/Pause Button");

    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(one);

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals("Action Listener added\n" +
            "Shapes added to view\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n", output.toString());

  }

  // Tests Rewind then loop button
  @Test
  public void PlayRewindLoop() {

    float[] blueArray = {0, 0, 1};
    MyColor blue = new MyColor(blueArray);
    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    float[] deltaColor = {-1, 0, 1};
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new ColorChangeCommand(animateRectangle1, 30, 60, deltaColor);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorInteractiveView interactiveView = new EmptyShellView(output);

    EasyAnimatorController controller = new EasyAnimatorInteractiveController(interactiveView,
            myModel);


    controller.run(1);
    ActionEvent one = new ActionEvent(interactiveView, ActionEvent.ACTION_FIRST,
            "Play/Pause Button");
    ActionEvent two = new ActionEvent(interactiveView, ActionEvent.ACTION_FIRST,
            "Rewind Button");
    ActionEvent three = new ActionEvent(interactiveView, ActionEvent.ACTION_FIRST,
            "Loop Button");

    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(one);
    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(two);
    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(three);

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals("Action Listener added\n" +
            "Shapes added to view\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n", output.toString());

  }

  // Tests Loop then Export Button
  @Test
  public void LoopExportButton() {

    float[] blueArray = {0, 0, 1};
    MyColor blue = new MyColor(blueArray);
    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    float[] deltaColor = {-1, 0, 1};
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new ColorChangeCommand(animateRectangle1, 30, 60, deltaColor);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorInteractiveView interactiveView = new EmptyShellView(output);

    EasyAnimatorController controller = new EasyAnimatorInteractiveController(interactiveView,
            myModel);

    controller.run(1);


    ActionEvent one = new ActionEvent(interactiveView, ActionEvent.ACTION_FIRST,
            "Play/Pause Button");
    ActionEvent two = new ActionEvent(interactiveView, ActionEvent.ACTION_LAST,
            "Loop Button");
    ActionEvent three = new ActionEvent(interactiveView, ActionEvent.ACTION_LAST,
            "Export Button");

    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(one);
    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(two);
    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(three);

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals("Action Listener added\n" +
            "Shapes added to view\n" +
            "getOutputName called\n" +
            "getOutputName called\n" +
            "Export to SVG called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n", output.toString());

  }

  // Tests Export Button with no play
  @Test
  public void JustExportButton() {

    float[] blueArray = {0, 0, 1};
    MyColor blue = new MyColor(blueArray);
    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    float[] deltaColor = {-1, 0, 1};
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new ColorChangeCommand(animateRectangle1, 30, 60, deltaColor);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorInteractiveView interactiveView = new EmptyShellView(output);

    EasyAnimatorController controller = new EasyAnimatorInteractiveController(interactiveView,
            myModel);

    controller.run(1);


    ActionEvent three = new ActionEvent(interactiveView, ActionEvent.ACTION_LAST,
            "Export Button");

    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(three);

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals("Action Listener added\n" +
            "Shapes added to view\n" +
            "getOutputName called\n" +
            "getOutputName called\n" +
            "Export to SVG called\n", output.toString());

  }

  @Test
  public void NoPlayButton() {

    float[] blueArray = {0, 0, 1};
    MyColor blue = new MyColor(blueArray);
    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    float[] deltaColor = {-1, 0, 1};
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new ColorChangeCommand(animateRectangle1, 30, 60, deltaColor);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorInteractiveView interactiveView = new EmptyShellView(output);

    EasyAnimatorController controller = new EasyAnimatorInteractiveController(interactiveView,
            myModel);

    controller.run(1);


    ActionEvent one = new ActionEvent(interactiveView, ActionEvent.ACTION_LAST,
            "Rewind Button");
    ActionEvent two = new ActionEvent(interactiveView, ActionEvent.ACTION_LAST,
            "Loop Button");
    ActionEvent three = new ActionEvent(interactiveView, ActionEvent.ACTION_LAST,
            "Restart Button");

    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(one);
    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(two);
    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(three);

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals("Action Listener added\n" +
            "Shapes added to view\n", output.toString());

  }

  // Play with higher frames per second.
  @Test
  public void FasterPlayButton() {

    float[] blueArray = {0, 0, 1};
    MyColor blue = new MyColor(blueArray);
    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    float[] deltaColor = {-1, 0, 1};
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new ColorChangeCommand(animateRectangle1, 30, 60, deltaColor);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorInteractiveView interactiveView = new EmptyShellView(output);

    EasyAnimatorController controller = new EasyAnimatorInteractiveController(interactiveView,
            myModel);

    controller.run(10);


    ActionEvent one = new ActionEvent(interactiveView, ActionEvent.ACTION_LAST,
            "Play/Pause Button");

    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(one);

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals("Action Listener added\n" +
            "Shapes added to view\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n", output.toString());

  }

  // Get Speed without Play
  @Test
  public void SpeedButtonWithoutPlay() {

    float[] blueArray = {0, 0, 1};
    MyColor blue = new MyColor(blueArray);
    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    float[] deltaColor = {-1, 0, 1};
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new ColorChangeCommand(animateRectangle1, 30, 60, deltaColor);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorInteractiveView interactiveView = new EmptyShellView(output);

    EasyAnimatorController controller = new EasyAnimatorInteractiveController(interactiveView,
            myModel);

    controller.run(1);


    ActionEvent one = new ActionEvent(interactiveView, ActionEvent.ACTION_LAST,
            "Speed Button");

    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(one);

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals("Action Listener added\n" +
            "Shapes added to view\n" +
            "Get speed called\n" +
            "Get speed called\n", output.toString());

  }

  // Get Speed with Play
  @Test
  public void SpeedButton() {

    float[] blueArray = {0, 0, 1};
    MyColor blue = new MyColor(blueArray);
    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    float[] deltaColor = {-1, 0, 1};
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new ColorChangeCommand(animateRectangle1, 30, 60, deltaColor);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorInteractiveView interactiveView = new EmptyShellView(output);

    EasyAnimatorController controller = new EasyAnimatorInteractiveController(interactiveView,
            myModel);

    controller.run(1);


    ActionEvent one = new ActionEvent(interactiveView, ActionEvent.ACTION_LAST,
            "Play/Pause Button");
    ActionEvent two = new ActionEvent(interactiveView, ActionEvent.ACTION_LAST,
            "Speed Button");


    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(one);
    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(two);

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals("Action Listener added\n" +
            "Shapes added to view\n" +
            "Get speed called\n" +
            "Get speed called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n" +
            "Draw Called\n", output.toString());

  }

  // Play then Pause
  @Test
  public void PlayThenPause() {

    float[] blueArray = {0, 0, 1};
    MyColor blue = new MyColor(blueArray);
    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    float[] deltaColor = {-1, 0, 1};
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new ColorChangeCommand(animateRectangle1, 30, 60, deltaColor);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorInteractiveView interactiveView = new EmptyShellView(output);

    EasyAnimatorController controller = new EasyAnimatorInteractiveController(interactiveView,
            myModel);

    controller.run(1);


    ActionEvent one = new ActionEvent(interactiveView, ActionEvent.ACTION_LAST,
            "Play/Pause Button");
    ActionEvent two = new ActionEvent(interactiveView, ActionEvent.ACTION_LAST,
            "Play/Pause Button");


    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(one);
    ((EmptyShellView) interactiveView).getButtonListener().actionPerformed(two);

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals("Action Listener added\n" +
            "Shapes added to view\n", output.toString());

  }


  // No Buttons
  @Test
  public void NoButtons() {

    float[] blueArray = {0, 0, 1};
    MyColor blue = new MyColor(blueArray);
    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    float[] deltaColor = {-1, 0, 1};
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new ColorChangeCommand(animateRectangle1, 30, 60, deltaColor);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorInteractiveView interactiveView = new EmptyShellView(output);

    EasyAnimatorController controller = new EasyAnimatorInteractiveController(interactiveView,
            myModel);

    controller.run(1);


    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals("Action Listener added\n" +
            "Shapes added to view\n", output.toString());

  }


}