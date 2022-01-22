package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.CustomerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerByID(Long id) {
        return customerRepository.findById(id)
                .map(customer -> {
                CustomerDTO customerDTO = customerMapper.CustomerToCustomerDTO(customer);
                customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
                return customerDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO){
        Customer customer = customerMapper.CustomerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO returnedDTO = customerMapper.CustomerToCustomerDTO(savedCustomer);
        returnedDTO.setCustomerUrl("/api/v1/customers/" + savedCustomer.getId());
        return returnedDTO;
    }

    private CustomerDTO saveAndReturnDTO(Customer customer){
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO returnedDTO = customerMapper.CustomerToCustomerDTO(savedCustomer);
        returnedDTO.setCustomerUrl("/api/v1/customers/" + returnedDTO.getId());
        return returnedDTO;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.CustomerDTOToCustomer(customerDTO);
        customer.setId(id);
        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {

            if(customerDTO.getFirstName() != null){
                customer.setFirstName(customerDTO.getFirstName());
            }

            if(customerDTO.getLastName() != null){
                customer.setLastName(customerDTO.getLastName());
            }

            CustomerDTO returnedDTO = customerMapper.CustomerToCustomerDTO(customerRepository.save(customer));
            returnedDTO.setCustomerUrl("/api/v1/customers/" + id);
            return returnedDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
