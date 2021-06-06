package com.labs.lab4_java2ee_ws;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("/students")
@Produces({MediaType.APPLICATION_JSON})
public class StudentResource {

    @Resource(lookup = "jdbc/ws_students_db")
    private DataSource dataSource;

    @GET
    public LinkedHashSet<Student> getStudents(@QueryParam("searchParams") final List<String> searchArgs) {
        System.out.println(searchArgs);
        return new PostgreSQLDAO(getConnection()).getStudentsByFields(searchArgs);
    }

    private Connection getConnection() {
        Connection result = null;
        try {
          result = dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(StudentResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}