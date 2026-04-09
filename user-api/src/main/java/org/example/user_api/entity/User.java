package org.example.user_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя не должно быть пустым")
    private String name;

    @Email(message = "Email должен быть валидным")
    @NotBlank(message = "Email не должен быть пустым")
    private String email;

    @Min(value = 0, message = "Возраст не может быть отрицательным")
    private Long age;
}
