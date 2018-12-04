package com.kustov.webproject.command;

import com.kustov.webproject.logic.*;


/**
 * The Enum CommandType.
 */
public enum CommandType {

    /**
     * The prepare login.
     */
    PREPARE_LOGIN(new PrepareLoginCommand()),

    /**
     * The login.
     */
    LOGIN(new LoginCommand(new UserReceiver())),


    /**
     * The logout.
     */
    LOGOUT(new LogoutCommand()),

    SUBJECTS(new SubjectsCommand(new SubjectReceiver())),

    GROUP_SUBJECT(new GroupSubjectCommand()),

    TEACHER_GROUPS(new TeacherGroupsCommand(new SubjectReceiver())),

    BEFORE_ADD_MARK(new BeforeAddMarkCommand()),

    ADD_MARK(new AddMarkCommand());


    /**
     * The command.
     */
    private Command command;

    /**
     * Instantiates a new command type.
     *
     * @param command the command
     */
    CommandType(Command command) {
        this.command = command;
    }

    /**
     * Gets the command.
     *
     * @return the command
     */
    public Command getCommand() {
        return command;
    }
}
