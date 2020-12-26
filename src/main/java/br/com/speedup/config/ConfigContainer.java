package br.com.speedup.config;

class ConfigContainer implements Config {

    private final String dbRoot = "/usr/share/speedup/";
    private final String indexRoot = "/usr/share/speedup/index/";

    @Override
    public String getDbRoot() {
        return dbRoot;
    }

    @Override
    public String getIndexRoot() {
        return indexRoot;
    }
}
