package com.kustov.webproject.command;

import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class BeforeAddMarkCommand implements Command {
    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        PropertyManager propertyManager = new PropertyManager("pages");
        String page = propertyManager.getProperty("path_page_add_mark");
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Аудиторное занятие");
        categories.add("Проверочная работа");
        categories.add("Пропуск");
        categories.add("Рейтинг");
        categories.add("Экзамен");
        request.setAttribute("categories", categories);
        return new CommandPair(CommandPair.DispatchType.FORWARD, page);
    }
}
