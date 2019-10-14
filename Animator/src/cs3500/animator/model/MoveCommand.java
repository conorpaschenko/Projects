package cs3500.animator.model;

/**
 * A class to execute a move method upon an EasyAnimateShape within an animation.
 * Receives two vectors to change x and y locations of the shape respectively.
 */
public class MoveCommand extends AbstractCommand {
  private float deltaX;
  private float deltaY;



  /**
   * Initializes the MoveCommand.
   * @param shape the shape to be moved
   * @param startTime the time at which to start the movement
   * @param endTime the time at which to complete the movement
   * @param deltaX the x vector for the desired end location
   * @param deltaY the y vector for the desired end location
   * @throws IllegalArgumentException if the start is less than theShapes's appear time, or if
   *                   the end is greater than theShape's disappear time.
   */
  public MoveCommand(EasyAnimatorShape shape, int startTime,
                          int endTime, float deltaX, float deltaY) throws IllegalArgumentException {
    super(shape, startTime, endTime);
    this.deltaX = deltaX;
    this.deltaY = deltaY;
  }

  @Override
  public void increment(int time) throws IllegalArgumentException {
    if (time < this.startTime || time > this.endTime) {
      throw new IllegalArgumentException("Time for executing command is out of bounds");
    }
    float totalTime = endTime - startTime;
    currentShape.setLocation(currentShape.getLocation().plus(
            (time - this.startTime) * deltaX / totalTime,
            (time - this.startTime) * deltaY / totalTime));

    this.endState = currentShape.copy();
  }

  @Override
  public String formatAsSVG(int framesPerSecond, boolean loop) {
    String moveX =  "<animate attributeType=\"xml\" begin=\"";
    if (loop) {
      moveX += "base.begin+";
    }
    moveX += (1000 * this.startTime) /
            framesPerSecond
            + "ms\" dur=\"" + (1000 * (this. endTime - this.startTime)) / framesPerSecond +
            "ms\" attributeName=\"" + this.currentShape.getShape().getSVGDimensionPrefix()
            + "x\" from=\"" + (endState.getLocation().getX() - deltaX) +
            "\" to=\"" + endState.getLocation().getX() + "\" fill=\"freeze\" />";
    String moveY = "<animate attributeType=\"xml\" begin=\"";
    if (loop) {
      moveY += "base.begin+";
    }
    moveY +=   (1000 * this.startTime) / framesPerSecond + "ms\" dur=\""
            + (1000 * (this. endTime - this.startTime)) / framesPerSecond +
            "ms\" attributeName=\"" + this.currentShape.getShape().getSVGDimensionPrefix()
            + "y\" from=\"" + (endState.getLocation().getY() - deltaY) +
            "\" to=\"" + endState.getLocation().getY() + "\" fill=\"freeze\" />";

    return moveX + "\n" + moveY;

  }

  @Override
  protected String printAction() {
    return "moves from " + endState.getLocation().plus(-1 * deltaX,
            -1 * deltaY).format() +
            " to " + endState.getLocation().format();
  }


}
