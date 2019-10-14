package cs3500.animator.model;


/**
 * An interface to model a shape.
 */
public interface Shape {
  /**
   * Returns a String with information about the shape's dimensions.
   *
   * @return the formatted String
   */
  String formatDimensions();

  /**
   * Returns a String of the name and type of shape.
   *
   * @return a String of the name and type of shape.
   */
  String formatGenDescription();

  /**
   * Returns the Shape's name as a String.
   *
   * @return the Shape's name as a String.
   */
  String printName();

  /**
   * Returns the type of shape as a String. Example: Rectangle will return "rectangle".
   *
   * @return the type of shape as a String.
   */
  String shapeTypeToString();

  /**
   * Prints the location type in regards to how it will be placed on a canvas. In general,
   * Round shapes will use the center, and sided shapes will use their top left most corner.
   */
  String locationTypeToString();

  /**
   * Returns the color as a string in the format [r=...,g=...,b=...] with "..." replaced by the
   * corresponding colors.
   *
   * @return the Color to a string
   */
  String formatColor();

  /**
   * Returns a deep copy of the current Shape.
   *
   * @return a deep copy of the current shape
   */
  Shape copy();

  /**
   * Mutates Shape by multiplying all of the dimensions by the given scaleFactor.
   *
   * @param scaleFactorX the proportion to scale the x dimension by
   * @param scaleFactorY the proportion to scale the y dimension by
   */
  void scale(float scaleFactorX, float scaleFactorY);

  /**
   * Returns the color of the Shape.
   *
   * @return the color of the Shape
   */
  MyColor getColor();

  /**
   * Sets the Shape's color.
   *
   * @param color the color to set the shape to
   */
  void setColor(MyColor color);


  Posn getLocation();

  void setLocation(Posn position);

  /**
   * Formats a String representing how the shape would be initialized if the String were called
   * as a method in an SVG file.
   *
   * @return the method call as a String
   */
  String formatAsSVG();

  /**
   * Finishes the SVG format of each shape.
   *
   * @return < + / + the corresponding element that has been closed + > as a String.
   */
  String finishSVGInit();

  /**
   * obtains the x dimension of the shape.
   *
   * @return the x dimension (width or cx) of the shape
   */
  double getXDim();

  /**
   * obtains the y dimension of the shape.
   *
   * @return the y dimension (height or cy) of the shape
   */
  double getYDim();

  /**
   * Adds c prefix to width and height if the shape is an oval.
   *
   * @return either "c" or nothing.
   */
  String getSVGDimensionPrefix();

  /**
   * checks if shape should display width or cx.
   *
   * @return t(width or cx) a String.
   */
  String xDimAsString();

  /**
   * checks if shape should display height or cy.
   *
   * @return t(height or cy) a String.
   */
  String yDimAsString();

}
