package model;

public class Student {
    private int studentID;
    private String name;
    private String phone;

    public Student(int studentID, String name, String phone) {
        this.studentID = studentID;
        this.name = name;
        this.phone = phone;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString()
    {
        return this.studentID + " - " + this.name;
    }
}
