package cs3500.animator.view;

import java.io.IOException;

import cs3500.animator.model.EasyAnimatorShape;


/**
 * Class for SVG View that implements methods from EasyAnimatorView. Reads input and converts it to
 * a String that represents the input as SVG code. The String is then appended to the output.
 */
public class EasyAnimatorSVGViewImpl extends AbstractTextView {
  private StringBuilder myStringBuilder;
  private int width;
  private int height;


  /**
   * Creates one EasyAnimatorView with the SVG View. Sets myStringBuilder to empty, inputFinished to
   * false, and accumulator of shapes to a new ArrayList. Adds shapes to accumulator when they
   * appear.
   *
   * @param output Appendable that view will append StringBuilder to.
   * @param width  given width of the entire background.
   * @param height given height of the entire background.
   * @throws IllegalArgumentException output is null or given width or height are not greater than 0
   */
  public EasyAnimatorSVGViewImpl(Appendable output, int width, int height) throws
          IllegalArgumentException {
    super(output);
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height have to be greater than 0.");
    }
    this.myStringBuilder = new StringBuilder(); // StringBuilder that gathers all input from model
    // and converts it to svg code as a String.
    this.height = height;
    this.width = width;
    this.initScene();
  }

  /**
   * Initializes the overall svg dimensions in the StringBuilder.
   */
  private void initScene() {
    myStringBuilder.append("<svg width=\"" + width + "\" height=\"" + height +
            "\" version=\"1.1\"\n" + "     xmlns=\"http://www.w3.org/2000/svg\">");
  }

  @Override
  public void addToOutPut(int framesPerSecond) {
    for (EasyAnimatorShape shape : accuShapes) {
      myStringBuilder.append(shape.formatAsSVG(framesPerSecond, false));
    }
    myStringBuilder.append("</svg>");
    try {
      output.append(myStringBuilder.toString());
    } catch (IOException e) {
      throw new IllegalStateException("Output not in a state to receive input");
    }
  }


}
