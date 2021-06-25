package Modelo;

import java.lang.reflect.Array;

public class Distance {
    Array destination_addresses, origin_addresses, rows, JSON, elements;
    String status;

    public Distance() {
    }

    public Array getJSON() {
        return JSON;
    }

    public void setJSON(Array JSON) {
        this.JSON = JSON;
    }

    public Array getElements() {
        return elements;
    }

    public void setElements(Array elements) {
        this.elements = elements;
    }

    public Array getDestination_addresses() {
        return destination_addresses;
    }

    public void setDestination_addresses(Array destination_addresses) {
        this.destination_addresses = destination_addresses;
    }

    public Array getOrigin_addresses() {
        return origin_addresses;
    }

    public void setOrigin_addresses(Array origin_addresses) {
        this.origin_addresses = origin_addresses;
    }

    public Array getRows() {
        return rows;
    }

    public void setRows(Array rows) {
        this.rows = rows;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
