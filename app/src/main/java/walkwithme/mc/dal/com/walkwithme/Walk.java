package walkwithme.mc.dal.com.walkwithme;

public class Walk {
    //Elements used for the Home Screen
    private String eventName;
    private String eventDatetime;
    private String eventLocation;
    private String eventImageURL;
    //Remaining elements for Create and View Activity
    private int eventId;
    private Double eventCoordinateLang;
    private Double eventCoordinateLong;
    private String eventDescription;
    private String eventWeather;

    public Walk(Integer id, String name, String datetime, String location, String imgURL) {

        this.eventName = name;
        this.eventDatetime = datetime;
        this.eventLocation = location;
        this.eventImageURL = imgURL;

        this.eventId = id;
        this.eventCoordinateLang = 0.0;
        this.eventCoordinateLong = 0.0;
        this.eventDescription = "";
        this.eventWeather = "";

    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDatetime() {
        return eventDatetime;
    }

    public void setEventDatetime(String eventDatetime) {
        this.eventDatetime = eventDatetime;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventImageURL() {
        return eventImageURL;
    }

    public void setEventImageURL(String eventImageURL) {
        this.eventImageURL = eventImageURL;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Double getEventCoordinateLang() {
        return eventCoordinateLang;
    }

    public void setEventCoordinateLang(Double eventCoordinateLang) {
        this.eventCoordinateLang = eventCoordinateLang;
    }

    public Double getEventCoordinateLong() {
        return eventCoordinateLong;
    }

    public void setEventCoordinateLong(Double eventCoordinateLong) {
        this.eventCoordinateLong = eventCoordinateLong;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventWeather() {
        return eventWeather;
    }

    public void setEventWeather(String eventWeather) {
        this.eventWeather = eventWeather;
    }
}



