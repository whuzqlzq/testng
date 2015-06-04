package org.testng.internal;

import org.testng.ITestNGMethod;
import org.testng.annotations.IExpectedExceptionsAnnotation;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationFinder;

import java.util.regex.Pattern;

/**
 * A class that contains the expected exceptions and the message regular expression.
 * @author cbeust
 */
public class ExpectedExceptionsHolder extends AbstractExpectedExceptionsHolder {
  public static final String DEFAULT_REGEXP = ".*";

  public ExpectedExceptionsHolder(IAnnotationFinder finder, ITestNGMethod method) {
    super(finder, method);
  }

  /**
   *   message / regEx  .*      other
   *   null             true    false
   *   non-null         true    match
   */
  @Override
  protected boolean isExceptionMatches(Throwable ite) {
    String messageRegExp = getRegExp();

    if (DEFAULT_REGEXP.equals(messageRegExp)) {
      return true;
    }

    final String message = ite.getMessage();
    return message != null && Pattern.compile(messageRegExp, Pattern.DOTALL).matcher(message).matches();
  }

  protected String getWrongExceptionMessage(Throwable ite) {
    return "The exception was thrown with the wrong message:" +
           " expected \"" + getRegExp() + "\"" +
           " but got \"" + ite.getMessage() + "\"";
  }

  private String getRegExp() {
    String messageRegExp = DEFAULT_REGEXP;
    IExpectedExceptionsAnnotation expectedExceptions =
        finder.findAnnotation(method, IExpectedExceptionsAnnotation.class);

    if (expectedExceptions == null) {
      // New syntax
      ITestAnnotation testAnnotation = finder.findAnnotation(method, ITestAnnotation.class);
      if (testAnnotation != null) {
        Class<?>[] ee = testAnnotation.getExpectedExceptions();
        if (ee.length > 0) {
          messageRegExp = testAnnotation.getExpectedExceptionsMessageRegExp();
        }
      }
    } // else: Old syntax => keep default value

    return messageRegExp;
  }
}
