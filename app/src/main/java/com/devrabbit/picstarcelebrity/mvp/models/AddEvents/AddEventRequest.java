package com.devrabbit.picstarcelebrity.mvp.models.AddEvents;

public class AddEventRequest

{
    public int event_id=0;
    public String celebrity_id;
    public String loggedin_user_id;
    public String event_location;
    public String event_name;
    public String event_desc;
    public String event_date;


    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getCelebrity_id() {
        return celebrity_id;
    }

    public void setCelebrity_id(String celebrity_id) {
        this.celebrity_id = celebrity_id;
    }

    public String getLoggedin_user_id() {
        return loggedin_user_id;
    }

    public void setLoggedin_user_id(String loggedin_user_id) {
        this.loggedin_user_id = loggedin_user_id;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_desc() {
        return event_desc;
    }

    public void setEvent_desc(String event_desc) {
        this.event_desc = event_desc;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }
}
