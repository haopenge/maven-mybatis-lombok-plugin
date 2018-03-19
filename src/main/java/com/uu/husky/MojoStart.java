package com.uu.husky;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @goal execute
 */
public class MojoStart extends AbstractMojo {

    /**
     * @parameter default-value="generatorConfig.xml"
     */
    private String configFilePath;


    public void execute() throws MojoExecutionException, MojoFailureException {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File(configFilePath);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        try {
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                    callback, warnings);
            myBatisGenerator.generate(null);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

}
