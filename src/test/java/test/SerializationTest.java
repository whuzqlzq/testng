package test;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializationTest {

  @Test
  public void iSuiteShouldBeSerializable(ITestContext context) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(out);
    oos.writeObject(context.getSuite());
    oos.close();
  }

  @Test
  public void testngMethodShouldBeSerializable(ITestContext context) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(out);
    oos.writeObject(context.getAllTestMethods()[0]);
    oos.close();
  }
}
