package co.microservicios.exceptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import co.microservicios.enums.ErrorEnum;
//TODO: Add documentation to class and methods
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return response.getRawStatusCode() >= 400;
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		StringBuilder textBuilder = new StringBuilder();

		try (Reader reader = new BufferedReader(
				new InputStreamReader(response.getBody(), Charset.forName(StandardCharsets.UTF_8.name())))) {
			int c = 0;
			while ((c = reader.read()) != -1) {
				textBuilder.append((char) c);
			}
		}
		if (response.getRawStatusCode() == 500) {
			throw new ApiException(ErrorEnum.TECHNICAL, textBuilder.toString());
		}
	}
}

