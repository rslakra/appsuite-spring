package com.rslakra.appsuite.spring.filter;

/**
 * Test custom class for Filter testing.
 *
 * @author Rohtash Lakra
 * @created 1/1/25
 */
public class TestUser {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private Boolean active;

    public TestUser() {
    }

    public TestUser(Long id, String name, String email, Integer age, Boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "TestUser{id=" + id + ", name='" + name + "', email='" + email + "', age=" + age + ", active=" + active + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TestUser testUser = (TestUser) o;
        if (id != null ? !id.equals(testUser.id) : testUser.id != null) {
            return false;
        }
        return email != null ? email.equals(testUser.email) : testUser.email == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}

