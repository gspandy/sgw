package cn.wanglin.sgw.exchange;

import groovy.lang.GroovyClassLoader;

public class GroovyService {

    static GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    public static <T> T getObject(String scriptName, String content, Class<T> clz) throws Exception {
        Class  scriptClass = getGroovyClassLoader().parseClass(content, scriptName);
        Object obj         = scriptClass.newInstance();
        return (T) obj;
    }

    private synchronized static GroovyClassLoader getGroovyClassLoader() {
        if (groovyClassLoader == null)
            GroovyService.groovyClassLoader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader());

        return GroovyService.groovyClassLoader;
    }


}
