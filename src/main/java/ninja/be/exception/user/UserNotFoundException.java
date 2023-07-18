package ninja.be.exception.user;

public class UserNotFoundException extends RuntimeException {
    private static final String MESSAGE = "유저 검색 결과가 없습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
