package com.example.dbpractices78.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Vlad Utts
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUserDTO {
    @NotEmpty(message = "ФИО не может быть пустым!")
    @Size(max = 150, message = "ФИО не должно быть больше 150 символов")
    @Pattern(regexp = "^[a-zA-Z\\u0400-\\u04FF\\s]*$", message = "Некорректное ФИО! (Формат: Фамилия Имя Отчество)")
    private String fullName;
    @NotEmpty(message = "Серия и номер паспорта не могут быть пустыми!")
    @Size(max = 10, message = "Серия и номер паспорта не могут быть больше 10 символов!")
    @Pattern(regexp = "^[A-Z0-9\\u0400-\\u04FF]*$", message = "Некорректные серия и номер паспорта! (Формат: ССССНННННН)")
    private String passportSerialNumber;
    @NotEmpty(message = "Email не должен быть пустым!")
    @Email(message = "Некорректный email!")
    @Size(max = 50, message = "Email не должен быть больше 50 символов!")
    private String email;
    @Pattern(regexp = "^\\+\\d{1,12}$", message = "Некорректный номер телефона! (Формат: +79998887766)")
    private String phoneNumber;
    private String positionName;

    @NotEmpty(message = "Имя пользователя не может быть пустым!")
    @Size(min = 3, max = 50, message = "Длина имени пользователя должна быть между 3 и 50 символами!")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Некорректное имя пользователя! (Формат: только латинские буквы и цифры)")
    private String username;
    @NotEmpty(message = "Пароль не может быть пустым!")
    @Size(min = 8, message = "Пароль не может быть меньше 8 символов!")
    private String password;
}
