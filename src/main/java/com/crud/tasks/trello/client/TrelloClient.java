package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TrelloClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    private final RestTemplate restTemplate;

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;
    @Value("${trello.app.key}")
    private String trelloAppKey;
    @Value("${trello.app.token}")
    private String trelloToken;
    @Value("${trello.app.username}")
    private String username;

    public List<TrelloBoardDto> getTrelloBoards() {
        try {
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(buildURI(), TrelloBoardDto[].class);
            return Optional.ofNullable(boardsResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(p -> Objects.nonNull(p.getId()) && Objects.nonNull(p.getName()))
                    .filter(p -> p.getName().contains("Kodilla"))
                    .collect(Collectors.toList());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
//        return Arrays.asList(ofNullable(boardsResponse).orElse(new TrelloBoardDto[0]));
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto) {
        try {
            return restTemplate.postForObject(getUrl(trelloCardDto), null, CreatedTrelloCard.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new CreatedTrelloCard();
        }
    }

    private URI getUrl(TrelloCardDto trelloCardDto) {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/cards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPosition())
                .queryParam("idList", trelloCardDto.getListId())
                .build().encode().toUri();
    }

    private URI buildURI() {
        return UriComponentsBuilder.fromHttpUrl(
                trelloApiEndpoint + "/members/" + username + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build().encode().toUri();
    }

}
