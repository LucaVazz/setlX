package org.randoom.setlx.utilities;

import org.randoom.setlx.exceptions.JVMIOException;

/**
 * This interface provides access to the I/O mechanisms of the target platform.
 */
public interface EnvironmentProvider {

    /**
     * Query if user provided input stream (e.g. stdin) has queued input.
     *
     * @return                True if input has queued input.
     * @throws JVMIOException Thrown in case of IO errors.
     */
    public abstract boolean inReady() throws JVMIOException;

    /**
     * Read a single line without termination character(s) from user provided
     * input stream (e.g. stdin).
     *
     * @return                Contents of the line read.
     * @throws JVMIOException Thrown in case of IO errors.
     */
    public abstract String  inReadLine() throws JVMIOException;

    /**
     * Write to standard output.
     *
     * @param msg Message to write.
     */
    public abstract void    outWrite(final String msg);

    /**
     * Write to standard error.
     *
     * @param msg Message to write.
     */
    public abstract void    errWrite(final String msg);

    /**
     * Display a message to the user, before querying for input.
     *
     * @param msg Message to display.
     */
    public abstract void    promptForInput(final String msg);

    /**
     * Get the tabulator character to use.
     *
     * @return Tabulator character.
     */
    public abstract String  getTab();

    /**
     * Get system dependent newline character sequence.
     *
     * @return Newline character sequence.
     */
    public abstract String  getEndl();

    /**
     * Allows modification of filename/path when reading files from within setlX.
     *
     * @param fileName Filename as passed to setlX via input/source code.
     * @return         Filename to use when opening for read/write.
     */
    public abstract String  filterFileName(final String fileName);

    /**
     * Allows modification of library-path when loading libraries from within setlX.
     *
     * @param name Filename as expected by setlX.
     * @return     Filename to use when opening for read/write.
     */
    public abstract String  filterLibraryName(final String name);

}

