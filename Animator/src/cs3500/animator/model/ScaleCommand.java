package cs3500.animator.model;

/**
 * A Command to execute the scaling of a shape
 * The shape that will be scaled is stored within
 * This class executes a proportional scaling, meaning all dimensions are changed at the same
 * proportion. The shape will reach the complete scale by the initialized end time.
 */
public class ScaleCommand extends AbstractCommand {

  private float scaleFactorX;
  private float scaleFactorY;

  /**
   * Initializes a scale command.
   * @param shape the shape to be scaled
   * @param startTime the time at which to start the movement
   * @param endTime the time at which to complete the movement
   * @param scaleFactorX the factor by which the x dimension of the shape will be scaled
   * @param scaleFactorY the factor by which the y dimension of the shape will be scaled
   * @throws IllegalArgumentException if the start is less than theShapes's appear time, or if
   *               the end is greater than theShape's disappear time.
   */
  public ScaleCommand(EasyAnimatorShape shape, int startTime,
                      int endTime, float scaleFactorX, float scaleFactorY) throws
          IllegalArgumentException {
    super(shape, startTime, endTime);
    this.scaleFactorX = scaleFactorX;
    this.scaleFactorY = scaleFactorY;
  }

  @Override
  protected String printAction() {
    EasyAnimatorShape endStateScaledBack = this.endState.copy();
    endStateScaledBack.getShape().scale((float) 1 / scaleFactorX,
            (float) 1 / scaleFactorY);
    return "scales from " + endStateScaledBack.getShape().formatDimensions()
            + " to " + endState.getShape().formatDimensions() ;
  }


  @Override
  public void increment(int time) {
    if (time < this.startTime || time > this.endTime) {
      throw new IllegalArgumentException("Time for executing command is out of bounds");
    }
    currentShape.getShape().scale((float) Math.pow(scaleFactorX,
            (float)  (time - this.startTime) * 1 / (endTime - startTime)),
            (float) Math.pow(scaleFactorY,
                    (float)  (time - this.startTime) * 1 / (endTime - startTime)));
    this.endState = currentShape.copy();
  }

  @Override
  public String formatAsSVG(int framesPerSecond, boolean loop) {
    String scale = "<animate "
            + "attributeType=\"xml\" "
            + "begin=\"";
    if (loop) {
      scale += "base.begin+";
    }
    scale +=  (this.startTime * 1000) / framesPerSecond + "ms\" "
            + "dur=\"" + 1000 * (this.endTime - this.startTime) / framesPerSecond
            + "ms\" attributeName=\"" + this.currentShape.getShape().xDimAsString()
            + "\" from=\"" + this.endState.getShape().getXDim() * (1 / scaleFactorX) + "\" "
            + "to=\"" + this.endState.getShape().getXDim() +  "\" fill = \"freeze\" />" + "\n"
            + "<animate "
            + "attributeType=\"xml\" "
            + "begin=\"";
    if (loop) {
      scale += "base.begin+";
    }
    scale +=  (this.startTime * 1000) / framesPerSecond + "ms\" "
            + "dur=\"" + 1000 * (this.endTime - this.startTime) / framesPerSecond
            + "ms\" attributeName=\"" + this.currentShape.getShape().yDimAsString()
            + "\" from=\"" + this.endState.getShape().getYDim() * (1 / scaleFactorY) + "\" "
            + "to=\"" + this.endState.getShape().getYDim() +  "\" fill =\"freeze\" />" + "\n";

    return scale;
  }
}
