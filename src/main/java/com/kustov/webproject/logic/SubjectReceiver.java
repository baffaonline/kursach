package com.kustov.webproject.logic;

import com.kustov.webproject.dao.SubjectDAO;
import com.kustov.webproject.entity.Subject;
import com.kustov.webproject.entity.Teacher;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SubjectReceiver {
    public ArrayList<Subject> findSubjectsByCourseAndGroup(int course, int group) throws ServiceException{
        SubjectDAO subjectDAO = new SubjectDAO();
        try{
            return subjectDAO.findSubjectByGroupAndCourse(group, course);
        } catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public ArrayList<Subject> findSubjectsByTeacherId(Teacher teacher) throws ServiceException{
        SubjectDAO subjectDAO = new SubjectDAO();
        try{
            ArrayList<Subject> subjects = subjectDAO.findSubjectByTeacherId(teacher.getId());
            for (Subject subject : subjects){
                subject.setTeacher(teacher);
            }
            return subjects;
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public Subject findSubjectById(int id) throws ServiceException{
        SubjectDAO subjectDAO = new SubjectDAO();
        try{
            return subjectDAO.findById(id);
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public List<Subject> findAllSubjects() throws ServiceException{
        SubjectDAO dao = new SubjectDAO();
        try{
            return dao.findAll();
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public ArrayList<Pair<Integer, Integer>> findGroupAndCourseBySubjectAndTeacher(Subject subject, Teacher teacher)
            throws ServiceException{
        SubjectDAO dao = new SubjectDAO();
        try{
            return dao.findCourseAndGroupBySubjectAndTeacher(subject.getId(), teacher.getId());
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }
}
