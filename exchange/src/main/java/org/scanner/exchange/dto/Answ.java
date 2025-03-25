package org.scanner.exchange.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scanner.core.dto.Resp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answ {
    private String answer;
    private JsonNode document;
//    private Resp response;
    private Date dateRequest;
}
