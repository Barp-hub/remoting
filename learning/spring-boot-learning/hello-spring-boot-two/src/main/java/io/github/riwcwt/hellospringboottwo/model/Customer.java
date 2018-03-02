package io.github.riwcwt.hellospringboottwo.model;

public class Customer {
    private long custId;
    private String firstname;
    private String lastname;
    private int age;

    public Customer(long custId, String firstname, String lastname, int age) {
        this.custId = custId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }

    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
