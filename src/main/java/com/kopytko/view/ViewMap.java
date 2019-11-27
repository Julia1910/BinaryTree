package com.kopytko.view;

import com.kopytko.controller.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class ViewMap {
    private static final Logger log = LogManager.getLogger(ViewMap.class);
    private Controller controller;
    private Map<String, String> menu;
    private Map<String, Printable> methodsMenu;
    private static Scanner input = new Scanner(System.in);

    public ViewMap() {
        controller = new ControllerImpl();
        menu = new LinkedHashMap<>();
        menu.put("1", "  1 - get Value from Tree by Key");
        menu.put("2", "  2 - remove Node");
        menu.put("3", "  3 - put new Value");
        menu.put("Q", "  Q - exit");

        methodsMenu = new LinkedHashMap<>();
        methodsMenu.put("1", this::pressButton1);
        methodsMenu.put("2", this::pressButton2);
        methodsMenu.put("3", this::pressButton3);
    }

    private void pressButton1() {
        log.info("Enter key");
        int key = input.nextInt();
        log.info(controller.getValueByKey(key));
    }

    private void pressButton2() {
        log.info("Enter key");
        int key = input.nextInt();
        controller.removeNode(key);
    }

    private void pressButton3() {
        log.info("Enter new value");
        String value = input.nextLine();
        log.info("Input key");
        int key = input.nextInt();
        controller.putNewNode(key, value);
    }


    private void outputMenu() {
        log.info("\nMENU:");
        for (String str : menu.values()) {
            log.info(str);
        }
    }

    public void show() {
        String keyMenu;
        do {
            outputMenu();
            log.info("Please, select menu point.");
            keyMenu = input.nextLine().toUpperCase();
            try {
                methodsMenu.get(keyMenu).print();
            } catch (Exception e) {
            }
        } while (!keyMenu.equals("Q"));
    }

}
