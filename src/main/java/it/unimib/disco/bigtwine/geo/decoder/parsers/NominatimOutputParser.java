package it.unimib.disco.bigtwine.geo.decoder.parsers;

import it.unimib.disco.bigtwine.commons.models.Address;

import java.io.Reader;

public final class NominatimOutputParser implements OutputParser {
    @Override
    public Reader getReader() {
        return null;
    }

    @Override
    public void setReader(Reader reader) {

    }

    @Override
    public Address[] items() {
        return new Address[0];
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Address next() {
        return null;
    }
}
