package com.example.springcashier;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class Order {

    private String drink;
    private String milk;
    private String size;
    private String total;
    private String register;
    private String status;

    public static String getCost(String drink, String size) {
        
        String total;
        if (drink.equals("Caffe Latte")) {
            if (size.equals("Venti")) {
                total = "$3.95";
            } else if (size.equals("Grande")) {
                total = "$3.65";
            } else {
                total = "$2.95";
            }
        } else if (drink.equals("Caffe Americano")) {
            if (size.equals("Venti")) {
                total = "$2.95";
            } else if (size.equals("Grande")) {
                total = "$2.65";
            } else {
                total = "$2.25";
            }
        } else if (drink.equals("Caffe Mocha")) {
            if (size.equals("Venti")) {
                total = "$4.45";
            } else if (size.equals("Grande")) {
                total = "$4.15";
            } else {
                total = "$3.45";
            }
        } else if (drink.equals("Cappuccino")) {
            if (size.equals("Venti")) {
                total = "$3.95";
            } else if (size.equals("Grande")) {
                total = "$3.65";
            } else {
                total = "$2.95";
            }
        } 
        //else Espresso
        else {
            if (size.equals("Tall")) {
                total = "$1.95";
            } else {
                total = "$1.75";
            }
        }

        return total;
    }

}

/*
 * 
 * https://priceqube.com/menu-prices/%E2%98%95-starbucks
 * 
 * var DRINK_OPTIONS = [ "Caffe Latte", "Caffe Americano", "Caffe Mocha",
 * "Espresso", "Cappuccino" ];
 * var MILK_OPTIONS = [ "Whole Milk", "2% Milk", "Nonfat Milk", "Almond Milk",
 * "Soy Milk" ];
 * var SIZE_OPTIONS = [ "Short", "Tall", "Grande", "Venti", "Your Own Cup" ];
 * 
 * Caffè Latte
 * =============
 * tall $2.95
 * grande $3.65
 * venti $3.95 (Your Own Cup)
 * 
 * Caffè Americano
 * ===============
 * tall $2.25
 * grande $2.65
 * venti $2.95 (Your Own Cup)
 * 
 * Caffè Mocha
 * =============
 * tall $3.45
 * grande $4.15
 * venti $4.45 (Your Own Cup)
 * 
 * Cappuccino
 * ==========
 * tall $2.95
 * grande $3.65
 * venti $3.95 (Your Own Cup)
 * 
 * Espresso
 * ========
 * short $1.75
 * tall $1.95
 * 
 */
