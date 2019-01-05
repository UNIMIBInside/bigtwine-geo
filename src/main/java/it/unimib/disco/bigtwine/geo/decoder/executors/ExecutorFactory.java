package it.unimib.disco.bigtwine.geo.decoder.executors;

import it.unimib.disco.bigtwine.commons.executors.Executor;
import org.springframework.beans.factory.FactoryBean;

public class ExecutorFactory  implements FactoryBean<Executor> {
    @Override
    public Executor getObject() throws Exception {
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
