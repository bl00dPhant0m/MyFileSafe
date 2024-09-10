package org.spring.myfilesafe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    @NotNull(message = "Username не может быть пустым")
    @Size(min = 5, max = 30, message = "Username должен содержать от 5 до 30 символов")
    private String username;

    @Column(nullable = false)
    @NotNull(message = "Пароль не может быть пустым")
    @Size(min = 8, max = 20, message = "Пароль должен содержать от 8 до 20 символов")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$",
            message = "Пароль должен содержать хотя бы одну заглавную букву, одну строчную букву, одну цифру и один специальный символ")
    private String password;


    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    List<File> files = new ArrayList<>();
}
