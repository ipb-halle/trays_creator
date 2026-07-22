package com.location.creator.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
@ConfigurationProperties(prefix = "trial.signals")
public class SignalsTrialProperties {

    private String baseUrl;
    private String apiKey;
//    private String locationType;
//    private List<String> parentLocationFields;
//    private List<String> traysFields;
}
