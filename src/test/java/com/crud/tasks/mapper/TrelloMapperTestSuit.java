package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TrelloMapperTestSuit {

    @Autowired
    private TrelloMapper mapper;

    @Test
    void testMapToCardDto() {
        // Given
        TrelloCard trelloCard = new TrelloCard("Shopping", "buy smth...", "1", "1234");
        // When
        TrelloCardDto trelloCardDto = mapper.mapToCardDto(trelloCard);
        // Then
        assertAll(
                () -> assertEquals("Shopping", trelloCardDto.getName()),
                () -> assertEquals("buy smth...", trelloCardDto.getDescription()),
                () -> assertEquals("1", trelloCardDto.getPosition()),
                () -> assertEquals("1234", trelloCardDto.getListId())
        );
    }

    @Test
    void testMapToCard() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Shopping", "buy smth...", "1", "1234");
        // When
        TrelloCard trelloCard = mapper.mapToCard(trelloCardDto);
        // Then
        assertAll(
                () -> assertEquals("Shopping", trelloCard.getName()),
                () -> assertEquals("buy smth...", trelloCard.getDescription()),
                () -> assertEquals("1", trelloCard.getPosition()),
                () -> assertEquals("1234", trelloCard.getListId())
        );
    }

    @Test
    void testMapToBoards() {
        // Given
        List<TrelloBoardDto> trelloBoardsDto = Stream.of(
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
        // When
        List<TrelloBoard> trelloBoards = mapper.mapToBoards(trelloBoardsDto);
        // Then
        assertAll(
                () -> assertEquals(4, trelloBoards.size()),
                () -> assertEquals(2, trelloBoards.get(0).getLists().size()),
                () -> assertEquals("name3", trelloBoards.get(1).getLists().get(2).getName())
        );
    }

    @Test
    void testMapToBoardsDto() {
        // Given
        List<TrelloBoard> trelloBoards = Stream.of(
                        new TrelloBoard("id1", "name1",
                                Arrays.asList(
                                        new TrelloList("id1", "name1", true),
                                        new TrelloList("id2", "name2", false)
                                )),
                        new TrelloBoard("id2", "name2",
                                Arrays.asList(
                                        new TrelloList("id1", "name1", true),
                                        new TrelloList("id2", "name2", false),
                                        new TrelloList("id3", "name3", true)
                                )))
                .collect(toList());
        // When
        List<TrelloBoardDto> trelloBoardsDto = mapper.mapToBoardsDto(trelloBoards);
        // Then
        assertAll(
                () -> assertEquals(2, trelloBoardsDto.size()),
                () -> assertEquals(3, trelloBoardsDto.get(1).getLists().size()),
                () -> assertEquals("name3", trelloBoards.get(1).getLists().get(2).getName())
        );
    }
}