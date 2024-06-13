package ru.gb.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties("application.reader")
public class ReaderProperties {

  private Integer maxAllowedBook;

  private Map<String, String> tags;

}
