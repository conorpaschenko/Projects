import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import cs3500.animator.controller.EasyAnimatorController;
import cs3500.animator.controller.EasyAnimatorControllerTextImpl;
import cs3500.animator.model.AnimatorShapeOperation;
import cs3500.animator.model.ColorChangeCommand;
import cs3500.animator.model.EasyAnimatorModel;
import cs3500.animator.model.EasyAnimatorModelImpl;
import cs3500.animator.model.EasyAnimatorShape;
import cs3500.animator.model.MoveCommand;
import cs3500.animator.model.MyColor;
import cs3500.animator.model.Oval;
import cs3500.animator.model.Posn;
import cs3500.animator.model.Rectangle;
import cs3500.animator.model.ScaleCommand;
import cs3500.animator.model.Shape;
import cs3500.animator.view.EasyAnimatorTextViewImpl;
import cs3500.animator.view.EasyAnimatorView;

import static org.junit.Assert.assertEquals;

public class EasyAnimatorTextViewImplTest {

  // Empty Model
  @Test
  public void EmptyModel() {


    Appendable output = new StringWriter();
    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorView textView = new EasyAnimatorTextViewImpl(output);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, textView);

    controller.run(20);


    assertEquals("", output.toString());

  }

  // Tests change Color
  @Test
  public void ChangeColor() {

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
    EasyAnimatorView textView = new EasyAnimatorTextViewImpl(output);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, textView);

    controller.run(20);


    assertEquals("Name: Red Rectangle\n" +
            "Type: rectangle\n" +
            "Corner: (50.00, 50.00) Width: 50.00, Height: 50.00, Color: [r=1.00,g=0.00,b=0.00]\n" +
            "Appears at t = 0.0s Disappears at t = 5.0s\n" +
            "\n" +
            "Shape Red Rectangle changes color from [r=1.00,g=0.00,b=0.00] to [r=0.00,g=0.00," +
            "b=1.00]" +
            " from t = 1.0s to t = 3.0s\n", output.toString());

  }

  // Tests Scale up
  @Test
  public void ScaleUp() {

    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);

    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new ScaleCommand(animateRectangle1, 30, 60,
                    2, 2);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorView textView = new EasyAnimatorTextViewImpl(output);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, textView);

    controller.run(20);


    assertEquals("Name: Red Rectangle\n" +
            "Type: rectangle\n" +
            "Corner: (50.00, 50.00) Width: 50.00, Height: 50.00, Color: [r=1.00,g=0.00,b=0.00]\n" +
            "Appears at t = 0.0s Disappears at t = 5.0s\n" +
            "\n" +
            "Shape Red Rectangle scales from Width: 50.00, Height: 50.00 to Width: 100.00," +
            " Height:" +
            " 100.00 from t = 1.0s to t = 3.0s\n", output.toString());

  }

  // Tests Scale down
  @Test
  public void ScaleDown() {

    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);

    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new ScaleCommand(animateRectangle1, 30, 60,
                    (float) .5, (float) .5);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorView textView = new EasyAnimatorTextViewImpl(output);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, textView);

    controller.run(20);


    assertEquals("Name: Red Rectangle\n" +
            "Type: rectangle\n" +
            "Corner: (50.00, 50.00) Width: 50.00, Height: 50.00, Color: [r=1.00,g=0.00,b=0.00]\n" +
            "Appears at t = 0.0s Disappears at t = 5.0s\n" +
            "\n" +
            "Shape Red Rectangle scales from Width: 50.00, Height: 50.00 to Width: 25.00," +
            " Height:" +
            " 25.00 from t = 1.0s to t = 3.0s\n", output.toString());

  }

  // Tests Move Up
  @Test
  public void MoveUp() {

    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);

    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new MoveCommand(animateRectangle1, 30, 60,
                    100, 100);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorView textView = new EasyAnimatorTextViewImpl(output);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, textView);

    controller.run(20);


    assertEquals("Name: Red Rectangle\n" +
            "Type: rectangle\n" +
            "Corner: (50.00, 50.00) Width: 50.00, Height: 50.00, Color: [r=1.00,g=0.00,b=0.00]\n" +
            "Appears at t = 0.0s Disappears at t = 5.0s\n" +
            "\n" +
            "Shape Red Rectangle moves from (50.00, 50.00) to (150.00, 150.00) from t = 1.0s to" +
            " t = 3.0s\n", output.toString());

  }

  // Tests Move down
  @Test
  public void MoveDown() {

    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);

    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new MoveCommand(animateRectangle1, 30, 60,
                    -25, -25);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorView textView = new EasyAnimatorTextViewImpl(output);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, textView);

    controller.run(20);


    assertEquals("Name: Red Rectangle\n" +
            "Type: rectangle\n" +
            "Corner: (50.00, 50.00) Width: 50.00, Height: 50.00, Color: [r=1.00,g=0.00,b=0.00]\n" +
            "Appears at t = 0.0s Disappears at t = 5.0s\n" +
            "\n" +
            "Shape Red Rectangle moves from (50.00, 50.00) to (25.00, 25.00) from t = 1.0s to" +
            " t = 3.0s\n", output.toString());

  }

  // Tests Null Output
  @Test(expected = IllegalArgumentException.class)
  public void NullOutput() {

    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);

    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    Appendable output = null;

    AnimatorShapeOperation move1 =
            new MoveCommand(animateRectangle1, 30, 60,
                    25, 25);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorView textView = new EasyAnimatorTextViewImpl(output);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, textView);

    controller.run(20);


    assertEquals("Output cannot be null", output.toString());

  }

  // Tests invalid Frames Per Second
  @Test(expected = IllegalArgumentException.class)
  public void invalidFPS() {

    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);

    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new MoveCommand(animateRectangle1, 30, 60,
                    25, 25);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorView textView = new EasyAnimatorTextViewImpl(output);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, textView);

    controller.run(0);


    assertEquals("Frames per second must be greater than 0", output.toString());

  }

  // Closed Output
  @Test(expected = IllegalStateException.class)
  public void closedOutput() {

    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    float[] deltaColor = {-1, 0, 1};
    Appendable output = new BufferedWriter(new StringWriter());
    try {
      ((BufferedWriter) output).close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    AnimatorShapeOperation move1 =
            new MoveCommand(animateRectangle1, 30, 60, 100, 100);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorView textView = new EasyAnimatorTextViewImpl(output);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, textView);

    controller.run(20);

  }

  // commands at same time
  @Test
  public void SameTimeCommands() {

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
            new MoveCommand(animateRectangle1, 30, 60,
                    -25, -25);
    AnimatorShapeOperation move2 =
            new ScaleCommand(animateRectangle1, 30, 60,
                    2, (float) .5);
    AnimatorShapeOperation move3 =
            new ColorChangeCommand(animateRectangle1, 30, 60,
                    deltaColor);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    moves.add(move2);
    moves.add(move3);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorView textView = new EasyAnimatorTextViewImpl(output);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, textView);

    controller.run(20);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    assertEquals("Name: Red Rectangle\n" +
            "Type: rectangle\n" +
            "Corner: (50.00, 50.00) Width: 50.00, Height: 50.00, Color: [r=1.00,g=0.00,b=0.00]\n" +
            "Appears at t = 0.0s Disappears at t = 5.0s\n" +
            "\n" +
            "Shape Red Rectangle moves from (50.00, 50.00) to (25.00, 25.00) from t = 1.0s" +
            " to t = 3.0s\n" +
            "Shape Red Rectangle scales from Width: 50.00, Height: 50.00 to Width: 100.00," +
            " Height: 25.00 from t = 1.0s to t = 3.0s\n" +
            "Shape Red Rectangle changes color from [r=1.00,g=0.00,b=0.00] to [r=0.00,g=0.00," +
            "b=1.00] from t = 1.0s to t = 3.0s\n", output.toString());

  }

  // Two Shapes
  @Test
  public void TwoShapes() {

    float[] blueArray = {0, 0, 1};
    MyColor blue = new MyColor(blueArray);
    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    Shape myOval = new Oval("Oval",
            new Posn(50, 50), blue, 100, 40);
    EasyAnimatorShape animateOval = new EasyAnimatorShape(myOval,
            50, 160, 1);
    float[] deltaColor = {-1, 0, 1};
    float[] deltaColor2 = {(float).4, (float).8, -1};
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new MoveCommand(animateRectangle1, 30, 60,
                    -25, -25);
    AnimatorShapeOperation move2 =
            new ScaleCommand(animateRectangle1, 30, 60,
                    2, (float) .5);
    AnimatorShapeOperation move3 =
            new ColorChangeCommand(animateRectangle1, 30, 60,
                    deltaColor);
    AnimatorShapeOperation move4 =
            new MoveCommand(animateOval, 60, 70,
                    25, -55);
    AnimatorShapeOperation move5 =
            new ScaleCommand(animateOval, 65, 90,
                    4, (float) .5);
    AnimatorShapeOperation move6 =
            new ColorChangeCommand(animateOval, 120, 160,
                    deltaColor2);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    moves.add(move2);
    moves.add(move3);
    moves.add(move4);
    moves.add(move5);
    moves.add(move6);
    shapes.add(animateRectangle1);
    shapes.add(animateOval);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorView textView = new EasyAnimatorTextViewImpl(output);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, textView);

    controller.run(20);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    assertEquals("Name: Red Rectangle\n" +
            "Type: rectangle\n" +
            "Corner: (50.00, 50.00) Width: 50.00, Height: 50.00, Color: [r=1.00,g=0.00,b=0.00]\n" +
            "Appears at t = 0.0s Disappears at t = 5.0s\n" +
            "\n" +
            "Name: Oval\n" +
            "Type: oval\n" +
            "Center: (50.00, 50.00) X Radius: 100.00, Y Radius: 40.00, Color: [r=0.00,g=0.00," +
            "b=1.00]\n" +
            "Appears at t = 2.0s Disappears at t = 8.0s\n" +
            "\n" +
            "Shape Red Rectangle moves from (50.00, 50.00) to (25.00, 25.00) from t = 1.0s to" +
            " t = 3.0s\n" +
            "Shape Red Rectangle scales from Width: 50.00, Height: 50.00 to Width: 100.00," +
            " Height: 25.00 from t = 1.0s to t = 3.0s\n" +
            "Shape Red Rectangle changes color from [r=1.00,g=0.00,b=0.00] to [r=0.00,g=0.00," +
            "b=1.00] from t = 1.0s to t = 3.0s\n" +
            "Shape Oval moves from (50.00, 50.00) to (75.00, -5.00) from t = 3.0s to t = 3.0s\n" +
            "Shape Oval scales from X Radius: 100.00, Y Radius: 40.00 to X Radius: 400.00, Y" +
            " Radius: 20.00 from t = 3.0s to t = 4.0s\n" +
            "Shape Oval changes color from [r=0.00,g=0.00,b=1.00] to [r=0.40,g=0.80,b=0.00]" +
            " from t = 6.0s to t = 8.0s\n", output.toString());

  }


}