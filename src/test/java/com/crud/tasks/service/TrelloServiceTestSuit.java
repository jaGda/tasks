package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrelloServiceTestSuit {

    @InjectMocks
    private TrelloService service;

    @Mock
    private TrelloClient client;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    void testFetchTrelloBoards() {
        // Given
        List<TrelloBoardDto> dtos = Stream.of(
                        new TrelloBoardDto("id1", "name1",
                                Arrays.asList(
                                        new TrelloListDto("id1", "name1", true),
                                        new TrelloListDto("id2", "name2", false)
                                )),
                        new TrelloBoardDto("id2", "name2",
                                Arrays.asList(
                                        new TrelloListDto("id1", "name1", true),
                                        new TrelloListDto("id2", "name2", false),
                                        new TrelloListDto("id3", "name3", true)
                                )),
                        new TrelloBoardDto("id3", "name3",
                                Arrays.asList(
                                        new TrelloListDto("id1", "name1", true),
                                        new TrelloListDto("id2", "name2", false)
                                )),
                        new TrelloBoardDto("id4", "name4",
                                Arrays.asList(
                                        new TrelloListDto("id1", "name1", true),
                                        new TrelloListDto("id2", "name2", false)
                                )))
                .collect(toList());
        when(client.getTrelloBoards()).thenReturn(dtos);

        // When
        List<TrelloBoardDto> result = service.fetchTrelloBoards();

        // Then
        assertAll(
                () -> assertEquals(4, result.size()),
                () -> assertEquals("name3", result.get(2).getName()),
                () -> assertEquals(2, result.get(3).getLists().size()),
                () -> assertTrue(result.get(3).getLists().get(0).isClosed()),
                () -> assertFalse(result.get(3).getLists().get(1).isClosed())
        );
    }

    @Test
    void testCreateTrelloCard() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("card1", "desc1", "position1", "listID_1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("id1", "name1", "name1/id1");
        Mail mail = Mail.builder().build();
        when(client.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        when(adminConfig.getAdminMail()).thenReturn("");

        // When
        CreatedTrelloCardDto result = service.createTrelloCard(trelloCardDto);

        // Then
        assertAll(
                () -> assertEquals("id1", result.getId()),
                () -> assertEquals("name1", result.getName()),
                () -> assertEquals("name1/id1", result.getShortUrl())
        );
    }

}