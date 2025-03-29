package org.scanner.exchange.loader;

import com.fasterxml.jackson.databind.JsonNode;
import org.scanner.core.dto.Resp;
import org.scanner.exchange.dto.Answ;
import org.scanner.exchange.dto.NodeLoader;

import java.sql.Date;
import java.util.List;


public interface Loader {
    void load(Resp respons, Answ answer);
    void loadPairs(Resp respons, Answ answer);
    JsonNode document(JsonNode message);
    List<JsonNode> documents(JsonNode message);

//    default void setLoader(NodeLoader nodeLoader) {
//
//    }
}
