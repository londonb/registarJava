import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;


public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";


  get("/", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    model.put("template", "templates/index.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/students", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    List<Student> students = Student.all();
    model.put("students", students);
    model.put("template", "templates/students.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/classes", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    List<Class> classes = Class.all();
    model.put("classes", classes);
    model.put("template", "templates/classes.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/students/:id", (request,response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    int id = Integer.parseInt(request.params("id"));
    Student student = Student.find(id);
    model.put("student", student);
    model.put("allClasses", Class.all());
    model.put("template", "templates/student.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/classes/:id", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    int id = Integer.parseInt(request.params("id"));
    Class class1 = Class.find(id);
    model.put("class", class1);
    model.put("allStudents", Student.all());
    model.put("template", "templates/class.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/students", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    String name = request.queryParams("name");
    String date = request.queryParams("date");
    Student newStudent = new Student(name, date);
    newStudent.save();
    response.redirect("/students");
    return null;
  });

  post("/classes", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    String course_name = request.queryParams("course_name");
    String course_number = request.queryParams("course_number");
    Class newClass = new Class(course_name,course_number);
    newClass.save();
    response.redirect("/classes");
    return null;
  });

  post("/add_students", (request, response) -> {
    int studentId = Integer.parseInt(request.queryParams("student_id"));
    int classId = Integer.parseInt(request.queryParams("class_id"));
    Class class1 = Class.find(classId);
    Student student = Student.find(studentId);
    class1.addStudent(student);
    response.redirect("/classes/" + classId);
    return null;
  });

  post("/add_classes", (request, response) -> {
    int studentId = Integer.parseInt(request.queryParams("student_id"));
    int classId = Integer.parseInt(request.queryParams("class_id"));
    Class class1 = Class.find(classId);
    Student student = Student.find(studentId);
    student.addClass(class1);
    response.redirect("/students/" + studentId);
    return null;
  });
  }
}
