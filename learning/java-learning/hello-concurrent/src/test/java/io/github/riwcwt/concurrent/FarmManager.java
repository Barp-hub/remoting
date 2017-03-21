package io.github.riwcwt.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by michael on 2017-03-13.
 */
public class FarmManager {

    private static final Logger logger = LoggerFactory.getLogger(FarmManager.class);

    private ArrayList<Farm> farmList = new ArrayList<Farm>();
    private static ArrayList<FarmData> farmDataList = null;


    private Object monitor = new Object();

    public Farm getFarm(int id) {
        try {
            for (Farm farm : farmList) {
                if (farm.getId() == id) {
                    return farm;
                }
            }

            synchronized (monitor) {
                for (Farm farm : farmList) {
                    if (farm.getId() == id) {
                        return farm;
                    }
                }

                for (FarmData farmData : farmDataList) {
                    if (farmData.getId().equals(id)) {
                        Farm farm = new Farm();
                        farm.setId(id);

                        TimeUnit.MILLISECONDS.sleep((int) (Math.random() * 10));

                        farmList.add(farm);
                        return farm;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public void init() {
        farmDataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            FarmData data = new FarmData();
            data.setId(i);
            farmDataList.add(data);
        }
    }
}


class Farm {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

class FarmData {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}