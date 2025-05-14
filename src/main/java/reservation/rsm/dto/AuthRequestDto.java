package reservation.rsm.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto {
    private String username;
    private String password;
}
