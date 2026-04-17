package models;

public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private Integer age;

    public Long getId() {
        return id;
    }

    public UserResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UserResponse setAge(Integer age) {
        this.age = age;
        return this;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ",age='" + age + '\'' +
                '}';
    }
}
