package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/trello")
@RequiredArgsConstructor
public class TrelloController {

    private final TrelloClient trelloClient;

    @GetMapping("getTrelloBoards")
    public void getTrelloBoards() {
        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        if (trelloBoards.isEmpty()) {
            System.out.println("There are no lists...");
        } else {
            trelloBoards.stream()
                    .filter(list -> Optional.ofNullable(list.getId()).isPresent()
                            && Optional.ofNullable(list.getName()).isPresent()
                            && list.getName().contains("Kodilla"))
                    .forEach(list -> System.out.println(list.getId() + " " + list.getName()));
        }
    }

}
