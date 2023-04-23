package cinema.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class SeatDoesNotExist extends RuntimeException{
    public SeatDoesNotExist(String msg) {
        super(msg);
    }
}
