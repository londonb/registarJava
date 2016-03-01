import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class ClassTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void testsSaveMethod() {
    Class myClass = new Class("Class 1", "CS101");
    myClass.save();
    assertTrue(Class.all().get(0).equals(myClass));
  }

  @Test
  public void testFindMethod() {
    Class myClass = new Class ("Class 1", "CS101");
    myClass.save();
    Class savedClass = Class.find(myClass.getId());
    assertTrue(myClass.equals(savedClass));
  }

  @Test
  public void testGetStudentsFromAClass() {
    Class myClass = new Class ("Class 1", "CS101");
    myClass.save();
    Student newStudent = new Student("Student2", "2000-01-01");
    newStudent.save();
    myClass.addStudent(newStudent);
    Student savedStudent = myClass.getStudents().get(0);
    assertTrue(newStudent.equals(savedStudent));
  }
}
