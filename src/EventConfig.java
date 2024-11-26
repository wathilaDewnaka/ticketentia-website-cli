import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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

    // Sending the request
    public void sendRequest(String urlStr) throws Exception {
        String payload = toPayload();
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Send the request payload
        connection.getOutputStream().write(payload.getBytes("UTF-8"));

        // Get the response
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);
    }
}
