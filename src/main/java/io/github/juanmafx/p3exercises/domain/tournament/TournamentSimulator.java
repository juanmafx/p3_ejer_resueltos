package io.github.juanmafx.p3exercises.domain.tournament;

import io.github.juanmafx.p3exercises.domain.statistics.WilsonInterval;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SplittableRandom;

public final class TournamentSimulator {

    private TournamentSimulator() {
    }

    public static List<TitleOdds> estimateTitleOdds(SimulationConfig config) {
        List<String> names = config.teams().stream().map(TeamStrength::team).toList();
        Map<String, Double> rates = new HashMap<>();
        config.teams().forEach(team -> rates.put(team.team(), team.goalRate()));
        List<Fixture> fixtures = RoundRobinScheduler.doubleRoundRobin(names).stream()
                .flatMap(List::stream)
                .toList();
        Map<String, Integer> titles = new LinkedHashMap<>();
        names.forEach(name -> titles.put(name, 0));
        SplittableRandom root = new SplittableRandom(config.seed());
        for (int run = 0; run < config.runs(); run++) {
            SplittableRandom random = root.split();
            List<MatchResult> results = new ArrayList<>(fixtures.size());
            for (Fixture fixture : fixtures) {
                double homeRate = Math.min(
                        PoissonSampler.MAX_RATE, rates.get(fixture.home()) * config.homeAdvantage());
                results.add(new MatchResult(
                        fixture.home(),
                        fixture.away(),
                        PoissonSampler.sample(homeRate, random),
                        PoissonSampler.sample(rates.get(fixture.away()), random)));
            }
            titles.merge(champion(names, results, random), 1, Integer::sum);
        }
        return titles.entrySet().stream()
                .map(entry -> toOdds(entry.getKey(), entry.getValue(), config.runs()))
                .sorted(Comparator.comparingInt(TitleOdds::titles).reversed().thenComparing(TitleOdds::team))
                .toList();
    }

    private static String champion(List<String> names, List<MatchResult> results, SplittableRandom random) {
        List<StandingRow> table = Standings.compute(names, results);
        StandingRow leader = table.get(0);
        List<StandingRow> tied = table.stream()
                .filter(row -> Standings.SPORTING_ORDER.compare(row, leader) == 0)
                .toList();
        return tied.get(random.nextInt(tied.size())).team();
    }

    private static TitleOdds toOdds(String team, int titles, int runs) {
        return new TitleOdds(
                team,
                titles,
                titles / (double) runs,
                WilsonInterval.of(titles, runs, WilsonInterval.Z_95_PERCENT));
    }
}
