package com.crud.tasks.trello.validator;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TrelloValidatorTestSuit {

    @Autowired
    private TrelloValidator trelloValidator;

    @Test
    void testValidateTrelloBoards() {
        // Given
        List<TrelloBoard> trelloBoards = Stream.of(
                        new TrelloBoard("id1", "name1",
                                Arrays.asList(
                                        new TrelloList("id1", "name1", true),
                                        new TrelloList("id2", "name2", false)
                                )),
                        new TrelloBoard("id2", "TesT",
                                Arrays.asList(
                                        new TrelloList("id1", "name1", true),
                                        new TrelloList("id2", "name2", false)
                                )),
                        new TrelloBoard("id3", "tESt",
                                Arrays.asList(
                                        new TrelloList("id1", "name1", true),
                                        new TrelloList("id2", "name2", false)
                                )),
                        new TrelloBoard("id4", "name2",
                                Arrays.asList(
                                        new TrelloList("id1", "name1", true),
                                        new TrelloList("id2", "name2", false),
                                        new TrelloList("id3", "name3", true)
                                )))
                .collect(toList());

        // When
        List<TrelloBoard> result = trelloValidator.validateTrelloBoards(trelloBoards);

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void testValidateCard() {
        // Given
        TrelloCard trelloCard = new TrelloCard("test_name", "description", "position", "list ID");
        Logger VALIDATOR_LOGGER = (Logger) LoggerFactory.getLogger(TrelloValidator.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        VALIDATOR_LOGGER.addAppender(listAppender);

        // When
        trelloValidator.validateCard(trelloCard);

        // Then
        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals("Someone is testing my application!", logsList.get(0)
                .getMessage());
        assertEquals(Level.INFO, logsList.get(0)
                .getLevel());
    }
}