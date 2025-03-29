package org.scanner.exchange.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answ {
    private String answer;
    private JsonNode document;
    private List<JsonNode> documents;
    private Date dateRequest;
}
