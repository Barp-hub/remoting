package io.github.riwcwt.grpc.registry.zookeeper;

/**
 * Created by michael on 2017-03-02.
 */
public class Instance {
    private String version;
    private String description;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "version='" + version + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
