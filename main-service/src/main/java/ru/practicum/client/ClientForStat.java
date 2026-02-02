package ru.practicum.client;

import lombok.NoArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class ClientForStat {
    private final static String BASE_URL = "http://localhost:9090/";
    private final static String URL_HIT = BASE_URL + "hit";
    private final static String URL_STATS = BASE_URL + "stats/uniq";
    RestTemplate restTemplate = new RestTemplate();

    public void hit(String ip, String Uri) {
        EndpointHitMain hitDto = new EndpointHitMain();
        hitDto.setApp("main-service");
        hitDto.setUri(Uri);
        hitDto.setIp(ip);
        hitDto.setTimestamp(LocalDateTime.now());
        restTemplate.postForObject(URL_HIT, hitDto, Void.class);
    }

    public void hit(String ip) {
        hit(ip, "/events");
    }

    public List<ViewStatsDtoMain> getStats(List<String> uris) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_STATS);

        if (uris != null && !uris.isEmpty()) {
            for (String uri : uris) {
                builder.queryParam("uris", uri);
            }
        }

        String urlWithParams = builder.toUriString();
        ResponseEntity<List<ViewStatsDtoMain>> responseEntity = restTemplate.exchange(
                urlWithParams,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return responseEntity.getBody();
    }
}
