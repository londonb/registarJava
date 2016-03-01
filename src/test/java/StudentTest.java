import java.util.Arrays;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class StudentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();


  @Test
  public void testsSaveMethod() {
    Student myStudent = new Student("Student 1", "11");
    myStudent.save();
    assertTrue(Student.all().get(0).equals(myStudent));
  }
}
