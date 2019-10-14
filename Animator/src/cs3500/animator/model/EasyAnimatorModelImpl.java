package cs3500.animator.model;

import java.util.ArrayList;

import java.util.Arrays;

import cs3500.animator.util.TweenModelBuilder;

/**
 * An implementation of the model for an animation application. Stores data of EasyAnimateShapes
 * at every instance of time throughout the total animation. The total time is the value of the
 * greatest disappearTime of all the EasyAnimateShapes involved. In the spaceTime field, every
 * EasyAnimateShape is stored in the index of its corresponding time as the variation of the shape
 * caused by the animation up to that point.
 */
public class EasyAnimatorModelImpl implements EasyAnimatorModel {
  private  EasyAnimatorShape[][] spaceTime;
  private  ArrayList<EasyAnimatorShape> shapes;
  private  ArrayList<AnimatorShapeOperation> commands;
  private int totalTime;

  /**
   * A builder method for an EasyAnimatorModelImpl. Uses the builder pattern to create an instance
   * of the model.
   */
  public static class Builder implements TweenModelBuilder<EasyAnimatorModel> {
    ArrayList<EasyAnimatorShape> shapes;
    ArrayList<AnimatorShapeOperation> commands;

    public Builder() {
      this.shapes = new ArrayList<>();
      this.commands = new ArrayList<>();
    }

    @Override
    public TweenModelBuilder addOval(String name, float cx, float cy, float xRadius, float yRadius,
                                     float red, float green, float blue, int startOfLife,
                                     int endOfLife, int layer) {
      float[] colorArray = new float[]{red, green, blue};
      MyColor newColor = new MyColor(colorArray);
      Oval newOval = new Oval(name,new Posn(cx, cy), newColor, xRadius, yRadius);
      EasyAnimatorShape newAnimateShape =
              new EasyAnimatorShape(newOval,
                      startOfLife, endOfLife, new ArrayList<>(),
                      shapes.size(), 0, layer);
      this.shapes.add(newAnimateShape);
      return this;
    }

    @Override
    public TweenModelBuilder addRectangle(String name, float lx, float ly, float width,
                                          float height, float red, float green, float blue,
                                          int startOfLife, int endOfLife, int layer) {
      float[] colorArray = new float[]{red, green, blue};
      MyColor newColor = new MyColor(colorArray);
      Rectangle newRectangle = new Rectangle(name, new Posn(lx, ly) ,newColor, width, height);
      EasyAnimatorShape newAnimateShape =
              new EasyAnimatorShape(newRectangle,startOfLife, endOfLife,
                      new ArrayList<>(), shapes.size(), 0, layer);
      this.shapes.add(newAnimateShape);
      return this;
    }

    //****** MoveFrom coords?
    @Override
    public TweenModelBuilder addMove(String name, float moveFromX, float moveFromY, float moveToX,
                                     float moveToY, int startTime, int endTime) {
      for (EasyAnimatorShape shape : shapes ) {
        if (shape.getShape().printName().equals(name)) {
          MoveCommand newMove =
                  new MoveCommand(shape, startTime, endTime,  moveToX - moveFromX,
                          moveToY - moveFromY);
          commands.add(newMove);
          break;
        }
      }
      return this;
    }

    @Override
    public TweenModelBuilder addColorChange(String name, float oldR, float oldG, float oldB,
                                            float newR, float newG, float newB, int startTime,
                                            int endTime) {
      for (EasyAnimatorShape shape : shapes ) {
        if (shape.getShape().printName().equals(name)) {
          float[] deltaColor = {newR - oldR, newG - oldG, newB - oldB};
          ColorChangeCommand newColorChange =
                  new ColorChangeCommand(shape, startTime, endTime, deltaColor );
          commands.add(newColorChange);
          break;
        }
      }
      return this;
    }

    @Override
    public TweenModelBuilder addScaleToChange(String name, float fromSx, float fromSy, float toSx,
                                              float toSy, int startTime, int endTime) {
      for (EasyAnimatorShape shape : shapes ) {
        if (shape.getShape().printName().equals(name)) {
          ScaleCommand newColorChange =
                  new ScaleCommand(shape, startTime, endTime, toSx / fromSx,
                          toSy / fromSy);
          commands.add(newColorChange);
          break;
        }
      }
      return this;
    }


    @Override
    public TweenModelBuilder addRotateChange(String name, int startTime, int endTime, int degree) {
      for (EasyAnimatorShape shape : shapes ) {
        if (shape.getShape().printName().equals(name)) {
          RotationOperation rotateOperation =
                  new RotationOperation(shape, startTime, endTime, degree);
          commands.add(rotateOperation);
          break;
        }
      }
      return this;
    }


    @Override
    public EasyAnimatorModel build() {
      return new EasyAnimatorModelImpl(this.shapes, this.commands);
    }
  }

