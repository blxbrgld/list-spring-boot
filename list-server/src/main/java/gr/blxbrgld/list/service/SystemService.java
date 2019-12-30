package gr.blxbrgld.list.service;

/**
 * System service interface
 * @author blxbrgld
 */
public interface SystemService {

    /**
     * Re-Build Lucene Index From Scratch
     * @param synchronously Index Synchronously or Asynchronously
     */
    void initializeLucene(boolean synchronously);
}
