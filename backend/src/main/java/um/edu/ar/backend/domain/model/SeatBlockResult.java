package um.edu.ar.backend.domain.model;


import lombok.Getter;

@Getter
public class SeatBlockResult {
    private final boolean result;
    private final String description;

    public SeatBlockResult(boolean result, String description) {
        this.result = result;
        this.description = description;
    }

}