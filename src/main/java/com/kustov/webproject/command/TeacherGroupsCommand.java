package com.kustov.webproject.command;

import com.kustov.webproject.entity.Subject;
import com.kustov.webproject.entity.Teacher;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.SubjectReceiver;
import com.kustov.webproject.service.PropertyManager;
import javafx.util.Pair;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class TeacherGroupsCommand implements Command {
    private SubjectReceiver receiver;

    TeacherGroupsCommand(SubjectReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        PropertyManager propertyManager = new PropertyManager("pages");
        String page = propertyManager.getProperty("path_page_teacher_group");
        Teacher teacher = (Teacher)request.getSession().getAttribute("role");
        int subjectId = Integer.parseInt(request.getParameter("subject_id"));
        try {
            Subject subject = receiver.findSubjectById(subjectId);
            subject.setTeacher(teacher);
            ArrayList<Pair<Integer, Integer>> groupCoursePairList = receiver.findGroupAndCourseBySubjectAndTeacher(subject, teacher);
            request.setAttribute("groupCourse", groupCoursePairList);
            request.getSession().setAttribute("subject", subject);
        }catch (ServiceException exc){
            throw new CommandException(exc.getMessage(), exc);
        }
        return new CommandPair(CommandPair.DispatchType.FORWARD, page);
    }
}
