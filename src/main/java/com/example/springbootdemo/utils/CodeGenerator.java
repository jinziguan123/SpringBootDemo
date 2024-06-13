package com.example.springbootdemo.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;


/**
* mp 代码生成器
* by Cigacase
* @since 2024-06-13
*/
public class CodeGenerator {
    public static void main(String[] args) {
        generate();
    }

    private static void generate(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2b8",
                        "root",
                        "Jinziguan123")
                .globalConfig(builder -> {
                    builder.author("Cigacase")
                            .enableSwagger()
                            .fileOverride()
                            .outputDir("/Users/jinziguan/Desktop/SpringBootDemo/src/main/java/");
                })
                .packageConfig(builder -> {
                    builder.parent("com.example.springbootdemo")
                            .moduleName("")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "/Users/jinziguan/Desktop/SpringBootDemo/src/main/resources/mapper/"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("sys_user")
                            .addTablePrefix("t_", "sys_");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
