package org.scanner.exchange.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scanner.core.dto.Resp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeLoader {
    private JsonNode document;
    private Resp response;

}
