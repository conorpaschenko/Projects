package cs3500.animator.model;

import java.util.ArrayList;



/**
 * A class to represent a shape capable of being animated. This shape will have an appear time,
 * a disappear time, a location, a list of animations, as well as an ID number.
 */
public class EasyAnimatorShape {
  private Posn initialLoc;
  private Shape shape;
  private int appearTime;
  private int disappearTime;
  private ArrayList<AnimatorShapeOperation> commands;
  private int id;
  private int rotationDegree;
  private Shape initialShape;
  private int layer;


  /**
   * Initializes the shape capable of animation.
   * Does not initialize the shape's animations.
   * @param shape         the Shape involved
   * @param appearTime    the time the shape appears in the animation
   * @param disappearTime the time the shape disappears in the animation
   * @param id            the id number of the shape
   * @throws IllegalArgumentException if the appearTime is negative or if the disappear time
   *                                  is less than the appear time
   */
  public EasyAnimatorShape(Shape shape, int appearTime,
                           int disappearTime, int id)
          throws IllegalArgumentException {
    if (appearTime < 0) {
      throw new IllegalArgumentException("Time cannot be negative");
    } else if (disappearTime < appearTime) {
      throw new IllegalArgumentException("Disappear time must be greater than appear time");
    }

    this.shape = shape;
    this.appearTime = appearTime;
    this.disappearTime = disappearTime;
    this.commands = new ArrayList<>();
    this.id = id;
    this.initialLoc = this.shape.getLocation().copy();
    this.initialShape = this.shape.copy();
    this.rotationDegree = 0;
    this.layer = 0;
  }

  /**
   * Initializes the shape capable of animation.
   * This constructor also initializes the shapes animations.
   * @param shape the Shape
   * @param appearTime the time to shape appears in the animation
   * @param disappearTime the time to shape disappears in the animation
   * @param commands the animations that act upon this
   * @param id the id number
   * @throws IllegalArgumentException if the appearTime is negative or if the disappear time
   *                                       is less than the appear time
   */
  public EasyAnimatorShape(Shape shape, int appearTime,
                           int disappearTime, ArrayList<AnimatorShapeOperation> commands, int id)
          throws IllegalArgumentException {
    if (appearTime < 0) {
      throw new IllegalArgumentException("Time cannot be negative");
    } else if (disappearTime < appearTime) {
      throw new IllegalArgumentException("Disappear time must be greater than appear time");
    }

    this.rotationDegree = 0;
    this.shape = shape;

    this.layer = 0;
    this.appearTime = appearTime;
    this.disappearTime = disappearTime;
    this.commands = commands;
    this.id = id;
    this.initialLoc = this.shape.getLocation().copy();
    this.initialShape = this.shape.copy();
  }


  /**
   * A constructor with all relevant information.
   * This constructor initializes the shapes animations, rotation degree, and layer.
   * @param shape the Shape
   * @param appearTime the time to shape appears in the animation
   * @param disappearTime the time to shape disappears in the animation
   * @param commands the animations that act upon this
   * @param id the id number
   * @param rotationDegree the degree to rotate the shape by
   * @param layer the layer of which the shape will be displayed
   * @throws IllegalArgumentException
   */
  public EasyAnimatorShape(Shape shape, int appearTime, int disappearTime,
                           ArrayList<AnimatorShapeOperation> commands, int id,
                           int rotationDegree , int layer)
          throws IllegalArgumentException {
    if (appearTime < 0) {
      throw new IllegalArgumentException("Time cannot be negative");
    } else if (disappearTime < appearTime) {
      throw new IllegalArgumentException("Disappear time must be greater than appear time");
    }

    this.shape = shape;

    this.layer = layer;
    this.appearTime = appearTime;
    this.disappearTime = disappearTime;
    this.commands = commands;
    this.id = id;
    this.initialLoc = this.shape.getLocation().copy();
    this.initialShape = this.shape.copy();
    this.rotationDegree = rotationDegree;
  }


  /**
   * Creates a deep copy of the EasyAnimateShape.
   *
   * @return a deep copy of this.
   */
  public EasyAnimatorShape copy() {
    return new EasyAnimatorShape(this.shape.copy(), this.appearTime,
            this.disappearTime, this.commands, this.id,  this.rotationDegree, layer);
  }

  /**
   * A getter for the Shape object.
   *
   * @return the Shape object
   */
  public Shape getShape() {
    return this.shape;
  }

  public void setRotationDegree(int rotationDegree) {
    this.rotationDegree = rotationDegree;
  }

  public int getRotationDegree() {
    return this.rotationDegree;
  }

  /**
   * Sets the location to a deep copy of the given Posn.
   *
   * @param position the new Posn
   */
  public void setLocation(Posn position) {
    this.shape.setLocation(position);
  }

  /**
   * Prints a description of the EasyAnimate Shape.
   *
   * @return the formatted description
   */
  public String formatDescription() {
    return this.shape.formatGenDescription() + ": " + this.initialLoc.format() + " " +
            this.shape.formatDimensions() + ", Color: " +
            this.shape.formatColor() + "\n"
            + "Appears at t = " + this.appearTime
            + " Disappears at t = " + this.disappearTime;
  }

