import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;

import javax.swing.JOptionPane;

import cs3500.animator.controller.EasyAnimatorController;
import cs3500.animator.controller.EasyAnimatorControllerViewImpl;
import cs3500.animator.controller.EasyAnimatorControllerTextImpl;
import cs3500.animator.controller.EasyAnimatorInteractiveController;
import cs3500.animator.model.EasyAnimatorModel;
import cs3500.animator.model.EasyAnimatorModelImpl;
import cs3500.animator.util.AnimationFileReader;


import cs3500.animator.view.EasyAnimatorInteractiveViewImpl;
import cs3500.animator.view.EasyAnimatorSVGViewImpl;
import cs3500.animator.view.EasyAnimatorTextViewImpl;
import cs3500.animator.view.EasyAnimatorView;
import cs3500.animator.view.EasyAnimatorVisualViewImpl;

/**
 * The main class for the EasyAnimator animation application.
 * Receives a list of strings of commands that will be interpreted and then executed if they
 * are valid commands.
 *  Types of commands:
 *    "-iv" : the type of view. Input must be "visual", "text", or "svg".
 *    "-if" : the file to read to initalize animation. File must be in correct format.
 *    "-speed" : the frames per second to run the animation. Defaults at 100.
 *    "-o" : the output destination. Defaults at System.out for text and svg views.
 */
public final class EasyAnimator {
  /**
   * The main method to run the file.
   * @param args the string arguments from the command line.
   */
  public static void main(String[] args) throws FileNotFoundException {
    Appendable out = null;
    int speed;
    EasyAnimatorView view;

    JOptionPane errorPane = new JOptionPane();
    String errorMessage = "Commands for Animation:\n" + "-iv : VIEW_TYPE " +
            "(options: \"view\", \"text\", or \"svg\")\n" + "-if : " +
            "INPUT_FILE (options: any correctly formatted file)\n" + "-o : OUTPUT_LOCATION\n"
            + "-speed : FPS (Any integer greater than 0)";


    String scannableStr = "";
    String outCommand = "";
    String speedCommand = "";
    String viewTypeCommand = "";
    String fileToRead = "";

    for (String str : args) {
      scannableStr += str + " ";
    }

    Scanner commandScanner = new Scanner(scannableStr);
    while (commandScanner.hasNext()) {
      String input = commandScanner.next();
      switch (input) {
        case "-iv":
          viewTypeCommand = commandScanner.next();
          break;
        case "-speed":
          speedCommand = commandScanner.next();
          break;
        case "-o":
          outCommand = commandScanner.next();
          break;
        case "-if":
          fileToRead = commandScanner.next();
          break;
        default:
          JOptionPane.showMessageDialog(null, errorMessage +
              "\n Invalid input", "Input Error", JOptionPane.ERROR_MESSAGE);
          throw new IllegalArgumentException("Invalid input");
      }
    }


    if (outCommand.equals("")) {
      out = System.out;
    } else {
      try {
        out = new BufferedWriter(new FileWriter(outCommand, true));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (speedCommand.equals("")) {
      speed = 1;
    } else {
      try {
        Integer.parseInt(speedCommand);
      } catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog(null, errorMessage +
                        "\nspeed must be a positive" +
                        " integer",
                "Input Error", JOptionPane.ERROR_MESSAGE);
        throw new IllegalArgumentException("speed must be a positive integer");
      }

      speed = Integer.parseInt(speedCommand);
      if (speed < 1) {
        JOptionPane.showMessageDialog(null, errorMessage +
                        "\nspeed must be a" +
                        " positive integer",
                "Input Error", JOptionPane.ERROR_MESSAGE);
        throw new IllegalArgumentException("speed must be a positive integer");
      }
    }

    EasyAnimatorModel model = null;
//    try {
      AnimationFileReader readMyFile = new AnimationFileReader();
      model = readMyFile.readFile(fileToRead, new EasyAnimatorModelImpl.Builder());
//    } catch (Exception e) {
//      JOptionPane.showMessageDialog(null, errorMessage +
//                      "\nInput file is not in the" +
//                      " correct format",
//              "Input Error", JOptionPane.ERROR_MESSAGE);
//      throw new IllegalArgumentException("Input file is not in the correct format");
//    }

    EasyAnimatorController controller = null;

    // If I had more time I would have made it so I would put a method in my model interface
    // that would get the maximum distances in the x and y directions and then set initialize those
    // variables as the arguments to the EasyAnimatorVisualViewImpl
    if (viewTypeCommand.equals("visual")) {
      view = new EasyAnimatorVisualViewImpl(1000, 1000);
      controller = new EasyAnimatorControllerViewImpl(model, view);
    } else if (viewTypeCommand.equals("svg")) {
      view = new EasyAnimatorSVGViewImpl(out, 1000, 1000);
      controller = new EasyAnimatorControllerTextImpl(model, view);
    } else if (viewTypeCommand.equals("text")) {
      view = new EasyAnimatorTextViewImpl(out);
      controller = new EasyAnimatorControllerTextImpl(model, view);
    } else if (viewTypeCommand.equals("interactive")) {
      view = new EasyAnimatorInteractiveViewImpl();
      controller =
              new EasyAnimatorInteractiveController((EasyAnimatorInteractiveViewImpl) view, model);
    }
    else {
      JOptionPane.showMessageDialog(null, errorMessage +
                      "\nView type invalid: enter \"view\", " +
                      "\"text\", or \"svg\"",
              "hello", JOptionPane.ERROR_MESSAGE);
      throw new IllegalArgumentException("View type invalid: enter \"view\", \"text\", or \"svg\"");
    }
    controller.run(speed);
    if (out != null && out != System.out) {
      try {
        ((BufferedWriter) out).close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

}