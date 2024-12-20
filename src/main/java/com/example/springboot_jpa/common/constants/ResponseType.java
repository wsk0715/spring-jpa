package com.example.springboot_jpa.common.constants;

public enum ResponseType {
	SUCCESS,  // http status - 2xx
	REDIRECTION,  // 3xx
	ERROR_CLIENT,  // 4xx
	ERROR_SERVER  // 5xx
}