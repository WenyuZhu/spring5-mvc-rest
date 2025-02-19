package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendorMapperTest {
    public static final String NAME = "Western Tasty Fruits Ltd.";
    public static final Long ID = 1L;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() {
        //given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);

        //when
        VendorDTO vendorDTO = vendorMapper.VendorToVendorDTO(vendor);

        //then
        assertEquals(ID, vendorDTO.getId());
        assertEquals(NAME, vendorDTO.getName());
    }
}
