package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void EpicSetIdTest(){
        Epic epic = new Epic("Epic", "Description");
        epic.setId(111);
        assertEquals(111, epic.getId(), "setId работает не корректно");
    }
}