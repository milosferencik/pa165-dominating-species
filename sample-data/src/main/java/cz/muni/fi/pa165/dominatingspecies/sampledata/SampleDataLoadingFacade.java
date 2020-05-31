package cz.muni.fi.pa165.dominatingspecies.sampledata;

import java.io.IOException;

/**
 * @author Milos Ferencik
 */

public interface SampleDataLoadingFacade {
    /**
     * Insert data into the database
     * @throws IOException
     */
    void loadData() throws IOException;
}
