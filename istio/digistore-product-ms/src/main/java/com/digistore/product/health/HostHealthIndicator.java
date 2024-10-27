package com.digistore.product.health;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.LivenessState;
import org.springframework.stereotype.Component;

@Component("host")
public class HostHealthIndicator implements HealthIndicator {
	
	@Autowired
	private ApplicationAvailability availability;

	@Override
	public Health health() {
		LivenessState state = availability.getLivenessState();
		Health.Builder builder = state == LivenessState.CORRECT ? Health.up() : Health.down();
		try {
			InetAddress inet = InetAddress.getLocalHost();
			builder.withDetail("host", inet.getHostName());
			builder.withDetail("version", System.getenv("version"));
		} catch (UnknownHostException e) {
		}
		return builder.build();
	}

}