  /**
   * Initializes the model implementation.
   * Initializes the spaceTime array according the contents of the shapes Array and the commands
   * Array.
   * @param shapes the list of shapes involved in the animation
   * @param commands the list commands acting upon the shapes
   * @throws IllegalArgumentException if any of the commands in the commands argument contradict
   *                        each other.
   */
  public EasyAnimatorModelImpl(ArrayList<EasyAnimatorShape> shapes,
                               ArrayList<AnimatorShapeOperation> commands)
          throws IllegalArgumentException {
    this.shapes = shapes;
    AnimatorShapeOperation[] commandsArray = new AnimatorShapeOperation[commands.size()];
    for (int i = 0 ; i < commands.size(); i++) {
      commandsArray[i] = commands.get(i);
    }
    commandsArray = sortCommands(commandsArray);
    this.commands = new ArrayList<>(Arrays.asList(commandsArray));
    this.addCommandsToShapes();
    if (this.checkCommands()) {
      throw new IllegalArgumentException("At least one illegal command was entered");
    }
    this.totalTime = this.getTotalTime();
    this.spaceTime = new EasyAnimatorShape[this.totalTime + 1][shapes.size()];
    this.initSpaceTime();
  }


  @Override
  public String printDescription() {
    String description = "Shapes:\n";
    for (EasyAnimatorShape shape : shapes) {
      description += this.spaceTime[shape.getAppearTime()][shape.getID()].formatDescription()
              + "\n\n";
    }
    for (AnimatorShapeOperation command : commands) {
      description += command.formatCommand() + "\n";
    }
    return  description;
  }

  @Override
  public String printDescription(int framesPerSecond) {
    String description = "Shapes:\n";
    for (EasyAnimatorShape shape : shapes) {
      description +=
              this.spaceTime[shape.getAppearTime()][shape.getID()]
                      .formatDescription(framesPerSecond)
              + "\n\n";
    }
    for (AnimatorShapeOperation command : commands) {
      description += command.formatCommand(framesPerSecond)
              + "\n";
    }
    return description;
  }

  @Override
  public EasyAnimatorShape[] getGameState(int t) throws IllegalArgumentException {
    if (t > totalTime  || t < 0) {
      throw new IllegalArgumentException("Time out of bounds");
    }
    return spaceTime[t];
  }

  @Override
  public int getTotalTime() {
    int lastDisappearTime = 0;
    for (EasyAnimatorShape shape : this.shapes) {

      if (shape.getDisappearTime() > lastDisappearTime) {
        lastDisappearTime = shape.getDisappearTime();
      }
    }
    return lastDisappearTime;
  }


  /**
   * Initializes the model representation as a 2D array.
   * The first array represents the total time.
   * Each array corresponding to a specific time represents the state of all shapes in the model
   * at that exact time.
   */
  private void initSpaceTime() {
    for (EasyAnimatorShape shape : shapes) {
      for (int i = shape.getAppearTime(); i < shape.getDisappearTime() + 1; i++) {
        spaceTime[i][shape.getID()] = shape.copy();
      }
    }
    for (AnimatorShapeOperation command : commands) {
      for (int i = command.getStartTime();
           i < command.getAnimateShape().getDisappearTime() + 1; i++) {
        if (i > command.getEndTime()) {
          EasyAnimatorShape preShape = spaceTime[i][command.getAnimateShape().getID()].copy();
          command.setShape(preShape);
          spaceTime[i][command.getAnimateShape().getID()] =
                  spaceTime[command.getEndTime()][command.getAnimateShape().getID()].copy();
          if (command.getAnimateShape().hasRotationAt(i)) {
            spaceTime[i][command.getAnimateShape().getID()].setRotationDegree(preShape.getRotationDegree());
          }
        }
        else {
          command.setShape(spaceTime[i][command.getAnimateShape().getID()].copy());
          command.increment(i);
          spaceTime[i][command.getAnimateShape().getID()] = command.getAnimateShape().copy();
        }
      }
    }
  }

  /**
   * Checks that there are no two contradictory commands within the model.
   * Two commands are contradictory when they are of the same command type, and their execution
   * periods have overlap.
   * @throws IllegalArgumentException if there are  contradicting commands
   */
  private boolean checkCommands() throws IllegalArgumentException {
    boolean invalidity = false;
    for (EasyAnimatorShape shape : shapes) {
      if (shape.validateCommands()) {
        invalidity = true;
      }
    }
    return invalidity;
  }

  /**
   * For every shape, every command that corresponds to that shape will be added to it's
   * commands field.
   */
  private void addCommandsToShapes() {
    for (EasyAnimatorShape shape : shapes) {
      for (AnimatorShapeOperation command : commands) {
        if (command.getAnimateShape().equals(shape)) {
          shape.getCommands().add(command);
        }
      }
    }
  }

  /**
   * Sorts the given commands using insertion sort.
   * @param commands the commands to be sorted
   * @return the sorted list of commands
   */
  public static AnimatorShapeOperation[] sortCommands(AnimatorShapeOperation[] commands) {
    int k = commands.length - 1;
    AnimatorShapeOperation reverse;
    ArrayList<AnimatorShapeOperation> rotateOperations = new ArrayList<>();
    for (int i = 1; i < commands.length; i++) {
      for (int j = i; j > 0; j--) {
        if (commands[j].getStartTime() < commands[j - 1].getStartTime()) {
            reverse = commands[j];
            commands[j] = commands[j - 1];
            commands[j - 1] = reverse;
        }
      }
    }
    return commands;
  }








}
