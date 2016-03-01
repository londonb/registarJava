import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/university_test", null, null);
   }

   protected void after() {
     try(Connection con = DB.sql2o.open()) {
       String deleteClassesQuery = "DELETE FROM classes *;";
       String deleteStudentsQuery = "DELETE FROM students *;";
       String deleteStudentsClassesQuery = "DELETE FROM students_classes *;";
       con.createQuery(deleteClassesQuery).executeUpdate();
       con.createQuery(deleteStudentsQuery).executeUpdate();
       con.createQuery(deleteStudentsClassesQuery).executeUpdate();
     }
   }
}
