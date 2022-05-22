package com.example.planningpoker.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SelectedCardsMessage {
    private List<Integer> cardsSelected;
}
