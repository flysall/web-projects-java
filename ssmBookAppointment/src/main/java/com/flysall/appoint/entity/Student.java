package com.flysall.appoint.entity;

public class Student {
    private Long studentId;
    private Long password;

    public Student(){

    }

    public Student(Long studentId, Long password){
        this.studentId = studentId;
        this.password = password;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getPasswork() {
        return password;
    }

    public void setPasswork(Long passwork) {
        this.password= passwork;
    }

    @Override
    public String toString() {
        return "Student [studentId=" + studentId + ", password=" + password + "]";
    }
}
