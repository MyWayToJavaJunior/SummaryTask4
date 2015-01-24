package ua.nure.norkin.SummaryTask4.sorter;

import java.util.Comparator;

import ua.nure.norkin.SummaryTask4.entity.Faculty;

public class FacultySorter {

	public static final Comparator<Faculty> SORT_FACULTIES_BY_NAME = new Comparator<Faculty>() {
		@Override
		public int compare(Faculty obj1, Faculty obj2) {
			return obj1.getName().compareTo(obj2.getName());
		}
	};

	public static final Comparator<Faculty> SORT_FACULTIES_BY_BUDGET_SEATS = new Comparator<Faculty>() {
		@Override
		public int compare(Faculty obj1, Faculty obj2) {
			return Short.compare(obj1.getBudgetSeats(), obj2.getBudgetSeats());
		}
	};

	public static final Comparator<Faculty> SORT_FACULTIES_BY_TOTAL_SEATS = new Comparator<Faculty>() {
		@Override
		public int compare(Faculty obj1, Faculty obj2) {
			return Short.compare(obj1.getTotalSeats(), obj2.getTotalSeats());
		}
	};

}
