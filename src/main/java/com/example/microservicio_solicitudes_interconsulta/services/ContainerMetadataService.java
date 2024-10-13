package com.example.microservicio_solicitudes_interconsulta.services;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@Service
public class ContainerMetadataService {

	private static final String ENVIRONMENT_VARIABLE_ECS_CONTAINER_METADATA_URI = "ECS_CONTAINER_METADATA_URI";

	private static final String DEFAULT_VALUE = "EMPTY";

	private static final Logger LOGGER = LoggerFactory.getLogger(ContainerMetadataService.class);

	// @Value(${ENVIRONMENT_VARIABLE_NAME:DEFAULT_VALUE})
	@Value("${" + ENVIRONMENT_VARIABLE_ECS_CONTAINER_METADATA_URI + ":" + DEFAULT_VALUE + "}")
	private String containerMetadataUri;

	public String retrieveContainerMetadataInfo() {

		if (containerMetadataUri.contains(DEFAULT_VALUE)) {
			LOGGER.info("Environment Variable Not Available - " + ENVIRONMENT_VARIABLE_ECS_CONTAINER_METADATA_URI);
			return "NA";
		}

		return new RestTemplate().getForObject(containerMetadataUri, String.class);
	}

}
