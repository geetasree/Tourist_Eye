package com.example.welcome.travelmatev11.Model;

/**
 * Created by Administrator on 22-Aug-16.
 */
public class EventInfo {
    private String userName;
    private String eventName;
    private Double eventBudget;
    private String eventDateFrom;
    private String eventDateTo;
    private int eventId;

    public EventInfo(String userName, String eventName, Double eventBudget, String eventDateFrom, String eventDateTo ) {
        this.eventBudget = eventBudget;
        this.eventDateFrom = eventDateFrom;
        this.eventDateTo = eventDateTo;
        this.eventName = eventName;
        this.userName = userName;
    }

    public EventInfo(int eventId, String userName, String eventName, Double eventBudget, String eventDateFrom, String eventDateTo ) {
        this.eventBudget = eventBudget;
        this.eventDateFrom = eventDateFrom;
        this.eventDateTo = eventDateTo;
        this.eventName = eventName;
        this.userName = userName;
        this.eventId = eventId;
    }

    public Double getEventBudget() {
        return eventBudget;
    }

    public int getEventId() {
        return eventId;
    }

    public String getEventDateFrom() {
        return eventDateFrom;
    }

    public String getEventDateTo() {
        return eventDateTo;
    }

    public String getEventName() {
        return eventName;
    }

    public String getUserName() {
        return userName;
    }
}
