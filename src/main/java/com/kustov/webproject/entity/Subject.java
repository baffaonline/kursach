package com.kustov.webproject.entity;

import java.util.Objects;

public class Subject extends Entity{
    private String name;
    private Teacher teacher;
    private int group;
    private int course;

    public Subject() {
        teacher = new Teacher();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subject subject = (Subject) o;
        return group == subject.group &&
                course == subject.course &&
                Objects.equals(name, subject.name) &&
                Objects.equals(teacher, subject.teacher);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, teacher, group, course);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", teacher=" + teacher +
                ", group=" + group +
                ", course=" + course +
                '}';
    }
}
