package co.microservicios.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Value {
    private String key;
    private String value;
    private List<Value> values;

   
}
