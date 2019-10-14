

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
import cs3500.animator.view.EasyAnimatorSVGViewImpl;


import cs3500.animator.view.EasyAnimatorView;

import static org.junit.Assert.assertEquals;

public class EasyAnimatorSVGViewImplTest {

  // Empty Model
  @Test
  public void EmptyModel() {


    Appendable output = new StringWriter();
    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorView svgView = new EasyAnimatorSVGViewImpl(output, 1000, 1000);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, svgView);

    controller.run(20);


    assertEquals("<svg width=\"1000\" height=\"1000\" version=\"1.1\"\n" +
            "     xmlns=\"http://www.w3.org/2000/svg\"></svg>", output.toString());

  }

  // Null output
  @Test(expected = IllegalArgumentException.class)
  public void NullOutput() {

    float[] blueArray = {0, 0, 1};
    MyColor blue = new MyColor(blueArray);
    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    float[] deltaColor = {-1, 0, 1};
    Appendable output = null;

    AnimatorShapeOperation move1 =
            new ColorChangeCommand(animateRectangle1, 30, 60, deltaColor);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorView svgView = new EasyAnimatorSVGViewImpl(output, 1000, 1000);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, svgView);

    controller.run(20);


    assertEquals("Output cannot be null", output.toString());

  }

  // Invalid Width
  @Test(expected = IllegalArgumentException.class)
  public void InvalidWidth() {

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
    EasyAnimatorView svgView = new EasyAnimatorSVGViewImpl(output, -1000, -1000);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, svgView);

    controller.run(20);


    assertEquals("Width and height have to be greater than 0.", output.toString());

  }

  // Invalid Height
  @Test(expected = IllegalArgumentException.class)
  public void IvalidHeight() {

    float[] blueArray = {0, 0, 1};
    MyColor blue = new MyColor(blueArray);
    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    float[] deltaColor = {-1, 0, 1};
    Appendable output = null;

    AnimatorShapeOperation move1 =
            new ColorChangeCommand(animateRectangle1, 30, 60, deltaColor);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorView sVGView = new EasyAnimatorSVGViewImpl(output, 1000, 1000);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, sVGView);

    controller.run(20);


    assertEquals("Width and height have to be greater than 0.", output.toString());

  }

  // Change Color
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
    EasyAnimatorView sVGView = new EasyAnimatorSVGViewImpl(output, 1000, 1000);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, sVGView);

    controller.run(20);


    assertEquals("<svg width=\"1000\" height=\"1000\" version=\"1.1\"\n" +
            "     xmlns=\"http://www.w3.org/2000/svg\"><rect id=\"Red Rectangle\" x=\"50.0\"" +
            " y=\"50.0\" width=\"50.0\" height=\"50.0\" fill=\"rgb(255,0,0)\" visibility=\"" +
            "hidden\" >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"XML\" to=\"visible\" begin=\"" +
            "500ms\" dur=\"5000ms\" fill=\"freeze\" />\n" +
            "    <animate attributeName=\"fill\" attributeType=\"XML\" from=\"rgb(255,0,0)\"" +
            "  to=\"rgb(0,0,255)\" begin=\"1500ms\" dur=\"1500ms\" fill=\"freeze\"/>\n" +
            "</rect></svg>", output.toString());

  }

  // Scale Up
  @Test
  public void ScaleUp() {

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
            new ScaleCommand(animateRectangle1, 30, 60, 2,
                    2);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorView sVGView = new EasyAnimatorSVGViewImpl(output, 1000, 1000);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, sVGView);

    controller.run(20);


    assertEquals("<svg width=\"1000\" height=\"1000\" version=\"1.1\"\n" +
            "     xmlns=\"http://www.w3.org/2000/svg\"><rect id=\"Red Rectangle\" x=\"50.0\"" +
            " y=\"50.0\" width=\"50.0\" height=\"50.0\" fill=\"rgb(255,0,0)\" visibility=\"" +
            "hidden\" >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"XML\" to=\"visible\" begin=\"" +
            "500ms\" dur=\"5000ms\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"1500ms\" dur=\"1500ms\" attributeName=\"" +
            "width\" from=\"50.0\" to=\"100.0\" fill = \"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1500ms\" dur=\"1500ms\" attributeName=\"" +
            "height\" from=\"50.0\" to=\"100.0\" fill =\"freeze\" />\n" +
            "\n" +
            "</rect></svg>", output.toString());

  }

  // Scale Down
  @Test
  public void ScaleDown() {

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
            new ScaleCommand(animateRectangle1, 30, 60, (float) .5,
                    (float) .5);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorView sVGView = new EasyAnimatorSVGViewImpl(output, 1000, 1000);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, sVGView);

    controller.run(20);

    assertEquals("<svg width=\"1000\" height=\"1000\" version=\"1.1\"\n" +
            "     xmlns=\"http://www.w3.org/2000/svg\"><rect id=\"Red Rectangle\" x=\"50.0\"" +
            " y=\"50.0\" width=\"50.0\" height=\"50.0\" fill=\"rgb(255,0,0)\" visibility=\"" +
            "hidden\" >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"XML\" to=\"visible\" begin=\"" +
            "500ms\" dur=\"5000ms\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"1500ms\" dur=\"1500ms\" attributeName=\"" +
            "width\" from=\"50.0\" to=\"25.0\" fill = \"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1500ms\" dur=\"1500ms\" attributeName=\"" +
            "height\" from=\"50.0\" to=\"25.0\" fill =\"freeze\" />\n" +
            "\n" +
            "</rect></svg>", output.toString());

  }


  // Move Up
  @Test
  public void MoveUp() {

    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    float[] deltaColor = {-1, 0, 1};
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new MoveCommand(animateRectangle1, 30, 60, 100, 100);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorView sVGView = new EasyAnimatorSVGViewImpl(output, 1000, 1000);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, sVGView);

    controller.run(20);


    assertEquals("<svg width=\"1000\" height=\"1000\" version=\"1.1\"\n" +
            "     xmlns=\"http://www.w3.org/2000/svg\"><rect id=\"Red Rectangle\" x=\"50.0\"" +
            " y=\"50.0\" width=\"50.0\" height=\"50.0\" fill=\"rgb(255,0,0)\" visibility=\"" +
            "hidden\" >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"XML\" to=\"visible\" begin=\"" +
            "500ms\" dur=\"5000ms\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"1500ms\" dur=\"1500ms\" attributeName=\"" +
            "x\" from=\"50.0\" to=\"150.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1500ms\" dur=\"1500ms\" attributeName=\"y\"" +
            " from=\"50.0\" to=\"150.0\" fill=\"freeze\" />\n" +
            "</rect></svg>", output.toString());

  }

  // Move Down
  @Test
  public void MoveDown() {

    float[] redArray = {1, 0, 0};
    MyColor red = new MyColor(redArray);
    Shape myRectangle1 = new Rectangle("Red Rectangle",
            new Posn(50, 50), red, 50, 50);
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
            10, 110, 0);
    float[] deltaColor = {-1, 0, 1};
    Appendable output = new StringWriter();

    AnimatorShapeOperation move1 =
            new MoveCommand(animateRectangle1, 30, 60, -25, -25);

    ArrayList<EasyAnimatorShape> shapes = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);
    shapes.add(animateRectangle1);

    EasyAnimatorModel myModel = new EasyAnimatorModelImpl(shapes, moves);
    EasyAnimatorView sVGView = new EasyAnimatorSVGViewImpl(output, 1000, 1000);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, sVGView);

    controller.run(20);

    assertEquals("<svg width=\"1000\" height=\"1000\" version=\"1.1\"\n" +
            "     xmlns=\"http://www.w3.org/2000/svg\"><rect id=\"Red Rectangle\" x=\"50.0\"" +
            " y=\"50.0\" width=\"50.0\" height=\"50.0\" fill=\"rgb(255,0,0)\" visibility" +
            "=\"hidden\" >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"XML\" to=\"visible\" begin=\"" +
            "500ms\" dur=\"5000ms\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"1500ms\" dur=\"1500ms\" attributeName=\"" +
            "x\" from=\"50.0\" to=\"25.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1500ms\" dur=\"1500ms\" attributeName=\"y\"" +
            " from=\"50.0\" to=\"25.0\" fill=\"freeze\" />\n" +
            "</rect></svg>", output.toString());

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
    EasyAnimatorView sVGView = new EasyAnimatorSVGViewImpl(output, 1000, 1000);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, sVGView);

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
    EasyAnimatorView sVGView = new EasyAnimatorSVGViewImpl(output, 1000, 1000);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, sVGView);

    controller.run(20);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    assertEquals("<svg width=\"1000\" height=\"1000\" version=\"1.1\"\n" +
            "     xmlns=\"http://www.w3.org/2000/svg\"><rect id=\"Red Rectangle\" x=\"50.0\"" +
            " y=\"50.0\" width=\"50.0\" height=\"50.0\" fill=\"rgb(255,0,0)\" visibility=\"" +
            "hidden\" >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"XML\" to=\"visible\" begin=\"" +
            "500ms\" dur=\"5000ms\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"1500ms\" dur=\"1500ms\" attributeName=" +
            "\"x\" from=\"50.0\" to=\"25.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1500ms\" dur=\"1500ms\" attributeName=\"y\"" +
            " from=\"50.0\" to=\"25.0\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"1500ms\" dur=\"1500ms\" attributeName=\"" +
            "width\" from=\"50.0\" to=\"100.0\" fill = \"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1500ms\" dur=\"1500ms\" attributeName=\"" +
            "height\" from=\"50.0\" to=\"25.0\" fill =\"freeze\" />\n" +
            "\n" +
            "    <animate attributeName=\"fill\" attributeType=\"XML\" from=\"rgb(255,0,0)\"  " +
            "to=\"rgb(0,0,255)\" begin=\"1500ms\" dur=\"1500ms\" fill=\"freeze\"/>\n" +
            "</rect></svg>", output.toString());

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
    EasyAnimatorView sVGView = new EasyAnimatorSVGViewImpl(output, 1000, 1000);

    EasyAnimatorController controller = new EasyAnimatorControllerTextImpl(myModel, sVGView);

    controller.run(20);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    assertEquals("<svg width=\"1000\" height=\"1000\" version=\"1.1\"\n" +
            "     xmlns=\"http://www.w3.org/2000/svg\"><rect id=\"Red Rectangle\" x=\"50.0\"" +
            " y=\"50.0\" width=\"50.0\" height=\"50.0\" fill=\"rgb(255,0,0)\" visibility=\"" +
            "hidden\" >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"XML\" to=\"visible\" begin=\"" +
            "500ms\" dur=\"5000ms\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"1500ms\" dur=\"1500ms\" attributeName=\"" +
            "x\" from=\"50.0\" to=\"25.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1500ms\" dur=\"1500ms\" attributeName=\"y\"" +
            " from=\"50.0\" to=\"25.0\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"1500ms\" dur=\"1500ms\" attributeName=\"" +
            "width\" from=\"50.0\" to=\"100.0\" fill = \"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1500ms\" dur=\"1500ms\" attributeName=\"" +
            "height\" from=\"50.0\" to=\"25.0\" fill =\"freeze\" />\n" +
            "\n" +
            "    <animate attributeName=\"fill\" attributeType=\"XML\" from=\"rgb(255,0,0)\"  " +
            "to=\"rgb(0,0,255)\" begin=\"1500ms\" dur=\"1500ms\" fill=\"freeze\"/>\n" +
            "</rect><ellipse id=\"Oval\" cx=\"50.0\" cy=\"50.0\" rx=\"100.0\" ry=\"40.0\" " +
            "fill=\"rgb(0,0,255)\" visibility=\"hidden\" >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"XML\" to=\"visible\" begin=\"" +
            "2500ms\" dur=\"5500ms\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"3000ms\" dur=\"500ms\" attributeName=\"" +
            "cx\" from=\"50.0\" to=\"75.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"3000ms\" dur=\"500ms\" attributeName=\"cy\"" +
            " from=\"50.0\" to=\"-5.0\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"3250ms\" dur=\"1250ms\" attributeName=\"" +
            "rx\" from=\"100.0\" to=\"400.0\" fill = \"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"3250ms\" dur=\"1250ms\" attributeName=\"ry\"" +
            " from=\"40.0\" to=\"20.0\" fill =\"freeze\" />\n" +
            "\n" +
            "    <animate attributeName=\"fill\" attributeType=\"XML\" from=\"rgb(0,0,255)\"  " +
            "to=\"rgb(102,204,0)\" begin=\"6000ms\" dur=\"2000ms\" fill=\"freeze\"/>\n" +
            "</ellipse></svg>", output.toString());

  }


}