package guru.springfamework.api.v1.model;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String firstName;
    private String LastName;
    private String customerUrl;
}
