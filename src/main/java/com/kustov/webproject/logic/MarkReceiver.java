package com.kustov.webproject.logic;

import com.kustov.webproject.dao.MarkDAO;
import com.kustov.webproject.dao.TeacherDAO;
import com.kustov.webproject.entity.Mark;
import com.kustov.webproject.entity.Student;
import com.kustov.webproject.entity.Subject;
import com.kustov.webproject.entity.Teacher;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkReceiver {
    public List<Mark> findMarksByGroupAndCourse(Student student, Subject subject) throws ServiceException{
        MarkDAO dao = new MarkDAO();
        try{
            return dao.findMarksByGroupAndCourseAndSubject(student.getGroup(), student.getCourse(), subject.getId());
        }catch(DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public Integer insertMark(Mark mark) throws ServiceException{
        MarkDAO dao = new MarkDAO();
        try {
            if (dao.findMarkByInfo(mark) != 0) {
                return 0;
            }
            else {
                return dao.insert(mark);
            }
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }
}
