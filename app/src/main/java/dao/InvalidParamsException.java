package dao;

/**
 * Created by xiao on 2016/8/30.
 */
public class InvalidParamsException extends Exception {
    public InvalidParamsException() {
        super();
    }

    public InvalidParamsException(String message) {
        super(message);
    }
}
