// [lv] Datu ģenerēšana JPA repozitorijās (50 demo pieteikumi)

package com.dps.application.data.generator;

import com.dps.application.data.entity.ComponentType;
import com.dps.application.data.entity.TechComponent;
import com.dps.application.data.repository.ComponentRepository;
import com.dps.application.data.repository.ComponentTypeRepository;
import com.dps.application.data.entity.Manufacturer;
import com.dps.application.data.entity.Status;
import com.dps.application.data.repository.ManufacturerRepository;
import com.dps.application.data.repository.StatusRepository;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(ComponentRepository componentRepository, ManufacturerRepository manufacturerRepository,
                                      ComponentTypeRepository componentTypeRepository, StatusRepository statusRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (componentRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generee demo datus");

            // Datu ievade ražotāju repozitorijā
            List<Manufacturer> manufacturers = manufacturerRepository
                    .saveAll(Stream.of("Dell", "Acer", "Razer", "Lenovo", "LG")
                    .map(Manufacturer::new).collect(Collectors.toList()));

            //logger.info("... manufacturers ...");
            //logger.info(String.valueOf(manufacturerRepository.count()));

            // Datu ievade komponenšu veidu repozitorijā
            List<ComponentType> componentTypes = componentTypeRepository
                    .saveAll(Stream.of("Stacionārs dators", "Portatīvais dators", "Monitors", "Datorpele")
                            .map(ComponentType::new).collect(Collectors.toList()));

            //logger.info("... komponenshu veidi ...");
            //logger.info(String.valueOf(componentTypeRepository.count()));

            // Datu ievade statusu repozitorijā
            List<Status> statuses = statusRepository
                    .saveAll(Stream.of("Neizskatīts", "Apstiprināts", "Noraidīts")
                            .map(Status::new).collect(Collectors.toList()));

            //logger.info("... statusi ...");
            //logger.info(String.valueOf(statusRepository.count()));

            logger.info("... generee 50 pieteikumu entiitijas ...");
            ExampleDataGenerator<TechComponent> componentGenerator = new ExampleDataGenerator<>(TechComponent.class,
                    LocalDateTime.now());
            // Datumu + laika ieguve no Vaadin piemēru datu bibliotēkas
            componentGenerator.setData(TechComponent::setAppDate, DataType.DATETIME_LAST_30_DAYS);
            //logger.info("... laiku genereshana pabeigta ...");

            // Pamatojumu aizvototāju ieguve no Vaadin piemēru datu bibliotēkas
            componentGenerator.setData(TechComponent::setJustification, DataType.SENTENCE);
            //logger.info("... pamatojumu genereshana pabeigta ...");

            // Pieteikumiem nejauši norāda komponentes tipu, skaitu, ražotāju un statusu
            Random r = new Random(seed);
            List<TechComponent> components = componentGenerator.create(50, seed).stream().map(component -> {
                component.setComponentType(componentTypes.get(r.nextInt(componentTypes.size())));
                component.setNumber(r.nextInt(15));
                component.setManufacturer(manufacturers.get(r.nextInt(manufacturers.size())));
                component.setStatus(statuses.get(r.nextInt(statuses.size())));
                return component;
            }).collect(Collectors.toList());
            componentRepository.saveAll(components); // Ģenerēto entītiju saglabāšana pieteikumu repozitorijā

            logger.info("Genereeti demo dati");
        };
    }

}
