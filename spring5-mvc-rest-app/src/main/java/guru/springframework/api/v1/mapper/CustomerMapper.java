package guru.springframework.api.v1.mapper;

import guru.springframework.model.CustomerDTO;
import guru.springframework.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO CustomerToCustomerDTO(Customer customer);

    Customer CustomerDTOToCustomer(CustomerDTO customerDTO);
}
