package org.scanner.core.loader;

import com.fasterxml.jackson.databind.JsonNode;
import org.scanner.core.dto.Answ;
import org.scanner.core.dto.Resp;


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
