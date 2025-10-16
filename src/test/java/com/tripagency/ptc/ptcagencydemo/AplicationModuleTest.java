package com.tripagency.ptc.ptcagencydemo;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class AplicationModuleTest {
    @Test
    void createApplicationModuleModel() {
        ApplicationModules modules = ApplicationModules.of(PtcagencydemoApplication.class);
        modules.forEach(System.out::println);
    }

    @Test
    void createDocumentation() {
        ApplicationModules modules = ApplicationModules.of(PtcagencydemoApplication.class);
        new Documenter(modules).writeDocumentation();
    }
}
