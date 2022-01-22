package guru.springframework.services;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;

    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendor() {
        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.VendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl("/api/v1/vendors/" + vendor.getId());
                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorByID(Long id){
        return vendorRepository.findById(id)
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.VendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl("/api/v1/vendors/" + vendor.getId());
                    return vendorDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO){
        Vendor vendor = vendorMapper.VendorDTOtoVendor(vendorDTO);
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO returnedDTO = vendorMapper.VendorToVendorDTO(savedVendor);
        returnedDTO.setVendorUrl("/api/v1/vendors/" + savedVendor.getId());
        return returnedDTO;
    }

    private VendorDTO saveAndReturnDTO(Vendor vendor){
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO returnedDTO = vendorMapper.VendorToVendorDTO(savedVendor);
        returnedDTO.setVendorUrl("/api/v1/vendors/" + returnedDTO.getId());
        return returnedDTO;
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.VendorDTOtoVendor(vendorDTO);
        vendor.setId(id);
        return saveAndReturnDTO(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {

            if(vendorDTO.getName() != null){
                vendor.setName(vendorDTO.getName());
            }

            VendorDTO returnedDTO = vendorMapper.VendorToVendorDTO(vendorRepository.save(vendor));
            returnedDTO.setVendorUrl("/api/v1/vendors/" + id);
            return returnedDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }
}
