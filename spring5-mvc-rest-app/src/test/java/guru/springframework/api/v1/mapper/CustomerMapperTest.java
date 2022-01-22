package guru.springframework.api.v1.mapper;

import guru.springframework.model.CustomerDTO;
import guru.springframework.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperTest {

    public static final String JOE = "Joe";
    public static final String SMITH = "smith";
    public static final Long ID = 1L;

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() {
        //given
        Customer customer = new Customer();
        customer.setFirstName(JOE);
        customer.setLastName(SMITH);
        customer.setId(ID);

        //when
        CustomerDTO customerDTO = customerMapper.CustomerToCustomerDTO(customer);

        //then
        assertEquals(JOE, customerDTO.getFirstName());
        assertEquals(SMITH, customerDTO.getLastName());
    }
}