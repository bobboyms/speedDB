package br.com.speedup.config;

class ConfigLinux implements Config {

    private final String dbRoot = "/home/thiago/database/";
    private final String indexRoot = "/home/thiago/database/index/";

    @Override
    public String getDbRoot() {
        return dbRoot;
    }

    @Override
    public String getIndexRoot() {
        return indexRoot;
    }
}
