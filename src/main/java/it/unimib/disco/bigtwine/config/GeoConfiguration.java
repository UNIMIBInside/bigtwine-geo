package it.unimib.disco.bigtwine.config;

import it.unimib.disco.bigtwine.geo.decoder.executors.ExecutorFactory;
import it.unimib.disco.bigtwine.geo.decoder.processors.ProcessorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeoConfiguration {
    private ApplicationProperties appProps;

    public GeoConfiguration(ApplicationProperties appProps) {
        this.appProps = appProps;
    }


    @Bean
    public ExecutorFactory getExecutorFactory() {
        return new ExecutorFactory(this.appProps.getExecutors());
    }

    @Bean
    public ProcessorFactory getProcessorFactory() {
        return new ProcessorFactory(this.appProps.getProcessors(), this.getExecutorFactory());
    }
}
