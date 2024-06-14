package Simulation;

/**
 * The ExternalCall record represents an external call given to an elevator simulation.
 *
 * @param level     the level at which the external call is made
 * @param direction the direction of the external call (UP or DOWN)
 */
public record ExternalCall(int level, Direction direction) { }
