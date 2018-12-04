package com.kustov.webproject.command;

import com.kustov.webproject.entity.*;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.MarkReceiver;
import com.kustov.webproject.logic.StudentReceiver;
import com.kustov.webproject.service.PropertyManager;
import com.kustov.webproject.service.StringDateFormatter;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class AddMarkCommand implements Command {
    private final static String NUMBER_PATTERN = "[+-]|\\d{1,2}";
    private final static String SKIP_PATTERN = "[нН]";
    private final static String CONTROL_PATTERN = "\\d{1,2}";
    private final static String TEN_PATTERN = "[0-9]|10";

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        PropertyManager propertyManager = new PropertyManager("pages");
        int group = (int)request.getSession().getAttribute("group");
        int course = (int)request.getSession().getAttribute("course");
        String page = propertyManager.getProperty("path_page_controller").
                concat("?command=group_subject&group=") + group + "&course=" + course;
        try {
            StudentReceiver studentReceiver = new StudentReceiver();
            Student student = studentReceiver.findStudentById(Integer.parseInt(request.getParameter("student")));
            String value = request.getParameter("mark");
            MarkCategory category = chooseCategory(request.getParameter("category"));
            Teacher teacher = (Teacher)request.getSession().getAttribute("role");
            Subject subject = (Subject)request.getSession().getAttribute("subject");
            LocalDate date = StringDateFormatter.getDateFromString(request.getParameter("date"));
            Mark mark = new Mark(0, student, teacher, value, subject, category, date);
            MarkReceiver receiver = new MarkReceiver();
            if (mark.getType().equals(MarkCategory.AUDITORY) && !Pattern.matches(NUMBER_PATTERN, mark.getMark())){
                page = propertyManager.getProperty("path_page_controller").concat("?command=before_add_mark");
                request.setAttribute("error", "Отметка в аудитории должна быть числом, а также + или -");
                return new CommandPair(CommandPair.DispatchType.FORWARD, page);
            }
            if (mark.getType().equals(MarkCategory.SKIP) && !Pattern.matches(SKIP_PATTERN, mark.getMark())){
                page = propertyManager.getProperty("path_page_controller").concat("?command=before_add_mark");
                request.setAttribute("error", "Пропуск отмечается русской буквой н");
                return new CommandPair(CommandPair.DispatchType.FORWARD, page);
            }
            if (mark.getType().equals(MarkCategory.CONTROL) && !Pattern.matches(CONTROL_PATTERN, mark.getMark())){
                page = propertyManager.getProperty("path_page_controller").concat("?command=before_add_mark");
                request.setAttribute("error", "Отметка за работу - число от 0 до 99");
                return new CommandPair(CommandPair.DispatchType.FORWARD, page);
            }
            if ((mark.getType().equals(MarkCategory.RATING) || mark.getType().equals(MarkCategory.EXAM)) &&
                    !Pattern.matches(TEN_PATTERN, mark.getMark())){
                page = propertyManager.getProperty("path_page_controller").concat("?command=before_add_mark");
                request.setAttribute("error", "Рейтинг и экзамен - числа от 0 до 10");
                return new CommandPair(CommandPair.DispatchType.FORWARD, page);
            }
            if (receiver.insertMark(mark) != 0){
                return new CommandPair(CommandPair.DispatchType.REDIRECT, page);
            }else{
                page = propertyManager.getProperty("path_page_controller").concat("?command=before_add_mark");
                request.setAttribute("error", "Отметка на данную дату уже стоит");
                return new CommandPair(CommandPair.DispatchType.FORWARD, page);
            }
        }catch (ServiceException exc){
            throw new CommandException(exc.getMessage(), exc);
        }
    }

    private MarkCategory chooseCategory(String categoryName){
        switch (categoryName){
            case "Аудиторное занятие":
                return MarkCategory.AUDITORY;
            case "Проверочная работа":
                return MarkCategory.CONTROL;
            case "Пропуск":
                return MarkCategory.SKIP;
            case "Рейтинг":
                return MarkCategory.RATING;
            case "Экзамен":
                return MarkCategory.EXAM;
            default:
                return MarkCategory.AUDITORY;
        }
    }
}
