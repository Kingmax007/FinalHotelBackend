package classwork.demo.realdto;


import lombok.*;


@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserCreateDTO {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}