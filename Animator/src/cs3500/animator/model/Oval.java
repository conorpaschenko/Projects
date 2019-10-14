package cs3500.animator.model;


/**
 * A representation of a Oval. Has a name, color, and radius.
 */
public class Oval extends AbstractShape {
  private float radiusX;
  private float radiusY;

  /**
   * Initializes the Oval.
   * @param name the name of the Oval
   * @param color the color of the Oval
   * @param radiusX the X radius of the Oval
   * @param radiusY the Y radius of the Oval
   */
  public Oval(String name, Posn location, MyColor color, float radiusX, float radiusY) {
    super(name, location, color);
    this.radiusX = radiusX;
    this.radiusY = radiusY;

  }

  @Override
  public String formatDimensions() {
    return String.format("X Radius: %.2f, Y Radius: %.2f",  this.radiusX, this.radiusY);
  }


  @Override
  public String shapeTypeToString() {
    return "oval";
  }

  @Override
  public String locationTypeToString() {
    return "Center";
  }

  @Override
  public Shape copy() {
    return new Oval(this.name, this.location.copy(), this.color, this.radiusX, this.radiusY);
  }

  @Override
  public void scale(float scaleFactorX, float scaleFactorY) {
    this.radiusX =  radiusX * scaleFactorX;
    this.radiusY = radiusY * scaleFactorY;
  }

  @Override
  public String formatAsSVG() {
    return "<ellipse id=\"" + this.name + "\" cx=\"" + this.location.getX() + "\" cy=\""
            + this.location.getY()
            + "\" rx=\"" + this.radiusX + "\" ry=\"" + this.radiusY + "\" "
            + "fill=\"rgb(" + (int) (this.color.getR() * 255) + ","
            + (int) (this.color.getG() * 255) + ","
            + (int) (this.color.getB() * 255)
            + ")\" visibility=\"hidden\" >";
  }

  @Override
  public String finishSVGInit() {
    return "</ellipse>";
  }

  @Override
  public double getXDim() {
    return this.radiusX;
  }

  @Override
  public double getYDim() {
    return this.radiusY;
  }

  @Override
  public String getSVGDimensionPrefix() {
    return "c";
  }

  @Override
  public String xDimAsString() {
    return "rx";
  }

  @Override
  public String yDimAsString() {
    return "ry";
  }


}
