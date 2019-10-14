package cs3500.animator.model;



/**
 * Represents a Rectangle that has a name, a MyColor as a Color, and width and height represented.
 */
public class Rectangle extends AbstractShape {
  private double width;
  private double height;

  /**
   * Constructor for the rectangle.
   * @param name name of the shape
   * @param color color of the shape
   * @param width width of the shape
   * @param height height of the shape
   */
  public Rectangle(String name, Posn location, MyColor color, double width, double height) {
    super(name, location, color);
    this.width = width;
    this.height = height;
  }

  @Override
  public String formatDimensions() {
    return String.format("Width: %.2f, Height: %.2f", width, height);
  }

  @Override
  public String shapeTypeToString() {
    return "rectangle";
  }

  @Override
  public String locationTypeToString() {
    return "Corner";
  }

  @Override
  public Shape copy() {
    return new Rectangle(this.name, this.location.copy(), this.color.copy(), this.width,
            this.height);
  }

  @Override
  public void scale(float scaleFactorX, float scaleFactorY) {
    this.width = width * scaleFactorX;
    this.height = height * scaleFactorY;
  }

  @Override
  public String formatAsSVG() {
    return "<rect id=\"" + this.name + "\" x=\"" + this.location.getX() + "\" y=\"" +
            this.location.getY()
            + "\" width=\"" + this.width + "\" height=\"" + this.height + "\" "
            + "fill=\"rgb(" + (int) (this.color.getR() * 255) + ","
            + (int) (this.color.getG() * 255)
            + ","
            + (int) (this.color.getB() * 255)
            + ")\" visibility=\"hidden\" >";
  }

  @Override
  public String finishSVGInit() {
    return "</rect>";
  }

  @Override
  public double getXDim() {
    return width;
  }

  @Override
  public double getYDim() {
    return this.height;
  }

  @Override
  public String getSVGDimensionPrefix() {
    return "";
  }

  @Override
  public String xDimAsString() {
    return "width";
  }

  @Override
  public String yDimAsString() {
    return "height";
  }


}
