package it.unimib.disco.bigtwine.geo.decoder.processors;

import org.springframework.beans.factory.FactoryBean;

public class ProcessorFactory implements FactoryBean<Processor> {
    @Override
    public Processor getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
