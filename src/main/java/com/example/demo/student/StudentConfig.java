package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
            Student arrin = new Student("Arrin","Arrin@gmail.com", LocalDate.of(1995, Month.AUGUST, 2));
            Student nate = new Student(4L,"Nate","Nate@gmail.com", LocalDate.of(1994, Month.AUGUST, 1));

            //repository.saveAll(List.of(arrin,nate));
        };
    }
}
