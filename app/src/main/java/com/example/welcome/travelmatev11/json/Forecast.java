package com.example.welcome.travelmatev11.json;


import java.util.List;

public class Forecast {

    private List<FiveWeathers> list;

    public Forecast(List<FiveWeathers> list) {
        this.list = list;
    }

    public List<FiveWeathers> getList() {
        return list;
    }
}
