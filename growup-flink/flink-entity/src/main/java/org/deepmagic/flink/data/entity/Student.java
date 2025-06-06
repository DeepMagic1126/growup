package org.deepmagic.flink.data.entity;

import java.io.Serializable;

/**
 * Student
 *
 * @author chenbin
 * @apiNote TODO
 * @since 2025/3/13 14:42
 */

public class Student implements Serializable {

    private int id;
    private String name;
    private String password;
    private int age;

    public Student(int id, String name, String password, int age) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
    }

    public Student() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
