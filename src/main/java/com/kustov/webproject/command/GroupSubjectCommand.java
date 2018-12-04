package com.kustov.webproject.command;

import com.kustov.webproject.entity.*;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.MarkReceiver;
import com.kustov.webproject.logic.StudentReceiver;
import com.kustov.webproject.logic.SubjectReceiver;
import com.kustov.webproject.logic.TeacherReceiver;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;

public class GroupSubjectCommand implements Command {
    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        PropertyManager propertyManager = new PropertyManager("pages");
        String page = propertyManager.getProperty("path_page_group_subject");
        User user = (User)request.getSession().getAttribute("user");
        try {
            switch (user.getType()) {
                case STUDENT:
                    request.getSession().removeAttribute("subjects");
                    Student student = (Student) request.getSession().getAttribute("role");
                    SubjectReceiver subjectReceiver = new SubjectReceiver();
                    Subject subject = subjectReceiver.findSubjectById(Integer.parseInt(request.getParameter("subject_id")));
                    TeacherReceiver teacherReceiver = new TeacherReceiver();
                    Teacher teacher = teacherReceiver.findTeacherByStudentAndSubject(student, subject);
                    subject.setTeacher(teacher);
                    request.getSession().setAttribute("subject", subject);
                    StudentReceiver studentReceiver = new StudentReceiver();
                    List<Student> students = studentReceiver.findStudentsByGroupAndCourse(student.getGroup(), student.getCourse());
                    MarkReceiver markReceiver = new MarkReceiver();
                    List<Mark> marks = markReceiver.findMarksByGroupAndCourse(student, subject);
                    Map<Student, ArrayList<Mark>> studentListMap = new HashMap<>();
                    ArrayList<LocalDate> dates = new ArrayList<>();
                    changeMap(studentListMap, dates, marks);
                    request.getSession().setAttribute("dates", dates);
                    Collections.sort(dates);
                    for (Student student1 : students){
                        if (studentListMap.containsKey(student1)){
                            studentListMap.get(student1).sort(Comparator.comparing(Mark::getDate));
                            student1.setMarks(studentListMap.get(student1));
                        }
                    }
                    request.getSession().setAttribute("students", students);
                    break;
                case TEACHER:
                    Subject subject1 = (Subject)request.getSession().getAttribute("subject");
                    StudentReceiver studentReceiver1 = new StudentReceiver();
                    int group = Integer.parseInt(request.getParameter("group"));
                    int course = Integer.parseInt(request.getParameter("course"));
                    List<Student> students1 = studentReceiver1.findStudentsByGroupAndCourse(group, course);
                    MarkReceiver markReceiver1 = new MarkReceiver();
                    List<Mark> marks1 = markReceiver1.findMarksByGroupAndCourse(students1.get(0), subject1);
                    Map<Student, ArrayList<Mark>> studentListMap1 = new HashMap<>();
                    ArrayList<LocalDate> dates1 = new ArrayList<>();
                    changeMap(studentListMap1, dates1, marks1);
                    request.getSession().setAttribute("dates", dates1);
                    Collections.sort(dates1);
                    for (Student student1 : students1){
                        if (studentListMap1.containsKey(student1)){
                            studentListMap1.get(student1).sort(Comparator.comparing(Mark::getDate));
                            student1.setMarks(studentListMap1.get(student1));
                        }
                    }
                    request.getSession().setAttribute("students", students1);
                    request.getSession().setAttribute("group", group);
                    request.getSession().setAttribute("course", course);
                    break;
            }
        }catch (ServiceException exc){
            throw new CommandException(exc);
        }
        return new CommandPair(CommandPair.DispatchType.FORWARD, page);
    }

    private void changeMap(Map<Student, ArrayList<Mark>> map, List<LocalDate> dates, List<Mark> marks){
        for (Mark mark : marks){
            if (map.containsKey(mark.getStudent())){
                map.get(mark.getStudent()).add(mark);
            }else {
                map.put(mark.getStudent(), new ArrayList<>());
                map.get(mark.getStudent()).add(mark);
            }
            if (!dates.contains(mark.getDate())){
                dates.add(mark.getDate());
            }
        }
    }
}
