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
}
