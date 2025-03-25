package org.scanner.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resp {
    private String name;
    private String url;
    private String coin1;
    private String coin2;
    private String nameLoader;
}