  /**
   * Prints a description of the EasyAnimate Shape, with appear and appear times translated to
   * seconds.
   *
   * @param framesPerSecond the frames per second to consider
   * @return the formatted string
   */
  public String formatDescription(int framesPerSecond) {

    return this.shape.formatGenDescription() + ": " + this.initialLoc.format() + " " +
            this.shape.formatDimensions() + ", Color: " +
            this.shape.formatColor() + "\n"
            + "Appears at t = "
            + (double) (this.appearTime / framesPerSecond) + "s"
            + " Disappears at t = "
            + (double) (this.disappearTime / framesPerSecond) + "s";
  }








  /**
   * Getter method for the location.
   *
   * @return the location
   */
  public Posn getLocation() {
    return this.shape.getLocation();
  }

  /**
   * A getter for the appearTime.
   *
   * @return the appearTime
   */
  public int getAppearTime() {
    return this.appearTime;
  }

  /**
   * A getter for the disappearTime.
   */
  public int getDisappearTime() {
    return this.disappearTime;
  }

  /**
   * A getter for the list of Commands.
   */
  public ArrayList<AnimatorShapeOperation> getCommands() {
    return this.commands;
  }

  /**
   * A getter for the ID.
   *
   * @return the ID
   */
  public int getID() {
    return this.id;
  }


  /**
   * This method validates that contradicting commands (two commands that are the same
   * type of command, as well as have overlapping executing periods)
   * act upon the EasyAnimatorShape.
   *
   * @throws IllegalArgumentException if there are contradicting commands
   */
  public boolean validateCommands() throws IllegalArgumentException {
    boolean invalidity = false;
    AnimatorShapeOperation[][] allPairs = AbstractCommand.getAllPairs(this.commands);
    for (AnimatorShapeOperation[] pair : allPairs) {
      if (AbstractCommand.checkContradiction(pair[0], pair[1])) {
        invalidity = true;
      }
    }
    return invalidity;
  }

  /**
   * Returns the shape formatted as svg code.
   *
   * @param framesPerSecond the frames to consider how to display timing
   * @param loop a boolean representing whether or not the animation will be looped
   * @return the code as a string
   */
  public String formatAsSVG(int framesPerSecond, boolean loop) {
    String accuString = "";
    accuString += this.shape.formatAsSVG();
    accuString += "\n    "
            + "<set attributeName=\"visibility\" attributeType=\"XML\" to=\"visible\" "
            + "begin=\"";
    if (loop) {
      accuString += "base.begin+";
    }
    accuString += (this.appearTime * 1000) / framesPerSecond
              + "ms\" dur=\"" + (this.disappearTime - this.appearTime) * 1000 / framesPerSecond
              + "ms\" fill=\"freeze\" />";

    for (int i = 0; i < commands.size(); i++) {
      accuString += "\n    " + commands.get(i).formatAsSVG(framesPerSecond, loop);
    }

    if (loop) {
      String resetColor = "<animate attributeType=\"xml\" begin=\"base.end-100\" " +
              "dur=\"1ms\" attributeName=\"fill\"" + " to=\""
              + "rgb("
              + (int) (this.initialShape.getColor().getR() * 255) + ","
              + (int) (this.initialShape.getColor().getG() * 255) + ","
              + (int) (this.initialShape.getColor().getB() * 255)
              + ")\" "
              + " fill=\"freeze\" />";
      String resetLoc = "<animate attributeType=\"xml\" begin=\"base.end-100\" " +
              "dur=\"1ms\" attributeName=\"" + this.shape.getSVGDimensionPrefix() + "x\"" + " to=\""
              + this.initialShape.getLocation().getX() + "\" fill=\"freeze\" />";
      resetLoc += "\n" + "<animate attributeType=\"xml\" begin=\"base.end-100\" " +
              "dur=\"1ms\" attributeName=\"" + this.shape.getSVGDimensionPrefix() + "y\"" + " to=\""
              + this.initialShape.getLocation().getY() + "\" fill=\"freeze\" />";

      String resetDimension = "<animate attributeType=\"xml\" begin=\"base.end-100\" " +
              "dur=\"1ms\" attributeName=\"" + this.shape.xDimAsString() + "\"" + " to=\""
              + this.initialShape.getXDim() + "\" fill=\"freeze\" />";

      resetDimension += "<animate attributeType=\"xml\" begin=\"base.end-100\" " +
              "dur=\"1ms\" attributeName=\"" + this.shape.yDimAsString() + "\"" + " to=\""
              + this.initialShape.getYDim() + "\" fill=\"freeze\" />";
      accuString += resetLoc + "\n" + resetColor + "\n" + resetDimension + "\n";
    }

    accuString += "\n" + this.shape.finishSVGInit();
    return accuString;
  }

  /**
   * Returns true if this has a rotation command at the given time i.
   * @param i the time
   * @return a boolean regarding if this has a rotation command at the given time i
   */
  public boolean hasRotationAt(int i) {
    ArrayList<AnimatorShapeOperation> rotations = new ArrayList<>();
    for (AnimatorShapeOperation command : commands) {
      if (command instanceof RotationOperation) {
        rotations.add(command);
      }
    }

    for (AnimatorShapeOperation rotation : rotations) {
      if (rotation.getStartTime() <= i && rotation.getEndTime() >= i) {
        return true;
      }
    }
    return false;
  }


  /**
   * A getter for the layer.
   * @return the layer
   */
  public int getLayer() {
    return this.layer;
  }




}
