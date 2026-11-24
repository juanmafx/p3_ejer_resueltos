package io.github.juanmafx.p3exercises.domain.tournament;

import io.github.juanmafx.p3exercises.domain.statistics.WilsonInterval;

public record TitleOdds(String team, int titles, double probability, WilsonInterval interval95) {
}
