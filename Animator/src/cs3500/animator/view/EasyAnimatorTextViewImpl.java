package cs3500.animator.view;


import cs3500.animator.model.AnimatorShapeOperation;
import cs3500.animator.model.EasyAnimatorShape;

/**
 * Class for Text View that implements methods from EasyAnimatorView. Reads input and converts it to
 * a String that represents the input as textual description. The string is then appended to the
 * given appendable (output).
 */
public class EasyAnimatorTextViewImpl extends AbstractTextView {

  /**
   * Creates one EasyAnimatorView with the Text View. Sets inputFinished to false and accumulator of
   * shapes to a new ArrayList. Adds shapes to accumulator when they appear.
   *
   * @param output Appendable that view will append input as a String to.
   * @throws IllegalArgumentException output is null.
   */
  public EasyAnimatorTextViewImpl(Appendable output) throws
          IllegalArgumentException {
    super(output);
  }

  @Override
  protected void addToOutPut(int framesPerSecond) {
    for (EasyAnimatorShape shape : accuShapes) {
      this.appendAbstraction(shape.formatDescription(framesPerSecond) + "\n\n");
    }
    for (EasyAnimatorShape shape : accuShapes) {
      for (AnimatorShapeOperation operation : shape.getCommands()) {
        this.appendAbstraction(operation.formatCommand(framesPerSecond) + "\n");
      }
    }

  }


}

