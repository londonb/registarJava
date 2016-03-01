import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Student {
  private int id;
  private String name;
  private String date;


  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEnrollment() {
    return date;
  }

  public Student(String name, String date) {
    this.name = name;
    this.date = date;
  }

  public static List<Student> all() {
    String sql = "SELECT * FROM students ORDER BY name";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Student.class);
    }
  }

  @Override
  public boolean equals(Object otherStudent){
    if (!(otherStudent instanceof Student)) {
      return false;
    } else {
      Student newStudent = (Student) otherStudent;
      return this.getName().equals(newStudent.getName()) &&
      this.getEnrollment().equals(newStudent.getEnrollment());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students(name, date) VALUES (:name, :date)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("date", this.date)
        .executeUpdate()
        .getKey();
    }
  }

  public static Student find(int id) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM students where id=:id";
    Student student = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Student.class);
    return student;
    }
  }

  public void addClass(Class class1) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_classes (student_id, class_id) VALUES (:student_id, :class_id)";
      con.createQuery(sql)
        .addParameter("student_id", this.getId())
        .addParameter("class_id", class1.getId())
        .executeUpdate();
    }
  }

  public ArrayList<Class> getClasses() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT DISTINCT class_id FROM students_classes WHERE student_id = :student_id";
      List<Integer> classIds = con.createQuery(sql)
        .addParameter("student_id", this.getId())
        .executeAndFetch(Integer.class);

      ArrayList<Class> classes = new ArrayList<Class>();

      for (Integer classId : classIds) {
        String classQuery = "SELECT * FROM classes WHERE id = :class_id";
        Class class1 = con.createQuery(classQuery)
          .addParameter("class_id", classId)
          .executeAndFetchFirst(Class.class);
          classes.add(class1);
        }
    return classes;
    }
  }
}
