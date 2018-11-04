public class PayloadLimitViolationException extends RuntimeException {
    public PayloadLimitViolationException() {}

    public PayloadLimitViolationException(String message) {
        super(message);
    }
}
