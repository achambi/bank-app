package bo.com.mondongo.bankapp.entity;

public enum Currency {

    BOLIVIANOS("201"),
    DOLLARS("202");

    private String value;

    Currency(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
