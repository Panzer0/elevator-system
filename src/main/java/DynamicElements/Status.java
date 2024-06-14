package DynamicElements;

/**
 * The Status enum represents the possible statuses of an elevator.
 * An elevator can be moving upward, moving downward, or idle.
 */
public enum Status {

    /**
     * Represents an elevator moving upwards.
     */
    UPWARD,

    /**
     * Represents an elevator moving downwards.
     */
    DOWNWARD,

    /**
     * Represents an elevator that is currently idle (no pending calls).
     */
    IDLE
}
