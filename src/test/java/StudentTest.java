import java.util.Arrays;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class StudentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();


  @Test
  public void testsSaveMethod() {
    Student myStudent = new Student("Student 1", "1996-08-21");
    myStudent.save();
    assertTrue(Student.all().get(0).equals(myStudent));
  }

  @Test
  public void testsFindMethod() {
    Student myStudent = new Student("Student 1", "12");
    myStudent.save();
    Student savedStudent = Student.find(myStudent.getId());
    assertTrue(myStudent.equals(savedStudent));
  }

  @Test
  public void testsGettingClassesFromAStudent() {
    Student myStudent = new Student("Student 1", "12");
    myStudent.save();
    Student savedStudent = Student.find(myStudent.getId());
    Class myClass1 = new Class ("Class 1", "CS101");
    myClass1.save();
    Class myClass2 = new Class ("Class 2", "CS102");
    myClass2.save();
    savedStudent.addClass(myClass1);
    savedStudent.addClass(myClass2);
    List classes = savedStudent.getClasses();
    assertEquals(2, classes.size());
  }
}
