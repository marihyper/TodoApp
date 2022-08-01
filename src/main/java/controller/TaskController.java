package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

public class TaskController {

    public void save(Task task) {
        String sql = "INSERT INTO tasks(idProject, name, description, " +
                "notes, deadline, completed, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statament = null;
        try {
            connection = ConnectionFactory.getConnection();
            statament = connection.prepareStatement(sql);
            statament.setInt(1, task.getIdProject());
            statament.setString(2, task.getName());
            statament.setString(3, task.getDescription());
//            statament.setByte(4, task.getStatus());
            statament.setString(4, task.getNotes());
            statament.setDate(5, new java.sql.Date(task.getDeadline().getTime()));
            statament.setBoolean(6, task.isCompleted());
            statament.setDate(7, new java.sql.Date(task.getCreatedAt().getTime()));
            statament.setDate(8, new java.sql.Date(task.getUpdatedAt().getTime()));
            statament.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar a tarefa " + ex.getMessage(), ex);
        } finally {
            try {
                if (statament != null) statament.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Error: databank connection was NOT closed", ex);
            }
        }
    }

    public void update(Task task) {
        String sql = "UPDATE tasks SET " +
                "idProject = ?, " +
                "name = ?, " +
                "description = ?, " +
                "notes = ?, " +
                "deadline = ?, " +
                "completed = ?, " +
                "createdAt = ?, " +
                "updatedAt = ? " +
                "WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt     (1, task.getIdProject());
            statement.setString  (2, task.getName());
            statement.setString  (3, task.getDescription());
//            statament.setByte    (4, task.getStatus());
            statement.setString  (4, task.getNotes());
            statement.setDate    (5, new java.sql.Date(task.getDeadline().getTime()));
            statement.setBoolean (6, task.isCompleted());
            statement.setDate    (7, new java.sql.Date(task.getCreatedAt().getTime()));
            statement.setDate    (8, new java.sql.Date(task.getUpdatedAt().getTime()));
            statement.setInt     (9, task.getId());
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Error: task NOT updated", ex);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Error: databank connection was NOT closed", ex);
            }
        }
    }

    public List<Task> getAll() {
        String sql = "SELECT * FROM tasks";
        List<Task> tasks = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
//                task.setStatus(resultSet.getByte("status"));
                task.setNotes(resultSet.getString("notes"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCompleted(resultSet.getBoolean("completed"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setCreatedAt(resultSet.getDate("updatedAt"));
                tasks.add(task);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error: tasks NOT found", ex);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Error: databank connection was NOT closed", ex);
            }
        }
        return tasks;
    }

    public List<Task> getByProjectId(int id) {
        String sql = "SELECT * FROM tasks where idProject = ?";
        List<Task> tasks = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            //set task project id to given id
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
//                task.setStatus(resultSet.getByte("status"));
                task.setNotes(resultSet.getString("notes"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCompleted(resultSet.getBoolean("completed"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setCreatedAt(resultSet.getDate("updatedAt"));
                tasks.add(task);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error: tasks we're NOT found!", ex);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Error: databank connection was NOT closed", ex);
            }
        }
        return tasks;
    }

    public void removeById(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Error: task NOT deleted", ex);
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