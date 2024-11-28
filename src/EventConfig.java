import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventConfig {
    private long vendorId;
    private String eventName;
    private String eventVenue;
    private String ticketPrice;
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
    public EventConfig(long vendorId, String eventName, String eventVenue, String ticketPrice, String eventDate,
                       String eventTime, String eventCategory, String eventDescription,
                       String eventType, double vipDiscount, int totalTickets,
                       int maximumPoolCapacity, int ticketReleaseRate, int customerRetrievalRate) {
        this.vendorId = vendorId;
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

    public long getVendorId() {
        return vendorId;
    }

    public void setVendorId(long vendorId) {
        this.vendorId = vendorId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        if (eventName.length() > 4){
            this.eventName = eventName;
            return;
        }

        throw new IllegalArgumentException();
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        if (eventVenue.length() > 4) {
            this.eventVenue = eventVenue;
        }

        throw new IllegalArgumentException();
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
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
        this.eventTime = eventTime;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public double getVipDiscount() {
        return vipDiscount;
    }

    public void setVipDiscount(double vipDiscount) {
        this.vipDiscount = vipDiscount;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getMaximumPoolCapacity() {
        return maximumPoolCapacity;
    }

    public void setMaximumPoolCapacity(int maximumPoolCapacity) {
        this.maximumPoolCapacity = maximumPoolCapacity;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public String toPayload() throws UnsupportedEncodingException {
        // Format the date to "yyyy-MM-dd HH:mm:ss.SSSSSS"
        SimpleDateFormat preciseDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        String eventDateString = preciseDateFormat.format(new Date());
        System.out.println("Formatted eventDate: " + eventDateString);

        return "vendorId=" + URLEncoder.encode(String.valueOf(vendorId), "UTF-8") +
                "&eventName=" + URLEncoder.encode(eventName, "UTF-8") +
                "&eventVenue=" + URLEncoder.encode(eventVenue, "UTF-8") +
                "&ticketPrice=" + URLEncoder.encode(ticketPrice, "UTF-8") +
                "&eventDate=" + URLEncoder.encode("2024-11-28 00:00:00.000000", "UTF-8") + // Use the precise date format
                "&eventTime=" + URLEncoder.encode(eventTime, "UTF-8") +
                "&eventCategory=" + URLEncoder.encode(eventCategory, "UTF-8") +
                "&eventDescription=" + URLEncoder.encode(eventDescription, "UTF-8") +
                "&eventType=" + URLEncoder.encode(eventType, "UTF-8") +
                "&vipDiscount=" + URLEncoder.encode(String.valueOf(vipDiscount), "UTF-8") +
                "&totalTickets=" + URLEncoder.encode(String.valueOf(totalTickets), "UTF-8") +
                "&maxTicketCapacity=" + URLEncoder.encode(String.valueOf(maximumPoolCapacity), "UTF-8") +
                "&ticketReleaseRate=" + URLEncoder.encode(String.valueOf(ticketReleaseRate), "UTF-8") +
                "&customerRetrievalRate=" + URLEncoder.encode(String.valueOf(customerRetrievalRate), "UTF-8");
    }

}
