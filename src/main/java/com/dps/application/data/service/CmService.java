// JPA repozitoriju apstrāde
// Iespēja noteiktu pieprasījumu meklēšana, pievienoša un dzēšana,
// kā arī visu ražotāju, komponenšu un statusu veidu atrašana

package com.dps.application.data.service;

import com.dps.application.data.entity.ComponentType;
import com.dps.application.data.entity.Manufacturer;
import com.dps.application.data.entity.Status;
import com.dps.application.data.entity.TechComponent;
import com.dps.application.data.repository.ComponentTypeRepository;
import com.dps.application.data.repository.ManufacturerRepository;
import com.dps.application.data.repository.ComponentRepository;
import com.dps.application.data.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Padara šo klasi kā Spring pārvaldītu pakalpojumu
public class CmService {

    private final ComponentRepository componentRepository;
    private final ComponentTypeRepository componentTypeRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final StatusRepository statusRepository;

    // Automātiski tiek pievienotas atbilstošās repozitorjas
    public CmService(ComponentRepository componentRepository,
                     ComponentTypeRepository componentTypeRepository, ManufacturerRepository manufacturerRepository,
                     StatusRepository statusRepository) {
        this.componentRepository = componentRepository;
        this.componentTypeRepository = componentTypeRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.statusRepository = statusRepository;
    }

    // Visu pieprasījumu meklēšana atbilstoši filtrā esošā teksta esamībai vai neesamībai
    public List<TechComponent> findAllComponents(String filterText) {
        if(filterText == null || filterText.isEmpty()){
            return componentRepository.findAll(); // ja filtrā nav norādīts teksts, tad sameklē visus pieprasījumus
        }
        else{
            return componentRepository.search(filterText); // ja filtrā ir norādīts teksts, tad tiek veikta noteikta meklēšana
        }
    }

    public long countComponents() {
        return componentRepository.count();
    }

    // Pieprasījuma dzēšana
    public void deleteComponent(TechComponent component){
        componentRepository.delete(component);
    }

    // Jauna pieprasījuma pievienošana
    public void saveComponent(TechComponent component){
        if(component == null) {
            System.err.println("Component is null.");
        }

        componentRepository.save(component);
    }

    // Atrod visus kompentes veidus
    public List<ComponentType> findAllCompTypes(){
        return componentTypeRepository.findAll();
    }

    // Atrod visus ražotāju veidus
    public List<Manufacturer> findAllManufacturers(){
        return manufacturerRepository.findAll();
    }

    // Atrod visu statusu veidus
    public List<Status> findAllStatuses(){
        return statusRepository.findAll();
    }
}
