package cs3500.animator.model;

/**
 * Represents a command that will execute a change in color.
 * The command must be initialized, and then the user must call increment with a specific time t
 * within this command's appear and disappear time range.
 */
public class ColorChangeCommand extends AbstractCommand {
  private float[] deltaColor;
  private EasyAnimatorShape initialState;

  /**
   * Creates a command capable of executing a color change corresponding to the int array argument.
   * This argument must be of length 3, and the ints in that array must be between 0 and 255.
   * The deltaColor represents a faux rgb value. Each element in the deltaColor will be added
   * to the currentShape's color.
   *
   * @param currentShape the shape recieving the action
   * @param startTime    the start time of the action
   * @param endTime      the end time of the action
   * @param deltaColor   the amount to change by
   * @throws IllegalArgumentException if the start is less than theShapes's appear time, or if
   *                                  the end is greater than theShape's disappear time.
   */
  public ColorChangeCommand(EasyAnimatorShape currentShape, int startTime,
                            int endTime, float[] deltaColor) throws IllegalArgumentException {
    super(currentShape, startTime, endTime);
    if (deltaColor.length != 3) {
      throw new IllegalArgumentException("Change in color must be presented as an array of 3 " +
              "ints ranging from 0 to 255");
    }
    this.deltaColor = deltaColor;
    this.initialState = currentShape.copy();
  }

  @Override
  protected String printAction() {
    return "changes color from " + initialState.getShape().getColor().toString() + " to " +
            initialState.getShape().getColor().plus(deltaColor).toString();
  }

  @Override
  public void increment(int time) throws IllegalArgumentException {
    if (this.deltaColor.length == 3) {

      float rDiff = (time - this.startTime) * this.deltaColor[0] / (this.endTime - this.startTime);
      float gDiff = (time - this.startTime) * this.deltaColor[1] / (this.endTime - this.startTime);
      float bDiff = (time - this.startTime) * this.deltaColor[2] / (this.endTime - this.startTime);
      float[] changeArray = new float[3];
      changeArray[0] = rDiff;
      changeArray[1] = gDiff;
      changeArray[2] = bDiff;
      this.currentShape.getShape().setColor(currentShape.getShape().getColor().plus(changeArray));
      this.endState = this.currentShape.copy();
    }
  }

  @Override
  public String formatAsSVG(int framesPerSecond, boolean loop) {
    String colorChange = "<animate attributeName=\"fill\" attributeType=\"XML\" " +
            "from=\"rgb("
            + (int) ((this.endState.getShape().getColor().getR() - deltaColor[0]) * 255) + ","
            + (int) ((this.endState.getShape().getColor().getG() - deltaColor[1]) * 255) + ","
            + (int) ((this.endState.getShape().getColor().getB() - deltaColor[2]) * 255)
            + ")\" "
            + " to=\""
            + "rgb("
            + (int) (this.endState.getShape().getColor().getR() * 255) + ","
            + (int) (this.endState.getShape().getColor().getG() * 255) + ","
            + (int) (this.endState.getShape().getColor().getB() * 255)
            + ")\" "
            + "begin=\"";
    if (loop) {
      colorChange += "base.begin+";
    }
    colorChange += this.startTime * 1000 / framesPerSecond + "ms\""
            + " dur=\"" + (1000 * (this.endTime - this.startTime) / framesPerSecond)
            + "ms\" fill=\"freeze\"/>";
    return colorChange;

  }
}
