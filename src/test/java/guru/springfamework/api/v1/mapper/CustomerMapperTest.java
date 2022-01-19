package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
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
        assertEquals(ID, customerDTO.getId());
        assertEquals(JOE, customerDTO.getFirstName());
        assertEquals(SMITH, customerDTO.getLastName());
    }
}