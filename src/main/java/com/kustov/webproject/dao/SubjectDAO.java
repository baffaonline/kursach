package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Subject;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO extends AbstractEntityDAO<Integer, Subject>{
    private final static String SQL_FIND_SUBJECTS_BY_GROUP_AND_COURSE = "SELECT subject.id as subj_id, " +
            "subject.name as subj_name, " +
            "group_subject.group as subj_group, course " +
            "FROM subject JOIN group_subject ON subject_id = subject.id WHERE group_subject.group = ? and course = ?";

    private final static String SQL_FIND_SUBJECTS_BY_TEACHER_ID = "SELECT distinct subject.id as subj_id, \n" +
            "            subject.name as subj_name\n" +
            "            FROM subject JOIN group_subject ON subject_id = subject.id WHERE teacher_id = ?";

    private final static String SQL_FIND_ALL_SUBJECTS = "SELECT distinct subject.id as subject_id, \n" +
            "            subject.name as subject_name\n" +
            "            FROM subject JOIN group_subject ON subject_id = subject.id";

    private final static String SQL_FIND_BY_ID = "SELECT id, name FROM subject WHERE id = ?";

    private final static String SQL_FIND_GROUP_AND_COURSE_BY_SUBJECT_AND_TEACHER = "SELECT group_subject.group as group_s, " +
            "course " +
            " FROM group_subject where subject_id = ? and teacher_id = ?";

    @Override
    public List<Subject> findAll() throws DAOException {
        ArrayList<Subject> subjects = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_SUBJECTS);
            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setId(resultSet.getInt("subject_id"));
                subject.setName(resultSet.getString("subject_name"));
                subjects.add(subject);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return subjects;
    }

    @Override
    public Integer insert(Subject entity) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Subject findById(Integer id) throws DAOException {
        Subject subject = null;
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                subject = new Subject();
                subject.setId(resultSet.getInt("id"));
                subject.setName(resultSet.getString("name"));
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return subject;
    }

    public ArrayList<Pair<Integer, Integer>> findCourseAndGroupBySubjectAndTeacher(int subjectId, int teacherId) throws DAOException{
        ArrayList<Pair<Integer, Integer>> groupCoursePair = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_GROUP_AND_COURSE_BY_SUBJECT_AND_TEACHER)) {
            statement.setInt(1, subjectId);
            statement.setInt(2, teacherId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                groupCoursePair.add(new Pair<>(resultSet.getInt("group_s"),
                        resultSet.getInt("course")));
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return groupCoursePair;
    }

    public ArrayList<Subject> findSubjectByGroupAndCourse(int group, int course) throws DAOException{
        ArrayList<Subject> subjects = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_SUBJECTS_BY_GROUP_AND_COURSE)) {
            statement.setInt(1, group);
            statement.setInt(2, course);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setId(resultSet.getInt("subj_id"));
                subject.setName(resultSet.getString("subj_name"));
                subject.setGroup(resultSet.getInt("subj_group"));
                subject.setCourse(resultSet.getInt("course"));
                subjects.add(subject);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return subjects;
    }

    public ArrayList<Subject> findSubjectByTeacherId(int id) throws DAOException{
        ArrayList<Subject> subjects = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_SUBJECTS_BY_TEACHER_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setId(resultSet.getInt("subj_id"));
                subject.setName(resultSet.getString("subj_name"));
                subjects.add(subject);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return subjects;
    }
}
