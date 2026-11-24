import sqlite3
import unittest
from pathlib import Path

SCHEMA = Path(__file__).resolve().parents[1] / "schema.sql"


class SchemaTest(unittest.TestCase):
    def setUp(self):
        self.db = sqlite3.connect(":memory:")
        self.db.executescript(SCHEMA.read_text(encoding="utf-8"))
        self.db.execute("PRAGMA foreign_keys = ON")

    def tearDown(self):
        self.db.close()

    def seed_tournament(self):
        self.db.executemany("INSERT INTO teams (team_id, name) VALUES (?, ?)", [(1, "A"), (2, "B"), (3, "C")])
        self.db.execute("INSERT INTO tournaments (tournament_id, name) VALUES (1, 'Cup')")
        self.db.executemany("INSERT INTO tournament_teams VALUES (1, ?)", [(1,), (2,)])

    def test_schema_is_idempotent(self):
        self.db.executescript(SCHEMA.read_text(encoding="utf-8"))

    def test_foreign_key_check_is_clean(self):
        self.seed_tournament()
        self.assertEqual(self.db.execute("PRAGMA foreign_key_check").fetchall(), [])

    def test_grade_bounds(self):
        self.db.execute("INSERT INTO students (student_id, name) VALUES (1, 'Alice')")
        with self.assertRaises(sqlite3.IntegrityError):
            self.db.execute("INSERT INTO student_grades VALUES (1, 1, 11)")

    def test_strict_typing(self):
        self.db.execute("INSERT INTO students (student_id, name) VALUES (1, 'Alice')")
        with self.assertRaises(sqlite3.IntegrityError):
            self.db.execute("INSERT INTO student_grades VALUES (1, 1, 'high')")

    def test_standing_view_uses_exact_threshold(self):
        self.db.executemany("INSERT INTO students (student_id, name) VALUES (?, ?)", [(1, "Alice"), (2, "Bob")])
        self.db.executemany(
            "INSERT INTO student_grades VALUES (?, ?, ?)",
            [(1, 1, 4), (1, 2, 4), (1, 3, 4), (2, 1, 4), (2, 2, 4), (2, 3, 3)],
        )
        rows = dict(self.db.execute("SELECT name, standing FROM student_standings"))
        self.assertEqual(rows, {"Alice": "REGULAR", "Bob": "NOT_REGULAR"})

    def test_team_cannot_play_itself(self):
        self.seed_tournament()
        with self.assertRaises(sqlite3.IntegrityError):
            self.db.execute("INSERT INTO matches VALUES (1, 1, 1, 1, 1, 0, 0)")

    def test_match_requires_enrolled_teams(self):
        self.seed_tournament()
        with self.assertRaises(sqlite3.IntegrityError):
            self.db.execute("INSERT INTO matches VALUES (1, 1, 1, 1, 3, 0, 0)")

    def test_interval_must_contain_probability(self):
        self.db.execute("INSERT INTO simulation_runs (simulation_run_id, seed, run_count, home_advantage) VALUES (1, 42, 10, 1.2)")
        with self.assertRaises(sqlite3.IntegrityError):
            self.db.execute("INSERT INTO simulation_team_results VALUES (1, 'A', 1.5, 5, 0.5, 0.6, 0.7)")

    def test_deleting_run_cascades(self):
        self.db.execute("INSERT INTO simulation_runs (simulation_run_id, seed, run_count, home_advantage) VALUES (1, 42, 10, 1.2)")
        self.db.execute("INSERT INTO simulation_team_results VALUES (1, 'A', 1.5, 5, 0.5, 0.2, 0.8)")
        self.db.execute("DELETE FROM simulation_runs")
        self.assertEqual(self.db.execute("SELECT COUNT(*) FROM simulation_team_results").fetchone()[0], 0)


if __name__ == "__main__":
    unittest.main()
