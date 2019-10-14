package cs3500.animator.model;

/**
 * An abstract class for the Shape interface.
 */
public abstract class AbstractShape implements Shape {
  protected String name;
  protected MyColor color;
  protected Posn location;

  /**
   * The super constructor for all classes extending AbstractShape.
   * @param name a String to represent the shape's name
   * @param color the color of the shape
   */
  AbstractShape(String name, Posn location, MyColor color) {
    this.location = location;
    this.name = name;
    this.color = color;
  }

  @Override
  public void setLocation(Posn position) {
    this.location = new Posn(position.getX(), position.getY());
  }

  @Override
  public Posn getLocation() {
    return this.location;
  }

  @Override
  public String printName() {
    return this.name;
  }

  public abstract String formatDimensions();

  @Override
  public String formatGenDescription() {
    return "Name: " + name + "\n" + "Type: " + this.shapeTypeToString() + "\n" +
            this.locationTypeToString();
  }

  @Override
  public abstract String locationTypeToString();

  @Override
  public void setColor(MyColor color) {
    this.color = color;
  }


  @Override
  public MyColor getColor() {
    return this.color;
  }

  @Override
  public String formatColor() {
    return this.color.toString();
  }





}
