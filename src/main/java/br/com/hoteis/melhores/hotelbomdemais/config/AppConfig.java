package br.com.hoteis.melhores.hotelbomdemais.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.hoteis.melhores.hotelbomdemais.interceptor.AppInterceptor;


@Configuration
public class AppConfig implements WebMvcConfigurer {
	
	@Autowired
	private AppInterceptor interceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// adiciona o interceptor na aplicação
		registry.addInterceptor(interceptor);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/hotelbomdemais");
		ds.setUsername("root");
		ds.setPassword("");
		return ds;
	}
	
	@Bean
	public JpaVendorAdapter vendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");
		adapter.setPrepareConnection(true);
		adapter.setGenerateDdl(true);
		adapter.setShowSql(true);
		return adapter;
	}
}
