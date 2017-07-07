package com.machine.design.pattern.behavioral.template;

/**
 * Created by michael on 2017-06-27.
 */
public class HousingClient {

    public static void main(String[] args) {
        HouseTemplate houseType = new WoodenHouse();

        //using template method
        houseType.buildHouse();
        System.out.println("************");
        houseType = new GlassHouse();
        houseType.buildHouse();
    }

}
