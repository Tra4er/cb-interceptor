//package com.itrach.cbinterceptor.demo.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DatabaseConfig {
//
//    @Bean
//    public DataSource dataSource() {
//
//        // no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
//        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//        EmbeddedDatabase db = builder
//                .setType(EmbeddedDatabaseType.HSQL) //.H2 or .DERBY
//                .addScript("db/sql/create-db.sql")
//                .addScript("db/sql/data.sql")
//                .build();
//        return db;
//    }
//
//    @Bean
//    public JdbcTemplate getJdbcTemplate() {
//        return new JdbcTemplate(dataSource());
//    }
//}
