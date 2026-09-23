package org.example.turismouq.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String mensaje;
    private Long id;
    private String nombre;
    private String correo;
}
