package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

public class ProjectController {

    public void save(Project project) {
        String sql = "INSERT INTO projects(name, description, createdAt, updatedAt) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            //create connection with databank
            connection = ConnectionFactory.getConnection();
            //create prepared statement
            statement = connection.prepareStatement(sql);
            //sets values to variables on data model
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new java.sql.Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new java.sql.Date(project.getUpdatedAt().getTime()));
            //executes sql command
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Error: project NOT saved!", ex);
        } finally {
            //close connection and statement
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Error: databank connection was NOT closed", ex);
            }
        }
    }

    public void update(Project project) {
        String sql = "UPDATE projects SET name = ?, description = ?, createdAt = ?, updatedAt = ? WHERE id = ?";
            //create connection with databank
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            //create connection with databank
            connection = ConnectionFactory.getConnection();
            //create prepared statement for query
            statement = connection.prepareStatement(sql);
            //sets values to variables on data model from query
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new java.sql.Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new java.sql.Date(project.getUpdatedAt().getTime()));
            statement.setInt(4, project.getId());
           //executes sql command
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Error: project NOT updated", ex);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Error: databank connection was NOT closed", ex);
            }
        }
    }

    public List<Project> getAll() {
        String sql = "SELECT * FROM projects";
        List<Project> projects = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        //class resultSet get data values from databank
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            //while result set has next
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setCreatedAt(resultSet.getDate("updatedAt"));
                //add projects found to projects list
                projects.add(project);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error: projects NOT found", ex);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Error: databank connection was NOT closed", ex);
            }
        }
        return projects;
    }

    public void removeById(int id) {
        String sql = "DELETE FROM projects WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Error: project NOT deleted", ex);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Error: databank connection was NOT closed", ex);
            }
        }
    }
}