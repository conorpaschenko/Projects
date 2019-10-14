import org.junit.Test;

import cs3500.animator.model.EasyAnimatorModel;

import cs3500.animator.model.EasyAnimatorModelImpl;

import cs3500.animator.model.EasyAnimatorShape;

import cs3500.animator.model.AnimatorShapeOperation;

import cs3500.animator.model.MoveCommand;

import cs3500.animator.model.ScaleCommand;

import cs3500.animator.model.ColorChangeCommand;

import cs3500.animator.model.Posn;

import cs3500.animator.model.Shape;

import cs3500.animator.model.Rectangle;

import cs3500.animator.model.Oval;

import cs3500.animator.model.MyColor;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class EasyAnimatorModelImplTest {
  float[] blueArray = {0, 0, 1};
  MyColor blue = new MyColor(blueArray);
  float[] redArray = {1, 0, 0};
  MyColor red = new MyColor(redArray);
  Shape myRectangle1 = new Rectangle("Red Rectangle",new Posn(50, 50), red, 50,
          50);
  Shape myOval = new Oval("C", new Posn(50, 50), blue, 4, 10);

  EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1,
          10, 110, 0);
  AnimatorShapeOperation move1 =
          new MoveCommand(animateRectangle1, 30, 60, 30, 30);
  AnimatorShapeOperation move2 =
          new MoveCommand(animateRectangle1, 30, 40, 100, 0);
  AnimatorShapeOperation move3 =
          new MoveCommand(animateRectangle1, 30, 60, -7, -2);


  // Checks the printDescription method on a model with two moves on the same shape
  @Test
  public void test1() {

    ArrayList<EasyAnimatorShape> shapesList = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    AnimatorShapeOperation mySecondMove =
            new MoveCommand(animateRectangle1, 60, 80, 20, 20);


    moves.add(move1);
    moves.add(mySecondMove);



    shapesList.add(animateRectangle1);
    EasyAnimatorModel model = new EasyAnimatorModelImpl(shapesList, moves);
    assertEquals(new Posn(65, 65), model.getGameState(45)[0].getLocation());
    assertEquals(new Posn(90, 90),  model.getGameState(70)[0].getLocation());
    assertEquals("Shapes:\n" +
            "Name: Red Rectangle\n" +
            "Type: rectangle\n" +
            "Corner: (50.00, 50.00) Width: 50.00, Height: 50.00, Color: [r=1.00,g=0.00,b=0.00]\n" +
            "Appears at t = 10 Disappears at t = 110\n" +
            "\n" +
            "Shape Red Rectangle moves from (50.00, 50.00) to " +
            "(80.00, 80.00) from t = 30 to t = 60\n" +
            "Shape Red Rectangle moves from (80.00, 80.00) to " +
            "(100.00, 100.00) from t = 60 to t = 80\n", model.printDescription());

    assertEquals("Shapes:\n" +
            "Name: Red Rectangle\n" +
            "Type: rectangle\n" +
            "Corner: (50.00, 50.00) Width: 50.00, Height: 50.00, Color: [r=1.00,g=0.00,b=0.00]\n" +
            "Appears at t = 5.0s Disappears at t = 55.0s\n" +
            "\n" +
            "Shape Red Rectangle moves from (50.00, 50.00) to (80.00, 80.00) from t = 15.0s to" +
            " t = 30.0s\n" +
            "Shape Red Rectangle moves from (80.00, 80.00) to (100.00, 100.00) from t = 30.0s to" +
            " t = 40.0s\n" , model.printDescription(2));



  }

  @Test
  public void test2() {
    ArrayList<EasyAnimatorShape> shapesList = new ArrayList<>();
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move2);
    Rectangle postRectangle = new Rectangle("Red Rectangle",new Posn(150, 50),
            red, 50, 50);
    EasyAnimatorShape postMove =
            new EasyAnimatorShape(postRectangle, 10, 110, 0 );
    shapesList.add(animateRectangle1);
    EasyAnimatorModel model = new EasyAnimatorModelImpl(shapesList, moves);
    for (int i = 40; i < 110; i++) {
      assertEquals(postMove.formatDescription(), model.getGameState(i)[0].formatDescription());
    }
    assertEquals("Name: Red Rectangle\n" +
                    "Type: rectangle\n" +
                    "Corner: (50.00, 50.00) Width: 50.00, Height: 50.00, Color: [r=1.00,g=0.00," +
                    "b=0.00]\n"
                    + "Appears at t = 10 Disappears at t = 110",
            model.getGameState(10)[0].formatDescription());
    assertEquals("Shapes:\n" + "Name: Red Rectangle\n" +
            "Type: rectangle\n" +
            "Corner: (50.00, 50.00) Width: 50.00, Height: 50.00, Color: [r=1.00,g=0.00,b=0.00]\n" +
            "Appears at t = 10 Disappears at t = 110\n" +
            "\n" + "Shape Red Rectangle moves from (50.00, 50.00) to " +
            "(150.00, 50.00) from t = 30 to " +
            "t = 40\n", model.printDescription());
    assertEquals(110, model.getTotalTime());
  }


  // Tests an average move
  @Test
  public void test3() {
    ArrayList<EasyAnimatorShape> shapesList = new ArrayList<>();


    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move1);

    shapesList.add(animateRectangle1);
    EasyAnimatorModel model = new EasyAnimatorModelImpl(shapesList, moves);

    assertEquals(new Posn(65, 65), model.getGameState(45)[0].getLocation());
    assertEquals( new Posn(80, 80), model.getGameState(60)[0].getLocation());
    assertEquals( new Posn(80, 80), model.getGameState(80)[0].getLocation());
  }

  // Tests a move in the negative directions
  @Test
  public void test4() {
    ArrayList<EasyAnimatorShape> shapesList = new ArrayList<>();


    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(move3);

    shapesList.add(animateRectangle1);
    EasyAnimatorModel model = new EasyAnimatorModelImpl(shapesList, moves);
    assertEquals(new Posn((float) 46.5, 49), model.getGameState(45)[0].getLocation());
    assertEquals(new Posn(43, 48), model.getGameState(60)[0].getLocation());
    assertEquals(new Posn(43, 48), model.getGameState(80)[0].getLocation());
  }

  // Tests the Ovals format method
  @Test
  public void test5() {
    EasyAnimatorShape animateOval = new EasyAnimatorShape(myOval,
            10, 110, 0);

    assertEquals("Name: C\n" +
            "Type: oval\n" +
            "Center: (50.00, 50.00) X Radius: 4.00, Y Radius: 10.00, Color: [r=0.00,g=0.00," +
            "b=1.00]\n" +
            "Appears at t = 10 Disappears at t = 110", animateOval.formatDescription());
  }

  // Tests the move command on a Oval
  @Test
  public void test6() {

    EasyAnimatorShape animateOval = new EasyAnimatorShape(myOval,
            0, 110, 0);
    animateOval.setLocation(new Posn(0,0));

    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    AnimatorShapeOperation ovalMove =
            new MoveCommand(animateOval, 0, 100, 100, 100);
    moves.add(ovalMove);
    ArrayList<EasyAnimatorShape> shapesList = new ArrayList<>();
    shapesList.add(animateOval);
    EasyAnimatorModel model = new EasyAnimatorModelImpl(shapesList, moves);

    assertEquals(new Posn(45, 45), model.getGameState(45)[0].getLocation());
    assertEquals(new Posn(100, 100), model.getGameState(100)[0].getLocation());
    assertEquals(new Posn(100, 100), model.getGameState(105)[0].getLocation());
  }

  //Tests to see that the description of the scale command formats correctly
  @Test
  public void test7() {
    EasyAnimatorShape animateOval = new EasyAnimatorShape(myOval,
            10, 110, 0);

    AnimatorShapeOperation ovalMove =
            new ScaleCommand(animateOval, 11, 20, (float) 0.5, (float) 0.5);
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(ovalMove);
    ArrayList<EasyAnimatorShape> shapesList = new ArrayList<>();
    shapesList.add(animateOval);
    EasyAnimatorModel model = new EasyAnimatorModelImpl(shapesList, moves);
    assertEquals("Name: C\n" +
                    "Type: oval\n" +
                    "Center: (50.00, 50.00) X Radius: 2.00, Y Radius: 5.00, Color: [r=0.00," +
                    "g=0.00,b=1.00]\n" +
                    "Appears at t = 10 Disappears at t = 110",
            model.getGameState(20)[0].formatDescription());

    assertEquals("Shapes:\n" +
                    "Name: C\n" +
                    "Type: oval\n" +
                    "Center: (50.00, 50.00) X Radius: 4.00, Y Radius: 10.00, Color: [r=0.00," +
                    "g=0.00,b=1.00]\n" +
                    "Appears at t = 10 Disappears at t = 110\n" +
                    "\n" +
                    "Shape C scales from X Radius: 4.00, Y Radius: 10.00 to X Radius: 2.00," +
                    " Y Radius: 5.00 from t = 11 to t = 20\n",
            model.printDescription());
  }

  // Tests a scale factor command on a rectangle
  @Test
  public void test8() {
    ArrayList<EasyAnimatorShape> shapesList = new ArrayList<>();

    AnimatorShapeOperation scaleRectangle =
            new ScaleCommand(animateRectangle1, 11, 20, 2,
                    2);
    AnimatorShapeOperation scaleRectangle2 =
            new ScaleCommand(animateRectangle1, 21, 30, (float) 0.5 ,
                    (float) 0.5);
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(scaleRectangle);
    moves.add(scaleRectangle2);

    shapesList.add(animateRectangle1);
    EasyAnimatorModel model1 = new EasyAnimatorModelImpl(shapesList, moves);

    assertEquals("Width: 50.00, Height: 50.00",
            model1.getGameState(10)[0].getShape().formatDimensions());
    assertEquals("Width: 100.00, Height: 100.00",
            model1.getGameState(20)[0].getShape().formatDimensions());
    assertEquals("Width: 100.00, Height: 100.00",
            model1.getGameState(20)[0].getShape().formatDimensions());
    assertEquals("Width: 50.00, Height: 50.00",
            model1.getGameState(30)[0].getShape().formatDimensions());
    assertEquals("Shapes:\n" +
                    "Name: Red Rectangle\n" +
                    "Type: rectangle\n" +
                    "Corner: (50.00, 50.00) Width: 50.00, Height: 50.00, Color: [r=1.00,g=0.00," +
                    "b=0.00]\n" +
                    "Appears at t = 10 Disappears at t = 110\n" +
                    "\n" +
                    "Shape Red Rectangle scales from Width: 50.00, Height: 50.00 to " +
                    "Width: 100.00, Height: " +
                    "100.00 from t = 11 to t = 20\n" +
                    "Shape Red Rectangle scales from Width: 100.00, Height: 100.00 to " +
                    "Width: 50.00, Height: 50.00 from t = 21 to t = 30\n",
            model1.printDescription());
    assertEquals("Name: Red Rectangle\n" +
                    "Type: rectangle\n" +
                    "Corner: (50.00, 50.00) Width: 100.00, Height: 100.00, Color: [r=1.00,g=0.00," +
                    "b=0.00]\n"
                    + "Appears at t = 10 Disappears at t = 110",
            model1.getGameState(20)[0].formatDescription());
  }


  // Tests to see two sequential scale moves that counteract each other, result in the initial shape
  @Test
  public void test9() {

    ArrayList<EasyAnimatorShape> shapesList = new ArrayList<>();

    AnimatorShapeOperation scaleRectangle =
            new ScaleCommand(animateRectangle1, 11, 20, 2,
                    2);
    AnimatorShapeOperation scaleRectangle2 =
            new ScaleCommand(animateRectangle1, 21, 29, (float) 0.5, (float) 0.5);
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(scaleRectangle);
    moves.add(scaleRectangle2);

    shapesList.add(animateRectangle1);
    EasyAnimatorModel model1 = new EasyAnimatorModelImpl(shapesList, moves);


    assertEquals("Name: Red Rectangle\n" +
                    "Type: rectangle\n" +
                    "Corner: (50.00, 50.00) Width: 50.00, Height: 50.00, Color: [r=1.00,g=0.00," +
                    "b=0.00]\n"
                    +  "Appears at t = 10 Disappears at t = 110",
            model1.getGameState(30)[0].formatDescription());
    assertEquals("Shapes:\n" +
                    "Name: Red Rectangle\n" +
                    "Type: rectangle\n" +
                    "Corner: (50.00, 50.00) Width: 50.00, Height: 50.00, Color: [r=1.00,g=0.00," +
                    "b=0.00]\n" +
                    "Appears at t = 10 Disappears at t = 110\n" +
                    "\n" +
                    "Shape Red Rectangle scales from Width: 50.00, Height: " +
                    "50.00 to Width: 100.00, Height: " +
                    "100.00 from t = 11 to t = 20\n" +
                    "Shape Red Rectangle scales from Width: 100.00, Height: 100.00 to " +
                    "Width: 50.00, Height: 50.00 from t = 21 to t = 29\n",
            model1.printDescription());
  }

  // Tests to see that two moves can happen at the same time and result in the desired
  // animation
  @Test
  public void test10() {
    ArrayList<EasyAnimatorShape> shapesList = new ArrayList<>();
    shapesList.add(animateRectangle1);

    AnimatorShapeOperation scaleRectangle =
            new ScaleCommand(animateRectangle1, 30, 60, 2,
                    2);
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(scaleRectangle);
    moves.add(move1);
    EasyAnimatorModel model = new EasyAnimatorModelImpl(shapesList, moves);
    assertEquals(new Posn(65, 65), model.getGameState(45)[0].getLocation());
    assertEquals("Width: 69.10, Height: 69.10",
            model.getGameState(44)[0].getShape().formatDimensions());
    assertEquals("Name: Red Rectangle\n" +
                    "Type: rectangle\n" +
                    "Corner: (80.00, 80.00) Width: 100.00, Height: 100.00, Color: [r=1.00,g=0.00," +
                    "b=0.00]\n"
                    + "Appears at t = 10 Disappears at t = 110",
            model.getGameState(60)[0].formatDescription());
    for (int i = 60 ; i < 110 ; i++ ) {
      assertEquals("Name: Red Rectangle\n" +
                      "Type: rectangle\n" +
                      "Corner: (80.00, 80.00) Width: 100.00, Height: 100.00, Color: [r=1.00," +
                      "g=0.00,b=0.00]\n"
                      + "Appears at t = 10 Disappears at t = 110",
              model.getGameState(i)[0].formatDescription());
    }
    assertEquals("Shapes:\n" +
            "Name: Red Rectangle\n" +
            "Type: rectangle\n" +
            "Corner: (50.00, 50.00) Width: 50.00, Height: 50.00, Color: [r=1.00,g=0.00,b=0.00]\n" +
            "Appears at t = 10 Disappears at t = 110\n" +
            "\n" +
            "Shape Red Rectangle scales from Width: 50.00," +
            " Height: 50.00 to " +
            "Width: 100.00, Height: 100.00 from t = 30 to t = 60\n" +
            "Shape Red Rectangle moves from (50.00, 50.00) to (80.00, 80.00) " +
            "from t = 30 to t = 60\n", model.printDescription());
  }

  // Tests the Color Change Command
  @Test
  public void test11() {
    ArrayList<EasyAnimatorShape> shapesList = new ArrayList<>();
    shapesList.add(animateRectangle1);
    float[] redToBlue = {-1, 0 ,1};
    AnimatorShapeOperation changeToBlue =
            new ColorChangeCommand(animateRectangle1, 30, 60, redToBlue);
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(changeToBlue);
    EasyAnimatorModel model = new EasyAnimatorModelImpl(shapesList, moves);
    assertEquals("Shapes:\n" +
            "Name: Red Rectangle\n" +
            "Type: rectangle\n" +
            "Corner: (50.00, 50.00) Width: 50.00, Height: 50.00, Color: [r=1.00,g=0.00,b=0.00]\n" +
            "Appears at t = 10 Disappears at t = 110\n" +
            "\n" +
            "Shape Red Rectangle changes color from [r=1.00,g=0.00,b=0.00] to [r=0.00,g=0.00," +
            "b=1.00] " +
            "from t = 30 to t = 60\n", model.printDescription());


    assertEquals("Name: Red Rectangle\n" +
                    "Type: rectangle\n" +
                    "Corner: (50.00, 50.00) Width: 50.00, Height: 50.00, Color: [r=0.50,g=0.00," +
                    "b=0.50]\n"
                    + "Appears at t = 10 Disappears at t = 110",
            model.getGameState(45)[0].formatDescription());
  }


  // Tests to make sure that the EasyAnimateShape id is computing correctly
  @Test
  public void test12() {
    EasyAnimatorShape animateOval = new EasyAnimatorShape(myOval,
            10, 110, 1);
    ArrayList<EasyAnimatorShape> shapesList = new ArrayList<>();
    shapesList.add(animateOval);
    shapesList.add(animateRectangle1);
    float[] redToBlue = {-1, 0 ,1};
    AnimatorShapeOperation changeToBlue =
            new ColorChangeCommand(animateRectangle1, 30, 60, redToBlue);
    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(changeToBlue);
    EasyAnimatorModel model = new EasyAnimatorModelImpl(shapesList, moves);

    assertEquals("Shapes:\n" +
            "Name: C\n" +
            "Type: oval\n" +
            "Center: (50.00, 50.00) X Radius: 4.00, Y Radius: 10.00, Color: [r=0.00,g=0.00," +
            "b=1.00]\n" +
            "Appears at t = 10 Disappears at t = 110\n" +
            "\n" +
            "Name: Red Rectangle\n" +
            "Type: rectangle\n" +
            "Corner: (50.00, 50.00) Width: 50.00, Height: 50.00, Color: [r=1.00,g=0.00,b=0.00]\n" +
            "Appears at t = 10 Disappears at t = 110\n" +
            "\n" +
            "Shape Red Rectangle changes color from [r=1.00,g=0.00,b=0.00] to [r=0.00,g=0.00," +
            "b=1.00]" +
            " from t = 30 to t = 60\n", model.printDescription());
    for (int i = 10 ; i < 110; i++) {
      assertEquals(animateOval.formatDescription(),
              model.getGameState(i)[1].formatDescription());
    }
    assertEquals("Name: Red Rectangle\n" +
                    "Type: rectangle\n" +
                    "Corner: (50.00, 50.00) Width: 50.00, Height: 50.00, " +
                    "Color: [r=0.00,g=0.00,b=1.00]\n"
                    + "Appears at t = 10 Disappears at t = 110",
            model.getGameState(100)[0].formatDescription());
  }




  // Tests to see that an exception is thrown when a command that has an earlier startTime than it's
  // EasyAnimatorShape's appearTime is initialized
  @Test(expected =  IllegalArgumentException.class)
  public void exceptionTest1() {

    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1, 10,
            110, 0);
    ArrayList<EasyAnimatorShape> shapesList = new ArrayList<>();
    AnimatorShapeOperation simpleMove =
            new MoveCommand(animateRectangle1, 5, 25, 10, 10);

    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(simpleMove);

    shapesList.add(animateRectangle1);
    EasyAnimatorModel model = new EasyAnimatorModelImpl(shapesList, moves);

  }


  // Tests to see that an exception is thrown when a command that has an later endTime than it's
  // EasyAnimatorShape's disappearTime is initialized
  @Test(expected =  IllegalArgumentException.class)
  public void exceptionTest2() {

    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1, 10,
            110, 0);
    ArrayList<EasyAnimatorShape> shapesList = new ArrayList<>();
    AnimatorShapeOperation simpleMove =
            new MoveCommand(animateRectangle1, 15, 150, 10, 10);

    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(simpleMove);

    shapesList.add(animateRectangle1);
    EasyAnimatorModel model = new EasyAnimatorModelImpl(shapesList, moves);
  }

  // Tests to see that an exception is thrown when two contradicting commands are placed on the
  // same EasyAnimateShape
  @Test(expected =  IllegalArgumentException.class)
  public void exceptionTest3() {

    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1, 10,
            110, 0);
    ArrayList<EasyAnimatorShape> shapesList = new ArrayList<>();
    AnimatorShapeOperation contradictingMove1 =
            new MoveCommand(animateRectangle1, 15, 80, 10, 10);
    AnimatorShapeOperation contradictingMove2 =
            new MoveCommand(animateRectangle1, 50, 55, 20, 10);


    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(contradictingMove1);
    moves.add(contradictingMove2);

    shapesList.add(animateRectangle1);
    EasyAnimatorModel model = new EasyAnimatorModelImpl(shapesList, moves);
  }

  // Tests to make sure that the getGameState methods throws an exception if the given time
  // is out of bounds
  @Test(expected = IllegalArgumentException.class)
  public void exceptionTest4() {

    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1, 10,
            110, 0);
    ArrayList<EasyAnimatorShape> shapesList = new ArrayList<>();
    AnimatorShapeOperation simpleMove =
            new MoveCommand(animateRectangle1, 15, 25, 10, 10);

    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(simpleMove);

    shapesList.add(animateRectangle1);
    EasyAnimatorModel model = new EasyAnimatorModelImpl(shapesList, moves);

    model.getGameState(-1);
  }


  // Tests to make sure that the getGameState methods throws an exception if the given time
  // is out of bounds
  @Test(expected = IllegalArgumentException.class)
  public void exceptionTest5() {

    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1, 10,
            110,0);
    ArrayList<EasyAnimatorShape> shapesList = new ArrayList<>();
    AnimatorShapeOperation simpleMove =
            new MoveCommand(animateRectangle1, 15, 25, 10, 10);

    ArrayList<AnimatorShapeOperation> moves = new ArrayList<>();
    moves.add(simpleMove);

    shapesList.add(animateRectangle1);
    EasyAnimatorModel model = new EasyAnimatorModelImpl(shapesList, moves);

    model.getGameState(111);
  }

  //Makes sure that an exception is thrown when initializing a command with an illegal start or end
  // time
  @Test(expected = IllegalArgumentException.class)
  public void exceptionTest6() {
    EasyAnimatorShape animateRectangle1 = new EasyAnimatorShape(myRectangle1, 10,
            110,0);
    AnimatorShapeOperation illegalMove =
            new MoveCommand(animateRectangle1, 15, 14, 10, 10);
  }

}