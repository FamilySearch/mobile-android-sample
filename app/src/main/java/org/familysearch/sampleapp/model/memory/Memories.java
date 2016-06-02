package org.familysearch.sampleapp.model.memory;

import java.util.List;

/**
 * @author Eduardo Flores
 */
public class Memories
{
    private List<SourceDescriptions> sourceDescriptions;

    public List<SourceDescriptions> getSourceDescriptions() {
        return sourceDescriptions;
    }

    public void setSourceDescriptions(
            List<SourceDescriptions> sourceDescriptions) {
        this.sourceDescriptions = sourceDescriptions;
    }
}
