package fr.lh.resultsmanager.dtos.apiDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class StatusApiDto {

    private String longStatus;
    private String shortStatus;
    @Getter @Setter
    private int elapsed;

    @JsonProperty("long")
    public String getLongStatus() {
        return longStatus;
    }

    @JsonProperty("long")
    public void setLongStatus(String longStatus) {
        this.longStatus = longStatus;
    }

    @JsonProperty("short")
    public String getShortStatus() {
        return shortStatus;
    }

    @JsonProperty("short")
    public void setShortStatus(String shortStatus) {
        this.shortStatus = shortStatus;
    }

}
