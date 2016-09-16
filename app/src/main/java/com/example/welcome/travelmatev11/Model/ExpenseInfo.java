package com.example.welcome.travelmatev11.Model;

/**
 * Created by Administrator on 22-Aug-16.
 */
public class ExpenseInfo {
    private String userName;
    private int eventId;
    private String expenseTitle;
    private Double expenseAmount;

    public ExpenseInfo(String userName, int eventId, String expenseTitle, Double expenseAmount) {
        this.userName = userName;
        this.eventId = eventId;
        this.expenseTitle = expenseTitle;
        this.expenseAmount = expenseAmount;
    }

    public String getUserName() {
        return userName;
    }

    public int getEventId() {
        return eventId;
    }

    public String getExpenseTitle() {
        return expenseTitle;
    }

    public Double getExpenseAmount() {
        return expenseAmount;
    }
}
