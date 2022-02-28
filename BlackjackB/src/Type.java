public enum Type {
    DIAMOND("♦"),
    HEART("♥"),
    SPADE("♠"),
    CLUB("♣"),
    ;

    private String value;
    Type(String value) {
        this.value = value;
    }
    public String toString(){
        return this.value;
    }
}
