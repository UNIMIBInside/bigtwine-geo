package it.unimib.disco.bigtwine.config;

import it.unimib.disco.bigtwine.geo.decoder.Decoder;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Geo.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private String defaultDecoder = ApplicationDefaults.defaultDecoder;
    private final Executors executors = new Executors();
    private final Processors processors = new Processors();

    public static class Processors {

    }

    public static class Executors {

    }

    public String getDefaultDecoder() {
        return defaultDecoder;
    }

    public void setDefaultDecoder(String defaultDecoder) {
        this.defaultDecoder = defaultDecoder;
        Decoder decoder = Decoder.valueOf(defaultDecoder);
        Decoder.setDefault(decoder);
    }

    public Executors getExecutors() {
        return this.executors;
    }

    public Processors getProcessors() {
        return this.processors;
    }
}
