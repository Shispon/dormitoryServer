package org.diplom.dormitory;

import org.springframework.boot.SpringApplication;

public class TestDormitoryApplication {

    public static void main(String[] args) {
        SpringApplication.from(DormitoryApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
