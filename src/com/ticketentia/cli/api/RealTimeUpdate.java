package com.ticketentia.cli.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ticketentia.cli.config.Storage;

public class RealTimeUpdate implements Runnable{
    private String sessionId;
    private HandleAPI handleAPI;
    private Storage storage;
    private String sessionData;

    public RealTimeUpdate(String sessionId) {
        this.sessionId = sessionId;
        this.storage = Storage.getInstance();
        this.handleAPI = new HandleAPI(storage.getJwtToken());
    }

    @Override
    public void run() {
        while (true) {
            try{
                String ticketPoolNew = handleAPI.ticketPoolData(sessionId);
                if (sessionData != null && !sessionData.equals(ticketPoolNew)){
                    sessionData = ticketPoolNew;
                    printData(sessionData);
                    Thread.sleep(5000);
                    continue;
                } else if (sessionData == null){
                    printData(ticketPoolNew);
                }

                sessionData = ticketPoolNew;
                Thread.sleep(5000);
            } catch (InterruptedException e){
                System.out.println("Interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void printData(String json){
        System.out.println("\nSession ID : " + getValue(json, "sessionId"));
        System.out.println("Current Tickets in Pool : " + getValue(json, "currentTicketsInPool"));
        System.out.println("Max Ticket Capacity : " + getValue(json, "maxTicketCapacity"));
        System.out.println("Release Ticket Count : " + getValue(json, "releaseTicketCount"));
        System.out.println("Total Tickets : " + getValue(json, "totalTickets") + "\n");


    }

    private String getValue(String json, String key) {
        try {
            // Parse the JSON string into a JsonObject
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

            // Check if the key exists
            if (jsonObject.has(key)) {
                return jsonObject.get(key).getAsString();
            } else {
                return null; // Key not found
            }
        } catch (Exception e) {
            return null;
        }
    }
}
