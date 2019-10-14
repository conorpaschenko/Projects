package cs3500.animator.model;


/**
 * Represents an Animation command that rotates the shape. The command changes its EasyAnimatorShape
 * rotationDegree field upon calling increment.
 */
public class RotationOperation extends AbstractCommand {
  int rotateBy;

  /**
   * The constructor for the rotation. This is where the degrees to rotate by is set.
   * @param shape the EasyAnimatorShape
   * @param startTime the start time
   * @param endTime the end time
   * @param rotateBy the amount to rotate the shape by.
   */
  RotationOperation(EasyAnimatorShape shape, int startTime, int endTime, int rotateBy) {
    super(shape, startTime, endTime);
    this.rotateBy = rotateBy;
  }


  @Override
  public void increment(int time) throws IllegalArgumentException {
    currentShape.setRotationDegree((time - startTime) * rotateBy / (endTime - startTime));

  }

  @Override
  public String formatAsSVG(int framesPerSecond, boolean loop) {
    return null;
  }

  @Override
  protected String printAction() {
    return null;
  }
}
