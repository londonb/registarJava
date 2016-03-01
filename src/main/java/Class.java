import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Class {
  private int id;
  private String course_name;
  private String course_number;


  public int getId() {
    return id;
  }

  public String getCourseName() {
    return course_name;
  }

  public String getCourseNumber() {
    return course_number;
  }

  public Class(String course_name, String course_number) {
    this.course_name = course_name;
    this.course_number = course_number;
  }

  public static List<Class> all() {
    String sql = "SELECT * FROM classes";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .executeAndFetch(Class.class);
    }
  }

  @Override
  public boolean equals(Object otherClass){
    if (!(otherClass instanceof Class)) {
      return false;
    } else {
      Class newClass = (Class) otherClass;
      return this.getCourseName().equals(newClass.getCourseName()) &&
      this.getCourseNumber().equals(newClass.getCourseNumber());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO classes (course_name, course_number) VALUES (:course_name, :course_number)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("course_name", course_name)
      .addParameter("course_number", course_number)
      .executeUpdate()
      .getKey();
    }
  }

  public static Class find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM classes WHERE id= :id";
      Class class1 = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Class.class);
      return class1;
    }
  }

  public void addStudent(Student student) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_classes (student_id, class_id) VALUES (:student_id, :class_id)";
      con.createQuery(sql)
        .addParameter("student_id", student.getId())
        .addParameter("class_id", this.getId())
        .executeUpdate();
    }
  }

  public ArrayList<Student> getStudents() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT DISTINCT student_id FROM students_classes WHERE class_id = :class_id";
      List<Integer> studentIds = con.createQuery(sql)
        .addParameter("class_id", this.getId())
        .executeAndFetch(Integer.class);

      ArrayList<Student> students = new ArrayList<Student>();

      for (Integer studentId : studentIds) {
        String studentQuery = "SELECT * FROM students WHERE id = :student_id";
        Student student = con.createQuery(studentQuery)
          .addParameter("student_id", studentId)
          .executeAndFetchFirst(Student.class);
          students.add(student);
        }
    return students;
    }
  }
}
