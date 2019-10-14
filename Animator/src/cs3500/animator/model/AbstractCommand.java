package cs3500.animator.model;

import java.util.ArrayList;


/**
 * An abstract class for a command class.
 * Provides useful helper functions
 */
public abstract class AbstractCommand implements AnimatorShapeOperation {
  protected EasyAnimatorShape currentShape;
  protected int startTime;
  protected int endTime;
  protected EasyAnimatorShape endState;


  /**
   * Initializes the super method of the abstraction.
   *
   * @param shape     the shape to be acted upon
   * @param startTime the start time of the command
   * @param endTime   the end time of the command
   * @throws IllegalArgumentException if the command is not valid
   */
  public AbstractCommand(EasyAnimatorShape shape, int startTime,
                         int endTime) throws IllegalArgumentException {
    if (checkValidCommand(shape, startTime, endTime)) {
      throw new IllegalArgumentException("Command is not valid");
    }
    this.currentShape = shape;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  @Override
  public int getEndTime() {
    return this.endTime;
  }

  @Override
  public int getStartTime() {
    return this.startTime;
  }

  @Override
  public String formatCommand() {
    return "Shape " + currentShape.getShape().printName()
            + " " + this.printAction() + " from t = " + startTime +
            " to t = " + endTime;
  }

  @Override
  public String formatCommand(int framesPerSecond) {
    return "Shape " + currentShape.getShape().printName()
            + " " + this.printAction() + " from t = "
            + (double) (startTime / framesPerSecond) + "s"
            + " to t = " + ((double) (endTime / framesPerSecond)) + "s";
  }

  @Override
  public EasyAnimatorShape getAnimateShape() {
    return this.currentShape;
  }


  @Override
  public abstract String formatAsSVG(int framesPerSecond, boolean loop);


  /**
   * Prints the action of what the specific command is doing.
   * Includes the start state and the end state of the shape field within the command.
   *
   * @return the action as a String
   */
  protected abstract String printAction();


  /**
   * Makes sure the given parameters set up to be a valid command.
   * Throws an IllegalArgumentException if the start is less than theShapes's appear time, or if
   * the end is greater than theShape's disappear time.
   *
   * @param theShape the Shape the command is going to act upon
   * @param start    the start time of the command
   * @param end      the end time of the command
   * @return a boolean regarding if if the start is less than theShapes's appear time, or if
   *            the end is greater than theShape's disappear time.
   */
  static boolean checkValidCommand(EasyAnimatorShape theShape, int start, int end) {
    return (start < theShape.getAppearTime() || end > theShape.getDisappearTime())
            || (start >= end);
  }


  @Override
  public void setShape(EasyAnimatorShape shape) {
    this.currentShape = shape.copy();
  }


  /**
   * Checks to see if two commands contradict each other.
   * Two commands contradict each other if they are of the same command type
   * and there time of execution has overlap.
   *
   * @param o1 first command to compare to the second command
   * @param o2 second command to compare to the first command
   * @throws IllegalArgumentException if the commands contradict each other
   */
  public static boolean checkContradiction(AnimatorShapeOperation o1, AnimatorShapeOperation o2)
          throws IllegalArgumentException {
    return (o2 != null && o2.getClass().equals(o1.getClass()) &&
            AbstractCommand.checkTimeOverlap(o1, o2));
  }

  /**
   * Returns all possible pairs of commands from the given list of commands.
   *
   * @param commands list of commands to paired.
   * @return the List of all possible pairs, which themselves are lists
   */
  public static AnimatorShapeOperation[][] getAllPairs(ArrayList<AnimatorShapeOperation> commands) {
    AnimatorShapeOperation[][] listOfAllPairs =
            new AnimatorShapeOperation[commands.size() * (commands.size() - 1) / 2][2];
    for (int i = 0; i < commands.size() - 1; i++) {
      for (int k = i + 1; k < commands.size(); k++) {
        listOfAllPairs[i] = new AnimatorShapeOperation[2];
        listOfAllPairs[i][0] = commands.get(i);
        listOfAllPairs[i][1] = commands.get(k);
      }
    }
    return listOfAllPairs;
  }

  /**
   * Returns a boolean to see if two commands have an overlapping time period of execution.
   *
   * @param o1 the first command
   * @param o2 the second command
   * @return a boolean regarding if there is overlap between the time periods of execution between
   *            o1 and o2
   */
  private static boolean checkTimeOverlap(AnimatorShapeOperation o1, AnimatorShapeOperation o2) {
    return (o1.getStartTime() > o2.getStartTime() && o1.getStartTime() < o2.getEndTime())
            || (o2.getStartTime() > o1.getStartTime() && o2.getStartTime() < o1.getEndTime());
  }
}
