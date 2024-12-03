package com.ticketentia.cli.config;

import com.ticketentia.cli.enums.EventType;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class EventConfig {
    private String eventName;
    private String eventVenue;
    private double ticketPrice;
    private String eventDate;
    private String eventTime;
    private String eventCategory;
    private String eventDescription;
    private String eventType;
    private double vipDiscount;
    private int totalTickets;
    private int maximumPoolCapacity;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    // Constructor
    public EventConfig(String eventName, String eventVenue, double ticketPrice, String eventDate,
                       String eventTime, String eventCategory, String eventDescription,
                       String eventType, double vipDiscount, int totalTickets,
                       int maximumPoolCapacity, int ticketReleaseRate, int customerRetrievalRate) {
        this.eventName = eventName;
        this.eventVenue = eventVenue;
        this.ticketPrice = ticketPrice;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventCategory = eventCategory;
        this.eventDescription = eventDescription;
        this.eventType = eventType;
        this.vipDiscount = vipDiscount;
        this.totalTickets = totalTickets;
        this.maximumPoolCapacity = maximumPoolCapacity;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public EventConfig(){

    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        if (eventName.length() >= 4){
            this.eventName = eventName;
            return;
        }

        throw new IllegalArgumentException();
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        if (ticketPrice <= 100){
            throw new IllegalArgumentException();
        }

        this.ticketPrice = ticketPrice;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        if (eventVenue.length() >= 4) {
            this.eventVenue = eventVenue;
            return;
        }

        throw new IllegalArgumentException();
    }



    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) throws IllegalArgumentException {
        // Define the expected date format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // Ensure strict parsing

        try {
            // Parse the input date
            Date inputDate = sdf.parse(eventDate);
            Date today = new Date(); // Current date

            // Check if the date is greater than today
            if (!inputDate.after(today)) {
                throw new IllegalArgumentException("The event date must be greater than today's date.");
            }

            // If valid, set the event date
            this.eventDate = eventDate;

        } catch (ParseException e) {
            // Handle invalid format
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd.");
        }
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        String timePattern = "^([01]\\d|2[0-3]):([0-5]\\d)$";
        if (!eventTime.matches(timePattern)){
            throw new IllegalArgumentException();
        }
        this.eventTime = eventTime;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        if (!(eventCategory.equals("LIVE CONCERT") ||
                eventCategory.equals("WEBINAR") ||
                eventCategory.equals("WORKSHOP") ||
                eventCategory.equals("MUSICAL CONCERT") ||
                eventCategory.equals("CONFERENCE"))) {
            throw new IllegalArgumentException();
        }

        this.eventCategory = eventCategory;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        if (eventDescription.length() < 20){
            throw new IllegalArgumentException();
        }

        this.eventDescription = eventDescription;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        if (!(eventType.equals(EventType.VIP.name()) || eventType.equals(EventType.REGULAR.name()))){
            throw new IllegalArgumentException();
        }
        this.eventType = eventType;
    }

    public double getVipDiscount() {
        return vipDiscount;
    }

    public void setVipDiscount(double vipDiscount) {
        if (vipDiscount > 60 || vipDiscount < 0){
            throw new IllegalArgumentException();
        }

        this.vipDiscount = vipDiscount;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        if (totalTickets < 10){
            throw new IllegalArgumentException();
        }
        this.totalTickets = totalTickets;
    }

    public int getMaximumPoolCapacity() {
        return maximumPoolCapacity;
    }

    public void setMaximumPoolCapacity(int maximumPoolCapacity) {
        if (maximumPoolCapacity >= totalTickets || maximumPoolCapacity < 5){
            throw new IllegalArgumentException();
        }
        this.maximumPoolCapacity = maximumPoolCapacity;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        if (ticketReleaseRate <= 0 || ticketReleaseRate > 10){
            throw new IllegalArgumentException();
        }
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        if (customerRetrievalRate <= 0 || customerRetrievalRate > 10){
            throw new IllegalArgumentException();
        }
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public String toPayload() throws UnsupportedEncodingException {
        // Format the date to "yyyy-MM-dd HH:mm:ss.SSSSSS"
        // Parse the input date
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inputFormat.parse(getEventDate());

            // Desired output format
            SimpleDateFormat outputFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (z)");
            outputFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata")); // Set the timezone

            // Format the date
            String formattedDate = outputFormat.format(date);
            Storage storage = Storage.getInstance();
            System.out.println(storage.getUserId());
            return "vendorId=" + URLEncoder.encode(String.valueOf(storage.getUserId()), "UTF-8") +
                    "&eventName=" + URLEncoder.encode(eventName, "UTF-8") +
                    "&eventVenue=" + URLEncoder.encode(eventVenue, "UTF-8") +
                    "&ticketPrice=" + URLEncoder.encode(String.valueOf(ticketPrice), "UTF-8") +
                    "&eventDate=" + URLEncoder.encode(formattedDate, "UTF-8") + // Use the precise date format
                    "&eventTime=" + URLEncoder.encode(eventTime, "UTF-8") +
                    "&eventCategory=" + URLEncoder.encode(eventCategory, "UTF-8") +
                    "&eventDescription=" + URLEncoder.encode(eventDescription, "UTF-8") +
                    "&eventType=" + URLEncoder.encode(eventType, "UTF-8") +
                    "&vipDiscount=" + URLEncoder.encode(String.valueOf(vipDiscount), "UTF-8") +
                    "&totalTickets=" + URLEncoder.encode(String.valueOf(totalTickets), "UTF-8") +
                    "&maxTicketCapacity=" + URLEncoder.encode(String.valueOf(maximumPoolCapacity), "UTF-8") +
                    "&ticketReleaseRate=" + URLEncoder.encode(String.valueOf(ticketReleaseRate), "UTF-8") +
                    "&customerRetrievalRate=" + URLEncoder.encode(String.valueOf(customerRetrievalRate), "UTF-8");

        } catch (Exception e){
            return null;
        }
    }

}
