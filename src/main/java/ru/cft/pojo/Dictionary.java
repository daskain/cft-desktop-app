package ru.cft.pojo;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {

    public static Map<String, String> carRentalStatus;
    static {
        carRentalStatus = new HashMap<>();
        carRentalStatus.put("free", "Доступна");
        carRentalStatus.put("rented", "В аренде");
        carRentalStatus.put("unavailable", "Не доступна");
    }

    public static Map<Boolean, String> clientStatus;
    static {
        clientStatus = new HashMap<>();
        clientStatus.put(true, "Активен");
        clientStatus.put(false, "Заблокирован");
    }
}
