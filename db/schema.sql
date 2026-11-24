PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS people (
    person_id   INTEGER PRIMARY KEY,
    first_name  TEXT NOT NULL CHECK (length(trim(first_name)) BETWEEN 1 AND 100),
    last_name   TEXT NOT NULL CHECK (length(trim(last_name)) BETWEEN 1 AND 100),
    birth_year  INTEGER NOT NULL CHECK (birth_year BETWEEN 1800 AND 9999),
    height_m    REAL CHECK (height_m IS NULL OR (height_m > 0 AND height_m <= 3)),
    weight_kg   REAL CHECK (weight_kg IS NULL OR (weight_kg > 0 AND weight_kg <= 700))
) STRICT;

CREATE TABLE IF NOT EXISTS vehicles (
    vehicle_id   INTEGER PRIMARY KEY,
    make         TEXT NOT NULL CHECK (length(trim(make)) BETWEEN 1 AND 100),
    model_year   INTEGER NOT NULL CHECK (model_year BETWEEN 1886 AND 9999),
    price_cents  INTEGER NOT NULL DEFAULT 0 CHECK (price_cents >= 0),
    mileage_km   INTEGER NOT NULL DEFAULT 0 CHECK (mileage_km >= 0)
) STRICT;

CREATE TABLE IF NOT EXISTS students (
    student_id  INTEGER PRIMARY KEY,
    name        TEXT NOT NULL CHECK (length(trim(name)) BETWEEN 1 AND 100)
) STRICT;

CREATE TABLE IF NOT EXISTS student_grades (
    student_id  INTEGER NOT NULL REFERENCES students (student_id) ON DELETE CASCADE,
    sequence    INTEGER NOT NULL CHECK (sequence BETWEEN 1 AND 64),
    grade       INTEGER NOT NULL CHECK (grade BETWEEN 0 AND 10),
    PRIMARY KEY (student_id, sequence)
) STRICT, WITHOUT ROWID;

CREATE VIEW IF NOT EXISTS student_standings AS
SELECT s.student_id,
       s.name,
       COUNT(g.grade) AS grade_count,
       AVG(g.grade * 1.0) AS average_grade,
       CASE WHEN SUM(g.grade) >= 4 * COUNT(g.grade) THEN 'REGULAR' ELSE 'NOT_REGULAR' END AS standing
FROM students AS s
JOIN student_grades AS g ON g.student_id = s.student_id
GROUP BY s.student_id, s.name;

CREATE TABLE IF NOT EXISTS teams (
    team_id  INTEGER PRIMARY KEY,
    name     TEXT NOT NULL UNIQUE CHECK (length(trim(name)) BETWEEN 1 AND 100)
) STRICT;

CREATE TABLE IF NOT EXISTS tournaments (
    tournament_id  INTEGER PRIMARY KEY,
    name           TEXT NOT NULL UNIQUE CHECK (length(trim(name)) BETWEEN 1 AND 100)
) STRICT;

CREATE TABLE IF NOT EXISTS tournament_teams (
    tournament_id  INTEGER NOT NULL REFERENCES tournaments (tournament_id) ON DELETE CASCADE,
    team_id        INTEGER NOT NULL REFERENCES teams (team_id) ON DELETE RESTRICT,
    PRIMARY KEY (tournament_id, team_id)
) STRICT, WITHOUT ROWID;

CREATE TABLE IF NOT EXISTS matches (
    match_id       INTEGER PRIMARY KEY,
    tournament_id  INTEGER NOT NULL,
    round_number   INTEGER NOT NULL CHECK (round_number >= 1),
    home_team_id   INTEGER NOT NULL,
    away_team_id   INTEGER NOT NULL,
    home_goals     INTEGER NOT NULL CHECK (home_goals BETWEEN 0 AND 999),
    away_goals     INTEGER NOT NULL CHECK (away_goals BETWEEN 0 AND 999),
    CHECK (home_team_id <> away_team_id),
    UNIQUE (tournament_id, round_number, home_team_id, away_team_id),
    FOREIGN KEY (tournament_id, home_team_id)
        REFERENCES tournament_teams (tournament_id, team_id) ON DELETE CASCADE,
    FOREIGN KEY (tournament_id, away_team_id)
        REFERENCES tournament_teams (tournament_id, team_id) ON DELETE CASCADE
) STRICT;

CREATE INDEX IF NOT EXISTS idx_matches_home_team ON matches (tournament_id, home_team_id);
CREATE INDEX IF NOT EXISTS idx_matches_away_team ON matches (tournament_id, away_team_id);

CREATE TABLE IF NOT EXISTS simulation_runs (
    simulation_run_id  INTEGER PRIMARY KEY,
    seed               INTEGER NOT NULL,
    run_count          INTEGER NOT NULL CHECK (run_count BETWEEN 1 AND 1000000),
    home_advantage     REAL NOT NULL CHECK (home_advantage > 0 AND home_advantage <= 3),
    created_at         TEXT NOT NULL DEFAULT (strftime('%Y-%m-%dT%H:%M:%SZ', 'now'))
) STRICT;

CREATE TABLE IF NOT EXISTS simulation_team_results (
    simulation_run_id  INTEGER NOT NULL REFERENCES simulation_runs (simulation_run_id) ON DELETE CASCADE,
    team_name          TEXT NOT NULL CHECK (length(trim(team_name)) BETWEEN 1 AND 100),
    goal_rate          REAL NOT NULL CHECK (goal_rate > 0 AND goal_rate <= 10),
    titles             INTEGER NOT NULL CHECK (titles >= 0),
    probability        REAL NOT NULL CHECK (probability BETWEEN 0 AND 1),
    ci95_lower         REAL NOT NULL CHECK (ci95_lower BETWEEN 0 AND 1),
    ci95_upper         REAL NOT NULL CHECK (ci95_upper BETWEEN 0 AND 1),
    CHECK (ci95_lower <= probability AND probability <= ci95_upper),
    PRIMARY KEY (simulation_run_id, team_name)
) STRICT, WITHOUT ROWID;
