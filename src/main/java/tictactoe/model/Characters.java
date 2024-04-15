package tictactoe.model;

public enum Characters {
    CIRCLE('O', "round"),
    CROSS('X', "cross"),
    EMPTY('-', "empty"),
    DRAW('D', "draw");

    private final char symbol;
    private final String name;

    /**
     * Constructor for the Characters enum
     *
     * @param symbol the symbol of the character
     * @param name   the name of the character
     */
    Characters(char symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    /**
     * Get the symbol of the character
     *
     * @return the symbol of the character
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Get the name of the character
     *
     * @return the name of the character
     */
    public String getName() {
        return name;
    }
}