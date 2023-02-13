package co.microservicios.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    private int code;
    private String description;
    private List<Value> values;
    private List<Response> responses;

    public Response(){
    }

    public Response(int code, String description) {
        this.code = code;
        this.description = description;
        this.values = new ArrayList<>();
        this.responses = new ArrayList<>();
    }

   
}
