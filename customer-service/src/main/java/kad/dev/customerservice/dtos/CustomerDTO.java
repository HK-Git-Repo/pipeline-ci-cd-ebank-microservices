package kad.dev.customerservice.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CustomerDTO {
        @NotEmpty()
        private Long id;
        @NotEmpty()
        @Size(min = 2)
        private String firstName;
        @NotEmpty()
        @Size(min = 2)
        private String lastName;
        @NotEmpty()
        @Size(min = 8)
        private String email;
}
