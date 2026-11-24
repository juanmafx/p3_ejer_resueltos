package io.github.juanmafx.p3exercises.domain.tournament;

import java.util.ArrayList;
import java.util.List;

public final class RoundRobinScheduler {

    private RoundRobinScheduler() {
    }

    public static List<List<Fixture>> singleRoundRobin(List<String> teams) {
        List<String> roster = TeamRoster.validated(teams);
        int slots = roster.size() + roster.size() % 2;
        int rounds = slots - 1;
        List<List<Fixture>> schedule = new ArrayList<>(rounds);
        for (int round = 0; round < rounds; round++) {
            List<Fixture> fixtures = new ArrayList<>(slots / 2);
            for (int pair = 0; pair < slots / 2; pair++) {
                int first = slotOccupant(pair, round, slots);
                int second = slotOccupant(slots - 1 - pair, round, slots);
                if (first < roster.size() && second < roster.size()) {
                    fixtures.add((round + pair) % 2 == 0
                            ? new Fixture(roster.get(first), roster.get(second))
                            : new Fixture(roster.get(second), roster.get(first)));
                }
            }
            schedule.add(List.copyOf(fixtures));
        }
        return List.copyOf(schedule);
    }

    public static List<List<Fixture>> doubleRoundRobin(List<String> teams) {
        List<List<Fixture>> firstLeg = singleRoundRobin(teams);
        List<List<Fixture>> schedule = new ArrayList<>(firstLeg);
        for (List<Fixture> round : firstLeg) {
            schedule.add(round.stream().map(Fixture::reversed).toList());
        }
        return List.copyOf(schedule);
    }

    private static int slotOccupant(int slot, int round, int slots) {
        return slot == 0 ? 0 : 1 + Math.floorMod(slot - 1 + round, slots - 1);
    }
}
