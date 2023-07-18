package ninja.be.exception.user;

public class EmailDuplicateException extends RuntimeException {
    private static final String MESSAGE = "이메일이 중복됩니다.";

    public EmailDuplicateException() {
        super(MESSAGE);
    }
}
